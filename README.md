# quarkus-langchain4j-samples

A collection of working code samples showing how to use [Quarkus LangChain4j](https://docs.quarkiverse.io/quarkus-langchain4j/dev/index.html) to talk to language models from a Quarkus application. Each package in `src/main/java` is a self-contained example you can read and run independently.

## Prerequisites

- Java 21+
- Maven 3.9+
- A running LLM — see [Connecting to a model](#connecting-to-a-model) below

## Connecting to a model

The app uses the OpenAI-compatible API. By default it points at [LM Studio](https://lmstudio.ai/) on `http://127.0.0.1:1234/v1` with `granite-4.0-h-small` as the chat model and `nomic-embed-text-v1.5` as the embedding model.

Swap the relevant lines in [`application.properties`](src/main/resources/application.properties) to use something else:

| Provider | What to change |
|---|---|
| **LM Studio** (default) | Already configured — just start LM Studio and load the models |
| **Ollama** | Uncomment the Ollama block and set your model names |
| **Podman Desktop AI Lab** | Uncomment the Podman Desktop block |
| **OpenAI / ChatGPT** | Set `OPENAI_API_KEY` in your environment and uncomment the `gpt-4o` model name |

```properties
# LM Studio (default)
quarkus.langchain4j.openai.base-url=http://127.0.0.1:1234/v1
quarkus.langchain4j.openai.chat-model.model-name=granite-4.0-h-small
```

## Running

```bash
./mvnw quarkus:dev
```

The app starts on <http://localhost:8080>. In dev mode you also get the Quarkus Dev UI at <http://localhost:8080/q/dev/>.

---

## Samples

### 1. Basic prompt — `prompt` package

The simplest possible thing: a plain Java interface that becomes an AI-backed service.

```
GET /earth/flat
```

[`Assistant`](src/main/java/com/redhat/developers/prompt/Assistant.java) is a one-method interface annotated with `@RegisterAiService`. Quarkus generates the implementation at build time. [`ExistentialQuestionResource`](src/main/java/com/redhat/developers/prompt/ExistentialQuestionResource.java) injects it and asks the model a famously bad question. Good starting point if you want to understand the minimum required to call a model.

---

### 2. Conversation memory — `memory` package

Shows how the model can remember context across multiple turns of a conversation.

```
GET /code/guess
GET /code/rest
GET /code/k8s
```

[`AssistantWithMemory`](src/main/java/com/redhat/developers/memory/AssistantWithMemory.java) takes a `@MemoryId` parameter so conversations are kept separate per user. [`DeveloperResource`](src/main/java/com/redhat/developers/memory/DeveloperResource.java) runs several back-and-forth exchanges — for example introducing two users in separate memory slots and then asking each one what they said earlier, or building a multi-turn coding conversation about REST endpoints and Kubernetes. Responses are printed to the application log.

---

### 3. Tool use — `tools` package

Shows how the model can call real Java code during a response.

```
GET /email-me-a-poem
```

[`AssistantWithContextAndTool`](src/main/java/com/redhat/developers/tools/AssistantWithContextAndTool.java) asks the model to write a 4-line poem about Quarkus and send it by email. [`EmailService`](src/main/java/com/redhat/developers/tools/EmailService.java) is a normal CDI bean with a method annotated `@Tool`. The model decides on its own to call it. In dev mode the mail is captured by [Mailpit](https://github.com/axllent/mailpit), which you can browse at <http://localhost:8025>.

---

### 4. Customer support chatbot with guardrails — `chatbotwithguardrails` package

A WebSocket-based chatbot for a fictional car rental company called *Miles of Smiles*, with a prompt injection defence in front of it.

```
ws://localhost:8080/chat
```

Open [`chat-assistant.html`](src/main/resources/META-INF/resources/chat-assistant.html) in a browser or connect with any WebSocket client. The bot knows the company's cancellation policy (loaded from [`miles-of-smiles-terms-of-use.txt`](src/main/resources/catalog/miles-of-smiles-terms-of-use.txt)) and can look up or cancel bookings via [`BookingTools`](src/main/java/com/redhat/developers/chatbotwithguardrails/BookingTools.java).

[`PromptInjectionGuard`](src/main/java/com/redhat/developers/chatbotwithguardrails/PromptInjectionGuard.java) sits in front of every message as an `InputGuardrail`. It passes the message to [`PromptInjectionDetectionService`](src/main/java/com/redhat/developers/chatbotwithguardrails/PromptInjectionDetectionService.java) (a second AI service used purely for classification) and blocks the request if the injection score exceeds 0.7. The session is `@SessionScoped` so each browser tab gets its own memory.

---

### 5. Skills — `skills` package

Shows the `quarkus-langchain4j-skills` extension, which lets you define reusable system-message fragments in plain Markdown files and compose them into an AI service at runtime.

```
GET /rivieradev?q=C'est quoi Quarkus ?
GET /rivieradev?q=Où manger ce soir à Nice ?
```

[`RivieraDevGuide`](src/main/java/com/redhat/developers/skills/RivieraDevGuide.java) uses `SkillsSystemMessageProvider` instead of a static `@SystemMessage`. At startup, Quarkus reads every `SKILL.md` file under `src/main/resources/skills/` and builds the system message from them. Two skills are active:

- **`sunny-disposition`** — instructs the model to respond in French, use HTML formatting, and write with the energy of someone at a conference on the Côte d'Azur.
- **`local-expert`** — tells the model to append a *🌊 Local Tip* about Nice or the French Riviera after every answer, even technical ones.

To add your own behaviour, drop a new directory with a `SKILL.md` inside `src/main/resources/skills/`.

---

### 6. Image generation — `imagegeneration` package

Generates an image from a text description and returns it directly as a PNG.

```
GET /image?prompt=a cat riding a skateboard
```

[`AiImageGeneration`](src/main/java/com/redhat/developers/imagegeneration/AiImageGeneration.java) is an AI service with a `generate` method that returns a LangChain4j `Image`. The image is persisted locally (controlled by `quarkus.langchain4j.openai.image-model.persist=true`) and served back by [`ImageGenerationResource`](src/main/java/com/redhat/developers/imagegeneration/ImageGenerationResource.java). Requires a model that supports image generation — configured as `qwen3-vl-30b` by default.

---

### 7. InstructLab / custom model — `instructlab` package

A minimal endpoint to test responses from a locally fine-tuned or custom model.

```
GET /instructlab?message=Create some code that returns a cat image
```

[`AssistantForInstructLab`](src/main/java/com/redhat/developers/instructlab/AssistantForInstructLab.java) has a system message that tells the model it's a Java developer who likes to over-engineer things. Swap the model name in `application.properties` to point at your own fine-tuned model and use this endpoint to compare its answers against a base model.

---

## Project layout

```
src/main/java/com/redhat/developers/
├── prompt/                   Basic prompt example
├── memory/                   Multi-turn conversation with per-user memory
├── tools/                    Tool use — model calls real Java methods
├── chatbotwithguardrails/    WebSocket chatbot with prompt injection detection
├── skills/                   Composable system message skills
├── imagegeneration/          Image generation
└── instructlab/              Custom / fine-tuned model testing

src/main/resources/
├── application.properties    All configuration
├── skills/                   Skill markdown files (sunny-disposition, local-expert)
├── catalog/                  Documents used for RAG (Miles of Smiles terms of use)
└── META-INF/resources/       Static HTML pages (index, chat UI)
```

## Useful links

- [Quarkus LangChain4j documentation](https://docs.quarkiverse.io/quarkus-langchain4j/dev/index.html)
- [Quarkus documentation](https://quarkus.io/guides/)
- [LangChain4j documentation](https://docs.langchain4j.dev/)
