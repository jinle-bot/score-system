package cz.cvut.fit.tjv.infosystem.exception;


public class BusinessRuleViolation extends RuntimeException {
    public BusinessRuleViolation(String message) { super(message); }
}