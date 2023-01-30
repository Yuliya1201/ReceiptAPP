package com.javacource.se.receiptapp.controllers;
import lombok.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.AllArgsConstructor;



import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode

public class Ingredient {
    @NotBlank(message = "Name is mandatory")
    private String name;
    @Positive
    private Integer weight;
    private String measureUnit;
}
