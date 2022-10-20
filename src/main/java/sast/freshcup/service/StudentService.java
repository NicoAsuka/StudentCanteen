package sast.freshcup.service;

import org.springframework.ui.Model;
import sast.freshcup.entity.Account;
import sast.freshcup.entity.Dish;

import java.nio.channels.AcceptPendingException;
import java.util.List;
import java.util.Map;

/**
 * @author: 風楪fy
 * @create: 2022-01-22 17:43
 **/
public interface StudentService {

    Account getBalance(Model model);

    Map<String, Object> addBalance(Integer money);

    Map<String, Object> getRestaurantList(Integer pageNum, Integer pageSize);

    List<Dish> getDishList(Integer pageNum, Integer pageSize);

    Map<String, Object> createOrder(String dishesId);

    String deleteOrder(Integer id);

    Map<String, Object> updateOrderList(Integer id, String dishesId);
}