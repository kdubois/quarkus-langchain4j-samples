package com.redhat.developers.instructlab;

import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/instructlab")
public class InstructLabResource {

    @Inject
    AssistantForInstructLab assistant;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String prompt(
            @QueryParam("message")
            @DefaultValue("Create some code that returns a cat image")
            String message) {
        return assistant.chat(message);
    }
}
