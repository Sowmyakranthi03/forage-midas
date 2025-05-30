package com.jpmc.midascore;

import com.jpmc.midascore.entity.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    private final String topic;
    private final KafkaTemplate<String, Transaction> kafkaTemplate;
public void send(String transactionLine) {
    String[] transactionData = transactionLine.split(",\\s*");
    
    if (transactionData.length < 3) {
        throw new IllegalArgumentException("Invalid transaction line format: " + transactionLine);
    }
    
    Transaction transaction = new Transaction();
    transaction.setTransactionId(transactionData[0]);
    transaction.setAccountId(transactionData[1]);
    transaction.setAmount(Double.parseDouble(transactionData[2]));
    
    // timestamp is optional or set to some default or current time
    transaction.setTimestamp(""); // or new Date().toString();
    
    kafkaTemplate.send(topic, transaction);
}

    public KafkaProducer(@Value("${midas.kafka.transactions-topic}") String topic, KafkaTemplate<String, Transaction> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

   
}
