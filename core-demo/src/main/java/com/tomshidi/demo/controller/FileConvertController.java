package com.tomshidi.demo.controller;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.lowagie.text.DocumentException;
import com.tomshidi.demo.dto.FormDataDto;
import com.tomshidi.demo.utils.WordToPDFConverter;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.tomshidi.demo.utils.FormDocxFileExporter.createDocxFile;
import static com.tomshidi.demo.utils.WordToPDFConverter.word2pdf;

/**
 * @author tomshidi
 * @since 2024/1/19 9:29
 */
@RestController
@RequestMapping("/convert")
public class FileConvertController {

    private final static Logger LOGGER = LoggerFactory.getLogger(FileConvertController.class);

    @GetMapping("/pdf")
    public String word2Pdf() throws IOException, DocumentException {
        LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
        Configure config = Configure.builder()
                .bind("applicationDatas", policy).build();
        XWPFTemplate template = XWPFTemplate.compile("/applicationForm.docx", config)
                .render("ssssss");
        template.writeAndClose(Files.newOutputStream(Paths.get("/测试模板导出.docx")));
        File wordFile = new File("/测试模板导出.docx");
        File pdfFile = word2pdf(wordFile);
        return pdfFile.getPath();
    }

    @PostMapping(path = "/export_pdf")
    public ResponseEntity<FileSystemResource> exportPdf(@RequestBody FormDataDto formData, HttpServletResponse response) throws IOException {
        PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource templateResource = resourcePatternResolver.getResource("classpath:templates/一键审图报告模板.docx");
        Path outputFilePath = Paths.get(System.getProperty("user.dir") + File.separator + "one-touch-review" + File.separator + System.currentTimeMillis());
        File docxFile = createDocxFile(formData, templateResource.getInputStream(), outputFilePath);
        File pdfFile = word2pdf(docxFile);
        LOGGER.info("导出的文件名为：{}", pdfFile.getName());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        // chrome浏览器下载文件可能出现：ERR_RESPONSE_HEADERS_MULTIPLE_CONTENT_DISPOSITION，
        // 产生原因：可能是因为文件名中带有英文半角逗号,
        // 解决办法：确保 filename 参数使用双引号包裹[1]
//        headers.add("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(file.getName(), CharsetUtil.UTF_8) + "\"");
        headers.setContentDispositionFormData("attachment", URLEncoder.encode(pdfFile.getName(), StandardCharsets.UTF_8.name()));
        headers.add(HttpHeaders.PRAGMA, "no-cache");
        headers.add(HttpHeaders.EXPIRES, "0");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new FileSystemResource(pdfFile));
    }
}
