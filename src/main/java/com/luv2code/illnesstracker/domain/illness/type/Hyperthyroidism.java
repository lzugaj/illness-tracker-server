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
@Table(name = "HYPERTHYROIDISM")
@EqualsAndHashCode(callSuper = true)
public class Hyperthyroidism extends BaseIllness implements Serializable {

    @Column(name = "tsh")
    @NotNull(message = "{validation.hyperthyroidism.tsh.not_null}")
    private Double tsh;

    @Column(name = "ft3")
    @NotNull(message = "{validation.hyperthyroidism.ft3.not_null}")
    private Double ft3;

    @Column(name = "ft4")
    @NotNull(message = "{validation.hyperthyroidism.ft4.not_null}")
    private Double ft4;

    @ManyToMany
    @JsonIgnore
    @ToString.Exclude
    @JoinTable(
            name = "USER_HYPERTHYROIDISM",
            joinColumns = @JoinColumn(name = "hyperthyroidism_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

}
