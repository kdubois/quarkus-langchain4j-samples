package com.redhat.developers.imagegeneration;

import dev.langchain4j.data.image.Image;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService()
public interface AiImageGeneration { 
    Image generate(String prompt);

    @UserMessage("""
            Describe the given message.
            """)
    String describe(Image image);
}
