package co.istad.idata.init;

import co.istad.idata.domains.Authority;
import co.istad.idata.domains.Role;
import co.istad.idata.feature.user.AuthorityRepository;
import co.istad.idata.feature.user.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final RoleRepository roleRepository;

    @PostConstruct
    void initRole(){

        // Auto generate role (USER, ADMIN)
        if (roleRepository.count() < 1){

            Authority userRead = new Authority();
            userRead.setName("user:read");
            Authority userWrite = new Authority();
            userWrite.setName("user:write");

            Role user = new Role();
            user.setName("USER");
            user.setAuthorities(List.of(
                    userWrite
            ));
            Role admin = new Role();
            admin.setName("ADMIN");
            admin.setAuthorities(List.of(
                    userRead, userWrite
            ));

            roleRepository.saveAll(List.of(
                    user, admin
            ));

        }

    }

}
