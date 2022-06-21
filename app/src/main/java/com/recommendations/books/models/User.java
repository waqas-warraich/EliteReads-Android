package com.recommendations.books.models;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.ArrayList;
import java.util.Date;

public class User {


    public User(){

    }
    String name;
    String email;
    String userImageUrl;
    String password;
    String UserId;

    ArrayList<String> recentBookViews;
    ArrayList<String> favoriteBooksIds;
    ArrayList<String> favoriteRecommendersIds;
    ArrayList<String> allViewedBooksIds;
    ArrayList<String> favoriteBooksGenre;
    ArrayList<String> favoriteRecommendersGenre;

    @ServerTimestamp
    private Date date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public ArrayList<String> getRecentBookViews() {
        return recentBookViews;
    }

    public void setRecentBookViews(ArrayList<String> recentBookViews) {
        this.recentBookViews = recentBookViews;
    }

    public ArrayList<String> getFavoriteBooksIds() {
        return favoriteBooksIds;
    }

    public void setFavoriteBooksIds(ArrayList<String> favoriteBooksIds) {
        this.favoriteBooksIds = favoriteBooksIds;
    }

    public ArrayList<String> getFavoriteRecommendersIds() {
        return favoriteRecommendersIds;
    }

    public void setFavoriteRecommendersIds(ArrayList<String> favoriteRecommendersIds) {
        this.favoriteRecommendersIds = favoriteRecommendersIds;
    }

    public ArrayList<String> getAllViewedBooksIds() {
        return allViewedBooksIds;
    }

    public void setAllViewedBooksIds(ArrayList<String> allViewedBooksIds) {
        this.allViewedBooksIds = allViewedBooksIds;
    }

    public ArrayList<String> getFavoriteBooksGenre() {
        return favoriteBooksGenre;
    }

    public void setFavoriteBooksGenre(ArrayList<String> favoriteBooksGenre) {
        this.favoriteBooksGenre = favoriteBooksGenre;
    }

    public ArrayList<String> getFavoriteRecommendersGenre() {
        return favoriteRecommendersGenre;
    }

    public void setFavoriteRecommendersGenre(ArrayList<String> favoriteRecommendersGenre) {
        this.favoriteRecommendersGenre = favoriteRecommendersGenre;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
