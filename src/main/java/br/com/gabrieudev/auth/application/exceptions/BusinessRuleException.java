package br.com.gabrieudev.auth.application.exceptions;

import br.com.gabrieudev.auth.adapters.input.rest.dtos.ApiResponseDTO;

public class BusinessRuleException extends RuntimeException {
    public BusinessRuleException(String message) {
        super(message);
    }

    public ApiResponseDTO<String> toApiResponse() {
        return ApiResponseDTO.error(getMessage(), 409);
    }
}
