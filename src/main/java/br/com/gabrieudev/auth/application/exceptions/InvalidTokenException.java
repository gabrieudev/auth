package br.com.gabrieudev.auth.application.exceptions;

import br.com.gabrieudev.auth.adapters.input.rest.dtos.ApiResponseDTO;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) {
        super(message);
    }

    public ApiResponseDTO<String> toApiResponse() {
        return ApiResponseDTO.error(getMessage(), 401);
    }
}
