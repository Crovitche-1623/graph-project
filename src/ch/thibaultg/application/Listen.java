package ch.thibaultg.application;

import ch.thibaultg.model.Edge;

public class Listen extends Edge {
    public Listen(String edgeName, StreamingSite destination) {
        super(edgeName, destination, 1);
    }

    public Listen(String edgeName, StreamingSite source, StreamingSite destination) {
        super(edgeName, source, destination, 1);
    }
}
