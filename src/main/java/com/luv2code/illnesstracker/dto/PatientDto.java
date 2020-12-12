package com.luv2code.illnesstracker.dto;

import com.luv2code.illnesstracker.domain.enums.GenderType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String oib;

    private LocalDate dateOfBirth;

    private GenderType gender;

}
