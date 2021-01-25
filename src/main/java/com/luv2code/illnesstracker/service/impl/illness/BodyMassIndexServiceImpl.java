package com.luv2code.illnesstracker.service.impl.illness;

import com.luv2code.illnesstracker.domain.User;
import com.luv2code.illnesstracker.domain.illness.type.BodyMassIndex;
import com.luv2code.illnesstracker.domain.info.BodyMassIndexInfo;
import com.luv2code.illnesstracker.repository.illness.IllnessTypeRepository;
import com.luv2code.illnesstracker.service.IllnessTypeInfoService;
import com.luv2code.illnesstracker.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class BodyMassIndexServiceImpl extends AbstractIllnessTypeService<BodyMassIndex> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BodyMassIndexServiceImpl.class);

    private final IllnessTypeInfoService<BodyMassIndexInfo> illnessTypeInfoService;

    @Autowired
    public BodyMassIndexServiceImpl(final IllnessTypeRepository<BodyMassIndex> illnessTypeRepository,
                                    final UserService userService,
                                    @Qualifier("bodyMassIndexInfoServiceImpl") final IllnessTypeInfoService<BodyMassIndexInfo> illnessTypeInfoService) {
        super(illnessTypeRepository, userService, BodyMassIndex.class);
        this.illnessTypeInfoService = illnessTypeInfoService;
    }

    @Override
    public BodyMassIndex save(final User user, final BodyMassIndex bodyMassIndex) {
        setupVariablesCreate(user, bodyMassIndex);
        return super.save(user, bodyMassIndex);
    }

    @Override
    public BodyMassIndex findById(final Long id) {
        return super.findById(id);
    }

    @Override
    public List<BodyMassIndex> findAll() {
        return super.findAll();
    }

    @Override
    public List<BodyMassIndex> findAllForUser(final User user) {
        return super.findAllForUser(user);
    }

    @Override
    public BodyMassIndex update(final BodyMassIndex oldBodyMassIndex, final BodyMassIndex newBodyMassIndex) {
        setupVariablesUpdate(oldBodyMassIndex, newBodyMassIndex);
        return super.update(oldBodyMassIndex, newBodyMassIndex);
    }

    @Override
    public void delete(BodyMassIndex illness) {
        super.delete(illness);
    }

    private void setupVariablesCreate(final User user, final BodyMassIndex bodyMassIndex) {
        LOGGER.info("Setting up BodyMassIndex variables for User with id: ´{}´.", user.getId());
        bodyMassIndex.setDateOfPerformedMeasurement(LocalDateTime.now());

        final List<BodyMassIndex> bodyMassIndexes = findAllForUser(user);
        LOGGER.info("Successfully founded ´{}´ BodyMassIndex record for User with id: ´{}´.", bodyMassIndexes.size(), user.getId());

        bodyMassIndexes.add(bodyMassIndex);
        bodyMassIndex.setUsers(Collections.singletonList(user));

        final Double bmiIndexValue = calculateBMIIndexValue(bodyMassIndex);
        bodyMassIndex.setIndexValue(bmiIndexValue);

        final BodyMassIndexInfo bodyMassIndexInfo = illnessTypeInfoService.findByIndexValue(bmiIndexValue);
        LOGGER.info("Successfully founded BodyMassIndexInfo with id: ´{}´.", bodyMassIndexInfo.getId());

        bodyMassIndex.setBodyMassIndexInfo(bodyMassIndexInfo);
        bodyMassIndexInfo.setBodyMassIndexes(Collections.singletonList(bodyMassIndex));
        user.setBodyMassIndexes(bodyMassIndexes);
    }

    private void setupVariablesUpdate(final BodyMassIndex oldBodyMassIndex, final BodyMassIndex newBodyMassIndex) {
        LOGGER.info("Setting up BodyMassIndex variables.");
        oldBodyMassIndex.setHeight(newBodyMassIndex.getHeight());
        oldBodyMassIndex.setWeight(newBodyMassIndex.getWeight());

        final Double bmiIndexValue = calculateBMIIndexValue(newBodyMassIndex);
        oldBodyMassIndex.setIndexValue(bmiIndexValue);

        final BodyMassIndexInfo bodyMassIndexInfo = illnessTypeInfoService.findByIndexValue(bmiIndexValue);
        LOGGER.info("Successfully founded BodyMassIndexInfo with id: ´{}´.", bodyMassIndexInfo.getId());

        oldBodyMassIndex.setBodyMassIndexInfo(bodyMassIndexInfo);
        bodyMassIndexInfo.setBodyMassIndexes(Collections.singletonList(oldBodyMassIndex));
    }

    private Double calculateBMIIndexValue(final BodyMassIndex bodyMassIndex) {
        final double bmiDivisionValue = (bodyMassIndex.getWeight() / Math.pow(bodyMassIndex.getHeight(), 2)) * 10000;
        final BigDecimal bmiIndexValue = new BigDecimal(bmiDivisionValue).setScale(1, RoundingMode.HALF_UP);
        LOGGER.info("Calculated BodyMassIndex values is: ´{}´.", bmiIndexValue);
        return bmiIndexValue.doubleValue();
    }
}
