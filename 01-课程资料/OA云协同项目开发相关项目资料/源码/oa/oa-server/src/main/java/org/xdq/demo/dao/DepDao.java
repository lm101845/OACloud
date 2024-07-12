package org.xdq.demo.dao;

import org.apache.ibatis.annotations.*;
import org.xdq.demo.dto.DepDto;
import org.xdq.demo.dto.DepQueryDto;
import org.xdq.demo.model.Dep;

import java.util.List;

@Mapper
public interface DepDao {

    List<Dep> findDepList(DepQueryDto depQueryDto);

    @Select("select ifnull(max(d_id),0) from t_dep")
    int findMaxId();

    @Insert("insert into t_dep(d_id,d_name,d_remark,d_status) values(#{d_id},#{d_name},#{d_remark},#{d_status})")
    void insertDep(DepDto depDto);

    @Update("update t_dep set d_name = #{d_name} , d_remark = #{d_remark} where d_id = #{d_id}")
    void updateDep(DepDto depDto);

    void deleteDep(@Param("ids") Integer[] ids);

    boolean findExistsUndetermined(@Param("ids") Integer[] ids);

    @Update("update t_dep set d_status = #{status} where d_id = #{id}")
    void updateStatus(@Param("id") Integer id, @Param("status") Integer status);
}
