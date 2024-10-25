package com.vivid.time_sheet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        String sql = "SELECT e.email, e.password, r.role_name " +
                "FROM Employees e " +
                "JOIN Employee_Roles er ON e.id = er.employee_id " +
                "JOIN Roles r ON er.role_id = r.id " +
                "WHERE e.email = ?";

        try {
            return jdbcTemplate.query(sql, new Object[]{email}, (ResultSet rs) -> {
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();

                String username = null;
                String password = null;

                // Loop through ResultSet to extract roles
                while (rs.next()) {
                    if (username == null) {
                        username = rs.getString("email");
                        password = rs.getString("password");
                    }
                    String roleName = rs.getString("role_name");
                    authorities.add(new SimpleGrantedAuthority(roleName));
                }

                if (username == null) {
                    throw new UsernameNotFoundException("User not found");
                }

                return User.withUsername(username)
                        .password(password)
                        .authorities(authorities)
                        .build();
            });
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found", e);
        }
    }
}
