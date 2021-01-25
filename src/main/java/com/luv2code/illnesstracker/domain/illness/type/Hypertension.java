package com.luv2code.illnesstracker.domain.illness.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luv2code.illnesstracker.domain.User;
import com.luv2code.illnesstracker.domain.base.BaseIllness;
import com.luv2code.illnesstracker.domain.info.HypertensionInfo;
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
@Table(name = "HYPERTENSION")
@EqualsAndHashCode(callSuper = true)
public class Hypertension extends BaseIllness implements Serializable {

    @Column(name = "systolic")
    @NotNull(message = "{validation.hypertension.systolic.not_null}")
    private Integer systolic;

    @Column(name = "diastolic")
    @NotNull(message = "{validation.hypertension.diastolic.not_null}")
    private Integer diastolic;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "hypertension_info_id", nullable = false)
    private HypertensionInfo hypertensionInfo;

    @ManyToMany
    @JsonIgnore
    @ToString.Exclude
    @JoinTable(
            name = "USER_HYPERTENSION",
            joinColumns = @JoinColumn(name = "hypertension_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

}
