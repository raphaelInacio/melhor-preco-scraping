package com.melhorpreco.llm;

public interface ShoppingListProcessor {
    StructuredShoppingList processRawList(String rawListInput, InputType type);
}
