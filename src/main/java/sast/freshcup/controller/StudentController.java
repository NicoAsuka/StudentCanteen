package sast.freshcup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sast.freshcup.annotation.OperateLog;
import sast.freshcup.entity.Account;
import sast.freshcup.entity.Dish;
import sast.freshcup.entity.DishOrder;
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
    public String getBalance(Model model) {
        Account account = studentService.getBalance(model);
        model.addAttribute("account",account);
        return "homePage";
    }

    @OperateLog(operDesc = "学生端获取账户信息")
    @GetMapping("/getBalanceByAddBalance")
    public String getBalanceByAddBalance(Model model) {
        Account account = studentService.getBalance(model);
        model.addAttribute("account",account);
        return "addBalance";
    }

    @OperateLog(operDesc = "学生端获取账户信息")
    @GetMapping("/getUserInfo")
    public String getUserInfo(Model model) {
        Account account = studentService.getBalance(model);
        model.addAttribute("account",account);
        return "updatePassword";
    }

    @OperateLog(operDesc = "学生端更新密码")
    @PostMapping("/updatePasswordController")
    public String updatePassword(String password,Model model) {
        if(password == null){
            return "updatePassword";
        }
        studentService.updatePassword(password);
        Account account = studentService.getBalance(model);
        model.addAttribute("account",account);
        return "updatePassword";
    }

    @OperateLog(operDesc = "学生端充值账户余额")
    @PostMapping("/addBalance")
    public String addBalance(String balances,String password1) {
        System.out.println(balances);
        System.out.println(password1);
        studentService.addBalance(balances,password1);
        return "redirect:/student/getBalance";
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

    @OperateLog(operDesc = "学生端获取订单列表")
    @GetMapping("/getDishOrderList")
    public String getDishOrderList(
            @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize,
            Model model) {
        List<DishOrder> dishOrderList = studentService.getDishOrderList(pageNum, pageSize);
        model.addAttribute("dishOrderList",dishOrderList);
        return "dishOrderList";
    }

    @OperateLog(operDesc = "学生端获取商铺列表")
    @GetMapping("/getRestaurantList")
    public Map<String, Object> getRestaurantList(
            @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize) {
        return studentService.getRestaurantList(pageNum, pageSize);
    }

    @OperateLog(operDesc = "学生端创建订单")
    @PostMapping("/createOrder")
    public String createOrder(Integer[] dishesId) {
        studentService.createOrder(dishesId);
        return "redirect:/student/getDishOrderList";
    }

    @OperateLog(operDesc = "学生端取消订单")
    @PostMapping("/deleteOrder")
    public String deleteOrder(Integer[] dishOrderIds) {
        for (Integer id:dishOrderIds){
            studentService.deleteOrder(id);
        }
        return "redirect:/student/getDishOrderList";
    }

//    @OperateLog(operDesc = "学生端更新订单")
//    @PostMapping("/updateOrderList")
//    public Map<String, Object> updateOrderList(@RequestParam Integer id,
//                                  @RequestParam String dishesId) {
//        return studentService.updateOrderList(id,dishesId);
//    }
}
