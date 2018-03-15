package com.galvin.interview.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries (
        @NamedQuery (name = "BlogPost.findAll",
                query = "SELECT bp FROM BlogPost bp")
)
@Table (name = "BLOGPOST")
public class BlogPost {
    @Id
    @GeneratedValue (generator = "blogpost-uuid")
    @GenericGenerator (name = "blogpost-uuid", strategy = "uuid2")
    @Column (name = "uuid", unique = true, nullable = false)
    private String uuid;

    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "pub_date")
    private long pubDate;

    @Column(name = "author", length = 100)
    private String author;

    @Column(name = "author_email", length = 50)
    private String authorEmail;

    @Column(name = "pull_quote", length = 100)
    private String pullQuote;

    @Column(name = "pull_quote_as_html", length = 2000)
    private String pullQuoteAsHtml;

    @Column(name = "body", length = 2000)
    private String body;

    @Column(name = "body_as_html", length = 2000)
    private String bodyAsHtml;

    public BlogPost() {}

    public BlogPost(String uuid, String title, long pubDate, String author, String authorEmail,
                    String pullQuote, String pullQuoteAsHtml, String body, String bodyAsHtml) {
        this.uuid = uuid;
        this.title = title;
        this.pubDate = pubDate;
        this.author = author;
        this.authorEmail = authorEmail;
        this.pullQuote = pullQuote;
        this.pullQuoteAsHtml = pullQuoteAsHtml;
        this.body = body;
        this.bodyAsHtml = bodyAsHtml;
    }

    public String getUuid () {
        return uuid;
    }

    public void setUuid (String uuid) {
        this.uuid = uuid;
    }

    public String getTitle () {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public long getPubDate () {
        return pubDate;
    }

    public void setPubDate (long pubDate) {
        this.pubDate = pubDate;
    }

    public String getAuthor () {
        return author;
    }

    public void setAuthor (String author) {
        this.author = author;
    }

    public String getAuthorEmail () {
        return authorEmail;
    }

    public void setAuthorEmail (String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public String getPullQuote () {
        return pullQuote;
    }

    public void setPullQuote (String pullQuote) {
        this.pullQuote = pullQuote;
    }

    public String getPullQuoteAsHtml () {
        return pullQuoteAsHtml;
    }

    public void setPullQuoteAsHtml (String pullQuoteAsHtml) {
        this.pullQuoteAsHtml = pullQuoteAsHtml;
    }

    public String getBody () {
        return body;
    }

    public void setBody (String body) {
        this.body = body;
    }

    public String getBodyAsHtml () {
        return bodyAsHtml;
    }

    public void setBodyAsHtml (String bodyAsHtml) {
        this.bodyAsHtml = bodyAsHtml;
    }
}