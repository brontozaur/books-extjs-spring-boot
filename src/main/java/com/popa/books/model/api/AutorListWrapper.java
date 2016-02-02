package com.popa.books.model.api;

import com.popa.books.model.Autor;

import java.util.List;

public class AutorListWrapper {

    private Long totalCount;
    private List<Autor> autorList;

    public AutorListWrapper(Long totalCount, List<Autor> autorList) {
        this.totalCount = totalCount;
        this.autorList = autorList;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public List<Autor> getAutorList() {
        return autorList;
    }

    public void setAutorList(List<Autor> autorList) {
        this.autorList = autorList;
    }
}
