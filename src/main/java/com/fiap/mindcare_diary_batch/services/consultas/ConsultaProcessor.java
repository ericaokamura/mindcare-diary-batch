package com.fiap.mindcare_diary_batch.services.consultas;

import com.fiap.mindcare_diary_batch.models.Consulta;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ConsultaProcessor implements ItemProcessor<Consulta, Message> {

    @Override
    public Message process(Consulta consulta) throws Exception {
        String token = consulta.getPaciente().getToken();

        if (token == null || token.isBlank()) {
            return null;
        }

        return Message.builder()
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

    }
}
