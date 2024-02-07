package br.com.bahcor.springbootauditsample.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

public class UserAuditorAware implements AuditorAware<String>  {

    @Override
    public Optional<String> getCurrentAuditor() {
        
        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // if (authentication == null || !authentication.isAuthenticated()) {
        //     return Optional.of("anonymous");
        // }
        // return Optional.of(authentication.getName());

        return Optional.of("anonymous");
    }
    
}
