package com.santander.fx;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

class PriceGeneratorTest {
    PriceGenerator priceGenerator = Mockito.mock(PriceGenerator.class);

    @ParameterizedTest()
    @CsvSource(value = {
            "'106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001'",
            "'107, EUR/JPY, 119.60,119.90,01-06-2020 12:01:02:002'",
            "'108, GBP/USD, 1.2500,1.2560,01-06-2020 12:01:02:002'",
            "'109, GBP/USD, \n1.2499,1.2561,01-06-2020 12:01:02:100'",
            "'110, EUR/JPY, 119.61,119.91,01-06-2020 12:01:02:110'",
    })
    void getPriceTestSuccess(String message) {
        priceGenerator.onMessage(message);
        Mockito.verify(priceGenerator, Mockito.times(1)).onMessage(message);
    }

    @ParameterizedTest()
    @CsvSource(value = {
            "'id;123'"
    })
    void getPriceTestFail(String message) {
        PriceGenerator priceGenerator = new PriceGenerator();
        Assertions.assertThrows(RuntimeException.class, () -> priceGenerator.onMessage(message));
    }
}
