package org.takehomeassessment.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.takehomeassessment.data.entities.Otp;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, String> {
    Optional<Otp> findByPhoneNumber(String token);
    Optional<Otp> findByOtpCode(String otpCode);
    Optional<Otp> findByOtpCodeAndPhoneNumber(String otpId, String phoneNumber);

}
