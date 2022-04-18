package com.liang.regiee.dto;

import com.liang.regiee.entity.Setmeal;
import com.liang.regiee.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
