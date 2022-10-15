package sast.freshcup.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.stereotype.Service;
import sast.freshcup.common.enums.ErrorEnum;
import sast.freshcup.entity.Dish;
import sast.freshcup.exception.LocalRunTimeException;
import sast.freshcup.mapper.DishMapper;
import sast.freshcup.service.AdminDishService;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static sast.freshcup.interceptor.AccountInterceptor.accountHolder;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Acow337
 * @Date: 2022/01/20/20:12
 * @Description:
 */

@Service
public class AdminDishServiceImpl implements AdminDishService {

    @Autowired
    DishMapper dishMapper;


    @Override
    public Map<String, Object> getDishList(Integer pageNum, Integer pageSize) {
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
    public Map<String, Object> createDish(String dishesId) {
        return null;
    }
}
