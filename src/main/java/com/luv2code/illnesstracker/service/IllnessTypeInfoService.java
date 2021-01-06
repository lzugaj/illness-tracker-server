package com.luv2code.illnesstracker.service;

public interface IllnessTypeInfoService<T> {

    T findById(final Long id);

    T findByClassification(final String classification);

    T findByIndexValue(final Double... values);

}
