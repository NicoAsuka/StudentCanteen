package sast.freshcup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sast.freshcup.annotation.AuthHandle;
import sast.freshcup.annotation.OperateLog;
import sast.freshcup.common.enums.AuthEnum;
import sast.freshcup.entity.Dish;
import sast.freshcup.service.AdminDishService;

import java.util.List;
import java.util.Map;

/**
 * @author: 孙帅
 * @create: 2022-10-15 17:22
 **/
@Controller
@RequestMapping("/admin/dish")
//@AuthHandle(AuthEnum.ADMIN)
public class AdminDishController {
    @Autowired
    private AdminDishService adminDishService;

    @OperateLog(operDesc = "管理端创建菜品")
    @PostMapping("/createDish")
    public String createDish(String name,
                             Integer restaurantId,
                             String description,
                             Double price,
                             Integer count) {
        adminDishService.createDish(name, restaurantId, description, price,count);
        return "redirect:/admin/dish/getDishList";
    }

    @OperateLog(operDesc = "管理端修改菜品")
    @PostMapping("/updateDish")
    public String updateDish(Integer id, String name, Integer restaurantId, String description, Double price,Integer count) {
        adminDishService.updateDish(id, name, restaurantId, description, price,count);
        return "redirect:/admin/dish/getDishList";
    }

    @OperateLog(operDesc = "管理端根据菜品id获取菜品返回页面")
    @GetMapping("/getUpdateDishList")
    public String getUpdateDishList(Integer id,Model model) {
        Dish dishById = adminDishService.getDishListById(id);
        model.addAttribute("dishById",dishById);
        return "updateDish2";
    }


    @OperateLog(operDesc = "管理端删除菜品")
    @PostMapping("/deleteDish")
    public String deleteDish(Integer[] dishesListIds) {
        for (Integer id : dishesListIds) {
            adminDishService.deleteDish(id);
        }
        return "redirect:/admin/dish/getDishList";
    }

    @OperateLog(operDesc = "管理端获取某一菜品")
    @GetMapping("/getOneDish")
    public Map<String, Object> getOneDish(Integer id) {
        return adminDishService.getOneDish(id);
    }

    @OperateLog(operDesc = "管理端获取菜品列表")
    @GetMapping("/getDishList")
    public String getDishList(
            @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize,
            Model model) {
        List<Dish> dishesList = adminDishService.getDishList(pageNum, pageSize);
        model.addAttribute("dishesList", dishesList);
        return "dishList2";
    }

    @OperateLog(operDesc = "管理端修改菜品数量")
    @PostMapping("/updateCountOfDish")
    public Map<String, Object> updateCountOfDish(Integer id, Integer count) {
        return adminDishService.updateCountOfDish(id, count);
    }

}
