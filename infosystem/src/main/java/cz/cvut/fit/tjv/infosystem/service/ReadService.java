package cz.cvut.fit.tjv.infosystem.service;

import org.springframework.stereotype.Service;
import java.util.List;


@Service
public interface ReadService<T,ID> {
    T readById(ID id, String entityName);
    List<T> readAll();
}
