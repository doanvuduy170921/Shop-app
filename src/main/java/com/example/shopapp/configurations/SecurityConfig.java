package com.example.shopapp.configurations;

import com.example.shopapp.model.User;
import com.example.shopapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final UserRepository userRepository;
// Đầu tiên sẽ khởi tạo 1 đối tượng user detail theo chuẩn của java spring .
// Khi đăng nhập vào hệ thống, tạo ra đối tượng đó để
// quản lý việc đăng nhập thông qua đối tượng user detail.

    @Bean // khởi tạo đối tượng và inject vào
    public UserDetailsService userDetailsService() {
        // UserDetailService : là kiểu interface --> kiểu trả về sẽ là 1 function
        return phoneNumber -> userRepository
                .findByPhoneNumber(phoneNumber)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }
    // Khi người dùng đăng ký hoặc thay đổi mật khẩu,
    // mật khẩu sẽ được mã hóa bằng thuật toán BCrypt trước khi lưu vào cơ sở dữ liệu.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return  config.getAuthenticationManager();
    }
}
