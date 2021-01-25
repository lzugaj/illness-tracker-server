package com.luv2code.illnesstracker.service.impl.illness;

import com.luv2code.illnesstracker.domain.User;
import com.luv2code.illnesstracker.domain.illness.type.Hyperthyroidism;
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
public class HyperthyroidismServiceImpl extends AbstractIllnessTypeService<Hyperthyroidism> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HyperthyroidismServiceImpl.class);

    @Autowired
    public HyperthyroidismServiceImpl(final IllnessTypeRepository<Hyperthyroidism> illnessTypeRepository,
                                      final UserService userService) {
        super(illnessTypeRepository, userService, Hyperthyroidism.class);
    }

    @Override
    public Hyperthyroidism save(final User user, final Hyperthyroidism hyperthyroidism) {
        setupVariablesCreate(user, hyperthyroidism);
        return super.save(user, hyperthyroidism);
    }

    @Override
    public Hyperthyroidism findById(final Long id) {
        return super.findById(id);
    }

    @Override
    public List<Hyperthyroidism> findAll() {
        return super.findAll();
    }

    @Override
    public List<Hyperthyroidism> findAllForUser(final User user) {
        return super.findAllForUser(user);
    }

    @Override
    public Hyperthyroidism update(final Hyperthyroidism oldHyperthyroidism, final Hyperthyroidism newHyperthyroidism) {
        setupVariablesUpdate(oldHyperthyroidism, newHyperthyroidism);
        return super.update(oldHyperthyroidism, newHyperthyroidism);
    }

    @Override
    public void delete(final Hyperthyroidism illness) {
        super.delete(illness);
    }

    private void setupVariablesCreate(final User user, final Hyperthyroidism hyperthyroidism) {
        LOGGER.info("Setting up new Hyperthyroidism variables.");
        hyperthyroidism.setDateOfPerformedMeasurement(LocalDateTime.now());

        final List<Hyperthyroidism> hyperthyroid = findAllForUser(user);
        LOGGER.info("Successfully founded ´{}´ Hyperthyroidism record for User with id: ´{}´.", hyperthyroid.size(), user.getId());

        hyperthyroid.add(hyperthyroidism);
        hyperthyroidism.setUsers(Collections.singletonList(user));

        LOGGER.info("Setting up User variables.");
        user.setHyperthyroid(hyperthyroid);
    }

    private void setupVariablesUpdate(final Hyperthyroidism oldHyperthyroidism, final Hyperthyroidism newHyperthyroidism) {
        LOGGER.info("Setting up old Hyperthyroidism variables.");
        oldHyperthyroidism.setTsh(newHyperthyroidism.getTsh());
        oldHyperthyroidism.setFt3(newHyperthyroidism.getFt3());
        oldHyperthyroidism.setFt4(newHyperthyroidism.getFt4());
    }
}
