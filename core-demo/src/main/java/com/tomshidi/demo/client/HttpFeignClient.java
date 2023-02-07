package com.tomshidi.demo.client;

import com.tomshidi.demo.config.DisableLoadBalanceConfiguration;
import feign.Body;
import feign.Headers;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.Map;

/**
 * @author tomshidi
 * @date 2023/1/11 16:09
 */
@FeignClient(name = "httpFeignClient", fallback = HttpFeignClientFallback.class, configuration = DisableLoadBalanceConfiguration.class)
public interface HttpFeignClient {

    @RequestLine("GET")
    String get(URI uri, @QueryMap Map<String, Object> paramMap);

    @RequestLine("POST")
    @Body("${body}")
    @Headers("Content-type:application/json;charset=utf-8")
    String post(URI uri, @Param("body") String body);

    @RequestLine("GET")
    Response download(URI uri, @QueryMap Map<String, Object> paramMap);

    @RequestLine("POST")
    @Headers("Content-Type: multipart/form-data")
    String upload(URI uri, @RequestPart MultipartFile multipartFile);
}
