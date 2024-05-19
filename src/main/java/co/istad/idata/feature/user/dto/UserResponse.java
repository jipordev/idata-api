package co.istad.idata.feature.user.dto;

public record UserResponse(

        Long id,
        String email,
        String firstName,
        String lastName,
        String username,
        String password,
        String avatar,
        String uuid

) {
}
