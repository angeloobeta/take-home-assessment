package org.takehomeassessment.services.sms;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.takehomeassessment.data.dtos.payload.OtpRequestDto;
import org.takehomeassessment.data.dtos.response.OtpResponseDto;
import org.takehomeassessment.data.dtos.payload.OtpValidationRequest;

import lombok.extern.slf4j.Slf4j;
import org.takehomeassessment.utils.TwilioUtils;

@Service
@Slf4j
@RequiredArgsConstructor
public class SmsService {

    private TwilioUtils twilioUtils;


    public OtpResponseDto sendSMS(OtpRequestDto otpRequestDto) {
        return twilioUtils.sendVerificationCode(otpRequestDto);
    }


    public String validateOtp(OtpValidationRequest otpValidationRequest) {
        return twilioUtils.validateOtp(otpValidationRequest);
    }
}
