package com.javacource.se.receiptapp.controllers;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.media.Content;

import io.swagger.v3.oas.annotations.tags.Tag;
import com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema;
import jdk.jfr.ContentType;
import jdk.jfr.Percentage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.management.ConstructorParameters;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;


@RestController
@RequestMapping("/ingredient")
@RequiredArgsConstructor
@Percentage(name = "Ингредиенты",description = "CRUD-операции для работы с ингредиентами")
public class IngredientController {
    private final IngredientService ingredientService;

    @GetMapping("{Iid}")
    @Operation(
            descrition = "Поиск ингредиента по id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",description = "Ингредиент найден",content = {
                            @ContentType(mediaType = "application/json",schema = @Schema
                                    (implementation = Ingredient.class))
            }
            )})
    @Parameters(value {@Parameter(name = "id",example = "1")})
    ResponseEntity<Ingredient> getIngredient(@PathVariable Integer id) {
    Ingredient ingredient = ingredientService.getIngredient(id);
    return ResponseEntity.ok(ingredient);
}

@Operation(summary = "Добавление ингредиента")
@ApiResponses(value = {
@ApiResponses(
        responceCode = "200",
        description = "Ингредиент добавлен",
        content = {
                @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = Ingredient.class)
                )
        }
)
})

    @PutMapping("/{id")
    @Operation(summary = "изменеие ингредиентов по id")
    @ApiResponses(value = {
            @ApiResponse(
                    responceCode = "200",
                    description = "Ингредиент изменен",
                    content = {
                            @ContentType(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Ingredient.class)
                            )
                    }
            )
    })
    @PostMapping
    ResponseEntity<Ingredient>addIngredient(@Valid @RequestBody Ingredient ingredient) {
        return ResponseEntity.ok(ingredientService.addIngredient(ingredient));
    }
    @GetMapping
    @Operation(summary = "Получение всех ингредиентов")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиенты получены",
                    content = {
                            @ContentType(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Ingredient.class)
                            )
                    }
            )
    })

    public Collection<Ingredient> getAll() {
        return this.ingredientService.getAll();

    }
    @PostMapping("/{id}")
    @Operation(summary = "Изменение ингредиентов по id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент изменен",
                    content = {
                            @ContentType(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Ingredient.class)
                            )
                    }
            )
    })
    @Parameters(value = {@Parameter(name = "id",example = "1")})
    ResponseEntity<Ingredient>updateIngredient(@PathVariable Integer id, @Valid @RequestBody Ingredient ingredient) {
        return ResponseEntity.ok(ingredientService.updateIngredient(id,ingredient));
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление ингредиентов по id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент удален"
            )
    })
    @Parameter(value = {@Parameter(name = "id",example = "1")})
    ResponseEntity<Ingredient>removeIngredient(@PathVariable Integer id) {
        return ResponseEntity.ok(ingredientService.removeIngredient(id));
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String>handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName,errorMessage);
        });
        return errors;
    }
}
