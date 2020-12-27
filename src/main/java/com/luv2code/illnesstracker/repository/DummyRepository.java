package com.luv2code.illnesstracker.repository;

import com.luv2code.illnesstracker.domain.Dummy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DummyRepository extends JpaRepository<Dummy, Long> {

    @Query(value = "select nextval('dummy_id_sequence')", nativeQuery = true)
    Long getNextSeriesId();

}
