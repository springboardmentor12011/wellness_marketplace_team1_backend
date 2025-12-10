package com.infosys.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
	public class User {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable =false)
	    private String name;

	    @Column(unique = true)
	    private String email;

	    @Column(nullable =false)
	    private String password;

	    @Column(nullable =false)
	    private String Role;

	    @Column(nullable =false)
	    private String bio;

	    
	
}
