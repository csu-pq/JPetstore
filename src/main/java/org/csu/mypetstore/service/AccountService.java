package org.csu.mypetstore.service;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Supplier;
import org.csu.mypetstore.persistence.AccountMapper;
import org.csu.mypetstore.persistence.SupplierMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    SupplierMapper supplierMapper;

    public Account getAccountByUsername(String username) {
        return accountMapper.getAccountByUsername(username);
    }

    public Account getAccount(String username){
        return accountMapper.getAccountByUsername(username);
    }

    public Account getAccount(String username, String password){
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        return accountMapper.getAccountByUsernameAndPassword(account);
    }

    /*
        声明式事务处理
     */
    @Transactional
    public void insertAccount(Account account){
        accountMapper.insertAccount(account);
        accountMapper.insertProfile(account);
        accountMapper.insertSignon(account);
    }

    public void updateAccount(Account account){
        accountMapper.updateAccount(account);
        accountMapper.updateProfile(account);

        if(account.getPassword() != null && account.getPassword().length() > 0){
            accountMapper.updateSignon(account);
        }
    }
    public Supplier getSupplier(String username){
        return supplierMapper.getSupplierByUsername(username);
    }
    public Supplier getSupplier(String username,String password){
        Supplier supplier=new Supplier();
        supplier.setUsername(username);
        supplier.setPassword(password);
        return supplierMapper.getSupplierByUsernameAndPassword(supplier);
    }
    public boolean supplierUsernameIsExist(String username)
    {
        return supplierMapper.getSupplierByUsername(username)!=null;
    }
    public void insertSupplier(Supplier supplier){
        supplierMapper.insertSupplier(supplier);
        supplierMapper.insertSignon(supplier);
    }
    public void updateSupplier(Supplier supplier){
        supplierMapper.updateSupplier(supplier);
        supplierMapper.updateSignon(supplier);
    }

    public List<Account> getAllAccount() {return accountMapper.getAllAccount(); }

    public void editAccount(Account account) { accountMapper.editAccount(account);}

    public void deleteAccount(String username){
        accountMapper.deleteAccount(username);
        accountMapper.deleteSignon(username);
        accountMapper.deleteProfile(username);
    }
}
