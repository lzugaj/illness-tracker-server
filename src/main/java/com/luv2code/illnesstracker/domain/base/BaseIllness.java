package com.luv2code.illnesstracker.domain.base;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public class BaseIllness extends BaseEntity {

    @Column(name = "date_of_performed_measurement")
    private LocalDateTime dateOfPerformedMeasurement;

    @Column(name = "is_active")
    private Boolean isActive;

}
