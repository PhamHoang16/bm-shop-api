package com.example.bmshopapi.service;

import com.example.bmshopapi.dto.ChangePasswordDto;
import com.example.bmshopapi.dto.LastDepositDto;
import com.example.bmshopapi.entity.Deposit;
import com.example.bmshopapi.entity.User;
import com.example.bmshopapi.exception.CustomException;
import com.example.bmshopapi.repository.DepositRepository;
import com.example.bmshopapi.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final DepositRepository depositRepository;
    private final JavaMailSender mailSender;



    @Value("${spring.mail.username}")
    private String mailFrom;

    public User getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new CustomException("Không tìm thấy người dùng", "E_001"));
    }

    public void deposit(String username, double amount) {
        userRepository.findByUsername(username).ifPresent(user -> {
            user.setBalance(user.getBalance() + amount);
            userRepository.save(user);
        });
        depositRepository.save(Deposit.builder()
                .username(username)
                .amount(amount)
                .detail("Nạp tiền")
                .createdAt(LocalDateTime.now())
                .build());
    }

    public List<Deposit> getDepositHistory(String userId) {
        List<Deposit> result = depositRepository.findByUserId(userId);
        if (result.isEmpty()) {
            throw new CustomException("Không tìm thấy lịch sử nạp tiền", "E_001");
        }
        return result;
    }

    public List<Deposit> getAllDepositHistory() {
        List<Deposit> result = depositRepository.findAll();
        if (result.isEmpty()) {
            throw new CustomException("Không tìm thấy lịch sử nạp tiền", "E_001");
        }
        return result;
    }

    public List<LastDepositDto> getLast10Deposit() {
        List<Deposit> deposits = depositRepository.findTop10ByOrderByCreatedAtDesc();
        return deposits.stream().map(this::convertDepositToDepositDto).collect(Collectors.toList());
    }

    private LastDepositDto convertDepositToDepositDto(Deposit deposit) {
        String amountStr = formatVnd(deposit.getAmount());
        String detail = String.format("Đã nạp %s - %s", amountStr, deposit.getDetail());
        long time = Duration.between(deposit.getCreatedAt(), LocalDateTime.now()).toMinutes();
        return LastDepositDto.builder()
                .username(deposit.getUsername())
                .detail(detail)
                .time(String.format("%s phút trước", time))
                .build();
    }

    private String formatVnd(double amount) {
        return String.format("%,.0f VND", amount);
    }

    public boolean signUp(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new CustomException("Username đã tồn tại", "E_001");
        } else if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new CustomException("Email đã tồn tại", "E_002");
        }
        userRepository.save(user);
        return true;
    }

    public boolean signIn(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new CustomException("Không tìm thấy người dùng", "E_001"));
        if (!user.getPassword().equals(password)) {
            throw new CustomException("Sai mật khẩu", "E_002");
        }
        return true;
    }

    public boolean changePassword(String username, ChangePasswordDto changePasswordDto) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new CustomException("Không tìm thấy người dùng", "E_001"));
        if (!user.getPassword().equals(changePasswordDto.getCurrentPassword())) {
            throw new CustomException("Sai mật khẩu hiện tại", "E_002");
        }
        user.setPassword(changePasswordDto.getNewPassword());
        userRepository.save(user);
        return true;
    }

    public String resetPassword(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException("Không tìm thấy người dùng", "E_001"));
        String newPassword = UUID.randomUUID().toString().replace("-", "").substring(0, 6);

        sendMail(email, newPassword);
        user.setPassword(newPassword);
        userRepository.save(user);
        return "Mật khẩu mới đã được gửi đến mail của bạn.";
    }

    private void sendMail(String to, String newPassword) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject("BM Shop - Đặt lại mật khẩu");
            helper.setText("Mật khẩu mới của bạn: " + newPassword);
            helper.setFrom("hoangpham1618@gmail.com");
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new CustomException("Có lỗi khi gửi mail", "E_001");
        }
    }
}
