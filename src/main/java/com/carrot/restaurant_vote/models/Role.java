package com.carrot.restaurant_vote.models;

import com.carrot.restaurant_vote.security.RoleType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "roles")
public class Role extends BaseEntity {
    @NotBlank
    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false)
    private RoleType name;

    public Role(RoleType name) {
        this(null, name);
    }

    public Role(Integer id, RoleType name) {
        super(id);
        this.name = name;
    }
}
