package co.istad.idata.feature.user.registration.token;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByToken(String token);

    Optional<VerificationToken> findByUserIdAndType(Long userId, VerificationToken.TokenType type);

}
