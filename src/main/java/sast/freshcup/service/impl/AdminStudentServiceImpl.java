package sast.freshcup.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sast.freshcup.entity.Account;
import sast.freshcup.entity.DishOrder;
import sast.freshcup.entity.Restaurant;
import sast.freshcup.mapper.AccountMapper;
import sast.freshcup.service.AdminStudentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 李林涛
 * @date 2022/10/15 19:54
 */
@Service
public class AdminStudentServiceImpl implements AdminStudentService {
    @Autowired
    AccountMapper accountMapper;
    @Override
    public Map<String, Object> getStudentList(Integer pageNum, Integer pageSize) {
        //这里不需要校验参数为空是因为我们在controller中设置了默认值
        //以下是分页查询的写法
        Page<Account> page = new Page<>(pageNum, pageSize);
        accountMapper.selectPage(page,new QueryWrapper<Account>()
                .eq("is_deleted",0)
                .orderByAsc("uid"));
        //封装返回
        List<Account> records = page.getRecords();
        long total = page.getTotal();
        Map<String, Object> res = new HashMap<>();
        res.put("total", total);
        res.put("pageNum", pageNum);
        res.put("pageSize", pageSize);
        res.put("records", records);
        return res;
    }
}
