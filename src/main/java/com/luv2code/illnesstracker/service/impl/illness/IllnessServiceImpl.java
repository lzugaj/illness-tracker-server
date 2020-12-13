package com.luv2code.illnesstracker.service.impl.illness;

import com.luv2code.illnesstracker.domain.Patient;
import com.luv2code.illnesstracker.domain.illness.Illness;
import com.luv2code.illnesstracker.repository.IllnessRepository;
import com.luv2code.illnesstracker.service.IllnessService;
import com.luv2code.illnesstracker.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IllnessServiceImpl implements IllnessService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IllnessServiceImpl.class);

    private static final String BODY_MASS_INDEX = "Body Mass Index";
    private static final String HYPERTENSION = "Hypertension";
    private static final String HYPERTHYROIDISM = "Hyperthyroidism";
    private static final String DIABETES_MELLITUS_TYPE_II = "Diabetes Mellitus Type II";
    private static final String PAINFUL_SYNDROMES = "Painful Syndromes";
    private static final String GASTRO_ESOPHAGEAL_REFLUX = "Gastro Esophageal Reflux";

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
                case BODY_MASS_INDEX:
                    patient.setIsBodyMassIndexActive(getIllnessSelectedValue(illness));
                    break;
                case HYPERTENSION:
                    patient.setIsHypertensionActive(getIllnessSelectedValue(illness));
                    break;
                case HYPERTHYROIDISM:
                    patient.setIsHyperthyroidismActive(getIllnessSelectedValue(illness));
                    break;
                case DIABETES_MELLITUS_TYPE_II:
                    patient.setIsDiabetesMellitusTypeIIActive(getIllnessSelectedValue(illness));
                    break;
                case PAINFUL_SYNDROMES:
                    patient.setIsPainfulSyndromesActive(getIllnessSelectedValue(illness));
                    break;
                case GASTRO_ESOPHAGEAL_REFLUX:
                    patient.setIsGastroEsophagealRefluxActive(getIllnessSelectedValue(illness));
                    break;
                default:
                    break;
            }
        }

        patientService.update(patient, patient);
        return illnesses;
    }

    private boolean getIllnessSelectedValue(final Illness illness) {
        return illness.getIsSelected();
    }

    @Override
    public List<Illness> findAll() {
        List<Illness> illnesses = illnessRepository.findAll();
        LOGGER.info("Searching all Illnesses.");
        return illnesses;
    }
}
