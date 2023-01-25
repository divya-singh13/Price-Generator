package com.santander.fx.utils;


import com.santander.fx.models.ForeignExchange;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Utility {

    private static double calculateBidMargin(double price) {
        return price - (price * Constants.BID_MARGIN / 100);
    }

    private static double calculateAskMargin(double price) {
        return price + (price * Constants.ASK_MARGIN / 100);
    }

    public static ForeignExchange processMsg(String message) {
        if (message.contains("\n")) {
            message = message.lines().collect(Collectors.joining(" "));
        }
        return txnMapper(message);
    }

    private static ForeignExchange txnMapper(String msg) {
        String[] messageArray = msg.split(",");
        ForeignExchange foreignExchange = null;
        if (messageArray.length == 5) {
            String[] updatedMsgArray = Arrays.stream(messageArray).map(String::trim).toArray(String[]::new);

            foreignExchange = ForeignExchange.builder().id(updatedMsgArray[0])
                    .instrumentName(updatedMsgArray[1])
                    .bidPrice(calculateBidMargin(Double.parseDouble(updatedMsgArray[2])))
                    .askPrice(calculateAskMargin(Double.parseDouble(updatedMsgArray[3])))
                    .timeStamp(LocalDateTime.parse(updatedMsgArray[4],
                            DateTimeFormatter.ofPattern(Constants.DATE_TIME_PATTERN))).build();
        }
        return foreignExchange;
    }


}
