package com.luv2code.illnesstracker.domain.illness.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luv2code.illnesstracker.domain.Patient;
import com.luv2code.illnesstracker.domain.base.BaseIllness;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
public class DiabetesMellitusTypeII extends BaseIllness {

    @Column(name = "guk0")
    @NotNull(message = "{validation.dmt.guk_0.not_null}")
    private Double guk0;

    @Column(name = "guk1")
    @NotNull(message = "{validation.dmt.guk_1.not_null}")
    private Double guk1;

    @ManyToMany
    @JsonIgnore
    @ToString.Exclude
    @JoinTable(
            name = "PATIENT_DIABETES_MELLITUS_TYPE_II",
            joinColumns = @JoinColumn(name = "dmt_id"),
            inverseJoinColumns = @JoinColumn(name = "patient_id"))
    private List<Patient> patients;

}
