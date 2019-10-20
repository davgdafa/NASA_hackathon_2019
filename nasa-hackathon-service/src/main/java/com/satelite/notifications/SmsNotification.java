package com.satelite.notifications;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class SmsNotification {
    
    private static final String ACCOUNT_SID = "<ACCOUNT_SID>";
    private static final String AUTH_TOKEN = "<AUTH_TOKEN>";
    
    public static void sendMessage(String phoneNumber,
                                   String messageBody) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(phoneNumber),
                new com.twilio.type.PhoneNumber("<Twilio number>"),
                messageBody)
                .create();
        
        System.out.println(message.getSid());
    }
}
