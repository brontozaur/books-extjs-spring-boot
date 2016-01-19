package com.popa.books.controller;

public interface Events {

    String SAVE_BOOK = "save-book";
    String DEL_BOOK = "del-book";
    String GET_BOOKS = "get-books";

    String GET_AUTORS = "get-autors";
    String DEL_AUTOR = "del-autor";
    String SAVE_AUTOR = "save-autor";

    String GET_EDITURI = "get-edituri";
    String DEL_EDITURA = "del-editura";
    String SAVE_EDITURA = "save-editura";

    String GET_CATEGORII = "get-categorii";
    String DEL_CATEGORIE = "del-categorie";
    String SAVE_CATEGORIE = "save-categorie";

    String GET_TREE_AUTORI = "get-tree-autori";
    String GET_TREE_BOOKS = "get-tree-books";
    String GET_TREE_EDITURI = "get-tree-edituri";

    String IMAGE_LOADER = "image-loader";
    String DELETE_UPLOAD = "del-upload";
}
