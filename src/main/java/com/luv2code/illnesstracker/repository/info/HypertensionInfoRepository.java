package com.luv2code.illnesstracker.repository.info;

import com.luv2code.illnesstracker.domain.info.HypertensionInfo;
import com.luv2code.illnesstracker.repository.info.IllnessTypeInfoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HypertensionInfoRepository extends IllnessTypeInfoRepository<HypertensionInfo> {

}
