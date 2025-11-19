package com.tomshidi.jpa.service.impl;

import com.tomshidi.jpa.constant.BaseExceptionEnums;
import com.tomshidi.jpa.entity.ModelParamInfo;
import com.tomshidi.jpa.exception.ModelException;
import com.tomshidi.jpa.model.dto.ModelParamInfoDto;
import com.tomshidi.jpa.repository.ModelParamInfoRepo;
import com.tomshidi.jpa.service.ModelParamService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tomshidi
 * @since 2024/4/17 16:16
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class ModelParamServiceImpl implements ModelParamService {

    private ModelParamInfoRepo paramInfoRepo;

    @Autowired
    public void setParamInfoRepo(ModelParamInfoRepo paramInfoRepo) {
        this.paramInfoRepo = paramInfoRepo;
    }

    @Override
    public void addParam(ModelParamInfoDto dto) {
        ModelParamInfo paramInfo = new ModelParamInfo();
        BeanUtils.copyProperties(dto, paramInfo);
        paramInfoRepo.save(paramInfo);
    }

    @Override
    public void addParams(List<ModelParamInfoDto> dtos) {
        List<ModelParamInfo> paramInfoList = dtos.stream().map(item -> {
            ModelParamInfo info = new ModelParamInfo();
            BeanUtils.copyProperties(item, info);
            return info;
        }).collect(Collectors.toList());
        paramInfoRepo.saveAll(paramInfoList);
    }

    @Override
    public void deleteById(String id) {
        paramInfoRepo.deleteById(id);
    }

    @Override
    public void deleteByModelId(String modelId) {
        paramInfoRepo.deleteByModelId(modelId);
    }

    @Override
    public ModelParamInfoDto update(ModelParamInfoDto modelParamInfoDto) {
        ModelParamInfo paramInfo = paramInfoRepo.findById(modelParamInfoDto.getId())
                .orElseThrow(() -> new ModelException(BaseExceptionEnums.MODEL_PARAM_NOT_FOUND));
        BeanUtils.copyProperties(modelParamInfoDto, paramInfo);
        paramInfoRepo.save(paramInfo);
        return modelParamInfoDto;
    }

    @Override
    public List<ModelParamInfoDto> findParamByModelId(String modelId) {
        List<ModelParamInfo> paramInfoList = paramInfoRepo.findByModelId(modelId);
        if (ObjectUtils.isEmpty(paramInfoList)) {
            return new ArrayList<>();
        }
        return paramInfoList.stream()
                .map(item -> {
                    ModelParamInfoDto dto = new ModelParamInfoDto();
                    BeanUtils.copyProperties(item, dto);
                    return dto;
                }).collect(Collectors.toList());
    }
}
