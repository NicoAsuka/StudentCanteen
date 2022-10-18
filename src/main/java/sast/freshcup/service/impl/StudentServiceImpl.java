package sast.freshcup.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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



    @Override
    public Map<String, Object> getBalance() {
        //一般来说先做判空校验，若要求不为空的传参为空，则返回error
        //这里没有传参所以先不演示，后面的接口再进行演示

        //通过token获取基本的用户信息如uid，username
        Integer uid = accountHolder.get().getUid();
        String username = accountHolder.get().getUsername();

        //通过uid获取对应的账户对象
        QueryWrapper<Account> eq = new QueryWrapper<Account>()
                .eq("uid", uid)
                .eq("is_deleted", 0);
        Account account = accountMapper.selectOne(eq);
        if (account == null){
            throw new LocalRunTimeException(ErrorEnum.NO_USER);
        }
        //通过对象得到该对象的账户余额
        Double balance = account.getBalance();

        //封装数据，返回
        Map<String, Object> returnBalance = new HashMap<>();
        returnBalance.put("uid",uid);
        returnBalance.put("username",username);
        returnBalance.put("balance",balance);
        return returnBalance;
    }

    @Override
    public Map<String, Object> addBalance(Integer money) {
        if (money == null || money <= 0){
            throw new LocalRunTimeException(ErrorEnum.MONEY_ERROR);
        }

        //通过token获取基本的用户信息如uid，username
        Integer uid = accountHolder.get().getUid();
        String username = accountHolder.get().getUsername();

        //通过uid从数据库中获取对应的账户对象
        Account account = accountMapper.selectById(uid);

        //通过对象得到该对象的账户余额
        Double oldBalance = account.getBalance();
        Double newBalance = oldBalance + money;

        //充值余额
        account.setBalance(newBalance);

        //更新数据库
        accountMapper.updateById(account);

        //封装数据，返回
        Map<String, Object> returnBalance = new HashMap<>();
        returnBalance.put("uid",uid);
        returnBalance.put("username",username);
        returnBalance.put("oldBalance",oldBalance);
        returnBalance.put("newBalance",account.getBalance());
        return returnBalance;
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
    public Map<String, Object> getDishList(Integer pageNum, Integer pageSize) {
        //这里不需要校验参数为空是因为我们在controller中设置了默认值

        //以下是分页查询的写法
        Page<Dish> page = new Page<>(pageNum, pageSize);
        //找到存在的restaurant信息，并通过id升序排列展示
        dishMapper.selectPage(page,new QueryWrapper<Dish>()
                .eq("is_deleted",0)
                .orderByAsc("id"));

        //封装返回
        List<Dish> records = page.getRecords();
        long total = page.getTotal();
        Map<String, Object> res = new HashMap<>();
        res.put("total", total);
        res.put("pageNum", pageNum);
        res.put("pageSize", pageSize);
        res.put("records", records);
        return res;
    }

    @Override
    public Map<String, Object> createOrder(String dishesId) {
        //传参判空
        if (dishesId == null){
            throw new LocalRunTimeException(ErrorEnum.NO_DISHESID);
        }

        //初始化一个totalPrice用于记录订单总金额
        Double totalPrice = 0.0;


        //解析dishesId字符串，遍历查询dish，计算totalPrice
        String[] dishesIdArray = dishesId.split(",");
        for (String dishId : dishesIdArray) {
            boolean matches = Pattern.compile("[1-9]").matcher(dishId).matches();
            //校验dishesId输入是否正确
            if (!matches) {
                throw new LocalRunTimeException(ErrorEnum.DISHESID_ERROR);
            }

            Integer integer = Integer.valueOf(dishId);
            Dish dish = dishMapper.selectById(integer);
            totalPrice += dish.getPrice();
        }

        //新建一个对象类
        DishOrder dishOrder = new DishOrder();
        //将参数传入
        dishOrder.setDishesId(dishesId);
        dishOrder.setUid(accountHolder.get().getUid());
        dishOrder.setIsDeleted(0);
        dishOrder.setTotalPrice(totalPrice);

        //将新的订单插入数据库
        orderMapper.insert(dishOrder);

        Map<String, Object> res = new HashMap<>();
        res.put("uid",accountHolder.get().getUid());
        res.put("username",accountHolder.get().getUsername());
        //TODO 能根据id一起返回菜品名称和价格吗？
        res.put("dishesId",dishesId);
        res.put("totalPrice",totalPrice);
        return res;
    }

    @Override
    public String deleteOrder(Integer id) {
        DishOrder dishOrder = orderMapper.selectById(id);
        dishOrder.setIsDeleted(1);
        orderMapper.updateById(dishOrder);
        return "成功取消订单";
    }

    @Override
    public Map<String, Object> updateOrderList(Integer id, String dishesId) {
        return null;
    }


}
