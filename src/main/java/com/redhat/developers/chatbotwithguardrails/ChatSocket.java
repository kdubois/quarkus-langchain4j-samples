package com.redhat.developers.chatbotwithguardrails;

import jakarta.websocket.OnOpen;
import io.quarkus.logging.Log;
import io.quarkus.websockets.next.OnTextMessage;
import io.quarkus.websockets.next.WebSocket;

@WebSocket(path = "/chat")
public class ChatSocket {

    private final AssistantForCustomerSupport assistant;

    public ChatSocket(AssistantForCustomerSupport assistant) {
        this.assistant = assistant;
    }

    @OnOpen
    public String onOpen() {
        return "Hello from Miles of Smiles, how can we help you?";
    }

    @OnTextMessage
    public String onMessage(String userMessage){
        try {
            return assistant.chat(userMessage);
        } catch (Exception e) {
            Log.error("Error calling the LLM", e);
            return "Sorry, I am unable to process your request at the moment. Please try again later.";
        }
        
    }
}