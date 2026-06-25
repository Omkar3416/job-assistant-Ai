package com.omkar.jobaiassistant.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "naukri_credentials")
public class NaukriCredential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String naukriEmail;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String encryptedPassword;

    @OneToOne
    @JoinColumn(
            name = "user_id",
            nullable = false,
            unique = true
    )
    private User user;

    public NaukriCredential() {
    }

    public Long getId() {
        return id;
    }

    public String getNaukriEmail() {
        return naukriEmail;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public User getUser() {
        return user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNaukriEmail(String naukriEmail) {
        this.naukriEmail = naukriEmail;
    }

    public void setEncryptedPassword(
            String encryptedPassword
    ) {
        this.encryptedPassword = encryptedPassword;
    }

    public void setUser(User user) {
        this.user = user;
    }
}