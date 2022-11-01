package sast.freshcup.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sast.freshcup.common.enums.ErrorEnum;
import sast.freshcup.entity.Account;
import sast.freshcup.entity.DishOrder;
import sast.freshcup.entity.Restaurant;
import sast.freshcup.exception.LocalRunTimeException;
import sast.freshcup.listener.AdminDataListener;
import sast.freshcup.mapper.AccountMapper;
import sast.freshcup.mapper.ImportMapper;
import sast.freshcup.service.AdminStudentService;
import sast.freshcup.service.ImportService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
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

    @Autowired
    ImportService importService;

    @Autowired
    ImportMapper importMapper;

    @Override
    public List<Account> getStudentList(Integer pageNum, Integer pageSize) {
        //这里不需要校验参数为空是因为我们在controller中设置了默认值
        //以下是分页查询的写法
        Page<Account> page = new Page<>(0, 100);
        accountMapper.selectPage(page,new QueryWrapper<Account>()
                .eq("role",0)
                .eq("is_deleted",0)
                .orderByAsc("uid"));
        //封装返回

        List<Account> records = page.getRecords();
//        long total = page.getTotal();
//        Map<String, Object> res = new HashMap<>();
//        res.put("total", total);
//        res.put("pageNum", pageNum);
//        res.put("pageSize", pageSize);
//        res.put("records", records);
        return records;
    }

    @Override
    public Account getStudent(Integer uid) {
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

        return account;
    }

    public  String deleteStudent(Integer id){
        Account account = accountMapper.selectById(id);
        account.setIsDeleted(1);
        accountMapper.updateById(account);
        return "成功删除学生信息";
    }
    public void updateStudent(Integer uid,String userName,String password,String realName,String schoolId){
        Account account = accountMapper.selectById(uid);

        account.setUsername(userName);
        account.setPassword(password);
        account.setSchoolId(schoolId);
        account.setRealName(realName);
        accountMapper.updateById(account);
    }

    @Override
    public String importStudent(MultipartFile studentFile, HttpServletResponse response) throws IOException {

            if (studentFile.isEmpty()) {
                throw new LocalRunTimeException(ErrorEnum.IMPORT_ERROR);
            }

            AdminDataListener adminDataListener = new AdminDataListener(importService, accountMapper);
            //读取excel并持久化到数据库
            EasyExcel.read(studentFile.getInputStream(), Account.class,
                    adminDataListener).sheet().doRead();

            //将处理后的数据写入excel，返回url输出
            try {
                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                response.setCharacterEncoding("utf-8");
                // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
                String fileName = URLEncoder.encode("食堂管理系统学生账号导出表", "UTF-8").replaceAll("\\+", "%20");
                response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
                // 这里需要设置不关闭流
                EasyExcel.write(response.getOutputStream(), Account.class).autoCloseStream(Boolean.FALSE).sheet("sheet1")
                        .doWrite(adminData());
            } catch (Exception e) {
                // 重置response
                response.reset();
                response.setContentType("application/json");
                response.setCharacterEncoding("utf-8");
                Map<String, String> map = MapUtils.newHashMap();
                map.put("status", "failure");
                map.put("message", "下载文件失败" + e.getMessage());
                response.getWriter().println(JSON.toJSONString(map));
            }
            return "success";
        }

        private List<Account> adminData() {
        List<Account> list = ListUtils.newArrayList();
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role", 0).eq("is_deleted", 0);
        List<Account> adminData = importMapper.selectList(queryWrapper);

        for (Account adminDatum : adminData) {
            Account data = new Account();
            data.setUid(adminDatum.getUid());
            data.setUsername(adminDatum.getUsername());
            data.setPassword(adminDatum.getPassword());
            data.setSchoolId(adminDatum.getSchoolId());
            data.setRealName(adminDatum.getRealName());
            data.setRole(0);
            list.add(data);
        }
        return list;
    }

}
