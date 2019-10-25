package com.example.excelto.Controller;

import com.example.excelto.Entity.userEntity;
import com.example.excelto.Repostory.UserRepository;
import com.example.excelto.commons.AjaxResult;
import com.example.excelto.commons.AjaxUtils;
import com.example.excelto.commons.Func_T;
import com.google.gson.Gson;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Controller("/")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @RequestMapping("/login")
    @ResponseBody
    public AjaxResult reqHome(HttpServletRequest request, String username, String pwd) {
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                List<userEntity> allByUsernameAndPassword = userRepository.findAllByUsernameAndPassword(username, pwd);
                if (allByUsernameAndPassword.size() == 0) {
                    throw new Exception("密码错误");
                }
                return "success";
            }
        });

    }


}
