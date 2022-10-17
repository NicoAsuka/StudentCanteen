package sast.freshcup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sast.freshcup.annotation.AuthHandle;
import sast.freshcup.common.enums.AuthEnum;
import sast.freshcup.service.StudentService;

/**
 * @author: 李林涛
 * @date 2022/10/15 19:45
 * contributor:acd12344
 */
@RestController
@RequestMapping("/admin/restaurant")
@AuthHandle(AuthEnum.ADMIN)
public class AdminRestaurantController {





}
