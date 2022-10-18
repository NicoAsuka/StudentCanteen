package sast.freshcup.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sast.freshcup.entity.Restaurant;
import sast.freshcup.mapper.DishMapper;
import sast.freshcup.mapper.RestaurantMapper;
import sast.freshcup.service.AdminRestaurantService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 王毕鑫
 * @date 2022/10/15 19:50
 */
@Service
public class AdminRestaurantServiceImpl implements AdminRestaurantService {
    @Autowired
    RestaurantMapper restaurantMapper;

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
}
