package com.example.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
public class User extends NamedEntity{

	@NotBlank
	@Length(min = 4)
	@SafeHtml
	private String password;

	@Enumerated(EnumType.STRING)
	private Role role;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + getId() +
				", name='" + getName() + "'" +
				", password='" + password + "'" +
				//", votes=" + votes +
				", role=" + role +
				'}';
	}
}