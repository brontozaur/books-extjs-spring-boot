package com.popa.books.model.node;

public class NodeSQL {

    private String name;
    private long id;
    private long count;

    public NodeSQL(final long id, final String name, final long count) {
        this.id = id;
        this.name = name;
        this.count = count;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getCount() {
        return count;
    }

}
