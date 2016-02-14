package com.taskmanager.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository("customUserDetailsService")
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager newEm) {
        this.entityManager = newEm;
    }


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            com.taskmanager.model.User domainUser = entityManager.createQuery("from User where username = :username", com.taskmanager.model.User.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return new User(
                    domainUser.getUsername(),
                    domainUser.getPassword(),
                    domainUser.isEnabled(),
                    domainUser.isAccountNonExpired(),
                    domainUser.isCredentialsNonExpired(),
                    domainUser.isAccountNonLocked(),
                    domainUser.getAuthorities());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
