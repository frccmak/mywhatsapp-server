package com.fmak.mywhatappserver.listener;

import com.fmak.mywhatappserver.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLOutput;

@Component
public class MessageListener {

    private final Logger logger = LoggerFactory.getLogger(MessageListener.class);

    @Autowired
    SimpMessagingTemplate template;

    @Value("${com.fmak.mywhatsappserver.kafka.listener.webSocketTopic}")
    private String webSocketTopic;

    @KafkaListener(
            topics = "${com.fmak.mywhatsappserver.kafka.topic}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void listen(Message message) {
        String logMessage = "Sending message via Kafka Listener to all WebSocket Clients listening "
        + "to topic " + webSocketTopic;
        logger.info(String.format("%s", logMessage));

        template.convertAndSend(webSocketTopic, message);
    }
}
