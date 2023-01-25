package com.santander.fx;

import com.santander.fx.interfaces.MessageQueue;
import com.santander.fx.models.ForeignExchange;
import com.santander.fx.utils.Utility;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PriceGenerator implements MessageQueue {

    Map<String, ForeignExchange> map = new HashMap<>();

    @Override
    public void onMessage(String message) {
        ForeignExchange foreignExchange = Utility.processMsg(message);
        if(foreignExchange.getId()!=null && foreignExchange.getInstrumentName()!=null && foreignExchange.getTimeStamp()!=null ){
            map.put(foreignExchange.getInstrumentName(), foreignExchange);
        }
        System.out.println(getPrice(foreignExchange.getInstrumentName()));
    }

    public ForeignExchange getPrice(String instrumentName) {
        instrumentName = Optional.ofNullable(instrumentName).orElseGet(String::new);
        String finalInstrumentName = instrumentName;
        return Optional.ofNullable(map.get(instrumentName))
                .orElseThrow(() -> new RuntimeException("Instrument not found " + finalInstrumentName));
    }

    public static void main(String[] args) {
        new PriceGenerator().onMessage("106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001");
    }
}