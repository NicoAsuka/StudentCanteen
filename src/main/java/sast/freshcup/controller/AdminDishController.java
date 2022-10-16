package sast.freshcup.controller;

import org.springframework.web.bind.annotation.*;
import sast.freshcup.annotation.AuthHandle;
import sast.freshcup.common.enums.AuthEnum;

/**
 * @author: 李林涛
 * @create: 2022-10-15 17:22
 **/
@RestController
@RequestMapping("/admin")
@AuthHandle(AuthEnum.ADMIN)
public class AdminDishController {
    private AdminDishController adminDishController;

}
