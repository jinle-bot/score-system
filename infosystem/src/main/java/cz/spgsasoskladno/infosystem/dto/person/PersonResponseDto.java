package cz.spgsasoskladno.infosystem.dto.person;

import cz.spgsasoskladno.infosystem.domain.UserType;

public record PersonResponseDto(
        String username,
        String email,
        UserType role
) {
}
