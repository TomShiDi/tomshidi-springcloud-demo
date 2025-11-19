package com.tomshidi.demo.dto;

import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.PictureType;
import com.deepoove.poi.data.Pictures;

/**
 * @author tangshili
 * @since 2024/6/15 16:21
 */
public class LandRangeDto {
    private String name;

    private String type;

    private String area;

    private String image;

    private PictureRenderData imagePoi;

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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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
}
