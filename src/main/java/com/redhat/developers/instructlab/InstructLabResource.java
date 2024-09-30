package com.redhat.developers.instructlab;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/instructlab")
public class InstructLabResource {

    @Inject
    AssistantForInstructLab assistant;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String prompt(@QueryParam("message") String message) {
        return assistant.chat(message);
    }
}
