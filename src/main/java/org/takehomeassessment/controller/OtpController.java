package org.takehomeassessment.controller;



import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import org.takehomeassessment.data.dtos.payload.OtpValidationRequest;
import org.takehomeassessment.services.sms.OtpServices;

@RestController
@RequestMapping("/api/user/otp")
@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
public class OtpController {

    private final OtpServices otpServices;

    @PostMapping("/validate-otp")
    public String validateOtp(@RequestBody OtpValidationRequest otpValidationRequest) {
        log.info("inside validateOtp :: "+otpValidationRequest.getPhoneNumber()+" "+otpValidationRequest.getOtpNumber());
        return otpServices.validateOtp(otpValidationRequest);
    }

}