package sast.freshcup.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author: 李林涛
 * @date 2022/10/14 15:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Dish {
    @NotNull(message = "菜品id不能为 null")
    private Integer id;

    @NotNull(message = "商铺id不能为 null")
    private Integer restaurantId;

    @NotNull(message = "菜品名称不能为 null")
    private String name;

    @NotNull(message = "菜品描述不能为 null")
    private String description;

    @NotNull(message = "菜品价格不能为 null")
    private Double price;

    @TableField(value = "is_deleted")
    private Integer isDeleted;
}
