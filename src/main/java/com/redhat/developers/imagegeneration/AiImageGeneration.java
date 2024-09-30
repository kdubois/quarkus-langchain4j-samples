package com.redhat.developers.imagegeneration;

import dev.langchain4j.data.image.Image;
import dev.langchain4j.model.output.Response;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService(modelName = "image-generation")
public interface AiImageGeneration {
    Response<Image> chat (String prompt);
}
