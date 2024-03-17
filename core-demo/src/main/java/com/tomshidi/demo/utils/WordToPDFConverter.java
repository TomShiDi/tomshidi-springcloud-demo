package com.tomshidi.demo.utils;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author tangshili
 * @since 2023/12/11 13:59
 */
public class WordToPDFConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(WordToPDFConverter.class);

    public static File word2pdf(File wordFile) throws IOException {
        FileInputStream inputStream = new FileInputStream(wordFile);
        XWPFDocument document = new XWPFDocument(inputStream);
        PdfOptions options = PdfOptions.create();
        options.fontEncoding("Identity-H");
        LOGGER.info("使用编码为：{}", options.getFontEncoding());
        String wordFilePath = wordFile.getPath();
        String pdfFilePath = wordFilePath.substring(0, wordFilePath.lastIndexOf(".")) + ".pdf";
        File pdfFile = new File(pdfFilePath);
        FileOutputStream outputStream = new FileOutputStream(pdfFile);
        PdfConverter.getInstance().convert(document, outputStream, options);
        return pdfFile;
    }

    public static void main(String[] args) throws IOException {
        LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
        Configure config = Configure.builder()
                .bind("applicationDatas", policy).build();
        XWPFTemplate template = XWPFTemplate.compile("E:\\applicationForm.docx", config)
                .render("ssssss");
        template.writeAndClose(Files.newOutputStream(Paths.get("E:\\测试模板导出.docx")));
        File wordFile = new File("E:\\测试模板导出.docx");
        File pdfFile = word2pdf(wordFile);
    }
}