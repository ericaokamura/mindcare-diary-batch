package com.fiap.mindcare_diary_batch.services.prescriptions;

import com.fiap.mindcare_diary_batch.models.Prescription;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class PrescriptionProcessor implements ItemProcessor<Prescription, Message> {

    @Override
    public Message process(Prescription prescription) {
        if (prescription == null || prescription.getPaciente() == null) {
            return null;
        }

        String token = prescription.getPaciente().getToken();

        if (token == null || token.isBlank()) {
            return null;
        }

        String medicines = "";

        if (prescription.getMedicines() != null) {
            medicines = prescription.getMedicines()
                    .stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining(", "));
        }

        if (medicines.isBlank()) {
            medicines = "informado(s)";
        }

        return Message.builder()
                .setToken(token)
                .setNotification(
                        Notification.builder()
                                .setTitle("Aviso de vencimento de receita médica")
                                .setBody(
                                        "Existe uma receita médica do(s) medicamento(s) "
                                                + medicines
                                                + " prestes a vencer em "
                                                + prescription.getExpirationDate()
                                                + "."
                                )
                                .build()
                )
                .build();
    }
}
