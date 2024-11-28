package com.jddev.velemenyezz.review.impl.model;

import jakarta.persistence.*;

@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "business_id", nullable = false)
    private Long businessID;

    @Column(name = "email")
    private String email;

    @Column(name = "text", length = 1500)
    private String text;

    @Column(name = "rating")
    private Short rating;



    protected Review(){}

    public Long getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Long businessID) {
        this.businessID = businessID;
    }

    public Short getRating() {
        return rating;
    }

    public void setRating(Short rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static class Builder{
        Review review;
        public Builder(){
            review = new Review();
        }

        public Builder withBusinessID(Long business){
            review.setBusinessID(business);
            return this;
        }

        public Builder withEmail(String email){
            review.setEmail(email);
            return this;
        }

        public Builder withText(String text){
            review.setText(text);
            return this;
        }

        public Builder withRating(Short rating){
            review.setRating(rating);
            return this;
        }

        public Review build(){
            return review;
        }
    }

}