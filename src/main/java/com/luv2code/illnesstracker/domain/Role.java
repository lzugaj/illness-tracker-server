package com.luv2code.illnesstracker.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luv2code.illnesstracker.domain.base.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ROLE")
public class Role extends BaseEntity {

    private String name;

    @JsonIgnore
    @ToString.Exclude
    @ManyToMany(mappedBy = "roles")
    private List<Patient> patients;

}
