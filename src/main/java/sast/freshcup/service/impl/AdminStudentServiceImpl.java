package sast.freshcup.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sast.freshcup.common.enums.ErrorEnum;
import sast.freshcup.entity.Account;
import sast.freshcup.entity.DishOrder;
import sast.freshcup.entity.Restaurant;
import sast.freshcup.exception.LocalRunTimeException;
import sast.freshcup.mapper.AccountMapper;
import sast.freshcup.service.AdminStudentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static sast.freshcup.interceptor.AccountInterceptor.accountHolder;

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

    @Override
    public Map<String, Object> getStudent(Integer uid) {
        //一般来说先做判空校验，若要求不为空的传参为空，则返回error
        //通过uid获取对应的账户对象
        QueryWrapper<Account> eq = new QueryWrapper<Account>()
                .eq("uid", uid)
                .eq("role",0)
                .eq("is_deleted", 0);
        Account account = accountMapper.selectOne(eq);
        if (account == null){
            throw new LocalRunTimeException(ErrorEnum.NO_USER);
        }
        //通过对象得到该对象的账户余额
        Double balance = account.getBalance();
        String username=account.getUsername();

        //封装数据，返回
        Map<String, Object> returnStudent = new HashMap<>();
        returnStudent.put("uid",uid);
        returnStudent.put("username",username);
        returnStudent.put("balance",balance);
        return returnStudent;
    }

    public  String deleteStudent(Integer id){
        Account account = accountMapper.selectById(id);
        account.setIsDeleted(1);
        accountMapper.updateById(account);
        return "成功删除学生信息";
    }
}
