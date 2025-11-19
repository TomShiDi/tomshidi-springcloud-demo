package com.tomshidi.jpa.controller;

import com.tomshidi.jpa.model.dto.ModelDicInfoDto;
import com.tomshidi.jpa.service.ModelDicService;
import com.tomshidi.jpa.service.ModelInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
 * @since 2024/4/18 10:58
 */
@Tag(name = "模型目录管理")
@Validated
@RestController
@RequestMapping("/model_dic")
public class ModelDicController {

    private ModelDicService dicService;

    private ModelInfoService modelInfoService;

    @Autowired
    public void setDicService(ModelDicService dicService) {
        this.dicService = dicService;
    }

    @Autowired
    public void setModelInfoService(ModelInfoService modelInfoService) {
        this.modelInfoService = modelInfoService;
    }

    @Operation(summary = "新增目录")
    @PostMapping("/add")
    public void addDic(@Parameter(description = "目录名", required = true)
                       @RequestParam(name = "dicName")
                       @NotBlank(message = "目录名不能为空") String dicName,
                       @Parameter(description = "父目录id，创建根目录时值为空", required = false)
                       @RequestParam(name = "parentId", defaultValue = "")
                       String parentId) {
        dicService.addDic(dicName, parentId);
    }

    @Operation(summary = "根据id删除目录，会连同子目录以及目录下的模型数据一起删除")
    @GetMapping("/delete")
    public void deleteById(@Parameter(description = "目录id")
                           @RequestParam(name = "id")
                           @NotBlank(message = "目录id不能为空") String id) {
        // 删除当前目录及其子目录
        List<String> deletedDicIds = dicService.deleteById(id);
        // 删除目录下关联的模型
        modelInfoService.deleteByDicIds(deletedDicIds);
    }

    @Operation(summary = "更新目录信息")
    @PostMapping("/update")
    public ModelDicInfoDto update(@RequestBody ModelDicInfoDto modelDicInfoDto) {
        return dicService.update(modelDicInfoDto);
    }

    @Operation(summary = "查找当前目录及其子目录信息")
    @GetMapping("/find_children_by_id")
    public ModelDicInfoDto findChildrenById(@Parameter(description = "目录id")
                                            @RequestParam(name = "currDicId")
                                            @NotBlank(message = "目录id不能为空") String currDicId) {
        return dicService.findChildrenById(currDicId);
    }

    @Operation(summary = "根据目录名模糊查找目录列表")
    @GetMapping("/find_by_name_like")
    public Page<ModelDicInfoDto> findDicByNameLike(@Parameter(description = "要查找的目录名，模糊查询")
                                                   @NotBlank(message = "查询条件不能为空")
                                                   @RequestParam(name = "dicName")
                                                   String dicName,
                                                   @RequestParam(name = "pageIndex", defaultValue = "1")
                                                   @Min(value = 1L, message = "页码不能小于1")
                                                   Integer pageIndex,
                                                   @RequestParam(name = "pageSize", defaultValue = "10")
                                                   @Positive(message = "分页大小需为正数")
                                                   Integer pageSize) {
        return dicService.findDicByNameLike(dicName, pageIndex - 1, pageSize);
    }

    @Operation(summary = "查找模型管理根目录")
    @GetMapping("/find_root_dics")
    public List<ModelDicInfoDto> findRootDics() {
        return dicService.findRootDics();
    }

    @Operation(summary = "查找以当前节点为根节点的层级树")
    @GetMapping("/find_tree")
    public ModelDicInfoDto findTree(@Parameter(description = "目录id")
                                    @RequestParam(name = "currDicId")
                                    @NotBlank(message = "目录id不能为空") String currDicId) {
        return dicService.findTree(currDicId);
    }
}
