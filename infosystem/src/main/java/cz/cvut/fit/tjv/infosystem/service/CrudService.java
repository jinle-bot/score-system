package cz.cvut.fit.tjv.infosystem.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CrudService<T, ID> extends ReadService<T, ID> {
    T create(T entity);
    T update(ID id, T entity);
    void deleteById(ID id);
}
