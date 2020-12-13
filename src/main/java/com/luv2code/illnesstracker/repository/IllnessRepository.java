package com.luv2code.illnesstracker.repository;

import com.luv2code.illnesstracker.domain.illness.Illness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IllnessRepository extends JpaRepository<Illness, Long> {

}
