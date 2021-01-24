package com.luv2code.illnesstracker.domain.illness.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luv2code.illnesstracker.domain.Patient;
import com.luv2code.illnesstracker.domain.base.BaseIllness;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PAINFUL_SYNDROME")
@EqualsAndHashCode(callSuper = true)
public class PainfulSyndrome extends BaseIllness {

    @Column(name = "body_part")
    @NotBlank(message = "{validation.ps.body_part.not_blank}")
    private String bodyPart;

    @Column(name = "description")
    @NotBlank(message = "{validation.ps.description.not_blank}")
    private String description;

    @Column(name = "vas_value")
    @NotNull(message = "{validation.ps.vas_value.not_null}")
    private Integer vasValue;

    @ManyToMany
    @JsonIgnore
    @ToString.Exclude
    @JoinTable(
            name = "PATIENT_PAINFUL_SYNDROME",
            joinColumns = @JoinColumn(name = "ps_id"),
            inverseJoinColumns = @JoinColumn(name = "patient_id"))
    private List<Patient> patients;

}
