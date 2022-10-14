package sast.freshcup.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: 李林涛
 * @date 2022/8/10 23:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AdminData implements Serializable {

    private static final long serialVersionUID = 3586888871818957805L;

    @TableId(type = IdType.AUTO)
    @ExcelProperty("UID")
    private Long uid;

    @NotNull(message = "用户名不能为 null")
    @ExcelProperty("账号")
    private String username;

    @ExcelProperty("密码")
    private String password;

    @ExcelIgnore
    private Integer role;

//    @ExcelIgnore
//    private String success;
//
//    @ExcelIgnore
//    private String failure;
}
