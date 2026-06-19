package com.omkar.jobaiassistant.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_sessions")
public class UserSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sessionId; // UUID per device login

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String refreshToken;

    private String deviceInfo;
    private String ipAddress;

    private LocalDateTime createdAt;
    private LocalDateTime lastActiveAt;

    private boolean active = true;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.lastActiveAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.lastActiveAt = LocalDateTime.now();
    }

    // getters & setters

    public Long getId() { return id; }

    public String getSessionId() { return sessionId; }

    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public String getRefreshToken() { return refreshToken; }

    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }

    public String getDeviceInfo() { return deviceInfo; }

    public void setDeviceInfo(String deviceInfo) { this.deviceInfo = deviceInfo; }

    public String getIpAddress() { return ipAddress; }

    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public boolean isActive() { return active; }

    public void setActive(boolean active) { this.active = active; }
}