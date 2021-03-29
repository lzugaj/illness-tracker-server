package com.luv2code.illnesstracker.service;

import java.util.List;

public interface IllnessTypeInfoService<T> {

    T findById(final Long id);

    T findByClassification(final String classification);

    T findByIndexValue(final Double... values);

    List<T> findAll();

}
