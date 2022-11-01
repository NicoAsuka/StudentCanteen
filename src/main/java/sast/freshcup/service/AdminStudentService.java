package sast.freshcup.service;

import org.springframework.web.multipart.MultipartFile;
import sast.freshcup.entity.Account;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author: 李林涛
 * @date 2022/10/15 19:48
 */
public interface AdminStudentService {
    List<Account> getStudentList(Integer pageNum, Integer pageSize);

    Account getStudent(Integer uid);
    String deleteStudent(Integer id);
    void updateStudent(Integer uid,String userName,String password,String realName,String schoolId);

    String importStudent(MultipartFile studentFile, HttpServletResponse response) throws IOException;


}
