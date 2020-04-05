package org.csu.mypetstore.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.domain.VerificationCode;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OtherController {

    @Autowired
    private CatalogService catalogService;
    @Autowired
    private AccountService accountService;



    @GetMapping("/verCode")
    public void code(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpg");
        //禁止图像缓存
        response.setHeader("Pragma","no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        HttpSession session = request.getSession();
        VerificationCode vercode = new VerificationCode(70, 20, 4, 10);
        session.setAttribute("code", vercode.getCode());
        try {
            vercode.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
