package org.takehomeassessment.data.repositories;

import org.takehomeassessment.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByPhoneNumber(String phoneNumber);
    List<User> findAllByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrPhoneNumberContainsIgnoreCase(String keyword, String keyword1, String keyword2);
    boolean findByVerified(boolean isVerified);


}
