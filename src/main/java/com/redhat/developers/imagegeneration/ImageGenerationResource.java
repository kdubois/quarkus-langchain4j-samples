package com.redhat.developers.imagegeneration;

import java.net.URI;
import java.nio.file.Paths;

import dev.langchain4j.data.image.Image;
import dev.langchain4j.model.output.Response;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;

@Path("/image")
public class ImageGenerationResource {

    @Inject
    AiImageGeneration AI;

    @GET
    @Produces("image/png")
    public java.nio.file.Path def(@QueryParam("prompt") String prompt) {
        if (prompt == null || prompt.isEmpty()) {
            throw new IllegalArgumentException("you must provide a \"prompt\" query parameter");
        }
        URI fileUri = AI.generate(prompt).url(); // this is saved locally because we have quarkus.langchain4j.openai.image-model.persist=true
        return Paths.get(fileUri);
    }

    
}
