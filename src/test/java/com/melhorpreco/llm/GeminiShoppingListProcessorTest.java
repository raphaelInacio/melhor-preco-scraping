package com.melhorpreco.llm;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.output.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GeminiShoppingListProcessorTest {

    @Mock
    private ChatLanguageModel chatLanguageModel;

    private GeminiShoppingListProcessor processor;

    @BeforeEach
    void setUp() {
        // We need to pass a dummy API key for the constructor, as it's not mocked
        processor = new GeminiShoppingListProcessor("dummy-api-key");
        // Manually inject the mocked chatLanguageModel
        // This is a workaround because @InjectMocks doesn't work well with final fields
        try {
            java.lang.reflect.Field field = GeminiShoppingListProcessor.class.getDeclaredField("chatLanguageModel");
            field.setAccessible(true);
            field.set(processor, chatLanguageModel);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void shouldProcessTextToListSuccessfully() {
        String rawInput = "apples 2kg, milk, bread";
        String geminiResponseJson = "[{"name":"apples","quantity":"2kg"},{"name":"milk","quantity":"1"},{"name":"bread","quantity":"1"}]";

        when(chatLanguageModel.generate(org.mockito.ArgumentMatchers.anyString()))
                .thenReturn(Response.from(geminiResponseJson));

        StructuredShoppingList result = processor.processRawList(rawInput, InputType.TEXT);

        assertThat(result).isNotNull();
        assertThat(result.getItems()).hasSize(3);
        assertThat(result.getItems().get(0).getName()).isEqualTo("apples");
        assertThat(result.getItems().get(0).getQuantity()).isEqualTo("2kg");
        assertThat(result.getItems().get(1).getName()).isEqualTo("milk");
        assertThat(result.getItems().get(1).getQuantity()).isEqualTo("1");
        assertThat(result.getItems().get(2).getName()).isEqualTo("bread");
        assertThat(result.getItems().get(2).getQuantity()).isEqualTo("1");
    }

    @Test
    void shouldReturnEmptyListForNonShoppingListText() {
        String rawInput = "This is just a random sentence.";
        String geminiResponseJson = "[]";

        when(chatLanguageModel.generate(org.mockito.ArgumentMatchers.anyString()))
                .thenReturn(Response.from(geminiResponseJson));

        StructuredShoppingList result = processor.processRawList(rawInput, InputType.TEXT);

        assertThat(result).isNotNull();
        assertThat(result.getItems()).isEmpty();
    }

    @Test
    void shouldThrowExceptionForInvalidJsonResponse() {
        String rawInput = "apples";
        String invalidJsonResponse = "invalid json";

        when(chatLanguageModel.generate(org.mockito.ArgumentMatchers.anyString()))
                .thenReturn(Response.from(invalidJsonResponse));

        assertThatThrownBy(() -> processor.processRawList(rawInput, InputType.TEXT))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Failed to parse JSON response from Gemini");
    }

    @Test
    void shouldThrowExceptionForNonTextInputs() {
        String rawInput = "audio_data";

        assertThatThrownBy(() -> processor.processRawList(rawInput, InputType.AUDIO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Only TEXT input type is supported for now.");

        assertThatThrownBy(() -> processor.processRawList(rawInput, InputType.IMAGE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Only TEXT input type is supported for now.");
    }
}
