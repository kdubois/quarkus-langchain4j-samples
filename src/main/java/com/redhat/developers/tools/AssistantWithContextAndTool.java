package com.redhat.developers.tools;


import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService(tools = EmailService.class)
public interface AssistantWithContextAndTool {

    /**
     * Ask the LLM to create a poem about the given topic.
     *
     * @param topic the topic of the poem
     * @param lines the number of line of the poem
     * @return the poem
     */
    @SystemMessage("You are a professional poet")
    @UserMessage("""
            Write a poem about {topic}. The poem should be {lines} lines long.
            Then send this poem by email using the tool provided in the context.
            """)
    String writeAPoem(String topic, int lines);

}