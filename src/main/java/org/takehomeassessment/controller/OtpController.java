package org.takehomeassessment.controller;



import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import org.takehomeassessment.data.dtos.payload.OtpRequestDto;
import org.takehomeassessment.data.dtos.payload.OtpValidationRequest;
import org.takehomeassessment.data.dtos.response.OtpResponseDto;
import org.takehomeassessment.services.sms.SmsService;

@RestController
@RequestMapping("/api/user/otp")
@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
public class OtpController {

    private final SmsService smsService;

    @PostMapping("/send-otp")
    public OtpResponseDto sendOtp(@RequestBody OtpRequestDto otpRequest) {
        log.info("inside sendOtp :: "+otpRequest.getUsername());
        return smsService.sendSMS(otpRequest);

    }
    @PostMapping("/validate-otp")
    public String validateOtp(@RequestBody OtpValidationRequest otpValidationRequest) {
        log.info("inside validateOtp :: "+otpValidationRequest.getUsername()+" "+otpValidationRequest.getOtpNumber());
        return smsService.validateOtp(otpValidationRequest);
    }

}