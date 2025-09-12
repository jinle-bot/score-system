package cz.spgsasoskladno.infosystem.service;

import cz.spgsasoskladno.infosystem.domain.EntityWithID;
import cz.spgsasoskladno.infosystem.exception.NotFoundException;
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
