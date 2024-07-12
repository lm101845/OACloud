package org.xdq.demo.dto;

import lombok.Data;
import org.xdq.demo.common.page.PageParamDto;

@Data
public class LeaveQueryDto extends PageParamDto {
    private String l_id;
    private String e_id;//请假申请人编号
    private String l_cause;

    private Integer l_status;

    private String leader_id;//领导编号

    private String e_name;//申请人姓名


}
