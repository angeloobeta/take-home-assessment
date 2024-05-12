package org.takehomeassessment.utils;

import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component

public class TwilioUtils {

    @Value("${ACCOUNT_SID}")
    private static String accountSid;
    @Value("${AUTH_TOKEN}")
    private static String apiKey;

    static {
        Twilio.init(accountSid, apiKey);
    }


    // send verification code to user's phone number
    public static void sendVerificationCode(String phoneNumber, String code) {
        com.twilio.rest.api.v2010.account.Message.creator(
                new com.twilio.type.PhoneNumber(phoneNumber),
                new com.twilio.type.PhoneNumber("+12056266099"),
                "Your verification code is: " + code
        ).create();
    }

    // verify phone number
    public static boolean verifyPhoneNumber(String phoneNumber, String code) {
        return true;
    }

    // generate verification code with random 6 digits
    public static String generateVerificationCode() {
        return String.valueOf((int) ((Math.random() * (999999 - 100000)) + 100000));
    }

}
