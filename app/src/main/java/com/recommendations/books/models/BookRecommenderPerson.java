package com.recommendations.books.models;

import java.io.Serializable;
import java.util.ArrayList;

public class BookRecommenderPerson implements Serializable {

    public BookRecommenderPerson() {

    }


    String bookRecommenderId;
    String name;
    String profession;
    String bio;
    String imageUrl;
    String booksCount;
    boolean popular;
    String wikiLink;
    String twitterLink;
    String websiteLink;

    String genreTag1;
    String genreTag2;
    String genreTag3;
    String genreTag4;
    String genreTag5;
    String recommenderCountry;
    String recommenderKnownFor;

    private ArrayList<String> recommenderGenreTags;



    public String getBookRecommenderId() {
        return bookRecommenderId;
    }

    public void setBookRecommenderId(String bookRecommenderId) {
        this.bookRecommenderId = bookRecommenderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public String getWikiLink() {
        return wikiLink;
    }

    public void setWikiLink(String wikiLink) {
        this.wikiLink = wikiLink;
    }

    public String getTwitterLink() {
        return twitterLink;
    }

    public void setTwitterLink(String twitterLink) {
        this.twitterLink = twitterLink;
    }

    public String getWebsiteLink() {
        return websiteLink;
    }

    public void setWebsiteLink(String websiteLink) {
        this.websiteLink = websiteLink;
    }

    public String getBooksCount() {
        return booksCount;
    }

    public void setBooksCount(String booksCount) {
        this.booksCount = booksCount;
    }

    public boolean isPopular() {
        return popular;
    }

    public void setPopular(boolean popular) {
        this.popular = popular;
    }

    public String getGenreTag1() {
        return genreTag1;
    }

    public void setGenreTag1(String genreTag1) {
        this.genreTag1 = genreTag1;
    }

    public String getGenreTag2() {
        return genreTag2;
    }

    public void setGenreTag2(String genreTag2) {
        this.genreTag2 = genreTag2;
    }

    public String getGenreTag3() {
        return genreTag3;
    }

    public void setGenreTag3(String genreTag3) {
        this.genreTag3 = genreTag3;
    }

    public String getGenreTag4() {
        return genreTag4;
    }

    public void setGenreTag4(String genreTag4) {
        this.genreTag4 = genreTag4;
    }

    public String getGenreTag5() {
        return genreTag5;
    }

    public void setGenreTag5(String genreTag5) {
        this.genreTag5 = genreTag5;
    }

    public String getRecommenderCountry() {
        return recommenderCountry;
    }

    public void setRecommenderCountry(String recommenderCountry) {
        this.recommenderCountry = recommenderCountry;
    }

    public String getRecommenderKnownFor() {
        return recommenderKnownFor;
    }

    public void setRecommenderKnownFor(String recommenderKnownFor) {
        this.recommenderKnownFor = recommenderKnownFor;
    }

    public ArrayList<String> getRecommenderGenreTags() {
        return recommenderGenreTags;
    }

    public void setRecommenderGenreTags(ArrayList<String> recommenderGenreTags) {
        this.recommenderGenreTags = recommenderGenreTags;
    }
}
