package ch.thibaultg.application;

import ch.thibaultg.model.Node;

import java.util.Date;

public class Person extends Node {

    private Date birthday;
    private String city;

    public Person(String name) {
        super(name);
    }

    public Person(String name, String city) {
        super(name);
        this.city = city;
    }

    public Person(String name, String city, Date birthday) {
        this(name, city);
        this.birthday = birthday;
    }

    public void addFriend(Person friend) {
        IsFriend edge = new IsFriend(friend);
        this.addExitingEdge(edge);
    }

    public void addWatch(StreamingSite site) {
        Watch edge = new Watch(site);
        this.addExitingEdge(edge);
    }

    public void addListen(String edgeName, StreamingSite site, double weight) {
        Listen edge = new Listen(edgeName, site);
        this.addExitingEdge(edge);
    }

    public void addCook(String edgeName, Recipe recipe) {
        Cook edge = new Cook(edgeName, recipe);
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
