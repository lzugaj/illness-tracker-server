package com.luv2code.illnesstracker.service;

import com.luv2code.illnesstracker.domain.info.BodyMassIndexInfo;

public interface BodyMassIndexInfoService {

    BodyMassIndexInfo findById(final Long id);

    BodyMassIndexInfo findByClassification(final String classification);

    BodyMassIndexInfo findByIndexValue(final Double indexValue);

}
