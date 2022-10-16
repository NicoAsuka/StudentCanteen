package sast.freshcup.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author: 李林涛
 * @date 2022/10/14 15:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Restaurant {
    @NotNull(message = "菜品id不能为 null")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @NotNull(message = "商铺名称不能为 null")
    private String name;

    @NotNull(message = "商铺描述不能为 null")
    private String description;

    @NotNull(message = "店铺地点不能为 null")
    private String location;

    @TableField(value = "is_deleted")
    private Integer isDeleted;
}
