package sast.freshcup.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import sast.freshcup.entity.Order;

/**
 * @author: 李林涛
 * @date 2022/10/15 20:55
 */
@Repository
public interface OrderMapper extends BaseMapper<Order> {
}
