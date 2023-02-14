package com.example.foodies.model.api;

import java.io.Serializable;
import java.util.ArrayList;


public class RecipesApiResponse implements Serializable {
    private RecipeResponse[] results;

    public RecipesApiResponse(RecipeResponse[] response) {
        this.results = response;
    }

    public RecipeResponse[] getResults() {
        return results;
    }

    public void setResults(RecipeResponse[] results) {
        this.results = results;
    }

    public class RecipeResponse implements Serializable {

        private String title;
        private String summary;
        private ArrayList<AnalyzedInstructionsResponse> analyzedInstructions;
        private ArrayList<String> dishTypes;
        private String image;
        private int preparationMinutes;

        public RecipeResponse(String title, String summary, ArrayList<AnalyzedInstructionsResponse> analyzedInstructions, ArrayList<String> dishTypes, String image, int preparationMinutes) {
            this.title = title;
            this.summary = summary;
            this.analyzedInstructions = analyzedInstructions;
            this.dishTypes = dishTypes;
            this.image = image;
            this.preparationMinutes = preparationMinutes;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public ArrayList<AnalyzedInstructionsResponse> getAnalyzedInstructions() {
            return analyzedInstructions;
        }

        public void setAnalyzedInstructions(ArrayList<AnalyzedInstructionsResponse> analyzedInstructions) {
            this.analyzedInstructions = analyzedInstructions;
        }

        public ArrayList<String> getDishTypes() {
            return dishTypes;
        }

        public void setDishTypes(ArrayList<String> dishTypes) {
            this.dishTypes = dishTypes;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getPreparationMinutes() {
            return preparationMinutes;
        }

        public void setPreparationMinutes(int preparationMinutes) {
            this.preparationMinutes = preparationMinutes;
        }


        public class AnalyzedInstructionsResponse implements Serializable {
            private ArrayList<StepsResponse> steps;

            public AnalyzedInstructionsResponse(ArrayList<StepsResponse> steps) {
                this.steps = steps;
            }

            public ArrayList<StepsResponse> getSteps() {
                return steps;
            }

            public void setSteps(ArrayList<StepsResponse> steps) {
                this.steps = steps;
            }
        }

        public class StepsResponse implements Serializable {
            private ArrayList<IngredientResponse> ingredients;

            public StepsResponse(ArrayList<IngredientResponse> ingredients) {
                this.ingredients = ingredients;
            }

            public ArrayList<IngredientResponse> getIngredients() {
                return ingredients;
            }

            public void setIngredients(ArrayList<IngredientResponse> ingredients) {
                this.ingredients = ingredients;
            }
        }

        public class IngredientResponse implements Serializable {
            private String name;

            public IngredientResponse(String name) {
                this.name = name;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}


