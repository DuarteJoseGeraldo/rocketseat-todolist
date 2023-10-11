package br.com.joseduarte.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.joseduarte.todolist.user.IUserRepository;
import br.com.joseduarte.todolist.user.UserModel;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var authEconded = request.getHeader("Authorization").substring("Basic".length()).trim();

        String authDecoded = new String(Base64.getDecoder().decode(authEconded));

        String[] credentials = authDecoded.split(":");

        UserModel user = this.userRepository.findByUsername(credentials[0]);

        if (Objects.isNull(user)) response.sendError(401);
        else{
            if(BCrypt.verifyer().verify(credentials[1].toCharArray(), user.getPassword()).verified) filterChain.doFilter(request, response);
            else response.sendError(401);
        }

    }
}
