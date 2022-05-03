package ch.thibaultg.model;

public class DijkstraTriplet {
    private final String name;
    private final int weight;
    private final Node predecessor;

    public DijkstraTriplet(
        String name,
        int weight,
        Node predecessor
    ) {
        this.name = name;
        this.weight = weight;
        this.predecessor = predecessor;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return '(' + this.name + ", " + this.weight + ", " + (predecessor == null ? "null" : predecessor.getName()) + ')';
    }
}
