package com.luv2code.illnesstracker.domain.illness;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luv2code.illnesstracker.domain.Patient;
import com.luv2code.illnesstracker.domain.base.BaseIllness;
import com.luv2code.illnesstracker.domain.info.BodyMassIndexInfo;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BODY_MASS_INDEX")
@EqualsAndHashCode(callSuper = true)
public class BodyMassIndex extends BaseIllness {

    @Column(name = "height")
    @NotNull(message = "{validation.bmi.height.not_null}")
    private Double height;

    @Column(name = "weight")
    @NotNull(message = "{validation.bmi.weight.not_null}")
    private Double weight;

    @Column(name = "index_value")
    private Double indexValue;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "bmi_info_id", nullable = false)
    private BodyMassIndexInfo bodyMassIndexInfo;

    @ManyToMany
    @JsonIgnore
    @ToString.Exclude
    @JoinTable(
            name = "PATIENT_BODY_MASS_INDEX",
            joinColumns = @JoinColumn(name = "bmi_id"),
            inverseJoinColumns = @JoinColumn(name = "patient_id"))
    private List<Patient> patients;

}
