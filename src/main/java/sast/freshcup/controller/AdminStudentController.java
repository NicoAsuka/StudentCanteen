package sast.freshcup.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sast.freshcup.annotation.AuthHandle;
import sast.freshcup.common.enums.AuthEnum;

/**
 * @author: 李林涛
 * @date 2022/10/15 19:44
 */
@RestController
@RequestMapping("/admin/student")
@AuthHandle(AuthEnum.ADMIN)
public class AdminStudentController {

}
