package andream.validation.payloads;

import java.time.LocalDate;
import java.util.List;

public record ErrorListDTO(String message, LocalDate timestamp, List<String> errorsList) {
}