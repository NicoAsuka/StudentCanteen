package sast.freshcup.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.ui.Model;
import sast.freshcup.common.enums.ErrorEnum;
import sast.freshcup.entity.Account;
import sast.freshcup.entity.Dish;
import sast.freshcup.entity.DishOrder;
import sast.freshcup.entity.Restaurant;
import sast.freshcup.exception.LocalRunTimeException;
import sast.freshcup.mapper.AccountMapper;
import sast.freshcup.mapper.DishMapper;
import sast.freshcup.mapper.DishOrderMapper;
import sast.freshcup.mapper.RestaurantMapper;
import sast.freshcup.pojo.DishOrderView;
import sast.freshcup.service.RedisService;
import sast.freshcup.service.StudentService;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static sast.freshcup.interceptor.AccountInterceptor.accountHolder;

/**
 * @author: 風楪fy
 * @create: 2022-01-22 17:44
 **/
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    RestaurantMapper restaurantMapper;

    @Autowired
    DishMapper dishMapper;

    @Autowired
    DishOrderMapper orderMapper;

    @Autowired
    RedisService redisService;



    @Override
    public Account getBalance(Model model) {
        //一般来说先做判空校验，若要求不为空的传参为空，则返回error
        //这里没有传参所以先不演示，后面的接口再进行演示

        //通过uid获取对应的账户对象
        QueryWrapper<Account> eq = new QueryWrapper<Account>()
                .eq("uid", redisService.get("uid"))
                .eq("is_deleted", 0);
        Account account = accountMapper.selectOne(eq);
        if (account == null){
            throw new LocalRunTimeException(ErrorEnum.NO_USER);
        }
        return account;
    }

    @Override
    public void addBalance(String money,String password) {
        if (money == null || Integer.parseInt(money) <= 0){
            throw new LocalRunTimeException(ErrorEnum.MONEY_ERROR);
        }

        //通过token获取基本的用户信息如uid，username
        Integer uid = (Integer) redisService.get("uid");
        String passwordFromDB = (String) redisService.get("password");

        if (!passwordFromDB.equals(password)){
            throw new LocalRunTimeException(ErrorEnum.MONEY_ERROR);
        }

        //通过uid从数据库中获取对应的账户对象
        Account account = accountMapper.selectById(uid);

        //通过对象得到该对象的账户余额
        Double oldBalance = account.getBalance();
        Double newBalance = oldBalance + Integer.parseInt(money);

        //充值余额
        account.setBalance(newBalance);

        //更新数据库
        accountMapper.updateById(account);

//        //封装数据，返回
//        Map<String, Object> returnBalance = new HashMap<>();
//        returnBalance.put("uid",uid);
//        returnBalance.put("username",username);
//        returnBalance.put("oldBalance",oldBalance);
//        returnBalance.put("newBalance",account.getBalance());
    }

    @Override
    public Map<String, Object> getRestaurantList(Integer pageNum, Integer pageSize) {
        //这里不需要校验参数为空是因为我们在controller中设置了默认值

        //以下是分页查询的写法
        Page<Restaurant> page = new Page<>(pageNum, pageSize);
        restaurantMapper.selectPage(page,new QueryWrapper<Restaurant>()
                .eq("is_deleted",0)
                .orderByAsc("id"));

        //封装返回
        List<Restaurant> records = page.getRecords();
        long total = page.getTotal();
        Map<String, Object> res = new HashMap<>();
        res.put("total", total);
        res.put("pageNum", pageNum);
        res.put("pageSize", pageSize);
        res.put("records", records);
        return res;
    }

    //注意看，这个接口的写法和上面的接口几乎一模一样，我只是把restaurant换成了dish
    @Override
    public List<Dish> getDishList(Integer pageNum, Integer pageSize) {
        //这里不需要校验参数为空是因为我们在controller中设置了默认值

        //以下是分页查询的写法
        Page<Dish> page = new Page<>(1,20);
        //找到存在的restaurant信息，并通过id升序排列展示
        dishMapper.selectPage(page,new QueryWrapper<Dish>()
                .eq("is_deleted",0)
                .orderByAsc("id"));

        //封装返回
        List<Dish> records = page.getRecords();
        return records;
    }

    @Override
    public void createOrder(Integer[] dishesId) {
        System.out.println(dishesId);
        //传参判空
        if (dishesId == null){
            throw new LocalRunTimeException(ErrorEnum.NO_DISHESID);
        }

        //初始化一个totalPrice用于记录订单总金额
        Double totalPrice = 0.0;

        int i = 0;
        String dishesIdString = new String();
        for (Integer dishId : dishesId){
            Dish dish = dishMapper.selectById(dishId);
            Double price = dish.getPrice();
            totalPrice += price;
            if (i == 0){
                dishesIdString += Integer.toString(dishId);
            }else{
                dishesIdString += ","+dishId;
            }
            i++;
        }

        Account student = accountMapper.selectById((Integer) redisService.get("uid"));
        Double newBalance = student.getBalance() - totalPrice;
        if (newBalance <= 0){
            throw new LocalRunTimeException(ErrorEnum.NO_BALANCE);
        }
        student.setBalance(newBalance);

        //新建一个对象类
        DishOrder dishOrder = new DishOrder();
        //将参数传入
        dishOrder.setDishesId(dishesIdString);
        dishOrder.setUid((Integer) redisService.get("uid"));
        dishOrder.setIsDeleted(0);
        dishOrder.setTotalPrice(totalPrice);



        //将新的订单插入数据库
        orderMapper.insert(dishOrder);
        accountMapper.updateById(student);
    }

    @Override
    public void deleteOrder(Integer id) {
        DishOrder dishOrder = orderMapper.selectById(id);
        dishOrder.setIsDeleted(1);
        orderMapper.updateById(dishOrder);
    }

    @Override
    public Map<String, Object> updateOrderList(Integer id, String dishesId) {
        return null;
    }

    @Override
    public void updatePassword(String password) {
        Integer uid = (Integer) redisService.get("uid");
        Account account = accountMapper.selectById(uid);
        account.setPassword(password);
        accountMapper.updateById(account);
        redisService.set("password",password);
    }

    @Override
    public List<DishOrder> getDishOrderList(Integer pageNum, Integer pageSize) {
        //这里不需要校验参数为空是因为我们在controller中设置了默认值

        //以下是分页查询的写法
        Page<DishOrder> page = new Page<>(1,20);
        //找到存在的restaurant信息，并通过id升序排列展示
        orderMapper.selectPage(page,new QueryWrapper<DishOrder>()
                .eq("is_deleted",0)
                .eq("uid",(Integer)redisService.get("uid"))
                .orderByAsc("id"));

        //Todo 考虑返回菜品的名字和价格；
//        List<DishOrder> records1 = page.getRecords();
//        DishOrderView dishOrderView = new DishOrderView();
//        //遍历每一条记录
//        for (DishOrder record : records1){
//            String[] dishId = record.getDishesId().split(",");
//
//            //遍历每条订单中的所有dishesId
//            for (String dish:dishId){
//                Dish dish1 = dishMapper.selectById(Integer.parseInt(dish));
//                String name = dish1.getName();
//
//            }
//            dishOrderView.setDishesId(record.getDishesId());
//            dishOrderView.setTotalPrice(record.getTotalPrice());
//            dishOrderView.setIsDeleted(record.getIsDeleted());
//            dishOrderView.setId(record.getId());
//            dishOrderView.setUid(record.getUid());
//
//        }

        //封装返回
        List<DishOrder> records = page.getRecords();
        return records;
    }
}
