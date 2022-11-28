package ru.otus.processor.homework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.processor.Processor;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ProcessorEvenSecondsTest {
    @Test
    @DisplayName("Тест EvenSecondsTest")

    void EvenSecondTest() {

        Processor processor = new ProcessorEvenSeconds(
                () -> LocalDateTime.of(2022,11,20,1,26,10));

        assertThrows(RuntimeException.class, () -> processor.process(null));

    }

    @Test
    @DisplayName("Тест OddSecondsTest")

    void OddSecondTest() {

        Processor processor = new ProcessorEvenSeconds(
                () -> LocalDateTime.of(2022,11,20,1,26,11));

        assertDoesNotThrow(() -> processor.process(null));
    }
}
