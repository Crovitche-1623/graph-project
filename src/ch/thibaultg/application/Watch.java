package ch.thibaultg.application;

import ch.thibaultg.model.Edge;

public class Watch extends Edge {
    public Watch(String edgeName, StreamingSite destination) {
        super(edgeName, destination, 1);
    }

    public Watch(StreamingSite destination) {
        super(destination);
    }
}
