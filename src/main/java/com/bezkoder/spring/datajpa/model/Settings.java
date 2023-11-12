package com.bezkoder.spring.datajpa.model;

import javax.persistence.*;

@Entity
@Table(name = "settings")
public class Settings {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "language")
	private String language;

	@Column(name = "currency")
	private String currency;

	@Column(name = "natureCompany")
	private String natureCompany;

	@Column(name = "tableRoomNumber")
	private String tableRoomNumber;


	@ManyToOne( cascade = CascadeType.ALL )
	@JoinColumn(name = "user_id")
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Settings() {

	}

	public Settings(String language, String currency, String natureCompany, String tableRoomNumber) {
		this.language = language;
		this.currency = currency;
		this.natureCompany = natureCompany;
		this.tableRoomNumber = tableRoomNumber;
	}
}
