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
    public String signOn(String username, String password, String code, Model model, HttpSession session) {
        String rightCode = (String) session.getAttribute("code");
        Account account=accountService.getAccount(username,password);
        if(account==null|| !code.equals(rightCode))
        {
            model.addAttribute("msg", "用户名、密码或验证码不正确");
            return "account/signOnForm";
        }
        else
        {
            if(account!=null)
            {
                model.addAttribute("account",account);
                return"catalog/main";
            }
            else
            {
                model.addAttribute("msg", "password error");
                return "account/signOnForm";
            }
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
        Account newAccount=new Account();
        model.addAttribute("newAccount",newAccount);
        return "account/newAccountForm";
    }

    @PostMapping("/newAccount")
    public String newAccount(Account newAccount,String repeatedPassword,Model model)
    {
        if(newAccount.getPassword()!=repeatedPassword||repeatedPassword==null)
        {
            model.addAttribute("msg","输入相同且正确的密码");
            return "account/newAccountForm";
        }
        else
        {
            System.out.println(newAccount.getUsername());
            Account account=newAccount;
            accountService.insertAccount(account);
            model.addAttribute("account",account);
            model.addAttribute("newAccount",null);
            return "catalog/main";
        }
    }

}
