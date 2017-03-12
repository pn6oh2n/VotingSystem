package com.example.to;

import com.example.HasId;

abstract class BaseTo implements HasId {
    protected Integer id;

    BaseTo() {}

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}
