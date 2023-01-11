package com.tomshidi.demo.client;

import feign.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.Map;

/**
 * @author tangshili
 * @date 2023/1/11 16:22
 */
public class HttpFeignClientFallback implements HttpFeignClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpFeignClientFallback.class);

    private String buildHystrixException() {
        LOGGER.error("服务调用异常，触发熔断");
        throw new RuntimeException();
    }

    @Override
    public String get(URI uri, Map<String, Object> paramMap) {
        return buildHystrixException();
    }

    @Override
    public String post(URI uri, String body) {
        return buildHystrixException();
    }

    @Override
    public Response download(URI uri, Map<String, Object> paramMap) {
        buildHystrixException();
        return null;
    }

    @Override
    public String upload(URI uri, MultipartFile multipartFile) {
        return buildHystrixException();
    }
}
