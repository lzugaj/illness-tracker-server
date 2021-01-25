package com.luv2code.illnesstracker.service.impl.illness;

import com.luv2code.illnesstracker.domain.User;
import com.luv2code.illnesstracker.domain.illness.type.Hypertension;
import com.luv2code.illnesstracker.domain.info.HypertensionInfo;
import com.luv2code.illnesstracker.repository.illness.IllnessTypeRepository;
import com.luv2code.illnesstracker.service.IllnessTypeInfoService;
import com.luv2code.illnesstracker.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class HypertensionServiceImpl extends AbstractIllnessTypeService<Hypertension> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HypertensionServiceImpl.class);

    private final IllnessTypeInfoService<HypertensionInfo> illnessTypeInfoService;

    @Autowired
    public HypertensionServiceImpl(final IllnessTypeRepository<Hypertension> illnessTypeRepository,
                                   final UserService userService,
                                   @Qualifier("hypertensionInfoServiceImpl") final IllnessTypeInfoService<HypertensionInfo> illnessTypeInfoService) {
        super(illnessTypeRepository, userService, Hypertension.class);
        this.illnessTypeInfoService = illnessTypeInfoService;
    }

    @Override
    public Hypertension save(final User user, final Hypertension hypertension) {
        setupVariablesCreate(user, hypertension);
        return super.save(user, hypertension);
    }

    @Override
    public Hypertension findById(final Long id) {
        return super.findById(id);
    }

    @Override
    public List<Hypertension> findAll() {
        return super.findAll();
    }

    @Override
    public List<Hypertension> findAllForUser(User user) {
        return super.findAllForUser(user);
    }

    @Override
    public Hypertension update(final Hypertension oldHypertension, final Hypertension newHypertension) {
        setupVariablesUpdate(oldHypertension, newHypertension);
        return super.update(oldHypertension, newHypertension);
    }

    @Override
    public void delete(final Hypertension hypertension) {
        super.delete(hypertension);
    }

    private void setupVariablesCreate(final User user, final Hypertension hypertension) {
        LOGGER.info("Setting up new Hypertension variables.");
        hypertension.setDateOfPerformedMeasurement(LocalDateTime.now());

        final List<Hypertension> hypertensives = findAllForUser(user);
        LOGGER.info("Successfully founded ´{}´ Hypertension record for User with id: ´{}´.", hypertensives.size(), user.getId());

        hypertensives.add(hypertension);
        hypertension.setUsers(Collections.singletonList(user));

        final HypertensionInfo hypertensionInfo = illnessTypeInfoService.findByIndexValue(Double.valueOf(hypertension.getSystolic()), Double.valueOf(hypertension.getDiastolic()));
        LOGGER.info("Successfully founded HypertensionInfo with id: ´{}´.", hypertensionInfo.getId());

        hypertension.setHypertensionInfo(hypertensionInfo);
        hypertensionInfo.setHypertension(Collections.singletonList(hypertension));

        LOGGER.info("Setting up User variables.");
        user.setHypertension(hypertensives);
    }

    private void setupVariablesUpdate(final Hypertension oldHypertension, final Hypertension newHypertension) {
        LOGGER.info("Setting up old Hypertension variables.");
        oldHypertension.setSystolic(newHypertension.getSystolic());
        oldHypertension.setDiastolic(newHypertension.getDiastolic());

        final HypertensionInfo hypertensionInfo = illnessTypeInfoService.findByIndexValue(Double.valueOf(oldHypertension.getSystolic()), Double.valueOf(oldHypertension.getDiastolic()));
        LOGGER.info("Successfully founded HypertensionInfo with id: ´{}´.", hypertensionInfo.getId());

        oldHypertension.setHypertensionInfo(hypertensionInfo);
        hypertensionInfo.setHypertension(Collections.singletonList(oldHypertension));
    }
}
