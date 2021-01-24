package com.luv2code.illnesstracker.repository.info;

import com.luv2code.illnesstracker.domain.base.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface IllnessTypeInfoRepository<T extends BaseEntity> extends JpaRepository<T, Long> {

    Optional<T> findByClassification(final String classification);

}
