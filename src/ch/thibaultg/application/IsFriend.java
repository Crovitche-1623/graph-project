package ch.thibaultg.application;

import ch.thibaultg.model.Edge;

public class IsFriend extends Edge {
    public IsFriend(String edgeName, Person destination) {
        super(edgeName, destination, 1);
    }

    public IsFriend(String edgeName, Person source, Person destination) {
        super(edgeName, source, destination, 1);
    }

    public IsFriend(Person destination) {
        super(destination);
    }
}
