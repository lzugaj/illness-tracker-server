package com.luv2code.illnesstracker.repository;

import com.luv2code.illnesstracker.domain.illness.Hypertension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HypertensionRepository extends JpaRepository<Hypertension, Long> {



}
