package com.bezkoder.spring.datajpa.model;

import javax.persistence.*;

@Entity
@Table(name = "food")
public class Food {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "title")
	private String title;



	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "price")
	private long price;

	@Column(name = "image")
	private String image;

	@Column(name = "description")
	private String description;

	@Column(name = "published")
	private boolean published;

	@ManyToOne( cascade = CascadeType.ALL )
	@JoinColumn(name = "user_id")
	private User user;

	public Food() {

	}

	public Food(String title, long price, String image, String description, boolean published) {
		this.title = title;
		this.price = price;
		this.image = image;
		this.description = description;
		this.published = published;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

     public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean isPublished) {
		this.published = isPublished;
	}

	@Override
	public String toString() {
		return "Command [id=" + id + ", title=" + title + ", desc=" + description + ", published=" + published + "]";
	}

}
