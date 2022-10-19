package sast.freshcup.service;

import java.util.Map;

/**
 * @author: 李林涛
 * @date 2022/10/15 19:48
 */
public interface AdminRestaurantService {
    Map<String, Object> getRestaurantList(Integer pageNum, Integer pageSize);

    Map<String, Object> createRestaurant(String name, Integer restaurantId, String description, String location);

    String deleteRestaurant(Integer id);

    Map<String, Object> updateRestaurant(String name, Integer restaurantId, String description, String location);
}
