package sast.freshcup.service;

import java.util.Map;

/**
 * @author: 風楪fy
 * @create: 2022-01-22 17:43
 **/
public interface StudentService {

    Map<String, Object> getBalance();

    Map<String, Object> addBalance(Integer money);

    Map<String, Object> getRestaurantList(Integer pageNum, Integer pageSize);

    Map<String, Object> getDishList(Integer pageNum, Integer pageSize);
}