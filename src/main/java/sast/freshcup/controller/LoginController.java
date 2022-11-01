package sast.freshcup.controller;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sast.freshcup.common.constants.RedisKeyConst;
import sast.freshcup.entity.Account;
import sast.freshcup.exception.LocalRunTimeException;
import sast.freshcup.mapper.AccountMapper;
import sast.freshcup.service.RedisService;
import sast.freshcup.util.JwtUtil;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author: 李林涛
 * @create: 2022-10-16 20:33
 **/
@Slf4j
@Controller
public class LoginController {
    public static final String LOGIN_VALIDATE_CODE = "VAL_CODE:";

    @Autowired
    private DefaultKaptcha kaptchaProducer;
    @Autowired
    private RedisService redisService;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 返回验证码图片，并将验证码存入Redis
     *
     * @param response
     */
    @GetMapping("/getValidateCode")
    public void getImgValidateCode(HttpServletResponse response) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.addHeader("CAPTCHA", uuid);
        response.setContentType("image/jpeg");

        String capText = kaptchaProducer.createText();
        BufferedImage bi = kaptchaProducer.createImage(capText);

        try {
            ServletOutputStream out = response.getOutputStream();
            ImageIO.write(bi, "jpg", out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        redisService.set(LOGIN_VALIDATE_CODE + uuid, capText, 60 * 5);
    }

    @PostMapping("/loginController")
    public String login(String username,
                        String password,
                        String validateCode,
                        Model model) {
        //验证验证码
//        String currentCode = (String) redisService.get(LOGIN_VALIDATE_CODE + uuid);
//        if (currentCode == null) {
//            throw new LocalRunTimeException("验证码失效");
//        } else if (!currentCode.equals(validateCode)) {
//            throw new LocalRunTimeException("验证码错误");
//        }
//        redisService.del(LOGIN_VALIDATE_CODE + uuid);

        //登录处理
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        Account accountFromDB = accountMapper.selectOne(queryWrapper);
        if (accountFromDB == null) {
            redisService.set("message","用户不存在");
            model.addAttribute(redisService.get("message"));
            return "login";
        } else if (!password.equals(accountFromDB.getPassword())) {
            redisService.set("message","密码错误");
            return "login";
        }

        redisService.set("username",accountFromDB.getUsername());
        redisService.set("uid",accountFromDB.getUid());
        redisService.set("role",accountFromDB.getRole());
        redisService.set("password",accountFromDB.getPassword());

//        model.addAttribute("uid",accountFromDB.getUid());
//        model.addAttribute("role",accountFromDB.getRole());
//        String token = jwtUtil.generateToken(accountFromDB);
//        Map<String, String> map = new HashMap<>();
//        map.put("role", accountFromDB.getRole().toString());
//        map.put("token", token);

        log.info("===============================================");
        log.info("用户登录：{}，role：{}", accountFromDB.getUsername(), accountFromDB.getRole());
//        log.info("登录Agent:{}", agent);
        log.info("===============================================");


        if ((Integer)redisService.get("role")==0){
            return "/index";
        } else if ((Integer)redisService.get("role")==1) {
            return "/index2";
        }
        return "/index";
    }
}
