package com.example.myapplication;


/**
 * The Observer interface for receiving update notifications.
 */
public interface Observer {
    /**
     * This method is called when the subject's state has changed
     * and the observer needs to be updated.
     *
     * @param message The update message sent by the subject.
     */
    void update(String message);
}
