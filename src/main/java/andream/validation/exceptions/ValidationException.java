package andream.validation.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class ValidationException extends RuntimeException {
    private List<String> errorList;

    public ValidationException(List<String> errorList) {
        super("multiple errors");
        this.errorList = errorList;
    }
}
