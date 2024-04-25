package com.tomshidi.jpa.service.impl;

import com.tomshidi.base.constant.ModuleCode;
import com.tomshidi.jpa.constant.BaseExceptionEnums;
import com.tomshidi.jpa.entity.ModelDicInfo;
import com.tomshidi.jpa.exception.ModelException;
import com.tomshidi.jpa.model.dto.ModelDicInfoDto;
import com.tomshidi.jpa.repository.ModelDicInfoRepo;
import com.tomshidi.jpa.service.ModelDicService;
import com.tomshidi.jpa.service.TreePathOperator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author tomshidi
 * @since 2024/4/17 14:30
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class ModelDicServiceImpl implements ModelDicService {

    private TreePathOperator treePathOperator;

    private ModelDicInfoRepo dicInfoRepo;

    @Autowired
    public void setTreePathOperator(TreePathOperator treePathOperator) {
        this.treePathOperator = treePathOperator;
    }

    @Autowired
    public void setDicInfoRepo(ModelDicInfoRepo dicInfoRepo) {
        this.dicInfoRepo = dicInfoRepo;
    }

    @Override
    public void addDic(String dicName, String parentId) {
        ModelDicInfo dicInfo = dicInfoRepo.findByDicName(dicName);
        if (dicInfo != null) {
            throw new ModelException(BaseExceptionEnums.MODEL_DIC_NAME_ALREADY_EXIST);
        }
        ModelDicInfo modelDicInfo = new ModelDicInfo();
        String dicId = UUID.randomUUID().toString();
        modelDicInfo.setId(dicId);
        modelDicInfo.setDicName(dicName);
        if (ObjectUtils.isEmpty(parentId)) {
            treePathOperator.addNode(dicId, ModuleCode.MODEL_TYPE);
        } else {
            treePathOperator.addNode(dicId, ModuleCode.MODEL_TYPE, parentId);
        }
        dicInfoRepo.save(modelDicInfo);
    }

    @Override
    public List<String> deleteById(String id) {
        // 删除节点关联
        List<String> deletedNodeIds = treePathOperator.deleteNode(id);
        // 删除目录信息
        dicInfoRepo.deleteByIdIn(deletedNodeIds);
        return deletedNodeIds;
    }

    @Override
    public ModelDicInfoDto update(ModelDicInfoDto modelDicInfoDto) {
        ModelDicInfo dicInfo = dicInfoRepo
                .findById(modelDicInfoDto.getId())
                .orElseThrow(() -> new ModelException(BaseExceptionEnums.MODEL_DIC_NOT_FOUND));
        // 目录名重复时抛出异常
        if (!dicInfo.getDicName().equals(modelDicInfoDto.getDicName())) {
            ModelDicInfo t = dicInfoRepo.findByDicName(modelDicInfoDto.getDicName());
            if (t != null) {
                throw new ModelException(BaseExceptionEnums.MODEL_DIC_NAME_ALREADY_EXIST);
            }
        }
        BeanUtils.copyProperties(modelDicInfoDto, dicInfo);
        dicInfoRepo.save(dicInfo);
        return modelDicInfoDto;
    }

    @Override
    public ModelDicInfoDto findChildrenById(String currDicId) {
        ModelDicInfo currDic = dicInfoRepo.findById(currDicId)
                .orElseThrow(() -> new ModelException(BaseExceptionEnums.MODEL_DIC_NOT_FOUND));
        ModelDicInfoDto dto = new ModelDicInfoDto();
        BeanUtils.copyProperties(currDic, dto);
        List<String> closestChildNodeIds = treePathOperator.findClosestChildNodeIds(currDicId);
        if (ObjectUtils.isEmpty(closestChildNodeIds)) {
            return dto;
        }
        List<ModelDicInfo> childrenDic = dicInfoRepo.findByIdIn(closestChildNodeIds);
        if (ObjectUtils.isEmpty(childrenDic)) {
            return dto;
        }
        List<ModelDicInfoDto> childrenDto = childrenDic.stream().map(item -> {
            ModelDicInfoDto d = new ModelDicInfoDto();
            BeanUtils.copyProperties(item, d);
            return d;
        }).collect(Collectors.toList());
        dto.setChildren(childrenDto);
        return dto;
    }

    @Override
    public Page<ModelDicInfoDto> findDicByNameLike(String dicName, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<ModelDicInfo> page = dicInfoRepo.findByDicNameLike("%" + dicName + "%", pageable);
        List<ModelDicInfoDto> dtoList = page.stream().map(item -> {
            ModelDicInfoDto dto = new ModelDicInfoDto();
            BeanUtils.copyProperties(item, dto);
            return dto;
        }).collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

    @Override
    public List<ModelDicInfoDto> findRootDics() {
        List<String> rootNodeIds = treePathOperator.findRootNodeIds(ModuleCode.MODEL_TYPE);
        List<ModelDicInfo> dicInfoList = dicInfoRepo.findByIdIn(rootNodeIds);
        return dicInfoList.stream()
                .map(item -> {
                    ModelDicInfoDto dto = new ModelDicInfoDto();
                    BeanUtils.copyProperties(item, dto);
                    return dto;
                }).collect(Collectors.toList());
    }

    @Override
    public ModelDicInfoDto findTree(String currNodeId) {
        ModelDicInfo rootDic = dicInfoRepo.findById(currNodeId).orElseThrow(() -> new ModelException(BaseExceptionEnums.MODEL_DIC_NOT_FOUND));
        ModelDicInfoDto rootDicDto = new ModelDicInfoDto();
        BeanUtils.copyProperties(rootDic, rootDicDto);
        Deque<ModelDicInfoDto> deque = new LinkedList<>();
        deque.addLast(rootDicDto);
        while (!deque.isEmpty()) {
            int size = deque.size();
            for (int i = 0; i < size; i++) {
                ModelDicInfoDto parent = deque.pollFirst();
                List<String> childNodeIds = treePathOperator.findClosestChildNodeIds(parent.getId());
                if (!ObjectUtils.isEmpty(childNodeIds)) {
                    List<ModelDicInfo> childDicList = dicInfoRepo.findByIdIn(childNodeIds);
                    List<ModelDicInfoDto> childDicDtoList = childDicList.stream().map(item -> {
                        ModelDicInfoDto dto = new ModelDicInfoDto();
                        BeanUtils.copyProperties(item, dto);
                        return dto;
                    }).collect(Collectors.toList());
                    parent.setChildren(childDicDtoList);
                    childDicDtoList.forEach(deque::addLast);
                }
            }
        }
        return rootDicDto;
    }
}
