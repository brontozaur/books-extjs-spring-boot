package com.popa.books.servlet.bean;

public interface Node {

    boolean isLeaf();

    String getName();
    
    boolean isLoaded();
    
    String ALL = "#";
}
