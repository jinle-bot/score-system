package cz.spgsasoskladno.infosystem.service;

import org.springframework.stereotype.Service;

@Service
public interface CrudService<T, ID> extends ReadService<T, ID> {
    T create(T entity);
    T update(ID id, T entity);
    void deleteById(ID id);
}
