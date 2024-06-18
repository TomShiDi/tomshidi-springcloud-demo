package com.tomshidi.demo.dto;

import java.util.List;

/**
 * @author tangshili
 * @since 2024/6/15 16:22
 */
public class ProjectDto {
    private String name;

    private String markName;

    private List<LandRangeDto> landRange;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarkName() {
        return markName;
    }

    public void setMarkName(String markName) {
        this.markName = markName;
    }

    public List<LandRangeDto> getLandRange() {
        return landRange;
    }

    public void setLandRange(List<LandRangeDto> landRange) {
        this.landRange = landRange;
    }
}
