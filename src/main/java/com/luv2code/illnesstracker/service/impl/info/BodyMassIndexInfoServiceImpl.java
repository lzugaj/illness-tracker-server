package com.luv2code.illnesstracker.service.impl.info;

import com.luv2code.illnesstracker.domain.info.BodyMassIndexInfo;
import com.luv2code.illnesstracker.exception.EntityNotFoundException;
import com.luv2code.illnesstracker.repository.BodyMassIndexInfoRepository;
import com.luv2code.illnesstracker.service.BodyMassIndexInfoService;
import com.luv2code.illnesstracker.util.BodyMassIndexClassificationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BodyMassIndexInfoServiceImpl implements BodyMassIndexInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BodyMassIndexInfoServiceImpl.class);

    private final BodyMassIndexInfoRepository bodyMassIndexInfoRepository;

    @Autowired

    public BodyMassIndexInfoServiceImpl(final BodyMassIndexInfoRepository bodyMassIndexInfoRepository) {
        this.bodyMassIndexInfoRepository = bodyMassIndexInfoRepository;
    }

    @Override
    public BodyMassIndexInfo findById(final Long id) {
        final Optional<BodyMassIndexInfo> searchedBodyMassIndexInfo = bodyMassIndexInfoRepository.findById(id);
        if (searchedBodyMassIndexInfo.isPresent()) {
            LOGGER.info("Searching BodyMassIndexInfo with id: ´{}´.", id);
            return searchedBodyMassIndexInfo.get();
        } else {
            LOGGER.error("BodyMassIndexInfo was not founded with id: ´{}´.", id);
            throw new EntityNotFoundException("BodyMassIndexInfo", "id", String.valueOf(id));
        }
    }

    @Override
    public BodyMassIndexInfo findByClassification(final String classification) {
        final Optional<BodyMassIndexInfo> bodyMassIndexInfo = bodyMassIndexInfoRepository.findByClassification(classification);
        if (bodyMassIndexInfo.isPresent()) {
            LOGGER.info("Searching BodyMassIndexInfo with classification: ´{}´.", classification);
            return bodyMassIndexInfo.get();
        } else {
            LOGGER.error("BodyMassIndexInfo was not founded with classification: ´{}´.", classification);
            throw new EntityNotFoundException("BodyMassIndexInfo", "classification", classification);
        }
    }

    @Override
    public BodyMassIndexInfo findByIndexValue(final Double indexValue) {
        LOGGER.info("Searching BodyMassIndexInfo with index value: ´{}´.", indexValue);
        if (indexValue <= 18.5) {
            return findByClassification(BodyMassIndexClassificationUtil.UNDERWEIGHT);
        } else if (indexValue <= 24.9) {
            return findByClassification(BodyMassIndexClassificationUtil.NORMAL_WEIGHT);
        } else if (indexValue <= 29.9) {
            return findByClassification(BodyMassIndexClassificationUtil.OVERWEIGHT);
        } else if (indexValue <= 34.9) {
            return findByClassification(BodyMassIndexClassificationUtil.OBESITY_CLASS_I);
        } else if (indexValue <= 39.9) {
            return findByClassification(BodyMassIndexClassificationUtil.OBESITY_CLASS_II);
        } else {
            return findByClassification(BodyMassIndexClassificationUtil.OBESITY_CLASS_III);
        }
    }
}
