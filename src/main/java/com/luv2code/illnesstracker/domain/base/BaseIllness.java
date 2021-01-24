package com.luv2code.illnesstracker.domain.base;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public abstract class BaseIllness extends BaseEntity implements Serializable {

    @Column(name = "date_of_performed_measurement")
    private LocalDateTime dateOfPerformedMeasurement;

}
