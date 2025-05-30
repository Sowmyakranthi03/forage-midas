package com.jpmc.midascore.foundation;

import com.jpmc.midascore.entity.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.CopyOnWriteArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TransactionListener {

    private static final Logger logger = LoggerFactory.getLogger(TransactionListener.class);

    public static final CopyOnWriteArrayList<Transaction> receivedTransactions = new CopyOnWriteArrayList<>();

    @KafkaListener(topics = "${midas.kafka.transactions-topic}", groupId = "midas-core-group", containerFactory = "transactionKafkaListenerContainerFactory")
    public void listen(Transaction transaction) {
        logger.info("Received transaction: {}", transaction);
        receivedTransactions.add(transaction);
    }
}
