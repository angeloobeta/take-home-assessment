package org.takehomeassessment.utils;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.takehomeassessment.config.TwilioConfig;
import org.takehomeassessment.data.dtos.payload.OtpRequestDto;
import org.takehomeassessment.data.dtos.payload.OtpValidationRequest;
import org.takehomeassessment.data.dtos.response.OtpResponseDto;
import org.takehomeassessment.data.entities.OtpStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@Service
@EnableConfigurationProperties
@RequiredArgsConstructor
public class TwilioUtils {

    Map<String, String> otpMap = new HashMap<>();


    @Value("${TWILIO_ACCOUNT_SID}")
    private static String accountSid;
    @Value("${TWILIO_AUTH_TOKEN}")
    private static String apiKey;
    @Value("${TWILIO_PHONE_NUMBER}")
    private static String phoneNumber;

    @PostConstruct
    public void setup() {
        Twilio.init(accountSid, apiKey);
    }


    // send verification code to user's phone number
    public OtpResponseDto sendVerificationCode(OtpRequestDto otpRequestDto) {
        OtpResponseDto otpResponseDto = null;
        try{
            String otpMessage = "Your verification code is: " + generateVerificationCode();
            Message.creator(
                    new PhoneNumber(phoneNumber),
                    new PhoneNumber(otpRequestDto.getPhoneNumber()),
                    otpMessage).create();
            otpMap.put(otpRequestDto.getUsername(), generateVerificationCode());
            otpResponseDto = new OtpResponseDto(OtpStatus.DELIVERED, otpMessage);

        }catch (Exception e){
            e.printStackTrace();
            otpResponseDto = new OtpResponseDto(OtpStatus.FAILED, e.getMessage());
        }
        return otpResponseDto;
    }

    // verify phone number
    public String validateOtp(OtpValidationRequest otpValidationRequest ) {
        Set<String> keys = otpMap.keySet();
        String username = null;
        for(String key : keys)
            username = key;
        if (otpValidationRequest.getUsername().equals(username)) {
            otpMap.remove(username,otpValidationRequest.getOtpNumber());
            return "OTP is valid!";
        } else {
            return "OTP is invalid!";
        }
    }

    // generate verification code with random 6 digits
    public static String generateVerificationCode() {
        return String.valueOf((int) ((Math.random() * (999999 - 100000)) + 100000));
    }
}
