package com.example.myapplication.data.model;

public class NormalUser extends User{
    private static NormalUser instance;
    private NormalUser(String username, String email) {
        super(username, email);
    }

    public static NormalUser getInstance() {
        if (instance == null)
            instance = new NormalUser(username, email);
        return instance;
    }

    @Override
    public void sendMessage(User user, String message) {
        System.out.println(this.username + " sends message to " + user.getUsername() + ": " + message);
    }

    // Specific methods for a normal user
    public void share(Post post) {
        System.out.println(this.username + " shares a post with content: " + post.getContent());
    }

    public void receiveNotification(Notification notification) {
        System.out.println(this.username + " received a " + notification.getType() + " notification at " + notification.getTime());
    }

}
