package com.luv2code.illnesstracker.dto.mapper;

import com.luv2code.illnesstracker.domain.Patient;
import com.luv2code.illnesstracker.dto.PatientDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    @Mappings({
            @Mapping(target = "id", source = "patient.id"),
            @Mapping(target = "firstName", source = "patient.firstName"),
            @Mapping(target = "lastName", source = "patient.lastName"),
            @Mapping(target = "email", source = "patient.email"),
            @Mapping(target = "username", source = "patient.username"),
            @Mapping(target = "phoneNumber", source = "patient.phoneNumber"),
            @Mapping(target = "dateOfBirth", source = "patient.dateOfBirth"),
            @Mapping(target = "gender", source = "patient.gender")
    })
    PatientDto toPatientDto(final Patient patient);

    @Mappings({
            @Mapping(target = "id", source = "patient.id"),
            @Mapping(target = "firstName", source = "patient.firstName"),
            @Mapping(target = "lastName", source = "patient.lastName"),
            @Mapping(target = "email", source = "patient.email"),
            @Mapping(target = "username", source = "patient.username"),
            @Mapping(target = "phoneNumber", source = "patient.phoneNumber"),
            @Mapping(target = "dateOfBirth", source = "patient.dateOfBirth"),
            @Mapping(target = "gender", source = "patient.gender")
    })
    List<PatientDto> toPatientsDto(final List<Patient> patients);

}
