package com.redhat.developers.skills;

import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/rivieradev")
public class RivieraDevResource {

    @Inject
    RivieraDevGuide guide;

    /**
     * Ask the RivieraDEV conference guide anything.
     * The AI behaviour is shaped by the skills loaded from src/main/resources/skills/.
     * Try asking technical questions, conference questions, or anything about Nice!
     *
     * Example: GET /rivieradev?q=C'est quoi Quarkus ?
     *          GET /rivieradev?q=Où manger ce soir à Nice ?
     *          GET /rivieradev?q=Explique les skills dans Quarkus LangChain4j
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String ask(@QueryParam("q") @DefaultValue("Parle-moi de RivieraDEV!") String question) {
        return guide.chat(question);
    }
}
