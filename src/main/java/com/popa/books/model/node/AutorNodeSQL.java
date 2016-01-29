package com.popa.books.model.node;

public class AutorNodeSQL {

    private String authorName;
    private long howManyBooks;
    private long howManyAuthors;

    public AutorNodeSQL(final String authorName, final long howManyBooks) {
        this(authorName, howManyBooks, 0);
    }

    public AutorNodeSQL(final String authorName, final long howManyBooks, final long authorNumber) {
        this.authorName = authorName;
        this.howManyBooks = howManyBooks;
        this.howManyAuthors = authorNumber;
    }

    public String getAuthorName() {
        return authorName;
    }

    public long getHowManyBooks() {
        return howManyBooks;
    }

    public long getHowManyAuthors() {
        return howManyAuthors;
    }
}
