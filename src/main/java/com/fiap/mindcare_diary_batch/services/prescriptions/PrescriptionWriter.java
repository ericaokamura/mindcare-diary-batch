package com.fiap.mindcare_diary_batch.services.prescriptions;

import com.fiap.mindcare_diary_batch.services.FirebaseService;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PrescriptionWriter implements ItemWriter<Message> {

    @Autowired
    private FirebaseService firebaseService;

    @Override
    public void write(Chunk<? extends Message> messages) throws Exception {
        for (Message message : messages) {
            firebaseService.sendNotification(message);
        }
    }
}
