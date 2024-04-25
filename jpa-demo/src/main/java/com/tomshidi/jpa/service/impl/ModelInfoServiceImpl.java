package com.tomshidi.jpa.service.impl;

import com.tomshidi.jpa.constant.BaseExceptionEnums;
import com.tomshidi.jpa.entity.ModelInfo;
import com.tomshidi.jpa.entity.ModelParamInfo;
import com.tomshidi.jpa.exception.ModelException;
import com.tomshidi.jpa.model.dto.ModelInfoDto;
import com.tomshidi.jpa.model.dto.ModelParamInfoDto;
import com.tomshidi.jpa.repository.ModelInfoRepo;
import com.tomshidi.jpa.repository.ModelParamInfoRepo;
import com.tomshidi.jpa.service.ModelInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 模型信息操作
 * @author tomshidi
 * @since 2024/4/17 15:20
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class ModelInfoServiceImpl implements ModelInfoService {

    private ModelInfoRepo modelInfoRepo;

    private ModelParamInfoRepo paramInfoRepo;

    @Autowired
    public void setModelInfoRepo(ModelInfoRepo modelInfoRepo) {
        this.modelInfoRepo = modelInfoRepo;
    }

    @Autowired
    public void setParamInfoRepo(ModelParamInfoRepo paramInfoRepo) {
        this.paramInfoRepo = paramInfoRepo;
    }

    @Override
    public void addModelInfo(ModelInfoDto modelInfoDto) {
        ModelInfo modelInfo = modelInfoRepo.findByModelName(modelInfoDto.getModelName());
        // 重名校验
        if (modelInfo != null) {
            throw new ModelException(BaseExceptionEnums.MODEL_INFO_NAME_ALREADY_EXIST);
        }
        modelInfo = new ModelInfo();
        BeanUtils.copyProperties(modelInfoDto, modelInfo,"paramInfoList");
        String modelId = UUID.randomUUID().toString();
        // TODO 生成id，待优化为雪花id
        modelInfo.setId(modelId);
        modelInfoRepo.save(modelInfo);
        List<ModelParamInfoDto> paramInfoDtoList = modelInfoDto.getParamInfoList();
        // 保存模型参数
        if (!ObjectUtils.isEmpty(paramInfoDtoList)) {
            List<ModelParamInfo> paramInfos = paramInfoDtoList.stream().map(item -> {
                ModelParamInfo info = new ModelParamInfo();
                BeanUtils.copyProperties(item, info);
                // TODO 生成id，待优化为雪花id
                info.setId(UUID.randomUUID().toString());
                info.setModelId(modelId);
                return info;
            }).collect(Collectors.toList());
            paramInfoRepo.saveAll(paramInfos);
        }
    }

    @Override
    public void deleteById(String id) {
        // 使用了@OneToMany注解，删除模型会自动删除模型参数
        modelInfoRepo.deleteById(id);
        // 删除模型绑定的参数
//        paramInfoRepo.deleteByModelId(id);
    }

    @Override
    public void deleteByDicIds(List<String> dicIds) {
        modelInfoRepo.deleteByDicIdIn(dicIds);
    }

    @Override
    public ModelInfoDto update(ModelInfoDto modelInfoDto) {
        ModelInfo modelInfo = modelInfoRepo.findById(modelInfoDto.getId())
                .orElseThrow(() -> new ModelException(BaseExceptionEnums.MODEL_INFO_NOT_FOUND));
        // 模型名重复时抛出异常
        if (!modelInfo.getModelName().equals(modelInfoDto.getModelName())) {
            ModelInfo t = modelInfoRepo.findByModelName(modelInfoDto.getModelName());
            if (t != null) {
                throw new ModelException(BaseExceptionEnums.MODEL_INFO_NAME_ALREADY_EXIST);
            }
        }
        BeanUtils.copyProperties(modelInfoDto, modelInfo);
        modelInfoRepo.save(modelInfo);
        return modelInfoDto;
    }

    @Override
    public Page<ModelInfoDto> findModelByDicId(String dicId, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<ModelInfo> page = modelInfoRepo.findByDicId(dicId, pageable);
        List<ModelInfoDto> dtoList = page.stream()
                .map(item -> {
                    ModelInfoDto dto = new ModelInfoDto();
                    BeanUtils.copyProperties(item, dto);
                    return dto;
                }).collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

    @Override
    public List<ModelInfoDto> findModelByName(String name) {
        List<ModelInfo> modelInfoList = modelInfoRepo.findByModelNameLike("%" + name + "%");
        if (ObjectUtils.isEmpty(modelInfoList)) {
            return new ArrayList<>();
        }
        return modelInfoList.stream()
                .map(item -> {
                    ModelInfoDto dto = new ModelInfoDto();
                    BeanUtils.copyProperties(item, dto);
                    return dto;
                }).collect(Collectors.toList());
    }
}
