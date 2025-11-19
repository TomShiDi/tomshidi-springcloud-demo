package com.tomshidi.demo.dto;

import com.deepoove.poi.data.PictureRenderData;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tangshili
 * @since 2024/6/15 16:23
 */
public class LayerLinkDto {
    private String name;

    private List<GeoDataDto> geoData;

    private List<GeoNameAreaDto> geoNameAreas;

    private List<GeoNameImagePoiDto> geoNameImagePois;

    private String allAreaSum;

    public void calculate() {
        if (geoData == null) {
            return;
        }
        geoData.forEach(GeoDataDto::calculate);
        geoNameAreas = geoData.stream().map(geo -> {
            GeoNameAreaDto nameAreaDto = new GeoNameAreaDto();
            nameAreaDto.setGeoName(geo.getName());
            nameAreaDto.setGeoArea(geo.getSingleAreaSum());
            return nameAreaDto;
        }).collect(Collectors.toList());
        geoNameImagePois = geoData.stream().map(geo -> {
            GeoNameImagePoiDto imagePoiDto = new GeoNameImagePoiDto();
            imagePoiDto.setImageName(geo.getName());
            imagePoiDto.setImagePoi(geo.getImagePoi());
            return imagePoiDto;
        }).collect(Collectors.toList());
        allAreaSum = geoData.stream()
                .map(geo -> BigDecimal.valueOf(Double.parseDouble(geo.getSingleAreaSum())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)
                .setScale(2, RoundingMode.CEILING)
                .toString();
    }

    public static class GeoNameAreaDto {
        private String geoName;
        private String geoArea;

        public String getGeoName() {
            return geoName;
        }

        public void setGeoName(String geoName) {
            this.geoName = geoName;
        }

        public String getGeoArea() {
            return geoArea;
        }

        public void setGeoArea(String geoArea) {
            this.geoArea = geoArea;
        }
    }

    public static class GeoNameImagePoiDto {
        private PictureRenderData imagePoi;

        private String imageName;

        public PictureRenderData getImagePoi() {
            return imagePoi;
        }

        public void setImagePoi(PictureRenderData imagePoi) {
            this.imagePoi = imagePoi;
        }

        public String getImageName() {
            return imageName;
        }

        public void setImageName(String imageName) {
            this.imageName = imageName;
        }
    }

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

    public List<GeoNameAreaDto> getGeoNameAreas() {
        return geoNameAreas;
    }

    public void setGeoNameAreas(List<GeoNameAreaDto> geoNameAreas) {
        this.geoNameAreas = geoNameAreas;
    }

    public List<GeoNameImagePoiDto> getGeoNameImagePois() {
        return geoNameImagePois;
    }

    public void setGeoNameImagePois(List<GeoNameImagePoiDto> geoNameImagePois) {
        this.geoNameImagePois = geoNameImagePois;
    }

    public String getAllAreaSum() {
        return allAreaSum;
    }

    public void setAllAreaSum(String allAreaSum) {
        this.allAreaSum = allAreaSum;
    }
}
