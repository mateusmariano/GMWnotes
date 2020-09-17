package com.example.gmwnotes.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String userId;
    private String displayName;
    private String password;
    private Double notadecorte;

    public LoggedInUser(String userId, String displayName, String password, Double notadecorte) {
        this.userId = userId;
        this.displayName = displayName;
        this.password = password;
        this.notadecorte = notadecorte;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPassword() {return password; }

    public Double getNotaDeCorte() {return notadecorte; }
}