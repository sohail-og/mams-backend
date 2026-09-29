package com.mams.backend.repository;

import com.mams.backend.entity.Base;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseRepository extends JpaRepository<Base, Long> {
}
