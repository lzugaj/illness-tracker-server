package com.luv2code.illnesstracker.domain.info;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luv2code.illnesstracker.domain.illness.type.BodyMassIndex;
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
@Table(name = "BODY_MASS_INDEX_INFO")
public class BodyMassIndexInfo extends BaseEntity {

    @Column(name = "value")
    private String value;

    @Column(name = "classification")
    private String classification;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "bodyMassIndexInfo")
    private List<BodyMassIndex> bodyMassIndexes;

}
