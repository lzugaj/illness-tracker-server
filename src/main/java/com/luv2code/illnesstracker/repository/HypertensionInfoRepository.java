package com.luv2code.illnesstracker.repository;

import com.luv2code.illnesstracker.domain.info.HypertensionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HypertensionInfoRepository extends JpaRepository<HypertensionInfo, Long> {

    Optional<HypertensionInfo> findByClassification(final String classification);

}
