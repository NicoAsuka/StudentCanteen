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

    Map<String, Object> getOneDish(Integer id);

    Map<String, Object> createDish(String name, Integer restaurantId, String description, Double price);

    Map<String, Object> updateDish(Integer integer, String name, Integer id, String description, Double price);

    String deleteDish(Integer id);

    Map<String, Object> updateCountOfDish(Integer integer,Integer count);

}

