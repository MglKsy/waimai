package com.liang.regiee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liang.regiee.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SetmealMapper extends BaseMapper<Setmeal> {
    @Update("update setmeal set status = 0 where id = #{id}")
    void updateToZero(@Param("id") Long id);

    @Update("update setmeal set status = 1 where id = #{id}")
    void updateToOne(@Param("id") Long id);
}
