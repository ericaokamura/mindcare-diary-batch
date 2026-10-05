package com.fiap.mindcare_diary_batch.repositories;

import com.fiap.mindcare_diary_batch.models.Paciente;
import com.fiap.mindcare_diary_batch.models.Prescription;
import com.fiap.mindcare_diary_batch.models.Profissional;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PrescriptionRepository {

    private final JdbcTemplate jdbcTemplate;

    public PrescriptionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Prescription> encontrePrescricoesVencendo() {
        return jdbcTemplate.execute(

                (CallableStatementCreator) connection -> {
                        CallableStatement cs = connection.prepareCall(
                                    "{ ? = call MINDCARE.VERIFICA_PRESCRICOES_VENCENDO() }"
                            );

                    cs.registerOutParameter(
                            1,
                            Types.REF_CURSOR
                    );

                    return cs;
                },

                (CallableStatementCallback<
                                        List<Prescription>>) cs -> {

                    cs.execute();

                    List<Prescription> result =
                            new ArrayList<>();

                    try (ResultSet rs =
                                 (ResultSet) cs.getObject(1)) {

                        while (rs.next()) {

                            Paciente paciente = new Paciente();

                            paciente.setId(
                                    rs.getLong("PACIENTE_ID")
                            );

                            paciente.setNomeUsuario(
                                    rs.getString("PACIENTE_NOME_USUARIO")
                            );

                            paciente.setNomeCompleto(
                                    rs.getString("PACIENTE_NOME_COMPLETO")
                            );

                            paciente.setToken(
                                    rs.getString("PACIENTE_TOKEN")
                            );


                            Profissional profissional = new Profissional();

                            profissional.setId(
                                    rs.getLong("PROFISSIONAL_ID")
                            );

                            profissional.setNomeUsuario(
                                    rs.getString("PROFISSIONAL_NOME_USUARIO")
                            );

                            profissional.setNomeCompleto(
                                    rs.getString("PROFISSIONAL_NOME_COMPLETO")
                            );


                            Prescription prescription = new Prescription();

                            prescription.setId(
                                    rs.getLong("ID")
                            );

                            prescription.setNumero(
                                    rs.getString("NUMERO")
                            );


                            Date issueDate = rs.getDate("ISSUE_DATE");

                            if (issueDate != null) {
                                prescription.setIssueDate(
                                        issueDate.toLocalDate()
                                );
                            }


                            Date expirationDate =
                                    rs.getDate("EXPIRATION_DATE");

                            if (expirationDate != null) {
                                prescription.setExpirationDate(
                                        expirationDate.toLocalDate()
                                );
                            }


                            prescription.setDaysRemaining(
                                    rs.getLong("DAYS_REMAINING")
                            );

                            prescription.setControlled(
                                    rs.getBoolean("CONTROLLED")
                            );

                            prescription.setValid(
                                    rs.getBoolean("VALID")
                            );

                            prescription.setPaciente(paciente);

                            prescription.setProfissional(profissional);


                            result.add(prescription);
                        }
                    }

                    return result;
                }
        );
    }
}