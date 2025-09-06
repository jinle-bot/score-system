package cz.cvut.fit.tjv.infosystem.domain;

public class View {
    public interface Summary {}  // For basic details
    public interface Detailed extends Summary {}  // Includes more details
}
