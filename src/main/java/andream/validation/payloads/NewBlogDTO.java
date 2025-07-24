package andream.validation.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record NewBlogDTO(
        @NotEmpty(message = "category is required")
        String category,
        @NotEmpty(message = "title is required")
        String title,
        @NotEmpty(message = "content is required")
        String content,
        @NotNull(message = "author id is required")
        UUID authorId
) {
}
