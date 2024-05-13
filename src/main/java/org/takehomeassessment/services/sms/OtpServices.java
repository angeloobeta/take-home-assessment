package org.takehomeassessment.services.sms;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.takehomeassessment.data.dtos.payload.OtpRequestDto;
import org.takehomeassessment.data.dtos.response.OtpResponseDto;
import org.takehomeassessment.data.dtos.payload.OtpValidationRequest;

import lombok.extern.slf4j.Slf4j;
import org.takehomeassessment.data.entities.Otp;
import org.takehomeassessment.data.entities.User;
import org.takehomeassessment.data.repositories.OtpRepository;
import org.takehomeassessment.data.repositories.UserRepository;
import org.takehomeassessment.utils.TwilioUtils;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OtpServices {

    private TwilioUtils twilioUtils;
    final OtpRepository otpRepository;
    final UserRepository userRepository;


    public OtpResponseDto sendSMS(OtpRequestDto otpRequestDto, String sentOtpCode) {
        return twilioUtils.sendVerificationCode(otpRequestDto, sentOtpCode);
    }


    public String validateOtp(OtpValidationRequest otpValidationRequest) {
        boolean isValidOTp  = verifyOtp(otpValidationRequest.getPhoneNumber(), otpValidationRequest.getOtpNumber());
        if(isValidOTp) {
            return "OTP verified";
        }else{
            return "Invalid OTP";
        }
    }

    public boolean verifyOtp(String phoneNumber, String otpCode) {
        Optional<Otp> otp = otpRepository.findByPhoneNumber(phoneNumber);
        if (otp.isPresent() && otp.get().getOtpCode().equals(otpCode)) {
            User user = otp.get().getUser();
            user.setVerified(true);
            userRepository.save(user);
            otpRepository.delete(otp.get());
            return true;
        }
        return false;
    }
}
