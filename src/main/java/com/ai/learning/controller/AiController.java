package com.ai.learning.controller;


import com.ai.learning.config.ChatConfig;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AiController {
    @Autowired
    ChatConfig chatConfig;
    String template ="Instruction: Act as professional copy writer and generate answers make sure it is engaging" +
            "question: {{question}}";

    @GetMapping(value = "/query")
    public String query(@RequestParam("question")String question){
        PromptTemplate promptTemplate=new PromptTemplate(template);
        Map<String,Object> vars=new HashMap<>();
        vars.put("question",question);
        Prompt prompt=promptTemplate.apply(vars);
        ChatLanguageModel chatModel= OpenAiChatModel.builder().apiKey(chatConfig.getApiKey()).modelName(chatConfig.getModelName()).temperature(Double.valueOf(chatConfig.getTemperature())).build();
        return chatModel.generate(prompt.text());
    }
}