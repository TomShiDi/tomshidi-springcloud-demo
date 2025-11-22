package com.tomshidi.aidemo.text2voice.controller;

import com.alibaba.cloud.ai.dashscope.audio.DashScopeSpeechSynthesisOptions;
import com.alibaba.cloud.ai.dashscope.audio.synthesis.SpeechSynthesisModel;
import com.alibaba.cloud.ai.dashscope.audio.synthesis.SpeechSynthesisPrompt;
import com.alibaba.cloud.ai.dashscope.audio.synthesis.SpeechSynthesisResponse;
import com.alibaba.cloud.ai.dashscope.image.DashScopeImageOptions;
import jakarta.annotation.Resource;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.util.UUID;

@RestController
@RequestMapping("/text2voice")
public class Text2VoiceController {

    /**
     * 语音生成模型
     */
    private final static String VOICE_MODEL = "cosyvoice-v3-plus";

    /**
     * 音色
     */
    private final static String VOICE_TIMBER = "longanhuan";

    @Resource
    private SpeechSynthesisModel speechSynthesisModel;

    /**
     * 基于文本生成语音示例
     *
     * @param msg 用户输入内容
     * @return 模型回复内容
     */
    @GetMapping("/voice")
    public String voice(@RequestParam(name = "msg", defaultValue = "你好，请问有什么能为您服务的？") String msg) {
        String filePath = "D:\\"  + UUID.randomUUID() + ".mp3";
        DashScopeSpeechSynthesisOptions options = DashScopeSpeechSynthesisOptions.builder()
                .model(VOICE_MODEL)
                .voice(VOICE_TIMBER)
                .build();
        SpeechSynthesisResponse response = speechSynthesisModel.call(new SpeechSynthesisPrompt(msg, options));
        ByteBuffer byteBuffer = response.getResult().getOutput().getAudio();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteBuffer.array());
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)){
            IOUtils.copy(byteArrayInputStream, fileOutputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(byteArrayInputStream);
        }
        return filePath;
    }
}
