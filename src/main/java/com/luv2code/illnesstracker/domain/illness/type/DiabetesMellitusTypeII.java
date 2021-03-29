package com.luv2code.illnesstracker.domain.illness.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luv2code.illnesstracker.domain.User;
import com.luv2code.illnesstracker.domain.base.BaseIllness;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DIABETES_MELLITUS_TYPE_II")
@EqualsAndHashCode(callSuper = true)
public class DiabetesMellitusTypeII extends BaseIllness implements Serializable {

    @Column(name = "guk0")
    @NotNull(message = "{validation.dmt.guk_0.not_null}")
    private Double guk0;

    @Column(name = "guk2")
    @NotNull(message = "{validation.dmt.guk_2.not_null}")
    private Double guk2;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @JsonIgnore
    @ToString.Exclude
    @JoinTable(
            name = "USER_DIABETES_MELLITUS_TYPE_II",
            joinColumns = @JoinColumn(name = "dmt_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

}
