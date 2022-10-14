package sast.freshcup.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import sast.freshcup.entity.Account;
import sast.freshcup.entity.AdminData;

import java.util.List;


/**
 * @author: 李林涛
 * @date 2022/8/11 22:26
 */
@Repository
public interface ImportMapper extends BaseMapper<Account> {
    // 如果是mybatis,尽量别直接调用多次insert,自己写一个mapper里面新增一个方法batchInsert,所有数据一次性插入
}
