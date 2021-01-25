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
import java.io.Serializable;
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
@Table(name = "USER")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity implements Serializable {

    @Column(name = "first_name")
    @NotBlank(message = "{validation.user.firstname.not_blank}")
    @Size(min = 2, message = "{validation.user.firstname.size}")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "{validation.user.lastname.not_blank}")
    @Size(min = 2, message = "{validation.user.lastname.size}")
    private String lastName;

    @Column(name = "email")
    @NotBlank(message = "{validation.user.email.not_blank}")
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "{validation.user.email.pattern}")
    private String email;

    @Column(name = "username")
    @NotBlank(message = "{validation.user.username.not_blank}")
    @Size(min = 5, message = "{validation.user.username.size}")
    private String username;

    @Column(name = "password")
    @NotBlank(message = "{validation.user.password.not_blank}")
    @Size(min = 8, message = "{validation.user.password.size}")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "{validation.user.password.pattern}")
    private String password;

    @Column(name = "date_of_birth")
    @JsonFormat(pattern="yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @NotNull(message = "{validation.user.date_of_birth.not_null}")
    private LocalDate dateOfBirth;

    @Column(name = "phone_number")
    @NotBlank(message = "{validation.user.phone_number.not_blank}")
    @Size(min = 13, max = 14, message = "{validation.user.phone_number.size}")
    @Pattern(regexp = "^\\+3859?[1,2,5,7,8,9]{1}?[0-9]{7}$", message = "{validation.user.phone_number.pattern}")
    private String phoneNumber;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{validation.user.gender.not_null}")
    private GenderType gender;

    @Column(name = "registration_date")
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
            name = "USER_ROLE",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @ToString.Exclude
    @ManyToMany(mappedBy = "users")
    private List<BodyMassIndex> bodyMassIndexes;

    @ToString.Exclude
    @ManyToMany(mappedBy = "users")
    private List<Hypertension> hypertension;

    @ToString.Exclude
    @ManyToMany(mappedBy = "users")
    private List<Hyperthyroidism> hyperthyroid;

    @ToString.Exclude
    @ManyToMany(mappedBy = "users")
    private List<DiabetesMellitusTypeII> diabetesMellitusTypesII;

    @ToString.Exclude
    @ManyToMany(mappedBy = "users")
    private List<PainfulSyndrome> painfulSyndromes;

    @ToString.Exclude
    @ManyToMany(mappedBy = "users")
    private List<GastroEsophagealReflux> gastroEsophagealRefluxes;

}
