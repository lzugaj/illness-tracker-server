package com.luv2code.illnesstracker.service.impl.illness;

import com.luv2code.illnesstracker.domain.User;
import com.luv2code.illnesstracker.domain.illness.type.DiabetesMellitusTypeII;
import com.luv2code.illnesstracker.repository.illness.IllnessTypeRepository;
import com.luv2code.illnesstracker.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class DiabetesMellitusTypeIIServiceImpl extends AbstractIllnessTypeService<DiabetesMellitusTypeII> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiabetesMellitusTypeIIServiceImpl.class);

    @Autowired
    public DiabetesMellitusTypeIIServiceImpl(final IllnessTypeRepository<DiabetesMellitusTypeII> illnessTypeRepository,
                                             final UserService userService) {
        super(illnessTypeRepository, userService, DiabetesMellitusTypeII.class);
    }

    @Override
    public DiabetesMellitusTypeII save(final User user, final DiabetesMellitusTypeII diabetesMellitusTypeII) {
        setupVariablesCreate(user, diabetesMellitusTypeII);
        return super.save(user, diabetesMellitusTypeII);
    }

    @Override
    public DiabetesMellitusTypeII findById(final Long id) {
        return super.findById(id);
    }

    @Override
    public List<DiabetesMellitusTypeII> findAll() {
        return super.findAll();
    }

    @Override
    public List<DiabetesMellitusTypeII> findAllForUser(final User user) {
        return super.findAllForUser(user);
    }

    @Override
    public DiabetesMellitusTypeII update(final DiabetesMellitusTypeII oldDiabetesMellitusTypeII, final DiabetesMellitusTypeII newDiabetesMellitusTypeII) {
        setupVariablesUpdate(oldDiabetesMellitusTypeII, newDiabetesMellitusTypeII);
        return super.update(oldDiabetesMellitusTypeII, newDiabetesMellitusTypeII);
    }

    @Override
    public void delete(final DiabetesMellitusTypeII diabetesMellitusTypeII) {
        super.delete(diabetesMellitusTypeII);
    }

    private void setupVariablesCreate(final User user, final DiabetesMellitusTypeII diabetesMellitusTypeII) {
        LOGGER.info("Setting up new DiabetesMellitusTypeII variables.");
        diabetesMellitusTypeII.setDateOfPerformedMeasurement(LocalDateTime.now());

        final List<DiabetesMellitusTypeII> diabetesMellitusTypesII = findAllForUser(user);
        LOGGER.info("Successfully founded ´{}´ DiabetesMellitusTypeII record for User with id: ´{}´.", diabetesMellitusTypesII.size(), user.getId());

        diabetesMellitusTypesII.add(diabetesMellitusTypeII);
        diabetesMellitusTypeII.setUsers(Collections.singletonList(user));

        LOGGER.info("Setting up User variables.");
        user.setDiabetesMellitusTypesII(diabetesMellitusTypesII);
    }

    private void setupVariablesUpdate(final DiabetesMellitusTypeII oldDiabetesMellitusTypeII, final DiabetesMellitusTypeII newDiabetesMellitusTypeII) {
        LOGGER.info("Setting up old DiabetesMellitusTypeII variables.");
        oldDiabetesMellitusTypeII.setGuk0(newDiabetesMellitusTypeII.getGuk0());
        oldDiabetesMellitusTypeII.setGuk2(newDiabetesMellitusTypeII.getGuk2());
    }
}
