package com.tomshidi.demo.controller;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.tomshidi.demo.utils.WordToPDFConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author tomshidi
 * @since 2024/1/19 9:29
 */
@RestController
@RequestMapping("/convert")
public class FileConvertController {

    @GetMapping("/pdf")
    public String word2Pdf() throws IOException {
        LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
        Configure config = Configure.builder()
                .bind("applicationDatas", policy).build();
        XWPFTemplate template = XWPFTemplate.compile("/applicationForm.docx", config)
                .render("ssssss");
        template.writeAndClose(Files.newOutputStream(Paths.get("/测试模板导出.docx")));
        File wordFile = new File("/测试模板导出.docx");
        File pdfFile = WordToPDFConverter.word2pdf(wordFile);
        return pdfFile.getPath();
    }
}
