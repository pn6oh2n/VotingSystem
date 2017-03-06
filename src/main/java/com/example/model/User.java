package com.example.model;

import com.example.View;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import java.util.List;

@Entity
//@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
public class User extends NamedEntity{

	//@Column(name = "password", nullable = false)
	@NotBlank
	@Length(min = 5)
	@JsonView(View.REST.class)
	@SafeHtml
	public String password;

	@OneToMany(fetch = FetchType.LAZY)
	List<Vote> votes;

	@Enumerated(EnumType.STRING)
	private Role role;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Vote> getVotes() {
		return votes;
	}

	public void setVotes(List<Vote> votes) {
		this.votes = votes;
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
				"id=" + password +
				", password='" + password + "'" +
				", votes=" + votes +
				", role=" + role +
				'}';
	}
}