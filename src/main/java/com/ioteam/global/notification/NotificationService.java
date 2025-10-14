package com.ioteam.global.notification;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NotificationService {

    public void sendPushNotification(String token, String title, String body, Map<String, String> data) {
        if (token == null || token.isEmpty()) {
            System.out.println("푸시 알림 전송 실패: 토큰이 없음");
            return;
        }

        Message.Builder messageBuilder = Message.builder()
            .setToken(token)
            .setNotification(Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build());

        if (data != null && !data.isEmpty()) {
            messageBuilder.putAllData(data);
        }

        Message message = messageBuilder.build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("푸시 알림 전송 성공: " + response);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException("푸시 알림 전송 실패", e);
        }
    }
}