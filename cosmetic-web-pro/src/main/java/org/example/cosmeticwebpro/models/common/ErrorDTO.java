package org.example.cosmeticwebpro.models.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ErrorDTO implements Serializable {
    private String code;

    // information of error
    private String message;

    // list errors
    private List<String> messages;

    public ErrorDTO(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorDTO(String message) {
        this.message = message;
    }

    public ErrorDTO(String code, List<String> messages) {
        this.code = code;
        this.messages = messages;
    }
}
