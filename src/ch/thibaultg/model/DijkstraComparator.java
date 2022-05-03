package ch.thibaultg.model;

import java.util.Comparator;

public class DijkstraComparator implements Comparator<Node> {
    @Override
    public int compare(Node o1, Node o2) {
        return Integer.compare(o1.getDijkstraWeight(), o2.getDijkstraWeight());
    }
}
