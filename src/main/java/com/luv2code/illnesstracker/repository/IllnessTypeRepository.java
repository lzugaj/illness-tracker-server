package com.luv2code.illnesstracker.repository;

import com.luv2code.illnesstracker.domain.base.BaseIllness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IllnessTypeRepository<T extends BaseIllness> extends JpaRepository<T, Long> {

}
