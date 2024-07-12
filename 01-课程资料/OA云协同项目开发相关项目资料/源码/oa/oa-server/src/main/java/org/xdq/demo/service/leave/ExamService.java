package org.xdq.demo.service.leave;

import org.xdq.demo.common.page.PageVo;
import org.xdq.demo.dto.LeaveDto;
import org.xdq.demo.dto.LeaveQueryDto;
import org.xdq.demo.model.Leave;

public interface ExamService {
    PageVo<Leave> getExamPage(LeaveQueryDto dto);

    void spLeave(LeaveDto dto);
}
