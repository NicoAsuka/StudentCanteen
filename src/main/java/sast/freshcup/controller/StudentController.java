package sast.freshcup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sast.freshcup.annotation.OperateLog;
import sast.freshcup.service.StudentService;

import java.util.Map;

/**
 * @author: 風楪fy
 * @create: 2022-01-15 17:22
 **/
//@AuthHandle(AuthEnum.STUDENT)
@RequestMapping("/student")
@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;


}
