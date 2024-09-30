package com.redhat.developers.imagegeneration;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

@Path("/image")
public class ImageGenerationResource {

    @Inject
    AiImageGeneration AI;

    @GET
    public void generateImage(@QueryParam("image") String param) {
        System.out.println(AI.chat(param).content().url());
    }
}
