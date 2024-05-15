package com.redhat.developers;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/email-me-a-poem")
public class EmailMeAPoemResource {

    private final AssistantWithContext service;

    public EmailMeAPoemResource(AssistantWithContext service) {
        this.service = service;
    }

    @GET
    public String emailMeAPoem() {
        return service.writeAPoem("Quarkus", 4);
    }

}