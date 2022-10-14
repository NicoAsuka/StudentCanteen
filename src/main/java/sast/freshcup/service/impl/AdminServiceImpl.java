package sast.freshcup.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.stereotype.Service;
import sast.freshcup.common.enums.ErrorEnum;
import sast.freshcup.exception.LocalRunTimeException;
import sast.freshcup.service.AdminService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static sast.freshcup.interceptor.AccountInterceptor.accountHolder;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Acow337
 * @Date: 2022/01/20/20:12
 * @Description:
 */

@Service
public class AdminServiceImpl implements AdminService {

}
