package com.fiap.mindcare_diary_batch.repositories;

import com.fiap.mindcare_diary_batch.models.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

}
