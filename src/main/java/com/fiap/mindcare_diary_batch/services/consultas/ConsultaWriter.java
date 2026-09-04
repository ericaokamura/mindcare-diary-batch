package com.fiap.mindcare_diary_batch.services.consultas;

import com.fiap.mindcare_diary_batch.models.Consulta;
import com.fiap.mindcare_diary_batch.services.FirebaseService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class ConsultaWriter implements ItemWriter<Consulta> {

    @Autowired
    private FirebaseService firebaseService;

    @Override
    public void write(Chunk<? extends Consulta> consultas) {
        for (Consulta consulta : consultas) {

            String token = consulta.getPaciente().getToken();

            if (token == null || token.isBlank()) {
                continue;
            }

            Message message = Message.builder()
                    .setToken(token)
                    .setNotification(
                            Notification.builder()
                                    .setTitle("Lembrete de Consulta")
                                    .setBody(
                                            "Sua consulta está agendada para "
                                                    + consulta.getDataHoraConsulta()
                                                    + " com o(a) profissional "
                                                    + consulta.getProfissional().getNomeCompleto()
                                                    + "."
                                    )
                                    .build()
                    )
                    .build();

            firebaseService.sendNotification(message);
        }
    }
}