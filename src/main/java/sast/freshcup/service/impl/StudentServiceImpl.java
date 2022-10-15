package sast.freshcup.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sast.freshcup.common.enums.ErrorEnum;
import sast.freshcup.entity.Account;
import sast.freshcup.exception.LocalRunTimeException;
import sast.freshcup.mapper.AccountMapper;
import sast.freshcup.service.StudentService;


import java.util.HashMap;
import java.util.Map;

import static sast.freshcup.interceptor.AccountInterceptor.accountHolder;

/**
 * @author: 風楪fy
 * @create: 2022-01-22 17:44
 **/
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    AccountMapper accountMapper;


    @Override
    public Map<String, Object> getBalance() {
        //一般来说先做判空校验，若要求不为空的传参为空，则返回error
        //这里没有传参所以先不演示，后面的接口再进行演示

        //通过token获取基本的用户信息如uid，username
        Integer uid = accountHolder.get().getUid();
        String username = accountHolder.get().getUsername();

        //通过uid获取对应的账户对象
        QueryWrapper<Account> eq = new QueryWrapper<Account>()
                .eq("uid", uid)
                .eq("is_deleted", 0);
        Account account = accountMapper.selectOne(eq);
        if (account == null){
            throw new LocalRunTimeException(ErrorEnum.NO_USER);
        }
        //通过对象得到该对象的账户余额
        Double balance = account.getBalance();

        //封装数据，返回
        Map<String, Object> returnBalance = new HashMap<>();
        returnBalance.put("uid",uid);
        returnBalance.put("username",username);
        returnBalance.put("balance",balance);
        return returnBalance;
    }

    @Override
    public Map<String, Object> addBalance(Integer money) {
        if (money == null || money <= 0){
            throw new LocalRunTimeException(ErrorEnum.MONEY_ERROR);
        }

        //通过token获取基本的用户信息如uid，username
        Integer uid = accountHolder.get().getUid();
        String username = accountHolder.get().getUsername();

        //通过uid从数据库中获取对应的账户对象
        Account account = accountMapper.selectById(uid);

        //通过对象得到该对象的账户余额
        Double oldBalance = account.getBalance();
        Double newBalance = oldBalance + money;

        //充值余额
        account.setBalance(newBalance);

        //更新数据库
        accountMapper.updateById(account);

        //封装数据，返回
        Map<String, Object> returnBalance = new HashMap<>();
        returnBalance.put("uid",uid);
        returnBalance.put("username",username);
        returnBalance.put("oldBalance",oldBalance);
        returnBalance.put("newBalance",account.getBalance());
        return returnBalance;
    }
}
