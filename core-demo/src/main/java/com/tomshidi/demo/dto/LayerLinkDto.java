package com.tomshidi.demo.dto;

import java.util.List;

/**
 * @author tangshili
 * @since 2024/6/15 16:23
 */
public class LayerLinkDto {
    private String name;

    private List<GeoDataDto> geoData;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GeoDataDto> getGeoData() {
        return geoData;
    }

    public void setGeoData(List<GeoDataDto> geoData) {
        this.geoData = geoData;
    }
}
