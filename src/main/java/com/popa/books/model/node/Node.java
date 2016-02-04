package com.popa.books.model.node;

public interface Node {

    boolean isLeaf();

    String getName();
    
    boolean isLoaded();
    
    String NOT_AVAILABLE_STR = "#";
    Long NOT_AVAILABLE_ID = -1L;
}
