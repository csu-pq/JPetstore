package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.csu.mypetstore.service.AccountService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String signOn(String username, String password, Model model) {
        if(username!=null)
        {
            Account account=accountService.getAccount(username,password);
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
        else
        {
            model.addAttribute("msg", "please enter username and password");
            return "account/signOnForm";
        }
    }

    @GetMapping("/signOff")
    public String signOff(Model model)
    {
        Account account=null;
        model.addAttribute("account",account);
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
}
