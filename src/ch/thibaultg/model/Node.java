package ch.thibaultg.model;

import java.util.*;

public class Node {
    private final String name;
    private final Map<String, Edge> exitingEdgeList = new HashMap<>();
    private boolean mark;
    private int level;
    private int degreeIn = 0;
    private int degreeOut = 0;

    // Vecteur pour le plus court chemin
    private final Map<String, DijkstraTriplet> vpcc = new HashMap<>();

    private int dijkstraWeight = Integer.MAX_VALUE;
    private Node dijkstraPredecessor = null;

    public Node(String name) {
        this.name = name;
    }

    public Collection<Edge> getExitingEdges() {
        return exitingEdgeList.values();
    }

    public void addExitingEdge(String edgeName, double weight, Node destination) {
        exitingEdgeList.putIfAbsent(edgeName, new Edge(edgeName, destination, weight));
    }

    public void addExitingEdge(Edge edge) {
        exitingEdgeList.putIfAbsent(edge.getName(), edge);
    }

    // Pas demandé par le prof
    public void replaceExitingEdge(String edgeName, double weight, Node destination) {
        if (exitingEdgeList.get(edgeName) != null) {
            exitingEdgeList.put(edgeName, new Edge(edgeName, destination, weight));
        }
    }

    public void deleteExitingEdge(String edgeName) {
        exitingEdgeList.remove(edgeName);
    }

    // Supprime les arcs allant vers la destination précisée
    public void deleteExitingEdges(Node destination) {
        Iterator<Edge> iterator = exitingEdgeList.values().iterator();
        while (iterator.hasNext()) {
            Edge current = iterator.next();
            if (current.getDestination() == destination) {
                iterator.remove();
            }
        }
    }

    public String getName() {
        return name;
    }

    public boolean isMarked() {
        return mark;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();

        sb.append(String.format("w++(%s) = ", name));
        sb.append("{");
        exitingEdgeList.forEach((edgeName, edge) -> {
            sb.append(edge).append(", ");
        });
        if (exitingEdgeList.size() > 0) {
            sb.replace(sb.length() - 2, sb.length(), "");
        }
        sb.append("}");

        return sb.toString();
    }

    public int getDijkstraWeight() {
        return dijkstraWeight;
    }

    public void setDijkstraWeight(int dijkstraWeight) {
        this.dijkstraWeight = dijkstraWeight;
    }

    public Node getDijkstraPredecessor() {
        return dijkstraPredecessor;
    }

    public void setDijkstraPredecessor(Node dijkstraPredecessor) {
        this.dijkstraPredecessor = dijkstraPredecessor;
    }

    public Map<String, DijkstraTriplet> getVPCC() {
        return this.vpcc;
    }

    public boolean nodeExistInVpcc(Node node) {
        return null != this.vpcc.get(node.getName());
    }

    public void addToVPCC(DijkstraTriplet triplet) {
        this.vpcc.putIfAbsent(triplet.getName(), triplet);
    }

    public int getDegreeIn() {
        return this.degreeIn;
    }

    public int getDegreeOut () {
        return this.degreeOut;
    }

    public int getDegree() {
        return this.getDegreeIn() + getDegreeOut();
    }

    public void setDegreeIn(int degree) {
        this.degreeIn = degree;
    }

    public void setDegreeOut(int degreeOut) {
        this.degreeOut = degreeOut;
    }
}
