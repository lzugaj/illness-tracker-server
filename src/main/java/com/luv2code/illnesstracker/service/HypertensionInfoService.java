package com.luv2code.illnesstracker.service;

import com.luv2code.illnesstracker.domain.info.HypertensionInfo;

public interface HypertensionInfoService {

    HypertensionInfo findById(final Long id);

    HypertensionInfo findByClassification(final String classification);

    HypertensionInfo findByIndexValue(final Integer systolic, final Integer diastolic);

}
