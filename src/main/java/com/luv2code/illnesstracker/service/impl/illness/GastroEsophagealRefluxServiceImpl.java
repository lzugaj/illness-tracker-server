package com.luv2code.illnesstracker.service.impl.illness;

import com.luv2code.illnesstracker.domain.User;
import com.luv2code.illnesstracker.domain.illness.type.GastroEsophagealReflux;
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
public class GastroEsophagealRefluxServiceImpl extends AbstractIllnessTypeService<GastroEsophagealReflux> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GastroEsophagealRefluxServiceImpl.class);

    @Autowired
    public GastroEsophagealRefluxServiceImpl(final IllnessTypeRepository<GastroEsophagealReflux> illnessTypeRepository,
                                             final UserService userService) {
        super(illnessTypeRepository, userService, GastroEsophagealReflux.class);
    }

    @Override
    public GastroEsophagealReflux save(final User user, final GastroEsophagealReflux gastroEsophagealReflux) {
        setupVariablesCreate(user, gastroEsophagealReflux);
        return super.save(user, gastroEsophagealReflux);
    }

    @Override
    public GastroEsophagealReflux findById(final Long id) {
        return super.findById(id);
    }

    @Override
    public List<GastroEsophagealReflux> findAll() {
        return super.findAll();
    }

    @Override
    public List<GastroEsophagealReflux> findAllForUser(final User user) {
        return super.findAllForUser(user);
    }

    @Override
    public GastroEsophagealReflux update(final GastroEsophagealReflux oldGastroEsophagealReflux, final GastroEsophagealReflux newGastroEsophagealReflux) {
        setupVariablesUpdate(oldGastroEsophagealReflux, newGastroEsophagealReflux);
        return super.update(oldGastroEsophagealReflux, newGastroEsophagealReflux);
    }

    @Override
    public void delete(final GastroEsophagealReflux gastroEsophagealReflux) {
        super.delete(gastroEsophagealReflux);
    }

    private void setupVariablesCreate(final User user, final GastroEsophagealReflux gastroEsophagealReflux) {
        LOGGER.info("Setting up new GastroEsophagealReflux variables.");
        gastroEsophagealReflux.setDateOfPerformedMeasurement(LocalDateTime.now());

        final List<GastroEsophagealReflux> gastroEsophagealRefluxes = findAllForUser(user);
        LOGGER.info("Successfully founded ´{}´ GastroEsophagealReflux record for User with id: ´{}´.", gastroEsophagealRefluxes.size(), user.getId());

        gastroEsophagealRefluxes.add(gastroEsophagealReflux);
        gastroEsophagealReflux.setUsers(Collections.singletonList(user));

        LOGGER.info("Setting up User variables.");
        user.setGastroEsophagealRefluxes(gastroEsophagealRefluxes);
    }

    private void setupVariablesUpdate(final GastroEsophagealReflux oldGastroEsophagealReflux, final GastroEsophagealReflux newGastroEsophagealReflux) {
        LOGGER.info("Setting up old GastroEsophagealReflux variables.");
        oldGastroEsophagealReflux.setDatetimeOfLastMeal(newGastroEsophagealReflux.getDatetimeOfLastMeal());
        oldGastroEsophagealReflux.setDatetimeOfOnsetOfSymptoms(newGastroEsophagealReflux.getDatetimeOfOnsetOfSymptoms());
    }

}
