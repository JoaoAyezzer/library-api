package br.com.elotech.libraryapi.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StandardError {
    private Long timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
