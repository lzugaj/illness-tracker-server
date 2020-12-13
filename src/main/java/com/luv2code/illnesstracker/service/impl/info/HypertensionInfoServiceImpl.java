package com.luv2code.illnesstracker.service.impl.info;

import com.luv2code.illnesstracker.domain.info.HypertensionInfo;
import com.luv2code.illnesstracker.exception.EntityNotFoundException;
import com.luv2code.illnesstracker.repository.HypertensionInfoRepository;
import com.luv2code.illnesstracker.service.HypertensionInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HypertensionInfoServiceImpl implements HypertensionInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HypertensionInfoServiceImpl.class);

    private static final String NORMAL = "Normal";
    private static final String ELEVATED = "Elevated";
    private static final String HIGH_BLOOD_PRESSURE_STAGE_1 = "High Blood Pressure Stage 1";
    private static final String HIGH_BLOOD_PRESSURE_STAGE_2 = "High Blood Pressure Stage 2";
    private static final String HYPERTENSIVE_CRISIS = "Hypertensive Crisis";

    private final HypertensionInfoRepository hypertensionInfoRepository;

    @Autowired
    public HypertensionInfoServiceImpl(final HypertensionInfoRepository hypertensionInfoRepository) {
        this.hypertensionInfoRepository = hypertensionInfoRepository;
    }

    @Override
    public HypertensionInfo findById(final Long id) {
        final Optional<HypertensionInfo> searchedHypertensionInfo = hypertensionInfoRepository.findById(id);
        if (searchedHypertensionInfo.isPresent()) {
            LOGGER.info("Searching HypertensionInfo with id: ´{}´.", id);
            return searchedHypertensionInfo.get();
        } else {
            LOGGER.error("HypertensionInfo was not founded with id: ´{}´.", id);
            throw new EntityNotFoundException("HypertensionInfo", "id", String.valueOf(id));
        }
    }

    @Override
    public HypertensionInfo findByClassification(final String classification) {
        final Optional<HypertensionInfo> searchedHypertensionInfo = hypertensionInfoRepository.findByClassification(classification);
        if (searchedHypertensionInfo.isPresent()) {
            LOGGER.info("Searching HypertensionInfo with classification: ´{}´.", classification);
            return searchedHypertensionInfo.get();
        } else {
            LOGGER.error("HypertensionInfo was not founded with classification: ´{}´.", classification);
            throw new EntityNotFoundException("HypertensionInfo", "classification", classification);
        }
    }

    @Override
    public HypertensionInfo findByIndexValue(final Integer systolic, final Integer diastolic) {
        LOGGER.info("Searching HypertensionInfo with index systolic value ´{}´ and diastolic value ´{}´.", systolic, diastolic);
        if (systolic < 120 && diastolic < 80) {
            return findByClassification(NORMAL);
        } else if ((systolic >= 120 && systolic < 129) && (diastolic < 80)) {
            return findByClassification(ELEVATED);
        } else if ((systolic >= 130 && systolic < 140) && (diastolic >= 80 && diastolic < 90)) {
            return findByClassification(HIGH_BLOOD_PRESSURE_STAGE_1);
        } else if ((systolic >= 140 && systolic < 181) && (diastolic >= 90 && diastolic < 121)) {
            return findByClassification(HIGH_BLOOD_PRESSURE_STAGE_2);
        } else {
            return findByClassification(HYPERTENSIVE_CRISIS);
        }
    }
}
