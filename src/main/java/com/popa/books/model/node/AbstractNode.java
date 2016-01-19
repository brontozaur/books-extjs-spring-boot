package com.popa.books.model.node;

public abstract class AbstractNode implements Node {

    private String name;
    private boolean leaf;
    private boolean loaded;
    private String id;

    @Override
    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(final boolean leaf) {
        this.leaf = leaf;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
