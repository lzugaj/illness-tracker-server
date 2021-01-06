package com.luv2code.illnesstracker.repository;

import com.luv2code.illnesstracker.domain.info.BodyMassIndexInfo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyMassIndexInfoRepository extends IllnessTypeInfoRepository<BodyMassIndexInfo> {

}
