package com.redhat.developers.skills;

import io.quarkiverse.langchain4j.RegisterAiService;
import io.quarkiverse.langchain4j.skills.SkillsSystemMessageProvider;

@RegisterAiService(systemMessageProviderSupplier = SkillsSystemMessageProvider.class)
public interface RivieraDevGuide {

    String chat(String userMessage);
}
