package com.carrot.restaurant_vote.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "restaurants", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Restaurant extends NamedEntity {
    @NotNull
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Restaurant(String name, User user) {
        this(null, name, user);
    }

    public Restaurant(Integer id, String name, User user) {
        super(id, name);
        this.user = user;
    }
}
