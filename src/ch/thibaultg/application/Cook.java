package ch.thibaultg.application;

import ch.thibaultg.model.Edge;
import ch.thibaultg.model.Node;

public class Cook extends Edge {
    public Cook(String edgeName, Node destination) {
        super(edgeName, destination, 1);
    }

    public Cook(String edgeName, Node source, Node destination) {
        super(edgeName, source, destination, 1);
    }
}
