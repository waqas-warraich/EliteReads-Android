package com.titanreads.topreads.models;

public class RecommendationQuote  {

    public RecommendationQuote(){

    }

    String quote;
    String recommenderName;
    String recommenderImageUrl;
    String quoteSourceLink;


    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getRecommenderName() {
        return recommenderName;
    }

    public void setRecommenderName(String recommenderName) {
        this.recommenderName = recommenderName;
    }

    public String getRecommenderImageUrl() {
        return recommenderImageUrl;
    }

    public void setRecommenderImageUrl(String recommenderImageUrl) {
        this.recommenderImageUrl = recommenderImageUrl;
    }

    public String getQuoteSourceLink() {
        return quoteSourceLink;
    }

    public void setQuoteSourceLink(String quoteSourceLink) {
        this.quoteSourceLink = quoteSourceLink;
    }
}
