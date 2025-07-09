package com.store.cincomenos.domain.dto.departament;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DataRegisterDepartament(
    @NotBlank(message = "The departament cannot be blank")
    @Pattern(regexp = "[\\p{L}]{3,25}", message = "The department name cannot have less than 3 characters and more than 25")
    String name
) {

}
