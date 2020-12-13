package com.luv2code.illnesstracker.service;

import com.luv2code.illnesstracker.domain.Illness;
import com.luv2code.illnesstracker.domain.Patient;

import java.util.List;

public interface IllnessService {

    List<Illness> select(final Patient patient, final List<Illness> illnesses);

    List<Illness> findAll();

}
