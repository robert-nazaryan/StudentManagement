package org.example.studentmanagement.security;


import lombok.Getter;
import org.example.studentmanagement.entity.User;
import org.springframework.security.core.authority.AuthorityUtils;


@Getter
public class SpringUser extends org.springframework.security.core.userdetails.User {

    private final User user;

    public SpringUser(User user) {
        super(user.getEmail(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getType().name()));
        this.user = user;
    }

}
