package org.xdq.demo.service.info;

import org.xdq.demo.common.page.PageVo;
import org.xdq.demo.dto.DepDto;
import org.xdq.demo.dto.DepQueryDto;
import org.xdq.demo.model.Dep;

public interface DepService {
    PageVo<Dep> getDepPage(DepQueryDto depQueryDto);

    void addDep(DepDto depDto);

    void updateDep(DepDto depDto);

    void removeDep(Integer... ids);

    void changeStatus(Integer id, Integer status);
}
