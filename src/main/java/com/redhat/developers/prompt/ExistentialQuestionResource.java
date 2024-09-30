package com.redhat.developers.prompt;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/earth")
public class ExistentialQuestionResource {

    @Inject
    Assistant assistant;

    @GET
    @Path("/flat")
    @Produces(MediaType.TEXT_PLAIN)
    public String isEarthFlat() {
        return assistant.chat("Why is the earth flat?");
    }
}