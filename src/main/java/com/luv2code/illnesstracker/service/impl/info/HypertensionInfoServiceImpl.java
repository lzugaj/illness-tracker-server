package com.luv2code.illnesstracker.service.impl.info;

import com.luv2code.illnesstracker.domain.info.HypertensionInfo;
import com.luv2code.illnesstracker.repository.info.IllnessTypeInfoRepository;
import com.luv2code.illnesstracker.util.HypertensionClassificationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HypertensionInfoServiceImpl extends AbstractIllnessTypeInfoService<HypertensionInfo> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HypertensionInfoServiceImpl.class);

    @Autowired
    public HypertensionInfoServiceImpl(final IllnessTypeInfoRepository<HypertensionInfo> illnessTypeInfoRepository) {
        super(illnessTypeInfoRepository, HypertensionInfo.class);
    }

    @Override
    public HypertensionInfo findById(final Long id) {
        return super.findById(id);
    }

    @Override
    public HypertensionInfo findByClassification(final String classification) {
        return super.findByClassification(classification);
    }

    @Override
    public HypertensionInfo findByIndexValue(final Double... values) {
        LOGGER.info("Searching HypertensionInfo with index systolic value ´{}´ and diastolic value ´{}´.", values[0], values[1]);
        if (values[0] < 120 && values[1] < 80) {
            return findByClassification(HypertensionClassificationUtil.NORMAL);
        } else if ((values[0] >= 120 && values[0] < 129) && (values[1] < 80)) {
            return findByClassification(HypertensionClassificationUtil.ELEVATED);
        } else if ((values[0] >= 130 && values[0] < 140) && (values[1] >= 80 && values[1] < 90)) {
            return findByClassification(HypertensionClassificationUtil.HIGH_BLOOD_PRESSURE_STAGE_1);
        } else if ((values[0] >= 140 && values[0] < 181) && (values[1] >= 90 && values[1] < 121)) {
            return findByClassification(HypertensionClassificationUtil.HIGH_BLOOD_PRESSURE_STAGE_2);
        } else {
            return findByClassification(HypertensionClassificationUtil.HYPERTENSIVE_CRISIS);
        }
    }

    @Override
    public List<HypertensionInfo> findAll() {
        return super.findAll();
    }
}
