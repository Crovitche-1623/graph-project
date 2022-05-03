package ch.thibaultg.model;

public class Edge {
    private String name;
    private double weight;
    private Node source;
    private Node destination;

    public Edge(String name, Node destination, double weight) {
        this.name = name;
        this.weight = weight;
        this.destination = destination;
    }

    public Edge(String name, Node source, Node destination,  double weight) {
        this.name = name;
        this.weight = weight;
        this.source = source;
        this.destination = destination;
    }

    public Edge(Node destination) {
        this.destination = destination;
        this.weight = 1;
        this.name = generateName();
    }

    public String generateName() {
        return this.destination.getName() + "-" + this.getClass().getSimpleName();
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public Node getSource() {
        return source;
    }

    public Node getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s, %.0f)", name, destination.getName(), weight);
    }
}
