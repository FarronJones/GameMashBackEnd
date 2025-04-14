package com.gamemash.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "player")
public class Player {

    @Id
    @Column(name = "UserID")
    private int userId;

    @Column(name = "Password")
    private String password;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "Email")
    private String email;

    @Column(name = "RegistrationDate")
    private Timestamp registrationDate;

    @Column(name = "LastLoginDate")
    private Timestamp lastLoginDate;
    
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public String getEmail() {
		// TODO Auto-generated method stub
		return email;
	}

	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

    // Add avatar, language, savedProgress, achievements if needed

    // Getters and setters
}