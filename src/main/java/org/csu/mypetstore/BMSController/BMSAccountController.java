package org.csu.mypetstore.BMSController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.SimpleAccount;
import org.csu.mypetstore.domain.Supplier;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.utils.ResultFactory;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.utils.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/bms/account")
public class BMSAccountController {
    @Autowired
    public AccountService accountService;
    @PostMapping("/login")
    public ResultFactory login(@RequestParam("username") String username,@RequestParam("password") String password){
        Supplier loginAccount=accountService.getSupplier(username, password);
        if(loginAccount!=null){
            return ResultFactory.successResult(loginAccount,"登录成功");
        }
        else{
            return ResultFactory.failedResult(50010,"登录失败");
        }
    }
    @PostMapping("/register")
    public ResultFactory register(@RequestParam("username")String username,@RequestParam("password") String password){
        Supplier registerAccount=accountService.getSupplier(username,password);
        if(registerAccount==null){
            Supplier supplier=new Supplier();
            supplier.setUsername(username);
            supplier.setPassword(password);
            //suppid是主键
            supplier.setSuppid("5");
            accountService.insertSupplier(supplier);
            return ResultFactory.successResult(accountService.getSupplier(username),"注册成功");
        }
        else{
            return ResultFactory.failedResult(50010,"注册失败");
        }
    }
    @GetMapping("/usernameIsExist")
    public ResultFactory check(@RequestParam("username")String username){
        boolean result=accountService.supplierUsernameIsExist(username);
        if(result){
            return ResultFactory.failedResult(50010,"账号已存在");
        }else{
            return ResultFactory.successResult(null,"账号可行");
        }
    }
    @PostMapping("/editSupplier")
    public ResultFactory editSupplier(@RequestParam("username") String username,@RequestParam("password") String password,@RequestParam("email") String email,@RequestParam("phone") String phone){
        Supplier supplier=accountService.getSupplier(username);
        supplier.setPassword(password);
        supplier.setEmail(email);
        supplier.setPhone(phone);
        accountService.updateSupplier(supplier);
        return ResultFactory.successResult(supplier,"成功");
    }

    @GetMapping("/viewAccount")
    public ResultFactory viewAccount(@RequestParam(value = "pagenum")int pagenum,@RequestParam(value = "pagesize")int pageSize){
        PageHelper.startPage(pagenum, pageSize);
        List<Account> accountList=accountService.getAllAccount();
        PageInfo<Account> pageInfo = new PageInfo<>(accountList);
        return ResultFactory.successResult(pageInfo,"查询成功");
    }
    @PostMapping("/editAccount")
    public ResultFactory editAccount(@RequestParam("username") String username,@RequestParam("password") String password,@RequestParam("address1") String address1,@RequestParam("address2") String address2,@RequestParam("phone") String phone,@RequestParam("email") String email){
        Account account=accountService.getAccount(username);
        account.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        account.setAddress1(address1);
        account.setAddress2(address2);
        account.setPhone(phone);
        account.setEmail(email);
        accountService.updateAccount(account);
        return ResultFactory.successResult(account,"修改成功");
    }
    @PostMapping("/addAccount")
    public ResultFactory addAccount(@RequestParam("username")String username,@RequestParam("password") String password,@RequestParam("address1") String address1,@RequestParam("address2") String address2,@RequestParam("email") String email,@RequestParam("phone") String phone){
        Account account=accountService.getAccount(username);
        if(account==null)
        {
            Account newAccount=new Account();
            newAccount.setUsername(username);
            newAccount.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
            newAccount.setAddress1(address1);
            newAccount.setAddress2(address2);
            newAccount.setEmail(email);
            newAccount.setPhone(phone);
            accountService.insertAccount(newAccount);
            return ResultFactory.successResult(newAccount,"添加成功");
        }
        else
            return ResultFactory.failedResult(50010,"添加失败");
    }
    @PostMapping("/deleteAccount")
    public ResultFactory deleteAccount(@RequestParam("username") String username){
        Account account=accountService.getAccount(username);
        if(account!=null)
        accountService.deleteAccount(username);
        return ResultFactory.successResult(account,"删除成功");
    }
}