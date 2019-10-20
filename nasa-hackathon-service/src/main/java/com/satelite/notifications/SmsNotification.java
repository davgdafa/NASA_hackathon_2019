package com.satelite.notifications;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class SmsNotification {
    
    private static final String ACCOUNT_SID = "ACf0aa05eec8e401f01b20cdabdf3d73ce";
    private static final String AUTH_TOKEN = "4774efa5515106634efe3d867eb24da4";
    
    public static void sendMessage(String phoneNumber,
                                   String messageBody) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(phoneNumber),//"+1 786-624-1347"
                new com.twilio.type.PhoneNumber("(435) 565-4118"),
                messageBody)
                .create();
        
        System.out.println(message.getSid());
    }
}
