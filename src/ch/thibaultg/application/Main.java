package ch.thibaultg.application;

import ch.thibaultg.model.Graph;
import ch.thibaultg.model.Node;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        // exerciceParcours();
        // exerciceParcoursLimiteEtTypage();
        dijkstraExercise();
    }

    public static void exerciceParcours() {
        Graph g = new Graph("G1");
        g.addNode("V1");
        g.addNode("V2");
        g.addNode("V3");
        g.addNode("V4");

        g.addEdge("V2", "V1", "u1", 4);
        g.addEdge("V1", "V2", "u2", 3);
        g.addEdge("V1", "V2", "u2", 2);
        g.addEdge("V1", "V2", "u3", 2);
        g.addEdge("V2", "V4", "u4", 5);
        g.addEdge("V4", "V1", "u5", 1);
        g.addEdge("V4", "V3", "u6", 42);

        System.out.println(g);

        g.deleteNode("V2");

        System.out.println(g);

        Graph g2 = new Graph("G2");
        g.addNode("A");
        g.addNode("B");
        g.addNode("C");
        g.addNode("D");
        g.addNode("E");
        g.addNode("F");

        g.addEdge("A", "C", "u1");
        g.addEdge("A", "B", "u2");
        g.addEdge("C", "D", "u5");
        g.addEdge("D", "E", "u6");
        g.addEdge("C", "E", "u7");
        g.addEdge("E", "F", "u8");

        Node a = g.findNode("A");
        printPath(g2.widthWay(a));
        printPath(g2.widthWay(a, 3));
        printPath(g2.widthWay(g.findNode("C"), 1));
        //printPath(g2.depthWay(a));
    }

    public static void exerciceParcoursLimiteEtTypage() {
        Person albert = new Person("Albert","Lausanne");
        Person lucie = new Person("Lucie","Neuchâtel");
        Person paul = new Person("Paul","Neuchâtel");
        Person jean = new Person("jean","Neuchâtel");
        Person alfred = new Person("Alfred","Lausanne");
        Person julie = new Person("Julie","Cernier");
        StreamingSite netflix = new StreamingSite("Netflix");
        StreamingSite disney = new StreamingSite("Disney");
        StreamingSite amazon = new StreamingSite("Amazon Prime Video");

        albert.addWatch(disney);
        albert.addWatch(amazon);

        julie.addFriend(albert);

        paul.addWatch(amazon);
        paul.addWatch(netflix);
        paul.addFriend(lucie);
        paul.addFriend(jean);
        paul.addFriend(julie);

        jean.addFriend(alfred);

        alfred.addWatch(netflix);

        lucie.addWatch(netflix);
        lucie.addWatch(amazon);

        Graph g = new Graph("Personnes");
        g.addNode(albert);
        g.addNode(lucie);
        g.addNode(paul);
        g.addNode(jean);
        g.addNode(alfred);
        g.addNode(julie);
        System.out.println(g);

        // Q1
        System.out.println("Q1. Paul regarde : ");
        g.widthWay(paul, 1, Watch.class)
            .stream()
            .skip(1)
            .forEach(node -> {
                System.out.println("- " + node.getName());
            });
        System.out.println();

        // Q2
        System.out.println("Q2. Les amis (jusqu'au 2ème niveau) de paul qui regardent des sites de streaming sont : ");
        g.widthWay(paul, 2, IsFriend.class)
            .stream()
            .skip(1)
            .forEach(friend -> {
                List<Node> streamingSites = g.widthWay(friend, 1, Watch.class);
                if (streamingSites.size() > 1) {
                    System.out.println("- " + friend.getName());
                }
            });
        System.out.println();

        // Q3
        Stream<Node> paulFriends = g.widthWay(paul, 1, IsFriend.class).stream().skip(1);
        Stream<Node> selectedFriends = paulFriends.filter(node -> {
            Person friend = (Person) node;
            Stream<Node> streamingSites = g.widthWay(friend, 1, Watch.class).stream().skip(1);
            boolean watchAmazon = streamingSites.anyMatch(site -> site.getName().equals("Amazon Prime Video"));

            return friend.getCity().equals("Neuchâtel") && watchAmazon;
        });

        System.out.println("Q3. Les amis de paul qui habitent à Neuchâtel et regarde Amazon Prime Video sont : ");
        selectedFriends.forEach(friend -> System.out.println("- " + friend.getName()));
    }

    public static void dijkstraExercise() {
        Graph graph = new Graph("Graph");
        Node a = new Node("A");
        Node b = new Node("B");
        Node c = new Node("C");
        Node d = new Node("D");
        Node e = new Node("E");
        Node f = new Node("F");
        Node g = new Node("G");
        Node h = new Node("H");
        a.addExitingEdge("u1", 4, b);
        a.addExitingEdge("u11", 1, g);
        b.addExitingEdge("u2", 5, c);
        b.addExitingEdge("u4", 8, d);
        c.addExitingEdge("u3", 10, e);
        c.addExitingEdge("u6", 3, f);
        d.addExitingEdge("u5", 5, c);
        d.addExitingEdge("u8", 3, f);
        d.addExitingEdge("u10", 2, g);
        d.addExitingEdge("u14", 1, a);
        f.addExitingEdge("u7", 2, e);
        g.addExitingEdge("u9", 4, f);
        h.addExitingEdge("u12", 2, a);
        h.addExitingEdge("u13", 9, g);
        // graph.dijkstra(a);
        try {
            graph.printShortestWay(a, e);
        } catch (Exception e1) {
            System.out.println(e1.getMessage());
        }
    }

    public static void printPath(List<Node> path) {
        if (path.size() == 0) {
            System.out.println("Empty path");
        }

        var sb = new StringBuilder();
        for (Node node : path) {
            sb.append(String.format("%s(%d) -> ", node.getName(), node.getLevel()));
        }
        sb.delete(sb.length() - 4, sb.length());

        System.out.println(sb.toString());
    }
}
