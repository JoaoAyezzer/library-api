package br.com.elotech.libraryapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusLoan {

    LOANED(0, "Emprestado"),
    RETURNED(1, "Entregue");


    private final int code;
    private final String description;

    public static StatusLoan toEnum(Integer code) {
        if (code == null) {
            return null;
        }

        for (StatusLoan x : StatusLoan.values()) {
            if (code.equals(x.getCode())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Invalid id: " + code);
    }


}
