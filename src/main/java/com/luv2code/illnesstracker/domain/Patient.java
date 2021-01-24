package com.luv2code.illnesstracker.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.luv2code.illnesstracker.domain.base.BaseEntity;
import com.luv2code.illnesstracker.domain.enums.GenderType;
import com.luv2code.illnesstracker.domain.enums.StatusType;
import com.luv2code.illnesstracker.domain.illness.type.*;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PATIENT")
@EqualsAndHashCode(callSuper = true)
public class Patient extends BaseEntity {

    @Column(name = "first_name")
    @NotBlank(message = "{validation.patient.firstname.not_blank}")
    @Size(min = 2, message = "{validation.patient.firstname.size}")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "{validation.patient.lastname.not_blank}")
    @Size(min = 2, message = "{validation.patient.lastname.size}")
    private String lastName;

    @Column(name = "email")
    @NotBlank(message = "{validation.patient.email.not_blank}")
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "{validation.patient.email.pattern}")
    private String email;

    @Column(name = "username")
    @NotBlank(message = "{validation.patient.username.not_blank}")
    @Size(min = 5, message = "{validation.patient.username.size}")
    private String username;

    @Column(name = "password")
    @NotBlank(message = "{validation.patient.password.not_blank}")
    @Size(min = 8, message = "{validation.patient.password.size}")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "{validation.patient.password.pattern}")
    private String password;

    @Column(name = "date_of_birth")
    @JsonFormat(pattern="yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @NotNull(message = "{validation.patient.date_of_birth.not_null}")
    private LocalDate dateOfBirth;

    @Column(name = "phone_number")
    @NotBlank(message = "{validation.patient.phone_number.not_blank}")
    @Size(min = 13, max = 14, message = "{validation.patient.phone_number.size}")
    @Pattern(regexp = "^\\+3859?[1,2,5,7,8,9]{1}?[0-9]{7}$", message = "{validation.patient.phone_number.pattern}")
    private String phoneNumber;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{validation.patient.gender.not_null}")
    private GenderType gender;

    @Column(name = "date_of_registration")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime dateOfRegistration;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusType status;

    @Column(name = "is_body_mass_index_active")
    private Boolean isBodyMassIndexActive;

    @Column(name = "is_hypertension_active")
    private Boolean isHypertensionActive;

    @Column(name = "is_hyperthyroidism_active")
    private Boolean isHyperthyroidismActive;

    @Column(name = "is_diabetes_mellitus_type_II_active")
    private Boolean isDiabetesMellitusTypeIIActive;

    @Column(name = "is_painful_syndromes_active")
    private Boolean isPainfulSyndromesActive;

    @Column(name = "is_gastro_esophageal_reflux_active")
    private Boolean isGastroEsophagealRefluxActive;

    @ManyToMany
    @ToString.Exclude
    @JoinTable(
            name = "PATIENT_ROLE",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @ToString.Exclude
    @ManyToMany(mappedBy = "patients")
    private List<BodyMassIndex> bodyMassIndexes;

    @ToString.Exclude
    @ManyToMany(mappedBy = "patients")
    private List<Hypertension> hypertension;

    @ToString.Exclude
    @ManyToMany(mappedBy = "patients")
    private List<Hyperthyroidism> hyperthyroid;

    @ToString.Exclude
    @ManyToMany(mappedBy = "patients")
    private List<DiabetesMellitusTypeII> diabetesMellitusTypesII;

    @ToString.Exclude
    @ManyToMany(mappedBy = "patients")
    private List<PainfulSyndrome> painfulSyndromes;

    @ToString.Exclude
    @ManyToMany(mappedBy = "patients")
    private List<GastroEsophagealReflux> gastroEsophagealRefluxes;

}
