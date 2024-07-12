package org.xdq.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class EmpDto {

    private String e_id;
    private String e_name;
    private Integer e_sex;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date e_birth;

    private Integer d_id;

    private Integer e_status;


}
