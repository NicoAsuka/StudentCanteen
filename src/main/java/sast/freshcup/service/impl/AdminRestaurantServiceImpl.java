package sast.freshcup.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sast.freshcup.common.enums.ErrorEnum;
import sast.freshcup.entity.Restaurant;
import sast.freshcup.exception.LocalRunTimeException;
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

    @Override
    public Map<String, Object> createRestaurant(String name, Integer restaurantId, String description, String location) {
        //传参判空
        if (name == null||restaurantId == null||description == null||location == null){
            throw new LocalRunTimeException(ErrorEnum.PARAMS_LOSS);
        }

        //新建一个对象类
        Restaurant restaurant = new Restaurant();
        //将参数传入
        restaurant.setName(name);
        restaurant.setIsDeleted(0);
        restaurant.setLocation(location);
        restaurant.setDescription(description);

        //将新的商铺插入数据库
        restaurantMapper.insert(restaurant);

        Map<String, Object> res = new HashMap<>();
        res.put("restaurantid",restaurant.getId());
        res.put("restaurantname",restaurant.getName());
        res.put("restaurant-description",restaurant.getDescription());
        res.put("restaurant-location",restaurant.getLocation());
        return res;
    }

    @Override
    public String deleteRestaurant(Integer restaurantId) {
        Restaurant restaurant = restaurantMapper.selectById(restaurantId);
        restaurant.setIsDeleted(1);
        restaurantMapper.updateById(restaurant);
        return "成功删除商品";
    }
    @Override
    public Map<String, Object> updateRestaurant(String name, Integer restaurantId, String description, String location){
        //通过id调出需修改的restaurant对象
        Restaurant restaurant = restaurantMapper.selectById(restaurantId);

        if (restaurant.getIsDeleted() == 1){
            Map<String, Object> res = new HashMap<>();
            res.put("dishesId",restaurant.getName());
            res.put("RestaurantId",restaurant.getId());
            res.put("description",restaurant.getDescription());
            res.put("Price",restaurant.getLocation());
            return res;
        }

        //传参判空，不能全部参数都空
        if (name == null && restaurantId == null && description == null && location == null){
            throw new LocalRunTimeException(ErrorEnum.PARAMS_LOSS);
        }

        if(name != null){
            restaurant.setName(name);
        }

        if (location != null){
            restaurant.setLocation(location);
        }

        if (restaurantId != null){
            restaurant.setId(restaurantId);
        }

        if (description != null){
            restaurant.setDescription(description);
        }
        restaurant.setIsDeleted(0);

        restaurantMapper.updateById(restaurant);

        Map<String, Object> res = new HashMap<>();
        res.put("restaurant",restaurant.getName());
        res.put("RestaurantId",restaurant.getId());
        res.put("description",restaurant.getDescription());
        res.put("Location",restaurant.getLocation());
        return res;
    }






}
