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
    @GetMapping("/usernameIsAvailable")
    @ResponseBody
    public String UsernameIsAvailable(String username) {
        Account account = accountService.getAccountByUsername(username);
        String json = null;
        if(username.equals("") || account != null) {
            json = "{\"msg\":\"不可用\"}";
        }else {
            json = "{\"msg\":\"可用\"}";
        }
        return json;
    }
    @GetMapping("/searchProductUpdate")
    @ResponseBody
    public void completeSearch(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String keyword = request.getParameter("keyword");
        //向server层调用相应的业务
        List<Product> productList = catalogService.searchProductList(keyword);

        response.setContentType("text/xml");
        PrintWriter out = response.getWriter();

        //返回结果
        String res = "";
        for(int i=0; i<productList.size(); i++){
            if(i>0){
                res += "," + productList.get(i).getName();
            }else{
                res += productList.get(i).getName();
            }
        }
        out.write(res);

        out.flush();
        out.close();
    }
    @GetMapping("/mouseShow")
    @ResponseBody
    public void mouseShow(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String categoryId = request.getParameter("categoryId");
        System.out.println(categoryId+"             ddddddddd");
        List<Product> productList = catalogService.getProductListByCategory(categoryId);
        String resp = "Product ID      Name\n";
        int i = 0;
        while(i < productList.size()){
            Product product = productList.get(i);
            resp += product.getProductId() + "      " + product.getName() + "\n";
            i++;
        }

        response.setContentType("text/xml");
        PrintWriter out = response.getWriter();
        out.write(resp);
    }




}
