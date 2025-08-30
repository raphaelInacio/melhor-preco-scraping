package com.melhorpreco.llm;

import dev.langchain4j.model.output.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.model.chat.ChatLanguageModel;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Service
public class GeminiShoppingListProcessor implements ShoppingListProcessor {

    private final ChatLanguageModel chatLanguageModel;
    private final ObjectMapper objectMapper;

    public GeminiShoppingListProcessor(@Value("${gemini.api.key}") String geminiApiKey) {
        this.chatLanguageModel = GoogleAiGeminiChatModel.builder()
                .apiKey(geminiApiKey)
                .timeout(Duration.ofSeconds(60))
                .build();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public StructuredShoppingList processRawList(String rawListInput, InputType type) {
        if (type != InputType.TEXT) {
            throw new IllegalArgumentException("Only TEXT input type is supported for now.");
        }

        String promptText = """
Extract the shopping list items and their quantities from the following text.
Respond only with a JSON array of objects, where each object has 'name' (String) and 'quantity' (String).
If no quantity is specified, use '1'. If the text is not a shopping list, return an empty array.
Example: 'apples 2kg, milk, bread' should become '[{"name":"apples","quantity":"2kg"},{"name":"milk","quantity":"1"},{"name":"bread","quantity":"1"}]'.

Text: """ + rawListInput;

        try {
            Response<String> response = chatLanguageModel.generate(promptText);
            String jsonResponse = response.content();

            if (jsonResponse == null || jsonResponse.trim().isEmpty()) {
                return new StructuredShoppingList(Collections.emptyList());
            }

            // Attempt to parse the JSON response
            List<StructuredShoppingList.Item> items = objectMapper.readValue(jsonResponse, new TypeReference<List<StructuredShoppingList.Item>>() {});
            return new StructuredShoppingList(items);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse JSON response from Gemini: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to process shopping list with Gemini: " + e.getMessage(), e);
        }
    }
}