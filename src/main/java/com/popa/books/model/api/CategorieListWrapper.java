package com.popa.books.model.api;

import com.popa.books.model.Categorie;

import java.util.List;

public class CategorieListWrapper {

    private Long totalCount;
    private List<Categorie> categorieList;

    public CategorieListWrapper(Long totalCount, List<Categorie> categorieList) {
        this.totalCount = totalCount;
        this.categorieList = categorieList;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public List<Categorie> getCategorieList() {
        return categorieList;
    }

    public void setCategorieList(List<Categorie> categorieList) {
        this.categorieList = categorieList;
    }
}
