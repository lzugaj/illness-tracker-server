package com.luv2code.illnesstracker.domain.illness;

import com.luv2code.illnesstracker.domain.base.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ILLNESS")
@EqualsAndHashCode(callSuper = true)
public class Illness extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "is_selected")
    private Boolean isSelected;

}
