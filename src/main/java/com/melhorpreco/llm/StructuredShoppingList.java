package com.melhorpreco.llm;

import java.util.List;

public class StructuredShoppingList {
    private List<Item> items;

    public StructuredShoppingList() {
    }

    public StructuredShoppingList(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public static class Item {
        private String name;
        private String quantity;

        public Item() {
        }

        public Item(String name, String quantity) {
            this.name = name;
            this.quantity = quantity;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }
    }
}
