package com.example.playstationdemo.payload;

import com.example.playstationdemo.entity.Role;
import com.example.playstationdemo.entity.User;
import com.example.playstationdemo.utills.CommonUtills;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private Long id;

    @NotBlank(message = "fio ni to'ldirish shart")
    private String fio;

    @NotBlank(message = "login is mandatory")
//    @Size(min = 5, max = 15, message = "login at least, min = 5 and max = 15 characters")
    private String login;

    @NotBlank(message = "password is mandatory")
//    @Size(min = 8, max = 15, message = "password length should be at least min = 5 and max = 15")
    private String password;

    private Set<Role> roles;

    public UserDto(Long id, String fio, String login, Set<Role> roles) {
        this.id = id;
        this.fio = fio;
        this.login = login;
        this.roles = roles;
    }

    public User mapToEntity(){
        User user = new User();
        user.setFio(this.fio);
        user.setPassword(CommonUtills.passwordEncoder(this.password));
        user.setLogin(this.login);
        user.setRoles(this.roles);
        return user;
    }

    public User mapToEntity(User user){
        user.setFio(this.fio);
        user.setLogin(this.login);
        user.setRoles(this.roles);
        return user;
    }
}
