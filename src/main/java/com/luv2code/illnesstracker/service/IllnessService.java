package com.luv2code.illnesstracker.service;

import com.luv2code.illnesstracker.domain.User;
import com.luv2code.illnesstracker.domain.illness.Illness;

import java.util.List;

public interface IllnessService {

    List<Illness> select(final User user, final List<Illness> illnesses);

    List<Illness> findAll();

}
