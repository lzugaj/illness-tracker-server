package com.luv2code.illnesstracker.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luv2code.illnesstracker.domain.base.BaseEntity;
import com.luv2code.illnesstracker.domain.enums.RoleType;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ROLE")
public class Role extends BaseEntity implements Serializable {

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private RoleType name;

    @Column(name = "description")
    private String description;

    @JsonIgnore
    @ToString.Exclude
    @ManyToMany(mappedBy = "roles")
    private List<User> users;

}
