package com.popa.books.model.node;

public class NodeSQL {

    private String name;
    private long count;

    public NodeSQL(final String name, final long count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public long getCount() {
        return count;
    }

}
