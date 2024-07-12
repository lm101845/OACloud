package org.xdq.demo.model;

import lombok.Data;

import java.util.List;

@Data
public class Role {

    private Integer ro_id;
    private String ro_name;

    private List<String> funNameList;
}
