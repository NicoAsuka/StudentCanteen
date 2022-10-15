package sast.freshcup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sast.freshcup.annotation.AuthHandle;
import sast.freshcup.annotation.OperateLog;
import sast.freshcup.common.enums.AuthEnum;
import sast.freshcup.service.AdminDishService;

import java.util.Map;

/**
 * @author: 孙帅
 * @create: 2022-10-15 17:22
 **/
@RestController
@RequestMapping("/admin")
@AuthHandle(AuthEnum.ADMIN)
public class AdminDishController {
    @Autowired
    private AdminDishService adminDishService;

    @OperateLog(operDesc = "管理端创建菜品")
    @PostMapping("/createDish")
    public Map<String, Object> createDish(@RequestParam String dishesId) {
        return adminDishService.createDish(dishesId);
    }

    @OperateLog(operDesc = "管理端获取菜品列表")
    @GetMapping("/getDishList")
    public Map<String, Object> getDishList(
            @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize) {
        return adminDishService.getDishList(pageNum, pageSize);
    }

}
