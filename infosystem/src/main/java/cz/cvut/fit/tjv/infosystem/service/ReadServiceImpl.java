package cz.cvut.fit.tjv.infosystem.service;

import cz.cvut.fit.tjv.infosystem.domain.EntityWithID;
import cz.cvut.fit.tjv.infosystem.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public abstract class ReadServiceImpl<T extends EntityWithID<ID>,ID> implements ReadService<T,ID> {

    @Override
    public T readById(ID id, String entityName) {
        return getRepository().findById(id)
                .orElseThrow(() -> new NotFoundException(entityName, id));
    }

    @Override
    public List<T> readAll() {
        return getRepository().findAll();
    }

    protected abstract JpaRepository<T, ID> getRepository();

}
