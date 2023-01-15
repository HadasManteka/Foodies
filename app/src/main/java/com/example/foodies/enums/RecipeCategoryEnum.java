package com.example.foodies.enums;

import java.util.Arrays;

public enum RecipeCategoryEnum {
    BAKE("Bake"),
    COOK("Cook"),
    SALAD("Salad"),
    DRINK("Drink"),
    OTHER("Other");

    private String category;

    RecipeCategoryEnum(String category) {
        this.category = category;
    }

    public String getCategory() {
        return this.category;
    }

    public static RecipeCategoryEnum getCategoryByText(String cat) {
        return Arrays.stream(RecipeCategoryEnum.values())
                    .filter(e -> e.category.equals(cat))
                    .findFirst().orElse(OTHER);
    }

    @Override
    public String toString() {
        return category;
    }
}
