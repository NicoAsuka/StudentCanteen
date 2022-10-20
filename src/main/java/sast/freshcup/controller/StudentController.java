package sast.freshcup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sast.freshcup.annotation.AuthHandle;
import sast.freshcup.annotation.OperateLog;
import sast.freshcup.common.enums.AuthEnum;
import sast.freshcup.entity.Dish;
import sast.freshcup.service.StudentService;

import java.util.List;
import java.util.Map;

/**
 * @author: 李林涛
 * @create: 2022-10-15 17:22
 **/
//@AuthHandle(AuthEnum.STUDENT)
@RequestMapping("/student")
@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @OperateLog(operDesc = "学生端获取账户余额")
    @GetMapping("/getBalance")
    public Map<String, Object> getBalance() {
        return studentService.getBalance();
    }

    @OperateLog(operDesc = "学生端充值账户余额")
    @PostMapping("/addBalance")
    public Map<String, Object> addBalance(@RequestParam Integer money) {
        return studentService.addBalance(money);
    }

    @OperateLog(operDesc = "学生端获取商铺列表")
    @GetMapping("/getRestaurantList")
    public Map<String, Object> getRestaurantList(
            @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize) {
        return studentService.getRestaurantList(pageNum, pageSize);
    }

    @OperateLog(operDesc = "学生端获取菜品列表")
    @GetMapping("/getDishList")
    public String getDishList(
            @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize,
            Model model) {
        List<Dish> dishesList = studentService.getDishList(pageNum, pageSize);
        model.addAttribute("dishesList",dishesList);
        return "dishList";
    }

    @OperateLog(operDesc = "学生端创建订单")
    @PostMapping("/createOrder")
    public Map<String, Object> createOrder(@RequestParam String dishesId) {
        return studentService.createOrder(dishesId);
    }

    @OperateLog(operDesc = "学生端取消订单")
    @PostMapping("/deleteOrder")
    public String deleteOrder(Integer id) {
        return studentService.deleteOrder(id);
    }

    @OperateLog(operDesc = "学生端取消订单")
    @PostMapping("/updateOrderList")
    public Map<String, Object> updateOrderList(@RequestParam Integer id,
                                  @RequestParam String dishesId) {
        return studentService.updateOrderList(id,dishesId);
    }
}
