package com.luv2code.illnesstracker.repository;

import com.luv2code.illnesstracker.domain.BodyMassIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyMassIndexRepository extends JpaRepository<BodyMassIndex, Long> {

}
