package com.example.app.data.dto;

import org.hibernate.annotations.Type;

import java.util.UUID;

public class AbstractDTO {


    @Type(type = "uuid-char")
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
