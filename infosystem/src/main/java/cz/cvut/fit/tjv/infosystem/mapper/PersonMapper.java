package cz.cvut.fit.tjv.infosystem.mapper;

import cz.cvut.fit.tjv.infosystem.domain.Person;
import cz.cvut.fit.tjv.infosystem.dto.person.PersonResponseDto;


public class PersonMapper {

    private PersonMapper() {
        // private constructor – utility class
    }

    public static PersonResponseDto toDto(Person entity) {
        if (entity == null) return null;
        return new PersonResponseDto(
                entity.getUsername(),
                entity.getEmail(),
                entity.getRole()
        );
    }

}
