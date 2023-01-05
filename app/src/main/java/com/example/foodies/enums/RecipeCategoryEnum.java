package com.example.foodies.enums;

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
        try {
            return RecipeCategoryEnum.valueOf(cat);
        } catch (Exception e) {
            return OTHER;
        }
    }
}
