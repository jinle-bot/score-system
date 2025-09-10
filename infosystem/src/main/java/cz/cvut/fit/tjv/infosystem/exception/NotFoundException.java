package cz.cvut.fit.tjv.infosystem.exception;


public class NotFoundException extends RuntimeException{
    public NotFoundException(String entity, Object id) {
        super(entity + " with id " + id + " not found");
    }
}
