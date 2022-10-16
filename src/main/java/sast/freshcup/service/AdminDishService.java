package sast.freshcup.service;

import org.springframework.beans.factory.parsing.Problem;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Acow337
 * @Date: 2022/01/20/20:05
 * @Description:
 */

public interface AdminDishService {

    Map<String, Object> getDishList(Integer pageNum, Integer pageSize);


    Map<String, Object> createDish(String name, Integer restaurantId, String description, Double price);
}

