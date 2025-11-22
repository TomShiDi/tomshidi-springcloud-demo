package com.tomshidi.aidemo.structuredoutput.controller;

import com.tomshidi.aidemo.structuredoutput.record.StudentRecord;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * Structured Output 示例 Controller
 */
@RestController
@RequestMapping("/structuredOutput")
public class StructuredOutputController {

    @Resource(name = "deepseekChatClient")
    private ChatClient deepseekClient;
    @Resource(name = "qwenChatClient")
    private ChatClient qwenClient;

    /**
     * Deepseek 模型结构化输出示例
     *
     * @param name  用户姓名
     * @param email 用户邮箱
     * @return 学生信息记录
     */
    @GetMapping("/chat")
    public StudentRecord chat(@RequestParam(name = "name") String name,
                              @RequestParam(name = "email") String email) {
        String template = """
                学号1001，我叫{name}，大学专业是计算机科学与技术，电子邮箱是{email}。
                """;
        return qwenClient.prompt()
                .user(promptUserSpec -> promptUserSpec.text(template)
                        .param("name", name)
                        .param("email", email)
                )
                .call()
                .entity(StudentRecord.class);
    }
}
