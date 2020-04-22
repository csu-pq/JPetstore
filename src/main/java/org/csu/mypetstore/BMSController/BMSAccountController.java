package org.csu.jpetstore.BMSController;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Supplier;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.utils.ResultFactory;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.utils.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bms/account")
public class BMSAccountController {
    @Autowired
    public AccountService accountService;
    @PostMapping("/login")
    public ResultFactory login(@RequestParam("username") String username,@RequestParam("password") String password){
        Account loginAccount=accountService.getAccount(username, password);
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
    @PostMapping("/editAccount")    //重复密码用js验证
    public ResultFactory editAccount(@RequestBody Supplier supplier){
        accountService.updateSupplier(supplier);
        return ResultFactory.successResult(supplier,"成功");
    }
}