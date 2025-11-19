package com.tomshidi.jpa.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * @author tomshidi
 * @since 2024/4/17 10:12
 */
@Schema(title = "模型目录")
public class ModelDicInfoDto {
    @Schema(description = "由后台生成，新增目录时不需要传值")
    private String id;

    @Schema(description = "目录名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String dicName;

    @Schema(description = "子目录信息，新增/修改目录时不需要传参",
            accessMode = Schema.AccessMode.READ_ONLY)
    private List<ModelDicInfoDto> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDicName() {
        return dicName;
    }

    public void setDicName(String dicName) {
        this.dicName = dicName;
    }

    public List<ModelDicInfoDto> getChildren() {
        return children;
    }

    public void setChildren(List<ModelDicInfoDto> children) {
        this.children = children;
    }
}
