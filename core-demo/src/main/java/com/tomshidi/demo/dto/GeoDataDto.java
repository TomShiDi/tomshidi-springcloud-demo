package com.tomshidi.demo.dto;

import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.PictureType;
import com.deepoove.poi.data.Pictures;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * @author tangshili
 * @since 2024/6/15 16:23
 */
public class GeoDataDto {
    private String name;

    private String type;

    private List<LayerAreaDto> layerField;

    private String image;

    private PictureRenderData imagePoi;

    private String singleAreaSum;

    public void calculate() {
        if (layerField == null) {
            return;
        }
        layerField.forEach(item -> item.setGeoName(name));
        singleAreaSum = layerField.stream()
                .map(item -> BigDecimal.valueOf(Double.parseDouble(item.getArea())))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO)
                .setScale(2, RoundingMode.CEILING).toString();
        buildPoiImage();
    }

    public void buildPoiImage() {
        if (image != null) {
            this.imagePoi = Pictures.ofBase64(image, PictureType.PNG)
                    .size(80, 25)
                    .create();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<LayerAreaDto> getLayerField() {
        return layerField;
    }

    public void setLayerField(List<LayerAreaDto> layerField) {
        this.layerField = layerField;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
        this.imagePoi = Pictures.ofBase64(image, PictureType.PNG)
                .size(80, 25)
                .create();
    }

    public PictureRenderData getImagePoi() {
        return imagePoi;
    }

    public void setImagePoi(PictureRenderData imagePoi) {
        this.imagePoi = imagePoi;
    }

    public String getSingleAreaSum() {
        return singleAreaSum;
    }

    public void setSingleAreaSum(String singleAreaSum) {
        this.singleAreaSum = singleAreaSum;
    }
}
