package com.jorgebarrios.expensetracker.user.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class RefreshTokenDTO {
    @NotNull
    @NotBlank
    private String refreshToken;

    public RefreshTokenDTO(final String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
