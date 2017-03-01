package com.development.vvoitsekh.favoritequotes.data.model;


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
     */
    public Quote() {
    }

    /**
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
     * @return The quoteText
     */
    public String getQuoteText() {
        return quoteText;
    }

    /**
     * @param quoteText The quoteText
     */
    public void setQuoteText(String quoteText) {
        this.quoteText = quoteText;
    }

    /**
     * @return The quoteAuthor
     */
    public String getQuoteAuthor() {
        return quoteAuthor;
    }

    /**
     * @param quoteAuthor The quoteAuthor
     */
    public void setQuoteAuthor(String quoteAuthor) {
        this.quoteAuthor = quoteAuthor;
    }
}
