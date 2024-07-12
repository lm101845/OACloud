package org.xdq.demo.dto;

import lombok.Data;
import org.xdq.demo.common.page.PageParamDto;

@Data
public class RoleQueryDto extends PageParamDto {

    private String ro_id;
    private String ro_name;
}
