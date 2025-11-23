package com.tomshidi.aidemo.todomenu.controller;

import com.alibaba.cloud.ai.dashscope.agent.DashScopeAgent;
import com.alibaba.cloud.ai.dashscope.agent.DashScopeAgentOptions;
import com.alibaba.cloud.ai.dashscope.api.DashScopeAgentApi;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/meenuCallAgeng")
public class MenuCallAgentController {
    /**
     * 百炼平台应用ID
     */
    @Value("${spring.ai.dashscope.agent.options.app-id}")
    private String appId;

    private DashScopeAgent dashScopeAgent;

    public MenuCallAgentController(DashScopeAgentApi dashScopeAgentApi) {
        this.dashScopeAgent = new DashScopeAgent(dashScopeAgentApi);
    }

    /**
     * 家常菜推荐，使用阿里云百炼平台Agent能力
     *
     * @param question 用户问题
     * @return 家常菜推荐结果
     */
    @GetMapping("/eat")
    public String eatAgent(@RequestParam(name = "msg", defaultValue = "今天吃什么") String question) {
        DashScopeAgentOptions options = DashScopeAgentOptions.builder().withAppId(appId).build();
        Prompt prompt = new Prompt(question, options);
        return dashScopeAgent.call(prompt).getResult().getOutput().getText();
    }
}
