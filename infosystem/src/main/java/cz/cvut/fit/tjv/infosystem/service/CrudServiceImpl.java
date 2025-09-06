package cz.cvut.fit.tjv.infosystem.service;

import cz.cvut.fit.tjv.infosystem.domain.EntityWithID;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public abstract class CrudServiceImpl<T extends EntityWithID<ID>,ID> implements CrudService<T, ID> {

    @Override
    public T create(T entity) {
        if (entity.getId() != null && getRepository().existsById(entity.getId()))
            throw new EntityExistsException("Entity by ID already exists");
        return checkAndPut(entity);
    }

    @Override
    public T update(ID id, T entity) {
        if ( ! getRepository().existsById(id) )
            throw new EntityNotFoundException("Entity does not exist");
        return checkAndPut(entity);
    }

    protected T checkAndPut (T entity) {
       return getRepository().save(entity);
    }

    @Override
    public void deleteById(ID id) {
        getRepository().deleteById(id);
    }

    @Override
    public T readById(ID id, String msg) {
        Optional<T> opt = getRepository().findById(id);
        if (opt.isEmpty())
            throw new EntityNotFoundException("Invalid ID: " + msg);
        return opt.get();
    }

    @Override
    public List<T> readAll() {
        return getRepository().findAll();
    }

    protected abstract JpaRepository<T, ID> getRepository();
}
