package sast.freshcup.entity;

import java.io.Serializable;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * (Account)实体类
 *
 * @author 風楪fy
 * @since 2022-01-17 17:47:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Account implements Serializable {

    private static final long serialVersionUID = 532960928738689155L;

    /**
     * 用户UID
     */
    @TableId(type = IdType.AUTO)
    @ExcelProperty("UID")
    private Integer uid;

    @NotNull(message = "用户名不能为 null")
    @ExcelProperty("账号")
    private String username;

    @NotNull(message = "密码不能为 null")
    @ExcelProperty("密码")
    private String password;

    @NotNull(message = "真实姓名不能为 null")
    @ExcelProperty("真实姓名")
    private String realName;

    @NotNull(message = "学号不能为 null")
    @ExcelProperty("学号")
    private String schoolId;

    @NotNull(message = "账户余额不能为 null")
    private Double balance;

    @NotNull(message = "is_deleted")
    private Integer isDeleted;

    /**
     * student/admin
     * 0,1
     */
    @ExcelIgnore
    private Integer role;

    public Account(String username, String password, Integer role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}

