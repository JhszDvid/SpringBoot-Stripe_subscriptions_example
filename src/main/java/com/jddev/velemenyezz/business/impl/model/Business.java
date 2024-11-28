package com.jddev.velemenyezz.business.impl.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "business")
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "owner_email", nullable = false)
    private String ownerEmail;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "website_url")
    private String websiteUrl;

    @Column(name = "google_review", length = 500)
    private String googleReview;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    protected Business(){}

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getGoogleReview() {
        return googleReview;
    }

    public void setGoogleReview(String googleReview) {
        this.googleReview = googleReview;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public static class Builder {
        Business business;
        public Builder(){
            business = new Business();
        }

        public Builder withOwner(String ownerEmail){
            business.setOwnerEmail(ownerEmail);
            return this;
        }

        public Builder withName(String businessName){
            business.setName(businessName);
            return this;
        }

        public Builder withLocation(String location){
            business.setLocation(location);
            return this;
        }

        public Builder withWebsiteURL(String websiteURL){
            business.setWebsiteUrl(websiteURL);
            return this;
        }

        public Builder withGoogleReview(String googleReview){
            business.setGoogleReview(googleReview);
            return this;
        }

        public Business build(){
            business.setUpdatedAt(LocalDateTime.now());
            return business;
        }
    }
}