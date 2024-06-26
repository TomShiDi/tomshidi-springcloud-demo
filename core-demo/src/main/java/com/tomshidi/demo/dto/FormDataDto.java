package com.tomshidi.demo.dto;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tangshili
 * @since 2024/6/15 16:17
 */
public class FormDataDto {
    private TitleDto title;

    private RecordDto record;

    private ProjectDto project;

    private List<LayerLinkDto> layerLink;

    public void calculate() {
        buildPoiImage();
        if (project != null) {
            project.calculate();
        }
        if (layerLink != null) {
            layerLink.forEach(LayerLinkDto::calculate);
        }
    }

    public void buildPoiImage() {
        if (project != null && project.getLandRange() != null) {
            for (LandRangeDto landRangeDto: project.getLandRange()) {
                landRangeDto.buildPoiImage();
            }
        }
        if (layerLink != null && !layerLink.isEmpty()) {
            List<LayerLinkDto> imageLayerList = layerLink.stream().filter(item -> item.getGeoData() != null).collect(Collectors.toList());
            for (LayerLinkDto layerLinkDto: imageLayerList) {
                layerLinkDto.getGeoData().forEach(GeoDataDto::buildPoiImage);
            }
        }
    }

    public TitleDto getTitle() {
        return title;
    }

    public void setTitle(TitleDto title) {
        this.title = title;
    }

    public RecordDto getRecord() {
        return record;
    }

    public void setRecord(RecordDto record) {
        this.record = record;
    }

    public ProjectDto getProject() {
        return project;
    }

    public void setProject(ProjectDto project) {
        this.project = project;
    }

    public List<LayerLinkDto> getLayerLink() {
        return layerLink;
    }

    public void setLayerLink(List<LayerLinkDto> layerLink) {
        this.layerLink = layerLink;
    }
}
