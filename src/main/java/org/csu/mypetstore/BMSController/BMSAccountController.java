package org.csu.mypetstore.BMSController;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.SimpleAccount;
import org.csu.mypetstore.domain.Supplier;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.utils.ResultFactory;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.utils.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    @PutMapping("/editSupplier")    //重复密码用js验证
    public ResultFactory editSupplier(@RequestBody Supplier supplier){
        accountService.updateSupplier(supplier);
        return ResultFactory.successResult(supplier,"成功");
    }

    @GetMapping("/viewAccount")
    public ResultFactory viewAccount(){
        List<Account> accountList=accountService.getAllAccount();
        return ResultFactory.successResult(accountList,"查询成功");
    }
    @PutMapping("/editAccount")
    public ResultFactory editAccount(@RequestBody SimpleAccount simpleAccount){ //不知道json转化为对象需不需要所有的属性赋值，所以用了一个简单的account对象
        Account account=Account.parse(simpleAccount);
        accountService.editAccount(account);
        return ResultFactory.successResult(simpleAccount,"修改成功");
    }
}