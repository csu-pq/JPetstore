package org.csu.mypetstore.persistence.service;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.persistence.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {
    @Autowired
    private AccountMapper accountMapper;

    public AccountService() {
    }

    public Account getAccount(String username) {
        return this.accountMapper.getAccountByUsername(username);
    }

    public Account getAccount(String username, String password) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        return this.accountMapper.getAccountByUsernameAndPassword(account);
    }

    @Transactional
    public void insertAccount(Account account) {
        this.accountMapper.insertAccount(account);
        this.accountMapper.insertProfile(account);
        this.accountMapper.insertSignon(account);
    }

    @Transactional
    public void updateAccount(Account account) {
        this.accountMapper.updateAccount(account);
        this.accountMapper.updateProfile(account);
        if (account.getPassword() != null && account.getPassword().length() > 0) {
            this.accountMapper.updateSignon(account);
        }

    }
}
