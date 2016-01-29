package com.popa.books.model.node;

public class EdituraNode extends AbstractNode {

    private long howManyEdituri;
    private long howManyBooks;

    public long getHowManyBooks() {
        return howManyBooks;
    }

    public void setHowManyBooks(long howManyBooks) {
        this.howManyBooks = howManyBooks;
    }

    public long getHowManyEdituri() {
        return howManyEdituri;
    }

    public void setHowManyEdituri(long howManyEdituri) {
        this.howManyEdituri = howManyEdituri;
    }
}
