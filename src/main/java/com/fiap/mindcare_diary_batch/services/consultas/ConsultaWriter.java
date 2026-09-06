package com.fiap.mindcare_diary_batch.services.consultas;

import com.fiap.mindcare_diary_batch.services.FirebaseService;
import com.google.firebase.messaging.Message;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsultaWriter implements ItemWriter<Message> {

    @Autowired
    private FirebaseService firebaseService;

    @Override
    public void write(Chunk<? extends Message> messages) {
        for (Message message : messages) {
            firebaseService.sendNotification(message);
        }
    }
}