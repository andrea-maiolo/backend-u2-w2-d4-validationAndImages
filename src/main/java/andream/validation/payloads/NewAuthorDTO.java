package andream.validation.payloads;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record NewAuthorDTO(
        @NotEmpty(message = "name must be inserted")
        @Size(min = 3, max = 15, message = "name must be bigger than 2 and shorter than 15")
        String name,
        @NotEmpty(message = "surname must be inserted")
        @Size(min = 3, max = 15, message = "surname must be bigger than 2 and shorter than 15")
        String surname,
        @NotEmpty(message = "email is required")
        @Email(message = "email is in the wrong format")
        String email,
        @NotNull(message = "dob is required")
        @Past
        LocalDate dob
) {
}
