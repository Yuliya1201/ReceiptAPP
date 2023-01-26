package com.javacource.se.receiptapp.controllers;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Recipe {
    private String name;
    private Integer cookingTime;
    private List<Ingredient> ingredients;
    private List<String> steps;

    public class ValidateUtils {
        public static String validateString(String value, String substitution) {
            return (value == null || value.isBlank() || value.isEmpty()) ? substitution : value;
        }
        public static boolean validateList(List value) {
            return !(value == null || value.isEmpty());
        }

        public static Boolean validateString(String value) {
            return !(value == null || value.isBlank() || value.isEmpty());
        }

        public static Boolean validateBoolean(Boolean value) {
            return value != null && value;
        }

        public static Integer validateInteger(Integer value, Integer substitution) {
            return (value == null || value <= 0) ? substitution : value;
        }

    }
}
