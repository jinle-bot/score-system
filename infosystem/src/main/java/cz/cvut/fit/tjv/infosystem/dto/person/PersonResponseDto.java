package cz.cvut.fit.tjv.infosystem.dto.person;

import cz.cvut.fit.tjv.infosystem.domain.UserType;

public record PersonResponseDto(
        String username,
        String email,
        UserType role
) {
}
