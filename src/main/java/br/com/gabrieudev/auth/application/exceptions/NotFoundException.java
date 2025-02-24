package br.com.gabrieudev.auth.application.exceptions;

import br.com.gabrieudev.auth.adapters.input.rest.dtos.ApiResponseDTO;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public ApiResponseDTO<String> toApiResponse() {
        return ApiResponseDTO.error(getMessage(), 404);
    }
}
