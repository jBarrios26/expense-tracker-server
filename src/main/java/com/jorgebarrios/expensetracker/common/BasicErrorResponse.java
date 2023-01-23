package com.jorgebarrios.expensetracker.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class BasicErrorResponse {
    private Integer code;
    private String message;

    public  BasicErrorResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


}
