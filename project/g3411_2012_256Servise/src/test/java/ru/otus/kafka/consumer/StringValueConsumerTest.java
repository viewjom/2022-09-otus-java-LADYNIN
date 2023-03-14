package ru.otus.kafka.consumer;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.*;
import java.util.stream.LongStream;

import static org.apache.kafka.clients.CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.CommonClientConfigs.CLIENT_ID_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.*;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;
import static ru.otus.kafka.consumer.JsonSerializer.OBJECT_MAPPER;
import static ru.otus.kafka.consumer.MyConsumer.TOPIC_NAME;

class StringValueConsumerTest {
    private static final Logger log = LoggerFactory.getLogger(StringValueConsumerTest.class);

    @BeforeAll
    public static void init() throws ExecutionException, InterruptedException, TimeoutException {
        KafkaBase.start(List.of(new NewTopic(TOPIC_NAME, 1, (short) 1)));
    }

    @Test
    void dataHandlerTest()  {
        //given
        List<StringValue> stringValues = LongStream.range(0, 9).boxed()
                .map(idx -> new StringValue(idx, "test333:" + idx))
                .toList();
        putValuesToKafka(stringValues);

        var myConsumer = new MyConsumer(KafkaBase.getBootstrapServers());

        List<StringValue> factStringValues = new CopyOnWriteArrayList<>();
        var dataConsumer = new StringValueConsumer(myConsumer, factStringValues::add);

        //when
        CompletableFuture.runAsync(dataConsumer::startConsume);

        //then
        await().atMost(30, TimeUnit.SECONDS).until(() -> factStringValues.size() == stringValues.size());
        Assertions.assertThat(factStringValues).hasSameElementsAs(stringValues);
        dataConsumer.stopSending();


    }

    private void putValuesToKafka(List<StringValue> stringValues) {
        Properties props = new Properties();
        props.put(CLIENT_ID_CONFIG, "myKafkaTestProducer");
        props.put(BOOTSTRAP_SERVERS_CONFIG, KafkaBase.getBootstrapServers());
        props.put(ACKS_CONFIG, "0");
        props.put(LINGER_MS_CONFIG, 1);
        props.put(KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        props.put(VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(OBJECT_MAPPER, new ObjectMapper());

        log.info("sending values, counter:{}", stringValues.size());
        try (var kafkaProducer = new KafkaProducer<Long, StringValue>(props)) {
            for (var value : stringValues) {
                kafkaProducer.send(new ProducerRecord<>(TOPIC_NAME, value.id(), value));
            }
        }
        log.info("done");
    }
}