package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Order;
import org.csu.mypetstore.domain.VerificationCode;
import org.csu.mypetstore.service.CatalogService;
import org.csu.mypetstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.csu.mypetstore.service.AccountService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/account")
@SessionAttributes({"account"})
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/viewSignOn")
    public  String viewSignOn()
    {
        return "account/signOnForm";
    }

    @PostMapping("/signOn")
    public String signOn(@SessionAttribute("code")String rightCode, String username, String password, String inputCode, Model model) {
        Account account=accountService.getAccount(username,password);

        if(account==null)
        {
            model.addAttribute("msg","用户名或密码错误");
            return "account/signOnForm";
        }
        else if(!inputCode.equals(rightCode))
        {
            model.addAttribute("msg","验证码错误");
            return "account/signOnForm";
        }
        else
        {
            model.addAttribute("account",account);
            return "catalog/main";
        }
    }

    @GetMapping("/signOff")
    public String signOff(Model model)
    {
        model.addAttribute("account",null);
        return "catalog/main";
    }

    @GetMapping("/editAccountForm")
    public String editAccountForm(@SessionAttribute("account") Account account , Model model)
    {
        model.addAttribute("account",account);
        return "account/editAccountForm";
    }

    @PostMapping("/editAccount")
    public String editAccount(Account account,String repeatedPassword,Model model)
    {
        if (account.getPassword() == null || account.getPassword().length() == 0 || repeatedPassword == null || repeatedPassword.length() == 0) {
            String msg = "密码不能为空";
            model.addAttribute("msg", msg);
            return "account/editAccountForm";
        } else if (!account.getPassword().equals(repeatedPassword)) {
            String msg = "两次密码不一致";
            model.addAttribute("msg", msg);
            return "account/editAccountForm";
        } else {
            accountService.updateAccount(account);
            model.addAttribute("account", account);
            return "catalog/main";
        }
    }

    @GetMapping("/newAccountForm")
    public String newAccountForm(Model model)
    {
        Account account=new Account();
        model.addAttribute("account",account);
        return "account/newAccountForm";
    }

    @PostMapping("/newAccount")
    public String newAccount(@SessionAttribute("code")String code, Account account,String repeatedPassword,String inputCode,Model model)
    {
        //通过输入的用户名查找是否有改用户
        Account newAccount=accountService.getAccount(account.getUsername());
        if(newAccount!=null)
        {
            String msg="账户名已存在";
            model.addAttribute("msg",msg);
            return "account/newAccountForm";
        }
        else if (account.getPassword() == null || account.getPassword().length() == 0 || repeatedPassword == null || repeatedPassword.length() == 0) {
            String msg = "密码不能为空";
            model.addAttribute("msg", msg);
            return "account/newAccountForm";
        } else if (!account.getPassword().equals(repeatedPassword)) {
            String msg = "两次密码不一致";
            model.addAttribute("msg", msg);
            return "account/newAccountForm";
        }
        else if(!code.equals(inputCode))
        {
            String msg="验证码错误";
            model.addAttribute("msg",msg);
            return "account/newAccountForm";
        }
        else
        {
            accountService.insertAccount(account);
            model.addAttribute("account", account);
            return "catalog/main";
        }
    }

}
