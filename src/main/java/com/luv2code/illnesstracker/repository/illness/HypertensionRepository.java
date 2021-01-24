package com.luv2code.illnesstracker.repository.illness;

import com.luv2code.illnesstracker.domain.illness.type.Hypertension;
import org.springframework.stereotype.Repository;

@Repository
public interface HypertensionRepository extends IllnessTypeRepository<Hypertension> {

}
