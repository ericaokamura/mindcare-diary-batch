package com.fiap.mindcare_diary_batch.services;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.FirebaseMessagingException;

@Service
public class FirebaseService {

    public String sendNotification(Message message) {
        try {
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Notificação Firebase enviada.");
            return response;
        } catch (FirebaseMessagingException e) {
            System.out.println("Erro ao enviar notificação: " + e.getMessage());
            return null;
        }
    }
}
