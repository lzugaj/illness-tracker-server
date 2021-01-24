package com.luv2code.illnesstracker.service.impl.info;

import com.luv2code.illnesstracker.domain.base.BaseEntity;
import com.luv2code.illnesstracker.exception.EntityNotFoundException;
import com.luv2code.illnesstracker.repository.info.IllnessTypeInfoRepository;
import com.luv2code.illnesstracker.service.IllnessTypeInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public abstract class AbstractIllnessTypeInfoService<T extends BaseEntity> implements IllnessTypeInfoService<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractIllnessTypeInfoService.class);

    private final Class<T> illnessInfoClass;

    private final IllnessTypeInfoRepository<T> illnessTypeInfoRepository;

    @Autowired
    public AbstractIllnessTypeInfoService(final IllnessTypeInfoRepository<T> illnessTypeInfoRepository,
                                          final Class<T> illnessInfoClass) {
        this.illnessTypeInfoRepository = illnessTypeInfoRepository;
        this.illnessInfoClass = illnessInfoClass;
    }

    @Override
    public T findById(final Long id) {
        final Optional<T> searchedIllnessTypeInfo = illnessTypeInfoRepository.findById(id);
        if (searchedIllnessTypeInfo.isPresent()) {
            LOGGER.info("Searching ´{}´ with id: ´{}´.", getIllnessInfoName(illnessInfoClass), id);
            return searchedIllnessTypeInfo.get();
        } else {
            LOGGER.error("´{}´ was not founded with id: ´{}´.", getIllnessInfoName(illnessInfoClass), id);
            throw new EntityNotFoundException(getIllnessInfoName(illnessInfoClass), "id", String.valueOf(id));
        }
    }

    @Override
    public T findByClassification(final String classification) {
        final Optional<T> searchedIllnessTypeInfo = illnessTypeInfoRepository.findByClassification(classification);
        if (searchedIllnessTypeInfo.isPresent()) {
            LOGGER.info("Searching ´{}´ with classification: ´{}´.", getIllnessInfoName(illnessInfoClass), classification);
            return searchedIllnessTypeInfo.get();
        } else {
            LOGGER.error("´{}´ was not founded with classification: ´{}´.", getIllnessInfoName(illnessInfoClass), classification);
            throw new EntityNotFoundException(getIllnessInfoName(illnessInfoClass), "classification", classification);
        }
    }

    private String getIllnessInfoName(final Class<T> illnessInfoClass) {
        return illnessInfoClass.getName().substring(40);
    }
}
