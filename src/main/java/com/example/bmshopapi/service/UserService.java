package com.example.bmshopapi.service;

import com.example.bmshopapi.entity.Deposit;
import com.example.bmshopapi.entity.User;
import com.example.bmshopapi.repository.DepositRepository;
import com.example.bmshopapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final DepositRepository depositRepository;

    public User getUser(String userId) {
        return userRepository.findById(userId).orElse(null);
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
                .build());
    }

    public List<Deposit> getDepositHistory(String userId) {
        List<Deposit> result = depositRepository.findByUserId(userId);
        if (result.isEmpty()) throw new RuntimeException("No deposit history found");
        return result;
    }
}
