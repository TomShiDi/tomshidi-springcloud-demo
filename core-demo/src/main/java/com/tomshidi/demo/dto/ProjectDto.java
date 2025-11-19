package com.tomshidi.demo.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * @author tangshili
 * @since 2024/6/15 16:22
 */
public class ProjectDto {
    private String name;

    private String markName;

    private List<LandRangeDto> landRange;

    private String allSum;

    public void calculate() {
        if (landRange == null) {
            return;
        }
        allSum = landRange.stream()
                .map(item -> BigDecimal.valueOf(Double.valueOf(item.getArea())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)
                .setScale(2, RoundingMode.CEILING)
                .toString();
    }

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

    public String getAllSum() {
        return allSum;
    }

    public void setAllSum(String allSum) {
        this.allSum = allSum;
    }
}
