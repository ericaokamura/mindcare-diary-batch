package com.fiap.mindcare_diary_batch.services.prescriptions;

import com.fiap.mindcare_diary_batch.models.Prescription;
import com.fiap.mindcare_diary_batch.services.FirebaseService;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PrescriptionWriter implements ItemWriter<Prescription> {

    @Autowired
    private FirebaseService firebaseService;

    @Override
    public void write(Chunk<? extends Prescription> prescriptions) throws Exception {
        for (Prescription prescription : prescriptions) {

            String token = prescription.getPaciente().getToken();

            if (token == null || token.isBlank()) {
                continue;
            }

            String medicines = prescription.getMedicines().stream().reduce("", (a, b) -> a + ", " + b);

            Message message = Message.builder()
                    .setToken(token)
                    .setNotification(
                            Notification.builder()
                                    .setTitle("Aviso de vencimento de receita médica")
                                    .setBody(
                                            "Existe uma receita médica do(s) medicamento(s) "
                                                    + medicines + " prester a vencer em "
                                                    + prescription.getExpirationDate()
                                                    + "."
                                    )
                                    .build()
                    )
                    .build();

            firebaseService.sendNotification(message);
        }
    }
}
