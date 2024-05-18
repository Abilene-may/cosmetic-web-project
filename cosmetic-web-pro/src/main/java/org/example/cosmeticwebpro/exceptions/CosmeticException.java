package org.example.cosmeticwebpro.exceptions;

import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cosmeticwebpro.models.common.ValidateError;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * exception inheritance serves error handling and synthesis.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CosmeticException extends Exception{
    private String messageKey;
    private String message;
    private Throwable throwable;
    private List<String> messages;
    private List<ValidateError> validateErrors; // NOSONAR

    public CosmeticException(List<ValidateError> validateErrors) {
        this.messageKey = "VALIDATE_ERROR";
        this.validateErrors = validateErrors;
        this.messages = new ArrayList<>();
        validateErrors.forEach(validateError -> this.messages.add(validateError.getErrorMessage()));
    }

    public CosmeticException(List<ValidateError> validateErrors, String messageKey) {
        this.messageKey = messageKey;
        this.validateErrors = validateErrors;
        this.messages = new ArrayList<>();
        validateErrors.forEach(validateError -> this.messages.add(validateError.getErrorMessage()));
    }

    public CosmeticException(String msgKey) {
        this.messageKey = msgKey;
    }

    public CosmeticException(String msgKey, String msg) {
        this.messageKey = msgKey;
        this.message = msg;
    }

    public List<String> getMessages() {
        return messages;
    }

    public String getMessage() {
        if (StringUtils.isNotEmpty(this.message)) {
            return message;
        }
        if (StringUtils.isNotEmpty(this.messageKey)) {
            if (this.messageKey.equals("VALIDATE_ERROR") && this.validateErrors != null) {
                var messages =
                        this.validateErrors.stream()
                                .map(ValidateError::getErrorMessage)
                                .collect(Collectors.toList());
                this.message = String.join(", ", messages);
            } else {
                this.message = String.format(ExceptionUtils.messages.get(this.messageKey));
            }
        }
        return message;
    }
}
