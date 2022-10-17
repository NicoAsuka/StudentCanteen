package sast.freshcup.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.stereotype.Service;
import sast.freshcup.common.enums.ErrorEnum;
import sast.freshcup.entity.Dish;
import sast.freshcup.entity.DishOrder;
import sast.freshcup.exception.LocalRunTimeException;
import sast.freshcup.mapper.DishMapper;
import sast.freshcup.service.AdminDishService;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static sast.freshcup.interceptor.AccountInterceptor.accountHolder;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 孙帅
 * @Date: 2022/10/17/17:00
 */

@Service
public class AdminDishServiceImpl implements AdminDishService {

    @Autowired
    DishMapper dishMapper;

    @Override
    public Map<String, Object> getDishList(Integer pageNum, Integer pageSize) {
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
    public Map<String, Object> createDish(String name, Integer restaurantId, String description, Double price) {

        System.out.println(name);
        System.out.println(restaurantId);
        System.out.println(description);
        System.out.println(price);
        //传参判空
        if (name == null||restaurantId == null||description == null||price == 0.0){
            throw new LocalRunTimeException(ErrorEnum.PARAMS_LOSS);
        }

        //新建一个对象类
        Dish dish = new Dish();
        //将参数传入
        dish.setName(name);
        dish.setIsDeleted(0);
        dish.setPrice(price);
        dish.setRestaurantId(restaurantId);
        dish.setDescription(description);

        //将新的订单插入数据库
        dishMapper.insert(dish);

        Map<String, Object> res = new HashMap<>();
        res.put("uid",accountHolder.get().getUid());
        res.put("username",accountHolder.get().getUsername());
        //TODO 能根据id一起返回菜品名称和价格吗？
        res.put("dishesId",name);
        res.put("totalPrice",price);
        return res;
    }
}
