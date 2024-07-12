package org.xdq.demo.dao;

import org.apache.ibatis.annotations.*;
import org.xdq.demo.dto.EmpDto;
import org.xdq.demo.dto.EmpQueryDto;
import org.xdq.demo.model.Dep;
import org.xdq.demo.model.Employee;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmployeeDao {
    List<Employee> findEmployeeList(EmpQueryDto empQueryDto);

    @Select("select d_id,d_name from t_dep where d_status = 2")
    List<Dep> findAllEnableDepList();

    @Select("select ifnull(max(cast(e_id as unsigned)) , 0 ) from t_emp")
    int findMaxNumberId();

    @Insert("insert into t_emp(e_id,e_name,e_sex,e_birth,d_id,e_status) values(#{e_id},#{e_name},#{e_sex},#{e_birth},#{d_id},#{e_status})")
    void insertEmp(EmpDto empDto);

    @Update("update t_emp set e_name = #{e_name},e_sex = #{e_sex},e_birth = #{e_birth},d_id = #{d_id} " +
            "where e_id = #{e_id}")
    void updateEmp(EmpDto empDto);

    boolean findExistsUndetermined(@Param("ids") String... ids);

    void deleteEmp(String[] ids);

    @Update("update t_emp set e_status = #{status} where e_id = #{id}")
    void updateStatus(@Param("id") String id, @Param("status") Integer status);

    @Insert("insert into t_user(u_id,u_name,u_pwd) select e_id,e_name,'123456' from t_emp where e_id = #{empId}")
    void insertUserByEmpId(Map<String, String> userIdMap);


    @Delete("delete from t_mgr where dep_id = (select d_id from t_emp where e_id = #{empId})")
    void deleteLeader(Map<String, String> empIdMap);

    @Insert("insert into t_mgr(dep_id,leader_id) select d_id,e_id from t_emp where e_id = #{empId}")
    void insertLeader(Map<String, String> empIdMap);

    @Delete("delete from t_ur where u_id = #{empId}")
    void deleteUserRoles(Map<String, String> userIdMap);

    @Delete("delete from t_user where u_id = #{empId}")
    void deleteUserIdForEmp(Map<String, String> userIdMap);

    @Delete("delete from t_mgr where leader_id = #{id}")
    void deleteLeaderByEmpId(String id);



}
