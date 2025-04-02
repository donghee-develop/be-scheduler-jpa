package test.schedule.dto.user.security;

import lombok.Getter;
import test.schedule.entity.User;

@Getter
public class UserDTO {
    private final Long userId;
    private final String email;
    private final String name;

    public UserDTO(User user) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.name = user.getName();
    }
}
