package no.lundesgaard.sudokufeud.api.security;

import static java.util.Collections.unmodifiableSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

class DefaultAuthentication implements Authentication {
    private static final long serialVersionUID = -7625573154442752673L;
    
    private final Set<GrantedAuthority> authorities;
    private Object principal;
    private Object credentials;
    private Object details;
    private boolean authenticated;

    public DefaultAuthentication(
            Authentication authentication, 
            boolean authenticated, 
            String... roles) {
        
        this.principal = authentication.getPrincipal();
        this.credentials = authentication.getCredentials();
        this.details = authentication.getDetails();
        this.authenticated = authenticated;
        authorities = initAuthorities(roles);
    }

    private Set<GrantedAuthority> initAuthorities(String[] roles) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (String role : roles) {
            authorities.add(new DefaultGrantedAuthority(role));
        }
        return unmodifiableSet(authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getDetails() {
        return details;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
        if (!authenticated) {
            this.authenticated = false;
        }
        throw new IllegalArgumentException("authentication rejected");
    }

    @Override
    public String getName() {
        return String.valueOf(principal);
    }

    private static class DefaultGrantedAuthority implements GrantedAuthority {
        private static final long serialVersionUID = 7112157903997244674L;
        
        private final String role;

        public DefaultGrantedAuthority(String role) {
            this.role = role;
        }

        @Override
        public String getAuthority() {
            return role;
        }
    }
}
