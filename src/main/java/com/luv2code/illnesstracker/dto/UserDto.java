package com.luv2code.illnesstracker.dto;

import com.luv2code.illnesstracker.domain.enums.GenderType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String username;

    private String phoneNumber;

    private LocalDate dateOfBirth;

    private GenderType gender;

}
