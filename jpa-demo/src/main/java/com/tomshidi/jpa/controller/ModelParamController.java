package com.tomshidi.jpa.controller;

import com.tomshidi.jpa.service.ModelParamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * @author tomshidi
 * @since 2024/4/18 16:00
 */
@Tag(name = "模型参数管理")
@Validated
@RestController
@RequestMapping("/model_param")
public class ModelParamController {

    private ModelParamService paramService;

    @Autowired
    public void setParamService(ModelParamService paramService) {
        this.paramService = paramService;
    }

    @Operation(description = "根据id删除模型参数")
    @GetMapping("/delete")
    public void deleteById(@RequestParam(name = "id") @NotBlank(message = "id参数不能为空") String id) {
        paramService.deleteById(id);
    }
}
