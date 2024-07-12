package org.xdq.demo.dto;

import lombok.Data;
import org.xdq.demo.common.page.PageParamDto;

@Data
public class UserQueryDto extends PageParamDto {

    private String u_id;
    private String u_name;
}
