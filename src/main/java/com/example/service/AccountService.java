package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public AccountService(AccountRepository repository) {
        accountRepository = repository;
    }
    
    public Account createAccount(Account account) {
        if (accountRepository.findByUsername(account.getUsername())!=null)
            return null;
        else
            return accountRepository.save(account);
    }

    public Account loginAccount(Account account) {
        Account userAccount = accountRepository.findByUsername(account.getUsername());
        return userAccount;
    }

    public Account getAccountByID(int id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent())
            return account.get();
        return null;
    }
}
