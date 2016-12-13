package com.development.vvoitsekh.favoritequotes.data.model;

/**
 * Created by v.voitsekh on 10.11.2016.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Quote {

    @SerializedName("quoteText")
    @Expose
    private String quoteText;
    @SerializedName("quoteAuthor")
    @Expose
    private String quoteAuthor;
    @SerializedName("senderName")
    @Expose
    private String senderName;
    @SerializedName("senderLink")
    @Expose
    private String senderLink;
    @SerializedName("quoteLink")
    @Expose
    private String quoteLink;

    /**
     * No args constructor for use in serialization
     *
     */
    public Quote() {
    }

    /**
     *
     * @param quoteText
     * @param senderLink
     * @param senderName
     * @param quoteLink
     * @param quoteAuthor
     */
    public Quote(String quoteText, String quoteAuthor, String senderName, String senderLink, String quoteLink) {
        this.quoteText = quoteText;
        this.quoteAuthor = quoteAuthor;
        this.senderName = senderName;
        this.senderLink = senderLink;
        this.quoteLink = quoteLink;
    }

    public Quote(String quoteText, String quoteAuthor) {
        this.quoteText = quoteText;
        this.quoteAuthor = quoteAuthor;
    }

    /**
     *
     * @return
     * The quoteText
     */
    public String getQuoteText() {
        return quoteText;
    }

    /**
     *
     * @param quoteText
     * The quoteText
     */
    public void setQuoteText(String quoteText) {
        this.quoteText = quoteText;
    }

    /**
     *
     * @return
     * The quoteAuthor
     */
    public String getQuoteAuthor() {
        return quoteAuthor;
    }

    /**
     *
     * @param quoteAuthor
     * The quoteAuthor
     */
    public void setQuoteAuthor(String quoteAuthor) {
        this.quoteAuthor = quoteAuthor;
    }

    /**
     *
     * @return
     * The senderName
     */
    public String getSenderName() {
        return senderName;
    }

    /**
     *
     * @param senderName
     * The senderName
     */
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    /**
     *
     * @return
     * The senderLink
     */
    public String getSenderLink() {
        return senderLink;
    }

    /**
     *
     * @param senderLink
     * The senderLink
     */
    public void setSenderLink(String senderLink) {
        this.senderLink = senderLink;
    }

    /**
     *
     * @return
     * The quoteLink
     */
    public String getQuoteLink() {
        return quoteLink;
    }

    /**
     *
     * @param quoteLink
     * The quoteLink
     */
    public void setQuoteLink(String quoteLink) {
        this.quoteLink = quoteLink;
    }

}
//
//    private int id;
//    private String text;
//    private String author;
//
//    public Quote(int id, String text, String author) {
//        this.id = id;
//        this.text = text;
//        this.author = author;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getText() {
//        return text;
//    }
//
//    public void setText(String text) {
//        this.text = text;
//    }
//
//    public String getAuthor() {
//        return author;
//    }
//
//    public void setAuthor(String author) {
//        this.author = author;
//    }
//}
