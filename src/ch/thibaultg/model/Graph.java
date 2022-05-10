package ch.thibaultg.model;

import java.util.*;

public class Graph {
    private String name;
    private Map<String, Node> nodeList = new HashMap<>();

    public Graph(String name) {
        this.name = name;
    }

    public void addNode(String nodeName) {
        nodeList.putIfAbsent(nodeName, new Node(nodeName));
    }

    public void addNode(Node node) {
        nodeList.putIfAbsent(node.getName(), node);
    }

    public void deleteNode(String nodeName) {
        // Il faut aussi supprimer tous les noeuds entrants. Pour faire cela,
        // on parcourt la liste des noeuds et on supprime tous leurs arcs sortant
        // vers le noeud que l'on veut supprimer.
        Node nodeToDelete = nodeList.get(nodeName);
        nodeList.forEach((name, node) -> node.deleteExitingEdges(nodeToDelete));

        /* Alternative pour parcourir une map :
        for (Map.Entry<String, Node> entry : nodeList.entrySet()) {
            entry.getValue().deleteExitingEdges(nodeToDelete);
        } */

        nodeList.remove(nodeName);
    }

    public Node findNode(String nodeName) {
        return nodeList.get(nodeName);
    }

    public void addEdge(String sourceName, String destName, String edgeName, double weight) {
        Node source = findNode(sourceName);
        Node dest = findNode(destName);
        if (source == null || dest == null) {
            return;
        }

        source.addExitingEdge(edgeName, weight, dest);
    }

    public void addEdge(String sourceName, String destName, String edgeName) {
        addEdge(sourceName, destName, edgeName, 0);
    }

    public void deleteEdge(String nodeName, String edgeName) {
        Node node = findNode(nodeName);
        if (node == null) {
            return;
        }

        node.deleteExitingEdge(edgeName);
    }

    public void calculateDegreeForEachNode() {
        for (Node node : this.nodeList.values()) {
            node.setDegreeOut(node.getExitingEdges().size());
            for (Edge exitingEdge : node.getExitingEdges()) {
                var destination = exitingEdge.getDestination();

                destination.setDegreeIn(destination.getDegreeIn() + 1);
            }
        }
    }

    public void dijkstra(Node startNode) {
        System.out.println("RUNNING DIJKSTRA...\n");

        // Reinitialize all
        for (Node node: this.nodeList.values()) {
            node.setDijkstraWeight(Integer.MAX_VALUE);
            node.setDijkstraPredecessor(null);
        }

        var priorityList = new ArrayList<Node>();
        startNode.setDijkstraWeight(0);
        priorityList.add(startNode);

        while (!priorityList.isEmpty()) {
            priorityList.sort(new DijkstraComparator());
            var currentNode = priorityList.remove(0);

            var triplet = new DijkstraTriplet(currentNode.getName(), currentNode.getDijkstraWeight(), currentNode.getDijkstraPredecessor());
            System.out.println(triplet);
            startNode.addToVPCC(triplet);

            for (Edge temporaryEdge : currentNode.getExitingEdges()) {
                var destinationNode = temporaryEdge.getDestination();
                double currentWeight = currentNode.getDijkstraWeight() + temporaryEdge.getWeight();

                if (Integer.MAX_VALUE == destinationNode.getDijkstraWeight()) {
                    priorityList.add(destinationNode);
                }

                if (currentWeight < destinationNode.getDijkstraWeight()) {
                    destinationNode.setDijkstraWeight((int) currentWeight);
                    destinationNode.setDijkstraPredecessor(currentNode);
                }
            }
        }
    }

    private AbstractSequentialList<Node> shortestPath(Node startNode, Node destinationNode) throws Exception {
        if (startNode.getVPCC().isEmpty()) {
            this.dijkstra(startNode);
        }

        if (null == destinationNode) {
            throw new Exception("Pas de chemin car la destination est null");
        }

        if (!startNode.nodeExistInVpcc(destinationNode)) {
            throw new Exception(String.format("Aucun chemin possible entre %s et %s", startNode.getName(), destinationNode.getName()));
        }

        var path = new LinkedList<Node>();

        while (destinationNode != null) {
            path.addFirst(destinationNode);
            destinationNode = destinationNode.getDijkstraPredecessor();
        }

        return path;
    }

    public void printShortestWay(Node startNode, Node destinationNode) throws Exception {
        var path = this.shortestPath(startNode, destinationNode);

        var eol = System.getProperty("line.separator");
        StringBuilder sb = new StringBuilder();
        sb.append("Path cost is ").append(destinationNode.getDijkstraWeight()).append(eol);
        sb.append("Step ares: ").append(eol);
        for (Node step : path) {
            sb.append("    ").append(step.getName());
        }
        sb.append(eol);
        System.out.println(sb.toString());
    }

    public List<Node> widthWay(Node startNode) {
        return widthWay(startNode, -1);
    }

    public List<Node> widthWay(Node startNode, int depth) {
        LinkedList<Node> memory = new LinkedList<>();
        List<Node> path = new LinkedList<>();

        startNode.setMark(true);
        startNode.setLevel(0);
        memory.addFirst(startNode);

        while (!memory.isEmpty()) {
            Node current = memory.removeLast();
            path.add(current);

            if (current.getLevel() == depth) {
                continue;
            }

            for (Edge edge : current.getExitingEdges()) {
                Node destination = edge.getDestination();
                if (!destination.isMarked()) {
                    destination.setMark(true);
                    destination.setLevel(current.getLevel() + 1);
                    memory.addFirst(destination);
                }
            }
        }

        unmark(path);
        return path;
    }

    public List<Node> widthWay(Node startNode, int depth, Class type) {
        LinkedList<Node> memory = new LinkedList<>();
        List<Node> path = new LinkedList<>();

        startNode.setMark(true);
        startNode.setLevel(0);
        memory.addFirst(startNode);

        while (!memory.isEmpty()) {
            Node current = memory.removeLast();
            path.add(current);

            if (current.getLevel() == depth) {
                continue;
            }

            for (Edge edge : current.getExitingEdges()) {
                if (edge.getClass() != type) {
                    continue;
                }

                Node destination = edge.getDestination();
                if (!destination.isMarked()) {
                    destination.setMark(true);
                    destination.setLevel(current.getLevel() + 1);
                    memory.addFirst(destination);
                }
            }
        }

        unmark(path);
        return path;
    }

    public List<Node> depthWay(Node startNode) {
        LinkedList<Node> memory = new LinkedList<>();
        List<Node> path = new LinkedList<>();

        startNode.setMark(true);
        memory.addLast(startNode);

        while (!memory.isEmpty()) {
            Node current = memory.removeLast();
            path.add(current);

            for (Edge edge : current.getExitingEdges()) {
                Node destination = edge.getDestination();
                if (!destination.isMarked()) {
                    destination.setMark(true);
                    memory.addLast(destination);
                }
            }
        }

        unmark(path);
        return path;
    }

    public void unmark(List<Node> nodes) {
        nodes.forEach(node -> node.setMark(false));
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();

        sb.append("Graph: ").append(name).append("\n\n");

        nodeList.forEach((k, node) -> {
            sb.append(node);
            sb.append("\n");
        });

        return sb.toString();
    }
}
