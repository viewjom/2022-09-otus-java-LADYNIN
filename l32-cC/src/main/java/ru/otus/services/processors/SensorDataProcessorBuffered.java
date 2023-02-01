package ru.otus.services.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.SensorDataProcessor;
import ru.otus.api.model.SensorData;
import ru.otus.lib.SensorDataBufferedWriter;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;


// Этот класс нужно реализовать
public class SensorDataProcessorBuffered implements SensorDataProcessor {
    private static final Logger log = LoggerFactory.getLogger(SensorDataProcessorBuffered.class);

    private final int bufferSize;
    private final SensorDataBufferedWriter writer;
    private List<SensorData> bufferedData = new CopyOnWriteArrayList<>();
    private List<SensorData> bufferedDataSort = new CopyOnWriteArrayList<>();
    private BlockingQueue<SensorData> dataBuffer;

    public SensorDataProcessorBuffered(int bufferSize, SensorDataBufferedWriter writer) {

        this.bufferSize = bufferSize;
        this.writer = writer;
        dataBuffer = new ArrayBlockingQueue<>(bufferSize);
    }

    @Override
    public void process(SensorData data) {

        if (dataBuffer.size() >= bufferSize - 1) {

            dataBuffer.offer(data);

            flush();

        } else {

            dataBuffer.offer(data);
        }
    }

    public synchronized void flush() {

        bufferedData.clear();
        bufferedDataSort.clear();
        dataBuffer.drainTo(bufferedData);

        if (!bufferedData.isEmpty()) {

            bufferedDataSort = bufferedData.stream().sorted(Comparator.comparing(SensorData::getMeasurementTime)).collect(Collectors.toList());

            try {

                writer.writeBufferedData(bufferedDataSort);

            } catch (Exception e) {
                log.error("Ошибка в процессе записи буфера", e);
            }

        }
    }

    @Override
    public void onProcessingEnd() {

        flush();

    }
}