package com.antplatform.admin.core.model.entity;


public abstract class AbstractDomainObject implements DomainObject {

    private static final long serialVersionUID = -1026311526086575034L;


    private boolean changed;


    public boolean hasChanged() {
        return changed;
    }

    protected void causeChanged() {
        this.changed = true;
    }

    @Override
    public String toString() {

        return String.format("Entity of type %s with id: %s", this.getClass().getName());
    }
}
