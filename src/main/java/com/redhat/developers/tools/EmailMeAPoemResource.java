package com.redhat.developers.tools;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/email-me-a-poem")
public class EmailMeAPoemResource {

    private final AssistantWithContextAndTool service;

    public EmailMeAPoemResource(AssistantWithContextAndTool service) {
        this.service = service;
    }

    @GET
    public String emailMeAPoem() {
        return service.writeAPoem("Quarkus", 4);
    }

}