package cz.spgsasoskladno.infosystem.service;

import cz.spgsasoskladno.infosystem.domain.EntityWithID;
import cz.spgsasoskladno.infosystem.exception.NotFoundException;
import jakarta.persistence.EntityExistsException;
import org.springframework.stereotype.Service;


@Service
public abstract class CrudServiceImpl<T extends EntityWithID<ID>,ID>
        extends ReadServiceImpl<T, ID>
        implements CrudService<T, ID> {

    @Override
    public T create(T entity) {
        if (entity.getId() != null && getRepository().existsById(entity.getId()))
            throw new EntityExistsException("Entity by ID already exists");
        return checkAndPut(entity);
    }

    @Override
    public T update(ID id, T entity) {
        if ( ! getRepository().existsById(id) )
            throw new NotFoundException(entity.getClass().getSimpleName(), id);
        return checkAndPut(entity);
    }

    //????
    protected T checkAndPut (T entity) {
       return getRepository().save(entity);
    }

    @Override
    public void deleteById(ID id) {
        getRepository().deleteById(id);
    }

}
