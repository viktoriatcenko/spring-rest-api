package ru.maxima.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PersonDTO {

    @NotEmpty(message = "Name should not to be empty")
    @Size(min = 2, max = 50, message = "Lenght of name should be min 2 symbols, max 50 symbols")
    private String name;

    @Min(value = 0, message = "Age should be min 0 year")
    private int age;

    @NotEmpty(message = "Email should not to be empty")
    @Email(message = "EMail should be valid")
    private String email;
}
