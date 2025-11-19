package com.tomshidi.demo.controller;

import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.OutputStream;
import java.nio.file.Files;

@RestController
@RequestMapping("/file")
public class FileTransportController {

    private final static Logger LOGGER = LoggerFactory.getLogger(FileTransportController.class);

    /**
     * 使用apache压缩工具，分片传输文件
     * 经过测试，传输600M的文件，内存占用控制在10M内
     * @return
     */
    @GetMapping("/download")
    public ResponseEntity<StreamingResponseBody> downloadLargeFile() {
        // 获取大文件资源
        Resource file = new FileSystemResource("C:\\Users\\Administrator\\Desktop\\Tools.zip");
        StreamingResponseBody responseBody = outputStream -> {

            // 使用GZIP压缩流
            try (OutputStream gzipStream = new GzipCompressorOutputStream(outputStream)) {

                // 将文件内容复制到压缩流中
                Files.copy(file.getFile().toPath(), gzipStream);

                // 关闭压缩流刷新缓冲区
//                gzipStream.close();
            } catch (Exception e) {
                LOGGER.error("下载发生错误：", e);
            }
        };

        // 设置响应头信息
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", file.getFilename() + ".gz");

        return ResponseEntity.ok()
                .headers(headers)
                .body(responseBody);
    }

    @PostMapping("/upload")
    public String upload(MultipartFile file) {
        return file.getOriginalFilename();
    }
}