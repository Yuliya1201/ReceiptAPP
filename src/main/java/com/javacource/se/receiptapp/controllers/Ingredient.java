package com.javacource.se.receiptapp.controllers;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@NoArgsConstructor
@Getter

public class Ingredient {
    @NotBlank(message = "Name is mandatory")
    private String name;
    @Positive
    private Integer weight;
    private String measureUnit;
}
