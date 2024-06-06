package org.listingbotv1.listingbot.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class SearchProfile {
    //TO DO
    // lease term,
    // address
    // Matching Criteria
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double MinHouseSize;
    private double MaxHouseSize;
    private double MinPrice;
    private double MaxPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    public SearchProfile() {
    }

    public SearchProfile(double minHouseSize, double maxHouseSize, double minPrice, double maxPrice) {
        MinHouseSize = minHouseSize;
        MaxHouseSize = maxHouseSize;
        MinPrice = minPrice;
        MaxPrice = maxPrice;
    }

    public double getMinHouseSize() {
        return MinHouseSize;
    }

    public void setMinHouseSize(double minHouseSize) {
        MinHouseSize = minHouseSize;
    }

    public double getMaxHouseSize() {
        return MaxHouseSize;
    }

    public void setMaxHouseSize(double maxHouseSize) {
        MaxHouseSize = maxHouseSize;
    }

    public double getMinPrice() {
        return MinPrice;
    }

    public void setMinPrice(double minPrice) {
        MinPrice = minPrice;
    }

    public double getMaxPrice() {
        return MaxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        MaxPrice = maxPrice;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Listing){
            Listing listing = (Listing) obj;
            if(listing.gethouse_size() >= this.MinHouseSize && listing.gethouse_size() <= this.MaxHouseSize){
                if(listing.getPrice() >= this.MinPrice && listing.getPrice() <= this.MaxPrice){
                    return true;
                }
            }
        }
        return false;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public Long getId() {
        return id;
    }
}
