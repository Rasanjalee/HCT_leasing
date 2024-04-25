package com.amarasiricoreservice.DTO;

public interface UserDetails {
    Authentication authentication =
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
}
