package com.tomshidi.aidemo.text2image.controller;

import com.alibaba.cloud.ai.dashscope.image.DashScopeImageOptions;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/text2image")
public class Text2ImageController {

    private final static String IMAGE_MODEL = "wan2.5-t2i-preview";

    @Resource
    private ImageModel imageModel;

    /**
     * 基于文本生成图片示例
     *
     * @param prompt 用户输入内容
     * @return 模型回复内容
     */
    @GetMapping("/image")
    public String image(@RequestParam(name = "prompt", defaultValue = "超人") String prompt) {
        return imageModel.call(new ImagePrompt(prompt, DashScopeImageOptions.builder().withModel(IMAGE_MODEL).build()))
                .getResult()
                .getOutput()
                .getUrl();
    }
}
