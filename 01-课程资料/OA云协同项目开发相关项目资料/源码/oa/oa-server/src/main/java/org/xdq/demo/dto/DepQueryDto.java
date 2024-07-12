package org.xdq.demo.dto;

import lombok.Data;
import org.xdq.demo.common.page.PageParamDto;

@Data
public class DepQueryDto extends PageParamDto {

    private String d_id;
    private String d_name;

    private String d_remark;

    private Integer d_status;
}
