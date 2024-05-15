package com.redhat.developers;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService(/*chatMemoryProviderSupplier = RegisterAiService.BeanChatMemoryProviderSupplier.class*/)
public interface AssistantWithMemory {

    String chat(@MemoryId Integer id, @UserMessage String msg);

}
