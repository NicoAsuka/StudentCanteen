package sast.freshcup.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import sast.freshcup.entity.Account;

import sast.freshcup.mapper.ImportMapper;
import sast.freshcup.service.ImportService;

/**
 * @author: 李林涛
 * @date 2022/8/15 11:07
 */
@Service
public class ImportServiceImpl extends ServiceImpl<ImportMapper, Account> implements ImportService {

}
