package com.example.ss_2022_e2_c1.config.security;

import com.example.ss_2022_e2_c1.entities.Authority;
import com.example.ss_2022_e2_c1.entities.SecurityUser;
import com.example.ss_2022_e2_c1.entities.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
@AllArgsConstructor
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private final CustomAuthenticationManager customAuthenticationManager;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        // 1.tạo một authentication object chưa xác thực
        // 2.ủy quyền authentication object đến manager
        // 3.quay trở lại authentication từ manager
        // 4.nếu đối tượng đã authenticated sau đó gửi request đến filter tiếp theo trong chain
        SaveFilterChain.setFilterChain(filterChain);
        String key = String.valueOf(request.getHeader("key"));
        if (Objects.isNull(key) || key.length() == 0 || key.equals("null")) {
            filterChain.doFilter(request, response);
            return;
        }
        CustomAuthentication ca = new CustomAuthentication(false, key);

        var a = customAuthenticationManager.authenticate(ca);
        if (a.isAuthenticated()) {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    new SecurityUser(new User(1, "2", "3", Set.of(new Authority(1, "read")))),
                    null,
                    List.of(new SimpleGrantedAuthority("read"))
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
    }
}
