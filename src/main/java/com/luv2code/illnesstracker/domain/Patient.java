package com.luv2code.illnesstracker.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
@Builder
@Entity
@Table(name = "PATIENT")
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

    @Column(name = "password")
    @NotBlank(message = "{validation.patient.password.not_blank}")
    @Size(min = 8, message = "{validation.patient.password.size}")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "{validation.patient.password.pattern}")
    private String password;

    @Column(name = "oib")
    @NotBlank(message = "{validation.patient.oib.not_blank}")
    @Size(max = 11, min = 11, message = "{validation.patient.oib.size}")
    @Pattern(regexp = "^[0][1-9]\\d{10}$|^[1-9]\\d{10}$", message = "{validation.patient.oib.pattern}")
    private String oib;

    @Column(name = "date_of_birth")
    @JsonFormat(pattern="yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
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
    private LocalDateTime dateOfRegistration;

    @Column(name = "is_active")
    private Boolean isActive;

    @ToString.Exclude
    @ManyToMany
    @JoinTable(
            name = "PATIENT_ROLE",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

}
