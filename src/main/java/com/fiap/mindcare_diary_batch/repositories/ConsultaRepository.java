package com.fiap.mindcare_diary_batch.repositories;

import com.fiap.mindcare_diary_batch.models.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

}
