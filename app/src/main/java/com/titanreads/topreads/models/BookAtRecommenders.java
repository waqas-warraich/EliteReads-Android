package com.titanreads.topreads.models;

import java.io.Serializable;
import java.util.ArrayList;

public class BookAtRecommenders implements Serializable {

    public BookAtRecommenders(){}

    String bookId;
    String bookName;
    String bookAuthor;
    String bookReleaseDate;
    String bookDescription;
    String bookImageUrl;

    String bookIsPremium;



    private ArrayList<String> bookFavoritesIds;
    private ArrayList<String> bookRecommendersIds;
    private ArrayList<String> bookRecommendersQuotes;
    private ArrayList<String> bookRecommendersNames;

   /*private ArrayList<String> profilePdfDownloadIds;
    private ArrayList<String> profileTorrentDownloadIds;*/

    String bookRating;
    String bookRatingReviewsCount;

    String bookReview;
    String bookSummary;

    String bookSummaryAudioUrl;
    String bookSummaryVideoUrl;
    String bookSummaryArticleUrl;

    String bookPedfffLink;
    String bookEpbbbLink;
    String bookAmazonLink;
    String bookTorrentLink;

    String bookTTLink;
    String bookPdvDDLink;

    private ArrayList<String> bookPdvDDIds;
    private ArrayList<String> bookAmazonBuyIds;
    private ArrayList<String> bookAffiliateLink1;
    private ArrayList<String> bookAffiliateLink2;
    private ArrayList<String> bookAffiliateLink3;
    private ArrayList<String> bookAffiliateLink4;
    private ArrayList<String> bookTTDDsIds;

    private ArrayList<String> bookGenreTags;
    private ArrayList<String> bookRecommendersGenreTags;


    String bookRecommenderQuote;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public String getBookImageUrl() {
        return bookImageUrl;
    }

    public void setBookImageUrl(String bookImageUrl) {
        this.bookImageUrl = bookImageUrl;
    }

    public ArrayList<String> getBookFavoritesIds() {
        return bookFavoritesIds;
    }

    public void setBookFavoritesIds(ArrayList<String> bookFavoritesIds) {
        this.bookFavoritesIds = bookFavoritesIds;
    }

    public ArrayList<String> getBookRecommendersIds() {
        return bookRecommendersIds;
    }

    public void setBookRecommendersIds(ArrayList<String> bookRecommendersIds) {
        this.bookRecommendersIds = bookRecommendersIds;
    }

    public ArrayList<String> getBookRecommendersNames() {
        return bookRecommendersNames;
    }

    public void setBookRecommendersNames(ArrayList<String> bookRecommendersNames) {
        this.bookRecommendersNames = bookRecommendersNames;
    }

    public String getBookReview() {
        return bookReview;
    }

    public void setBookReview(String bookReview) {
        this.bookReview = bookReview;
    }

    public String getBookSummary() {
        return bookSummary;
    }

    public void setBookSummary(String bookSummary) {
        this.bookSummary = bookSummary;
    }

    public String getBookPedfffLink() {
        return bookPedfffLink;
    }

    public void setBookPedfffLink(String bookPedfffLink) {
        this.bookPedfffLink = bookPedfffLink;
    }

    public String getBookAmazonLink() {
        return bookAmazonLink;
    }

    public void setBookAmazonLink(String bookAmazonLink) {
        this.bookAmazonLink = bookAmazonLink;
    }

    public String getBookTorrentLink() {
        return bookTorrentLink;
    }

    public void setBookTorrentLink(String bookTorrentLink) {
        this.bookTorrentLink = bookTorrentLink;
    }

    public String getBookRecommenderQuote() {
        return bookRecommenderQuote;
    }

    public void setBookRecommenderQuote(String bookRecommenderQuote) {
        this.bookRecommenderQuote = bookRecommenderQuote;
    }

    public ArrayList<String> getBookRecommendersQuotes() {
        return bookRecommendersQuotes;
    }

    public void setBookRecommendersQuotes(ArrayList<String> bookRecommendersQuotes) {
        this.bookRecommendersQuotes = bookRecommendersQuotes;
    }


    public ArrayList<String> getBookPdvDDIds() {
        return bookPdvDDIds;
    }

    public void setBookPdvDDIds(ArrayList<String> bookPdvDDIds) {
        this.bookPdvDDIds = bookPdvDDIds;
    }

    public ArrayList<String> getBookAmazonBuyIds() {
        return bookAmazonBuyIds;
    }

    public void setBookAmazonBuyIds(ArrayList<String> bookAmazonBuyIds) {
        this.bookAmazonBuyIds = bookAmazonBuyIds;
    }

    public ArrayList<String> getBookTTDDsIds() {
        return bookTTDDsIds;
    }

    public void setBookTTDDsIds(ArrayList<String> bookTTDDsIds) {
        this.bookTTDDsIds = bookTTDDsIds;
    }



    public String getBookEpbbbLink() {
        return bookEpbbbLink;
    }

    public void setBookEpbbbLink(String bookEpbbbLink) {
        this.bookEpbbbLink = bookEpbbbLink;
    }

    public String getBookReleaseDate() {
        return bookReleaseDate;
    }

    public void setBookReleaseDate(String bookReleaseDate) {
        this.bookReleaseDate = bookReleaseDate;
    }

    public String getBookRating() {
        return bookRating;
    }

    public void setBookRating(String bookRating) {
        this.bookRating = bookRating;
    }

    public String getBookRatingReviewsCount() {
        return bookRatingReviewsCount;
    }

    public void setBookRatingReviewsCount(String bookRatingReviewsCount) {
        this.bookRatingReviewsCount = bookRatingReviewsCount;
    }

    public String getBookSummaryAudioUrl() {
        return bookSummaryAudioUrl;
    }

    public void setBookSummaryAudioUrl(String bookSummaryAudioUrl) {
        this.bookSummaryAudioUrl = bookSummaryAudioUrl;
    }

    public String getBookSummaryVideoUrl() {
        return bookSummaryVideoUrl;
    }

    public void setBookSummaryVideoUrl(String bookSummaryVideoUrl) {
        this.bookSummaryVideoUrl = bookSummaryVideoUrl;
    }

    public String getBookSummaryArticleUrl() {
        return bookSummaryArticleUrl;
    }

    public void setBookSummaryArticleUrl(String bookSummaryArticleUrl) {
        this.bookSummaryArticleUrl = bookSummaryArticleUrl;
    }

    public ArrayList<String> getBookGenreTags() {
        return bookGenreTags;
    }

    public void setBookGenreTags(ArrayList<String> bookGenreTags) {
        this.bookGenreTags = bookGenreTags;
    }

    public String getBookTTLink() {
        return bookTTLink;
    }

    public void setBookTTLink(String bookTTLink) {
        this.bookTTLink = bookTTLink;
    }

    public String getBookPdvDDLink() {
        return bookPdvDDLink;
    }

    public void setBookPdvDDLink(String bookPdvDDLink) {
        this.bookPdvDDLink = bookPdvDDLink;
    }

    public ArrayList<String> getBookAffiliateLink1() {
        return bookAffiliateLink1;
    }

    public void setBookAffiliateLink1(ArrayList<String> bookAffiliateLink1) {
        this.bookAffiliateLink1 = bookAffiliateLink1;
    }

    public ArrayList<String> getBookAffiliateLink2() {
        return bookAffiliateLink2;
    }

    public void setBookAffiliateLink2(ArrayList<String> bookAffiliateLink2) {
        this.bookAffiliateLink2 = bookAffiliateLink2;
    }

    public ArrayList<String> getBookAffiliateLink3() {
        return bookAffiliateLink3;
    }

    public void setBookAffiliateLink3(ArrayList<String> bookAffiliateLink3) {
        this.bookAffiliateLink3 = bookAffiliateLink3;
    }

    public ArrayList<String> getBookAffiliateLink4() {
        return bookAffiliateLink4;
    }

    public void setBookAffiliateLink4(ArrayList<String> bookAffiliateLink4) {
        this.bookAffiliateLink4 = bookAffiliateLink4;
    }

    public ArrayList<String> getBookRecommendersGenreTags() {
        return bookRecommendersGenreTags;
    }

    public void setBookRecommendersGenreTags(ArrayList<String> bookRecommendersGenreTags) {
        this.bookRecommendersGenreTags = bookRecommendersGenreTags;
    }

    public String getBookIsPremium() {
        return bookIsPremium;
    }

    public void setBookIsPremium(String bookIsPremium) {
        this.bookIsPremium = bookIsPremium;
    }
}
