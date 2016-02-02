package com.popa.books.model.api;

import com.popa.books.model.Editura;

import java.util.List;

public class EdituraListWrapper {

    private Long totalCount;
    private List<Editura> edituraList;

    public EdituraListWrapper(Long totalCount, List<Editura> edituraList) {
        this.totalCount = totalCount;
        this.edituraList = edituraList;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public List<Editura> getEdituraList() {
        return edituraList;
    }

    public void setEdituraList(List<Editura> edituraList) {
        this.edituraList = edituraList;
    }
}
