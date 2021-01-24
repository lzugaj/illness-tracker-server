package com.luv2code.illnesstracker.domain.info;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luv2code.illnesstracker.domain.illness.type.Hypertension;
import com.luv2code.illnesstracker.domain.base.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "HYPERTENSION_INFO")
public class HypertensionInfo extends BaseEntity {

    @Column(name = "systolic_range")
    private String systolicRange;

    @Column(name = "diastolic_range")
    private String diastolicRange;

    @Column(name = "classification")
    private String classification;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "hypertensionInfo")
    private List<Hypertension> hypertension;

}
