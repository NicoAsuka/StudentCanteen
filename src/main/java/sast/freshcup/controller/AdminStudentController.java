package sast.freshcup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sast.freshcup.annotation.AuthHandle;
import sast.freshcup.annotation.OperateLog;
import sast.freshcup.common.enums.AuthEnum;
import sast.freshcup.service.AdminStudentService;

import java.util.Map;

/**
 * @author: 李林涛
 * @date 2022/10/15 19:44
 */
@RestController
@RequestMapping("/admin/student")
@AuthHandle(AuthEnum.ADMIN)
public class AdminStudentController {

    @Autowired
    private AdminStudentService adminStudentService;
    @OperateLog(operDesc = "获取学生列表")
    @GetMapping("/getStudentList")
    public Map<String, Object> getStudentList(
            @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize) {
        return adminStudentService.getStudentList(pageNum,pageSize);
    }


    //@OperateLog(operDesc = "删除学生信息")
    //@PostMapping("/deleteStudent")
    //public String deleteOrder(Integer id) {
    //    return adminStudentService.deleteStudent(id);
    //}
}
