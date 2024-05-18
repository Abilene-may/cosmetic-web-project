package org.example.cosmeticwebpro.models.common;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ValidateError implements Serializable {
    private static final long serialVersionUID = -317263205014648815L;
    private String code;
    private String errorMessage;
    private boolean isWarning = false;

    public ValidateError(String code, String errorMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
    }
}
