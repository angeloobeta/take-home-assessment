package org.takehomeassessment.utils;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.takehomeassessment.data.dtos.payload.OtpRequestDto;
import org.takehomeassessment.data.dtos.response.OtpResponseDto;
import org.takehomeassessment.data.entities.OtpStatus;


@Component
@Service
@EnableConfigurationProperties
@RequiredArgsConstructor
public class TwilioUtils {

    @Value("${TWILIO_ACCOUNT_SID}")
    private static String accountSid;
    @Value("${TWILIO_AUTH_TOKEN}")
    private static String apiKey;
    @Value("${TWILIO_PHONE_NUMBER}")
    private static String phoneNumber;



    @PostConstruct
    public void setup() {
        System.out.println("=============================================================");
        System.out.println(accountSid);
        System.out.println(apiKey);
        Twilio.init(accountSid, apiKey);
    }


    // send verification code to user's phone number
    public OtpResponseDto sendVerificationCode(OtpRequestDto otpRequestDto, String sentOtpCode) {
        OtpResponseDto otpResponseDto = null;
        try{
            String otpMessage = "Your verification code is: " + sentOtpCode;
            Message.creator(
                    new PhoneNumber(phoneNumber),
                    new PhoneNumber(otpRequestDto.getPhoneNumber()),
                    otpMessage).create();
            otpResponseDto = new OtpResponseDto(OtpStatus.DELIVERED, otpMessage);

        }catch (Exception e){
            e.printStackTrace();
            otpResponseDto = new OtpResponseDto(OtpStatus.FAILED, e.getMessage());
        }
        return otpResponseDto;
    }

    // generate verification code with random 6 digits
    public String generateVerificationCode() {
        return String.valueOf((int) ((Math.random() * (999999 - 100000)) + 100000));
    }
}
