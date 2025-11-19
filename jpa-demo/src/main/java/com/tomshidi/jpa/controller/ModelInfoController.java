package com.tomshidi.jpa.controller;

import com.tomshidi.jpa.model.dto.ModelInfoDto;
import com.tomshidi.jpa.service.ModelInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.util.List;

/**
 * @author tomshidi
 * @since 2024/4/17 10:53
 */
@Tag(name = "模型管理")
@Validated
@RequestMapping("/model")
@RestController
public class ModelInfoController {

    private ModelInfoService modelInfoService;

    @Autowired
    public void setModelInfoService(ModelInfoService modelInfoService) {
        this.modelInfoService = modelInfoService;
    }

    @Operation(summary = "添加模型信息")
    @PostMapping("/add")
    public void addModelInfo(@RequestBody ModelInfoDto modelInfoDto) {
        modelInfoService.addModelInfo(modelInfoDto);
    }

    @Operation(summary = "根据id删除模型信息")
    @GetMapping("/delete")
    public void deleteModelById(@RequestParam(name = "modelId") @NotBlank(message = "id不能为空") String modelId) {
        modelInfoService.deleteById(modelId);
    }

    @Operation(summary = "更新模型信息")
    @PostMapping("/update")
    public ModelInfoDto update(@RequestBody ModelInfoDto modelInfoDto) {
        return modelInfoService.update(modelInfoDto);
    }

    @Operation(summary = "根据目录分页查找模型")
    @GetMapping("/find_by_dic_id")
    public Page<ModelInfoDto> findModelByDicId(@RequestParam(name = "dicId")
                                               @NotBlank(message = "目录id不能为空")
                                               String dicId,
                                               @RequestParam(name = "pageIndex", defaultValue = "1")
                                               @Min(value = 1L, message = "页码不能小于1")
                                               Integer pageIndex,
                                               @RequestParam(name = "pageSize", defaultValue = "10")
                                               @Positive(message = "分页大小需为正数")
                                               Integer pageSize) {
        return modelInfoService.findModelByDicId(dicId, pageIndex - 1, pageSize);
    }

    @GetMapping("/find_by_name")
    public List<ModelInfoDto> findModelByName(@RequestParam(name = "name")
                                              @NotBlank(message = "模型名不能为空")
                                              String name) {
        return modelInfoService.findModelByName(name);
    }
}
