package com.luv2code.illnesstracker.repository;

import com.luv2code.illnesstracker.domain.info.BodyMassIndexInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BodyMassIndexInfoRepository extends JpaRepository<BodyMassIndexInfo, Long> {

    Optional<BodyMassIndexInfo> findByClassification(final String classification);

}
