package sast.freshcup.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author: 李林涛
 * @date 2022/10/21 18:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DishOrderView {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @NotNull(message = "用户uid不能为 null")
    private Integer uid;

    @NotNull(message = "菜品编号不能为 null")
    private String dishesId;

    @NotNull(message = "总价不能为 null")
    private Double totalPrice;

    @TableField(value = "is_deleted")
    private Integer isDeleted;

    private String[] dishesNames;

    private Double[] dishesPrices;

}
