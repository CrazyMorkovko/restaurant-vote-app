package com.carrot.restaurant_vote.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@MappedSuperclass
public abstract class NamedEntity extends BaseEntity {
    @NotBlank
    @Size(min = 1, max = 100)
    @Column(name = "name", nullable = false)
    protected String name;

    protected NamedEntity() {

    }

    public NamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }
}
