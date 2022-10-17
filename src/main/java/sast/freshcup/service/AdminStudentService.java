package sast.freshcup.service;

import java.util.Map;

/**
 * @author: 李林涛
 * @date 2022/10/15 19:48
 */
public interface AdminStudentService {
    Map<String,Object> getStudentList(Integer pageNum, Integer pageSize);

    Map<String,Object> getStudent(Integer uid);
    String deleteStudent(Integer id);


}
