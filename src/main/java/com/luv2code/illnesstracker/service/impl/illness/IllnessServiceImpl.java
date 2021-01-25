package com.luv2code.illnesstracker.service.impl.illness;

import com.luv2code.illnesstracker.domain.User;
import com.luv2code.illnesstracker.domain.illness.Illness;
import com.luv2code.illnesstracker.repository.illness.IllnessRepository;
import com.luv2code.illnesstracker.service.IllnessService;
import com.luv2code.illnesstracker.service.UserService;
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

    private final UserService userService;

    @Autowired
    public IllnessServiceImpl(final IllnessRepository illnessRepository,
                              final UserService userService) {
        this.illnessRepository = illnessRepository;
        this.userService = userService;
    }

    @Override
    public List<Illness> select(final User user, final List<Illness> illnesses) {
        LOGGER.info("Setting visible illness for User with id: ´{}´.", user.getId());
        for (Illness illness : illnesses) {
            switch (illness.getName()) {
                case IllnessTypeUtil.BODY_MASS_INDEX:
                    user.setIsBodyMassIndexActive(getIllnessSelectedValue(illness));
                    break;
                case IllnessTypeUtil.HYPERTENSION:
                    user.setIsHypertensionActive(getIllnessSelectedValue(illness));
                    break;
                case IllnessTypeUtil.HYPERTHYROIDISM:
                    user.setIsHyperthyroidismActive(getIllnessSelectedValue(illness));
                    break;
                case IllnessTypeUtil.DIABETES_MELLITUS_TYPE_II:
                    user.setIsDiabetesMellitusTypeIIActive(getIllnessSelectedValue(illness));
                    break;
                case IllnessTypeUtil.PAINFUL_SYNDROMES:
                    user.setIsPainfulSyndromesActive(getIllnessSelectedValue(illness));
                    break;
                case IllnessTypeUtil.GASTRO_ESOPHAGEAL_REFLUX:
                    user.setIsGastroEsophagealRefluxActive(getIllnessSelectedValue(illness));
                    break;
                default:
                    break;
            }
        }

        // TODO: @lzugaj => Refactor
        userService.update(user, user);
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
