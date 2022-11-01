package sast.freshcup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sast.freshcup.annotation.AuthHandle;
import sast.freshcup.annotation.OperateLog;
import sast.freshcup.common.enums.AuthEnum;
import sast.freshcup.entity.Account;
import sast.freshcup.service.AdminStudentService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author: 李林涛
 * @date 2022/10/15 19:44
 */
@Controller
@RequestMapping("/admin/student")
//@AuthHandle(AuthEnum.ADMIN)
public class AdminStudentController {

    @Autowired
    private AdminStudentService adminStudentService;
    @OperateLog(operDesc = "获取学生列表")
    @GetMapping("/getStudentList")
    public String getStudentList(
            @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize,
            Model model) {
        List<Account> studentList = adminStudentService.getStudentList(pageNum, pageSize);
        model.addAttribute("studentList",studentList);
        return "studentList2";
    }

    @OperateLog(operDesc = "根据uid获取单个学生信息")
    @GetMapping("/getUpdateStudentById")
    public String getStudent(Integer uid,Model  model) {
        Account student = adminStudentService.getStudent(uid);
        model.addAttribute("student",student);
        return "updateStudent2";
    }


    @OperateLog(operDesc = "删除学生信息")
    @PostMapping("/deleteStudent")
    public String deleteStudent(Integer id) {
        return adminStudentService.deleteStudent(id);
    }

    @OperateLog(operDesc = "修改学生信息")
    @PostMapping("/updateStudent")
    public String updateStudent(Integer uid,String username,String password,String realName,String schoolId) {
        adminStudentService.updateStudent(uid,username,password,realName,schoolId);
        return "redirect:/admin/student/getStudentList";
    }

    @OperateLog(operDesc = "管理端批量导入管理员")
    @PostMapping("/importStudent")
    public String importStudent(@RequestParam("file") MultipartFile file,
                              String student,
                              HttpServletResponse response) throws IOException {
        System.out.println(file);
        System.out.println(student);
        adminStudentService.importStudent(file,response);
        return "import2";
    }
}
