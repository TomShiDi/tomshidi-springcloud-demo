package com.tomshidi.demo.controller;

import com.tomshidi.base.encrypt.annotation.Encrypt;
import com.tomshidi.base.enums.TestEnum;
import com.tomshidi.demo.dto.AccountEntity;
import com.tomshidi.demo.interceptor.ServerMapInterceptor;
import com.tomshidi.demo.threadpool.DefaultThreadPool;
import com.tomshidi.demo.threads.DemoThread;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author tomshidi
 * @date 2021/10/11 15:44
 */
@RestController
@RequestMapping("/index")
public class IndexController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    private RestTemplate restTemplate;

    @Value("#{'${spring.banner.image}'.split(',')}")
    private List<String> bannerList;

    @Autowired(required = false)
    private ServerMapInterceptor serverMapInterceptor;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        if (serverMapInterceptor != null) {
            List<ClientHttpRequestInterceptor> interceptors = this.restTemplate.getInterceptors();
            interceptors.add(serverMapInterceptor);
            this.restTemplate.setInterceptors(interceptors);
        }
    }

    @RequestMapping("/longTimeRequest.do")
    public String longTimeRequest(@RequestParam(name = "timeout", required = false, defaultValue = "10") Integer timeout) throws InterruptedException {
        LOGGER.info("开始执行睡眠，时间：{}S", timeout);
        TimeUnit.SECONDS.sleep(timeout);
        LOGGER.info("睡眠结束");
        return "success";
    }

    @RequestMapping("/upload.do")
    public String handleUpload(@RequestPart(name = "file") MultipartFile multipartFile,
                               @RequestPart(name = "fileName") String fileName) {
        return String.format("文件名：%s，文件大小：%d", fileName, multipartFile.getSize());
    }

    @RequestMapping("/complex.do")
    public String complexObjectResolve(@RequestBody AccountEntity accountEntity,
                                       @RequestParam(name = "age") Integer age) {
        return accountEntity.toString();
    }

    @RequestMapping("/launch.do")
    public String launch(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(100000);
//        PostMethod postMethod = new PostMethod("http://localhost:8080/index/forInnerRequest.do");
        PostMethod postMethod = new PostMethod("http://127.0.0.1:8080/index/image/D%3A%2F%2F%E6%B5%8B%E8%AF%95%2F%2F100071175850.jpeg_files/15/51_28.jpeg");
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            postMethod.addRequestHeader("Cookie", String.format("%s=%s", cookie.getName(), cookie.getValue()));
        }
        int status = httpClient.executeMethod(postMethod);
//        request.getSession().setAttribute("name", "tohka");
//        request.getRequestDispatcher("/index/forInnerRequest.do").forward(request, response);
//        response.sendRedirect("/index/forInnerRedirect.do");
        return "执行完成: " + status;
    }

    @RequestMapping("/forInnerRequest.do")
    public String forInnerRequest(HttpServletRequest request) {
        LOGGER.info("Headers: {}", request.getHeader("Cookie"));
        return "none";
    }

    @RequestMapping("/forInnerRedirect.do")
    public String forInnerRedirect(HttpServletRequest request) {
        return "redirected";
    }

    @RequestMapping("/submitTask.do")
    public String submitTask() throws InterruptedException {
        DefaultThreadPool.execute(new DemoThread("2", 1));
        return "OK";
    }

    @RequestMapping("/shutdownPool.do")
    public String shutdownPool() {
        DefaultThreadPool.shutdown();
        return "OK";
    }

    @RequestMapping("/microInvoke.do")
    public ResponseEntity<String> microInvoke() {
        String url = "http://wxqyh/wxqyh/api/mytodoApiCtl/ajaxSearchTodoPage.do";
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        return forEntity;
    }
//    @RequestMapping(value = "/image/{filepath:.*?files}/{num1:\\d+}/{num2:\\d+}_{num3:\\d+}.jpeg")
//    public String regexUrlPattern(@PathVariable(name = "filepath") String filepath,
//                                  @PathVariable(name = "num1") Integer num1,
//                                  @PathVariable(name = "num2") Integer num2,
//                                  @PathVariable(name = "num3") Integer num3) {
//        return String.format("filePath=%s\nnum1=%s\nnum2=%s\nnum3=%s\n", filepath, num1, num2, num3);
//    }

    @RequestMapping(value = "/image/**")
    public String regexUrlPattern(HttpServletRequest request) throws UnsupportedEncodingException {

        Pattern pattern = Pattern.compile(".*?/image/(.*?files)/(\\d+)/(\\d+)_(\\d+).jpeg");
        Matcher matcher = pattern.matcher(request.getRequestURI());
        if (!matcher.matches()) {
            return "参数错误";
        }
        String filePath = URLDecoder.decode(matcher.group(1), "utf-8");
        String num1 = matcher.group(2);
        String num2 = matcher.group(3);
        String num3 = matcher.group(4);

        return String.format("filePath=%s\nnum1=%s\nnum2=%s\nnum3=%s\n", filePath, num1, num2, num3);
    }

    @GetMapping("/enum")
    public String enumParamAutoConstruct(@RequestParam TestEnum testEnum,
                                         @RequestParam String name) {
        return testEnum.getParam();
    }

    @PostMapping("/encrypt1")
    public String encrypt1(@RequestBody @Encrypt AccountEntity accountEntity,
                           @RequestParam @Encrypt String name) {
        return String.format("name: %s\n%s", name, accountEntity);
    }

    @PostMapping("/encrypt2")
    public String encrypt2(@RequestBody @Encrypt(targetType = AccountEntity.class) Map<String, Object> accountEntity,
                           @RequestParam @Encrypt String name) {
        return String.format("name: %s\n%s", name, accountEntity);
    }

    @PostMapping("/encrypt3")
    public String encrypt3(@RequestBody @Encrypt(targetName = "number", targetType = AccountEntity.class) Set<String> accountList,
                           @RequestParam String name) {
        return String.format("name: %s\n%s", name, accountList);
    }

    @PostMapping("/decrypt1")
    @Encrypt(enOrDecrypt = false)
    public AccountEntity decrypt1(@RequestBody @Encrypt AccountEntity accountEntity,
                                  @RequestParam @Encrypt String name) {
        LOGGER.info("入参值为：{}", String.format("name: %s\n%s", name, accountEntity));
        return accountEntity;
    }

    @PostMapping("/decrypt2")
    @Encrypt(targetType = AccountEntity.class, enOrDecrypt = false)
    public Map<String, Object> decrypt2(@RequestBody @Encrypt(targetType = AccountEntity.class) Map<String, Object> accountEntity,
                                        @RequestParam @Encrypt String name) {
        LOGGER.info("入参值为：{}", String.format("name: %s\n%s", name, accountEntity));
        return accountEntity;
    }

    @PostMapping("/decrypt3")
    @Encrypt(targetName = "number", targetType = AccountEntity.class, enOrDecrypt = false)
    public Set<String> decrypt3(@RequestBody @Encrypt(targetName = "number", targetType = AccountEntity.class) Set<String> accountList,
                                @RequestParam String name) {
        LOGGER.info("入参值为：{}", String.format("name: %s\n%s", name, accountList));
        return accountList;
    }
}
