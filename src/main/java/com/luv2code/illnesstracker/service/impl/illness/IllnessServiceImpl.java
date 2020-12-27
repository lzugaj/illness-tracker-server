package com.luv2code.illnesstracker.service.impl.illness;

import com.luv2code.illnesstracker.domain.Patient;
import com.luv2code.illnesstracker.domain.illness.Illness;
import com.luv2code.illnesstracker.repository.IllnessRepository;
import com.luv2code.illnesstracker.service.IllnessService;
import com.luv2code.illnesstracker.service.PatientService;
import com.luv2code.illnesstracker.util.IllnessTypeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IllnessServiceImpl implements IllnessService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IllnessServiceImpl.class);

    private final IllnessRepository illnessRepository;

    private final PatientService patientService;

    @Autowired
    public IllnessServiceImpl(final IllnessRepository illnessRepository,
                              final PatientService patientService) {
        this.illnessRepository = illnessRepository;
        this.patientService = patientService;
    }

    @Override
    public List<Illness> select(final Patient patient, final List<Illness> illnesses) {
        LOGGER.info("Setting visible illness for Patient with id: ´{}´.", patient.getId());
        for (Illness illness : illnesses) {
            switch (illness.getName()) {
                case IllnessTypeUtil.BODY_MASS_INDEX:
                    patient.setIsBodyMassIndexActive(getIllnessSelectedValue(illness));
                    break;
                case IllnessTypeUtil.HYPERTENSION:
                    patient.setIsHypertensionActive(getIllnessSelectedValue(illness));
                    break;
                case IllnessTypeUtil.HYPERTHYROIDISM:
                    patient.setIsHyperthyroidismActive(getIllnessSelectedValue(illness));
                    break;
                case IllnessTypeUtil.DIABETES_MELLITUS_TYPE_II:
                    patient.setIsDiabetesMellitusTypeIIActive(getIllnessSelectedValue(illness));
                    break;
                case IllnessTypeUtil.PAINFUL_SYNDROMES:
                    patient.setIsPainfulSyndromesActive(getIllnessSelectedValue(illness));
                    break;
                case IllnessTypeUtil.GASTRO_ESOPHAGEAL_REFLUX:
                    patient.setIsGastroEsophagealRefluxActive(getIllnessSelectedValue(illness));
                    break;
                default:
                    break;
            }
        }

        // TODO: @lzugaj => Refactor
        patientService.update(patient, patient);
        return illnesses;
    }

    private boolean getIllnessSelectedValue(final Illness illness) {
        return illness.getIsSelected();
    }

    @Override
    public List<Illness> findAll() {
        final List<Illness> illnesses = illnessRepository.findAll();
        LOGGER.info("Searching all Illnesses.");
        return illnesses;
    }
}
