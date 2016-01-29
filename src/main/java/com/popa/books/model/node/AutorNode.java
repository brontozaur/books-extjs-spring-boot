package com.popa.books.model.node;

public class AutorNode extends AbstractNode {

    private long howManyAutors;
    private long howManyBooks;

    public long getHowManyAutors() {
        return howManyAutors;
    }

    public void setHowManyAutors(long howManyAutors) {
        this.howManyAutors = howManyAutors;
    }

    public long getHowManyBooks() {
        return howManyBooks;
    }

    public void setHowManyBooks(long howManyBooks) {
        this.howManyBooks = howManyBooks;
    }
}
