package sast.freshcup.service;

import java.util.Map;

/**
 * @author: 李林涛
 * @date 2022/10/15 19:48
 */
public interface AdminRestaurantService {
    Map<String, Object> getRestaurantList(Integer pageNum, Integer pageSize);
}
