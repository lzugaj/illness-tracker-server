package com.luv2code.illnesstracker.service.impl.info;

import com.luv2code.illnesstracker.domain.info.BodyMassIndexInfo;
import com.luv2code.illnesstracker.repository.IllnessTypeInfoRepository;
import com.luv2code.illnesstracker.util.BodyMassIndexClassificationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BodyMassIndexInfoServiceImpl extends AbstractIllnessTypeInfoService<BodyMassIndexInfo> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BodyMassIndexInfoServiceImpl.class);

    @Autowired
    public BodyMassIndexInfoServiceImpl(final IllnessTypeInfoRepository<BodyMassIndexInfo> illnessTypeInfoRepository) {
        super(illnessTypeInfoRepository, BodyMassIndexInfo.class);
    }

    @Override
    public BodyMassIndexInfo findById(final Long id) {
        return super.findById(id);
    }

    @Override
    public BodyMassIndexInfo findByClassification(final String classification) {
        return super.findByClassification(classification);
    }

    @Override
    public BodyMassIndexInfo findByIndexValue(final Double... values) {
        LOGGER.info("Searching BodyMassIndexInfo with index value: ´{}´.", values[0]);
        if (values[0] <= 18.5) {
            return findByClassification(BodyMassIndexClassificationUtil.UNDERWEIGHT);
        } else if (values[0] <= 24.9) {
            return findByClassification(BodyMassIndexClassificationUtil.NORMAL_WEIGHT);
        } else if (values[0] <= 29.9) {
            return findByClassification(BodyMassIndexClassificationUtil.OVERWEIGHT);
        } else if (values[0] <= 34.9) {
            return findByClassification(BodyMassIndexClassificationUtil.OBESITY_CLASS_I);
        } else if (values[0] <= 39.9) {
            return findByClassification(BodyMassIndexClassificationUtil.OBESITY_CLASS_II);
        } else {
            return findByClassification(BodyMassIndexClassificationUtil.OBESITY_CLASS_III);
        }
    }
}
