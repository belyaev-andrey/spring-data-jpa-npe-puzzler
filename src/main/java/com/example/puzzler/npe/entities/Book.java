package com.example.puzzler.npe.entities;

import javax.persistence.*;

@Entity
@Table(name = "book")
public class Book {

    @EmbeddedId
    private BookId bookId;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @MapsId("authorId")
    private Author author;

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BookId getBookId() {
        return bookId;
    }

    public void setBookId(BookId bookId) {
        this.bookId = bookId;
    }
}