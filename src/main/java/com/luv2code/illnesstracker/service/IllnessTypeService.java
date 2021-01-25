package com.luv2code.illnesstracker.service;

import com.luv2code.illnesstracker.domain.User;
import com.luv2code.illnesstracker.domain.base.BaseIllness;

import java.util.List;

public interface IllnessTypeService<T extends BaseIllness> {

    T save(final User user, final T illness);

    T findById(final Long id);

    List<T> findAll();

    List<T> findAllForUser(final User user);

    T update(final T oldIllness, final T newIllness);

    void delete(final T illness);

}
