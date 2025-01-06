package com.example.bmshopapi.service;

import com.example.bmshopapi.dto.LastDepositDto;
import com.example.bmshopapi.entity.Deposit;
import com.example.bmshopapi.entity.User;
import com.example.bmshopapi.exception.CustomException;
import com.example.bmshopapi.repository.DepositRepository;
import com.example.bmshopapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final DepositRepository depositRepository;

    public User getUser(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new CustomException("Không tìm thấy người dùng", "E_001"));
    }

    public void deposit(String userId, double amount) {
        userRepository.findById(userId).ifPresent(user -> {
            user.setBalance(user.getBalance() + amount);
            userRepository.save(user);
        });
        depositRepository.save(Deposit.builder()
                .userId(userId)
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
}
