package org.xdq.demo.service.leave.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xdq.demo.common.page.PageVo;
import org.xdq.demo.dao.ExamDao;
import org.xdq.demo.dto.LeaveDto;
import org.xdq.demo.dto.LeaveQueryDto;
import org.xdq.demo.model.Leave;
import org.xdq.demo.service.leave.ExamService;

@RequiredArgsConstructor
@Service
@Transactional
public class ExamServiceImpl implements ExamService {
    private final ExamDao examDao;

    @Override
    public PageVo<Leave> getExamPage(LeaveQueryDto dto) {
        return PageVo.getPageVo(dto,()->examDao.findExamList(dto));
    }

    @Override
    public void spLeave(LeaveDto dto) {
        examDao.spLeave(dto);
    }
}
