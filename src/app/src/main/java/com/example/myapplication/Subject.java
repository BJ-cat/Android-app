package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;


/**
 * The Subject class maintains a list of observers and notifies them
 * of any changes by calling their update method.
 */
public class Subject {
    private List<Observer> observers = new ArrayList<>();  // List to store the observers

    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    // Notifies all subscribed observers by calling their update method
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}

