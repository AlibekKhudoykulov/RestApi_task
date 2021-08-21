package com.epam.esm.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ExceptionResponse {
    private String errorMessage;
    private Integer errorCode;
}
