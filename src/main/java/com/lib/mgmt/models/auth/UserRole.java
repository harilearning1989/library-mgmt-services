package com.lib.mgmt.models.auth;

import javax.persistence.*;

@Table(name = "USER_ROLE")
@Entity
public class UserRole {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private String role;

	@JoinColumn(name = "user_id")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private User user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
