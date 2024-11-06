package club.gach_dong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import club.gach_dong.entity.Role;
import club.gach_dong.entity.User;
import club.gach_dong.repository.UserRepository;
import club.gach_dong.util.JwtUtil;
import club.gach_dong.dto.request.RegistrationRequest;
import club.gach_dong.dto.request.ChangePasswordRequest;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private JwtUtil jwtUtil;

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int PASSWORD_LENGTH = 8;

    public void sendVerificationCode(String email) {
        try {
            if (!isValidEmail(email)) {
                throw new RuntimeException("이메일은 gachon.ac.kr 도메인이어야 합니다.");
            }

            String code = generateVerificationCode();
            redisTemplate.opsForValue().set(email, code, 3, TimeUnit.MINUTES);
            sendVerificationEmail(email, code);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("인증 코드 발송 중 오류 발생: " + e.getMessage());
        }
    }

    private String generateVerificationCode() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }

    private void sendVerificationEmail(String email, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("이메일 인증 코드");
        message.setText("안녕하세요! 아래의 인증 코드를 입력하여 이메일 인증을 완료해주세요:\n\n" + code);
        mailSender.send(message);
    }

    public void verifyCode(String email, String code) {
        String storedCode = redisTemplate.opsForValue().get(email);
        if (storedCode != null && storedCode.equals(code)) {
            redisTemplate.delete(email);
        } else {
            throw new RuntimeException("유효하지 않거나 만료된 인증 코드입니다.");
        }
    }

    public void completeRegistration(RegistrationRequest registrationRequest) {
        if (userRepository.findByEmail(registrationRequest.email()).isPresent()) {
            throw new RuntimeException("이메일이 이미 사용 중입니다.");
        }

        User user = User.of(
                registrationRequest.email(),
                passwordEncoder.encode(registrationRequest.password()),
                registrationRequest.name(),
                registrationRequest.role()
        );
        userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public boolean checkPassword(User user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

    public void resetPassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("이메일이 등록되어 있지 않습니다."));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public String generateRandomPassword() {
        StringBuilder password = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            password.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return password.toString();
    }

    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        userRepository.delete(user);
    }

    public void sendResetPasswordEmail(String email, String newPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("비밀번호 재발급");
        message.setText("안녕하세요! 아래의 임시 비밀번호로 로그인하세요:\n\n" + newPassword);
        mailSender.send(message);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[a-zA-Z0-9._%+-]+@gachon\\.ac\\.kr$");
    }

    public void blacklistToken(String token) {
        jwtUtil.blacklistUserToken(token);
    }

}
