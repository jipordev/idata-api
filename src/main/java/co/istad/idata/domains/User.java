package co.istad.idata.domains;

import co.istad.idata.domains.audit.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "users", schema = "public")
public class User extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String firstName;

    private String lastName;

    private String username;

    private String password;

    private String uuid;

    private String avatar;

    private Boolean isAccountNonExpired;

    private Boolean isAccountNonLocked;

    private Boolean isAccountStatus;

    private Boolean isCredentialsNonExpired;

    private Boolean isBlocked;

    private Boolean isDeleted;

    private Boolean isProfiledVisibility;

    private Boolean isEmailVerified;

    private LocalDateTime lastLoginAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

}
