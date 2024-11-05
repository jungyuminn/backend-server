package club.gach_dong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import club.gach_dong.entity.Admin;
import club.gach_dong.entity.User;
import club.gach_dong.repository.AdminRepository;
import club.gach_dong.util.JwtUtil;
import club.gach_dong.dto.request.RegistrationRequest;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private JwtUtil jwtUtil;

    public void verifyCode(String email, String code) {
        String storedCode = redisTemplate.opsForValue().get(email);
        if (storedCode != null && storedCode.equals(code)) {
            redisTemplate.delete(email);
        } else {
            throw new RuntimeException("유효하지 않거나 만료된 인증 코드입니다.");
        }
    }

    public void completeRegistration(RegistrationRequest registrationRequest) {
        if (adminRepository.findByEmail(registrationRequest.email()).isPresent()) {
            throw new RuntimeException("이메일이 이미 사용 중입니다.");
        }

        Admin admin = Admin.of(
                registrationRequest.email(),
                passwordEncoder.encode(registrationRequest.password()),
                registrationRequest.name(),
                registrationRequest.role()
        );
        adminRepository.save(admin);
    }

    public Admin findByEmail(String email) {
        return adminRepository.findByEmail(email).orElse(null);
    }

    public boolean checkPassword(Admin admin, String rawPassword) {
        return passwordEncoder.matches(rawPassword, admin.getPassword());
    }

    public void resetPassword(String email, String newPassword) {
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("이메일이 등록되어 있지 않습니다."));

        admin.setPassword(passwordEncoder.encode(newPassword));
        adminRepository.save(admin);
    }

    public String generateRandomPassword() {
        int length = 8;
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        return password.toString();
    }

    public void deleteUser(String email) {
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("관리자를 찾을 수 없습니다."));
        adminRepository.delete(admin);
    }

    public void sendResetPasswordEmail(String email, String newPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("비밀번호 재발급");
        message.setText("안녕하세요! 아래의 임시 비밀번호로 로그인하세요:\n\n" + newPassword);
        mailSender.send(message);
    }

    public void updateUser(Admin admin) {
        adminRepository.save(admin);
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public void blacklistToken(String token) {
        jwtUtil.blacklistAdminToken(token);
    }

}
