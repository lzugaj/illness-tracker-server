package com.luv2code.illnesstracker.domain.illness.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luv2code.illnesstracker.domain.User;
import com.luv2code.illnesstracker.domain.base.BaseIllness;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "GASTRO_ESOPHAGEAL_REFLUX")
@EqualsAndHashCode(callSuper = true)
public class GastroEsophagealReflux extends BaseIllness implements Serializable {

    @Column(name = "datetime_of_last_meal")
    @NotNull(message = "{validation.patient.datetime_of_last_meal.not_null}")
    private LocalDateTime datetimeOfLastMeal;

    @Column(name = "datetime_of_onset_of_symptoms")
    @NotNull(message = "{validation.patient.datetime_of_onset_of_symptoms.not_null}")
    private LocalDateTime datetimeOfOnsetOfSymptoms;

    @ManyToMany
    @JsonIgnore
    @ToString.Exclude
    @JoinTable(
            name = "USER_GASTRO_ESOPHAGEAL_REFLUX",
            joinColumns = @JoinColumn(name = "ger_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

}
