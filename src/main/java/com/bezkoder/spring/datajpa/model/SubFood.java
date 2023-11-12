package com.bezkoder.spring.datajpa.model;

import javax.persistence.*;

@Entity
@Table(name = "subfood")
public class SubFood {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private double price;

    @Column(name = "pic")
    private String pic;

    @Column(name = "description")
    private String description;

    @Column(name = "published")
    private boolean published;


    @ManyToOne( cascade = CascadeType.ALL )
    @JoinColumn(name = "food_id")
    private Food food;

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public SubFood() {}


    public SubFood(String title, double price, String pic, String description, boolean published) {
        this.title = title;
        this.price = price;
        this.pic = pic;
        this.description = description;
        this.published = published;
    }

    public long getId() {
        return id;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public double getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
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
        return "subFood [id=" + id + ", title=" + title+ ", price=" + price + ",pic" + pic+ ", desc=" + description + ", published=" + published + "]";
    }


}
