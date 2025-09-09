package com.andreiapp.andreiapp.security;

import com.andreiapp.andreiapp.entities.User;
import com.andreiapp.andreiapp.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    //loadUserByUsername: Metodo que spring llama cuando alguien intenta autenticarse
    //y si no existe el usuario lanzamos esta excepcion UsernameNotFoundException
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //Traemos la entidad de la db
        //consultamos a la DB por email
        User user = userRepo.findByEmail(username)
                //Si esta vacio lanzamos una excepcion
                .orElseThrow();

        //Devolvemos el UserDetails
        //empezamos a construir un AuthUser
        return AuthUser.builder()
                //Le pasamos los datos del usuario en la db para que Spring Security los pueda autenticar
                .user(user)
                //terminamos de crear el objeto y se los pasamos a Spring Security
                .build();
    }
}
