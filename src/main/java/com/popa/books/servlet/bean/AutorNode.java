package com.popa.books.servlet.bean;

public class AutorNode extends AbstractNode {

    private int howManyAutors;
    private int howManyBooks;

    public int getHowManyAutors() {
        return howManyAutors;
    }

    public void setHowManyAutors(int howManyAutors) {
        this.howManyAutors = howManyAutors;
    }

    public int getHowManyBooks() {
        return howManyBooks;
    }

    public void setHowManyBooks(int howManyBooks) {
        this.howManyBooks = howManyBooks;
    }
}
