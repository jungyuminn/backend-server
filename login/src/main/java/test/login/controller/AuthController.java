package test.login.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import test.login.dto.AuthResponse;
import test.login.dto.LoginDto;
import test.login.dto.UserDto;
import test.login.entity.User;
import test.login.service.UserService;

import java.security.Key;
import java.util.Date;

@Controller
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/signup")
    public String signupForm() {
        return "signup";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    private String generateVerificationToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS512)
                .compact();
    }

    private void sendVerificationEmail(String email, String token) {
        String verificationUrl = "http://localhost:8080/api/auth/verify?token=" + token;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("이메일 인증");
        message.setText("이메일 인증을 위해 아래 링크를 클릭하세요: " + verificationUrl);
        mailSender.send(message);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        try {
            User user = userService.registerUser(userDto.getEmail(), userDto.getPassword(), userDto.getName(), userDto.getRole());
            String token = generateVerificationToken(user);
            sendVerificationEmail(user.getEmail(), token);
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공. 이메일을 확인하세요.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입 실패: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        User user = userService.findByEmail(loginDto.getEmail());

        if (user != null && userService.checkPassword(user, loginDto.getPassword())) { // 해시 비교
            // 사용자가 활성화 상태인지 확인
            if (!user.isEnabled()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("이메일 인증이 필요합니다.");
            }

            String token = generateToken(user);
            return ResponseEntity.ok(new AuthResponse(token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    private String generateToken(User user) {
        byte[] keyBytes = jwtSecret.getBytes();
        Key key = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    @GetMapping("/verify")
    public ModelAndView verifyEmail(@RequestParam String token) {
        ModelAndView modelAndView = new ModelAndView("verify");

        try {
            String email = Jwts.parser()
                    .setSigningKey(jwtSecret.getBytes())
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            userService.verifyEmail(email);
            modelAndView.addObject("message", "이메일 인증 성공!");
        } catch (Exception e) {
            modelAndView.addObject("message", "유효하지 않거나 만료된 토큰입니다.");
        }

        return modelAndView;
    }
}
