package com.luv2code.illnesstracker.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DUMMY")
@SequenceGenerator(name = "id_seq", sequenceName = "dummy_id_sequence", allocationSize = 1)
public class Dummy {

    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq")
    private Long id;

}
