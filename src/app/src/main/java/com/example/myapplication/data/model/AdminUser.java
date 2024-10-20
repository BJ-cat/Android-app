package com.example.myapplication.data.model;

public class AdminUser extends User{
    public AdminUser(String username, String email) {
        super(username, email);
    }

    @Override
    public void sendMessage(User user, String message) {
        System.out.println(this.username + " (Admin) sends message to " + user.getUsername() + ": " + message);
    }

    // Admin-specific methods
    public void blockUser(User user) {
        System.out.println(this.username + " has blocked " + user.getUsername());
    }

    public void deletePost(Post post) {
        System.out.println(this.username + " (Admin) deletes a post with content: " + post.getContent());
    }
}
