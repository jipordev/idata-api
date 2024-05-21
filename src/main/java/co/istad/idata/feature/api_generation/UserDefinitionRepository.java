package co.istad.idata.feature.api_generation;

import co.istad.idata.domains.UserDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDefinitionRepository extends JpaRepository<UserDefinition, Long> {
}
