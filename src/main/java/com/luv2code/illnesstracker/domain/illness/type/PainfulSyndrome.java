package com.luv2code.illnesstracker.domain.illness.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luv2code.illnesstracker.domain.User;
import com.luv2code.illnesstracker.domain.base.BaseIllness;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PAINFUL_SYNDROME")
@EqualsAndHashCode(callSuper = true)
public class PainfulSyndrome extends BaseIllness implements Serializable {

    @Column(name = "body_part")
    @NotBlank(message = "{validation.ps.body_part.not_blank}")
    private String bodyPart;

    @Column(name = "description")
    @NotBlank(message = "{validation.ps.description.not_blank}")
    private String description;

    @Column(name = "vas_value")
    @NotNull(message = "{validation.ps.vas_value.not_null}")
    private Integer vasValue;

    @ManyToMany
    @JsonIgnore
    @ToString.Exclude
    @JoinTable(
            name = "USER_PAINFUL_SYNDROME",
            joinColumns = @JoinColumn(name = "ps_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

}
