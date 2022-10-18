package sast.freshcup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sast.freshcup.annotation.AuthHandle;
import sast.freshcup.annotation.OperateLog;
import sast.freshcup.common.enums.AuthEnum;
import sast.freshcup.service.AdminDishService;
import sast.freshcup.service.AdminRestaurantService;
import sast.freshcup.service.StudentService;

import java.util.Map;

/**
 * @author: 王毕鑫
 * @date 2022/10/15 19:45
 * contributor:acd12344
 */
@RestController
@RequestMapping("/admin/restaurant")
@AuthHandle(AuthEnum.ADMIN)
public class AdminRestaurantController {
    @Autowired
    private AdminRestaurantService adminRestaurantService;
    @OperateLog(operDesc = "管理端获取商店列表")
    @GetMapping("/getRestaurantList")
    public Map<String, Object> getRestaurantList(
            @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize) {
        return adminRestaurantService.getRestaurantList(pageNum, pageSize);
    }

    @OperateLog(operDesc = "管理端创建商铺")
    @PostMapping("/createRestaurant")
    public Map<String, Object> createDish(@RequestParam String name,
                                          @RequestParam Integer restaurantId,
                                          @RequestParam String description,
                                          @RequestParam String location) {
        return adminRestaurantService.createRestaurant( name, restaurantId,description,location);
    }




}




