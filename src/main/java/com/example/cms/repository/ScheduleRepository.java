package com.example.cms.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cms.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer>{

	List<Schedule> findByDateTimeLessThanEqual(LocalDateTime now);

}
