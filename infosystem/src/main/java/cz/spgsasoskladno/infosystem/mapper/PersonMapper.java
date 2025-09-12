package cz.spgsasoskladno.infosystem.mapper;

import cz.spgsasoskladno.infosystem.domain.Person;
import cz.spgsasoskladno.infosystem.dto.person.PersonResponseDto;


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
