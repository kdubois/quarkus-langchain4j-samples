package com.redhat.developers.memory;

import static dev.langchain4j.data.message.UserMessage.userMessage;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import dev.langchain4j.chain.ConversationalChain;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.TokenWindowChatMemory;
import dev.langchain4j.model.Tokenizer;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiTokenizer;
import dev.langchain4j.model.output.Response;

@Path("/code")
public class DeveloperResource {

    @Inject
    private ChatLanguageModel model;

    @Inject
    AssistantWithMemory assistant;

    @GET
    @Path("/guess")
    @Produces(MediaType.TEXT_PLAIN)
    public void guessWho() {       

        System.out.println(assistant.chat(1, "Hello, my name is Klaus, and I'm a Doctor"));

        System.out.println(assistant.chat(2, "Hello, my name is Francine, and I'm a Lawyer"));

        System.out.println(assistant.chat(1, "What is my name?"));

        System.out.println(assistant.chat(2, "What is my profession?"));

    }

    @GET
    @Path("/rest")
    @Produces(MediaType.TEXT_PLAIN)
    public void createRestEndpoint() {

        String message1 = "How to write a REST endpoint in Java?";

        System.out.println("[User]: " + message1 + System.lineSeparator());

        String response1 = assistant.chat(1, message1);

        System.out.println("[LLM]: " + response1 + System.lineSeparator());

        String userMessage2 =
                "Create a test of the first point? " +
                        "Be short, 15 lines of code maximum.";

        System.out.println("[User]: " + userMessage2 + System.lineSeparator());

        String response2 = assistant.chat(1, userMessage2);

        System.out.println("[LLM]: " + response2 + System.lineSeparator());

    }

    @GET
    @Path("/k8s")
    @Produces(MediaType.TEXT_PLAIN)
    public void generateKubernetes() {

        ConversationalChain chain = ConversationalChain.builder()
                .chatLanguageModel(model)
                .build();

        String userMessage1 = "Can you give a brief explanation of Kubernetes, 3 lines max?";
        System.out.println("[User]: " + userMessage1 + System.lineSeparator());

        String answer1 = chain.execute(userMessage1);
        System.out.println("[LLM]: " + answer1 + System.lineSeparator());

        String userMessage2 = "Can you give me a YAML example to deploy an application for that?";
        System.out.println("[User]: " + userMessage2 + System.lineSeparator());

        String answer2 = chain.execute(userMessage2);
        System.out.println("[LLM]: " + answer2);

    }
}