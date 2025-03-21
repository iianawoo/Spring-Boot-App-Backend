package com.example.todolist.web.security.expression;

import com.example.todolist.domain.user.Role;
import com.example.todolist.service.UserService;
import com.example.todolist.web.security.JwtEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("SecurityExpression")
@AllArgsConstructor
public class SecurityExpression {

    private final UserService userService;



    public boolean canAccessUser(Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtEntity user = (JwtEntity) authentication.getPrincipal();
        Long userId = user.getId();

        return userId.equals(id) || hasAnyRole(authentication, Role.ROLE_ADMIN);
    }




    private boolean hasAnyRole(Authentication authentication, Role... roles){
        for (Role role: roles) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.name());
            if(authentication.getAuthorities().contains(simpleGrantedAuthority)) return true;
        }
        return false;
    }


    public boolean canAccessTask(Long taskId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtEntity user = (JwtEntity) authentication.getPrincipal();
        return userService.isTaskOwner(user.getId(), taskId);

    }
}
