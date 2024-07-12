package org.xdq.demo.service.leave;

import org.xdq.demo.common.page.PageVo;
import org.xdq.demo.dto.LeaveDto;
import org.xdq.demo.dto.LeaveQueryDto;
import org.xdq.demo.model.Leave;

public interface ApprevService {
    PageVo<Leave> getApprevPage(LeaveQueryDto dto);

    void addLeave(LeaveDto dto);

    void updateLeave(LeaveDto dto);

    void removeLeave(Long... ids);

    void submitLeave(Long id);

    void backLeave(Long id);
}
