package co.istad.idata.feature.api_generation;

import co.istad.idata.domains.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataRepository extends JpaRepository<UserData, Long> {
}
