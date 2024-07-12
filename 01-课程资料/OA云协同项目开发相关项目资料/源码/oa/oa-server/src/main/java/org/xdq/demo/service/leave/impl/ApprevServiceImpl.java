package org.xdq.demo.service.leave.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xdq.demo.common.LeaveStatusEnum;
import org.xdq.demo.common.ex.BusinessException;
import org.xdq.demo.common.page.PageVo;
import org.xdq.demo.dao.ApprevDao;
import org.xdq.demo.dto.LeaveDto;
import org.xdq.demo.dto.LeaveQueryDto;
import org.xdq.demo.model.Leave;
import org.xdq.demo.service.leave.ApprevService;

@RequiredArgsConstructor
@Service
@Transactional
public class ApprevServiceImpl implements ApprevService {
    private final ApprevDao apprevDao;

    @Override
    public PageVo<Leave> getApprevPage(LeaveQueryDto dto) {
        return PageVo.getPageVo(dto,()->apprevDao.findApprevList(dto));
    }

    @Override
    public void addLeave(LeaveDto dto) {
        apprevDao.insertLeave(dto);
    }

    @Override
    public void updateLeave(LeaveDto dto) {
        apprevDao.updateLeave(dto);
    }

    @Override
    public void removeLeave(Long... ids) {
        if(ids == null || ids.length == 0){
            throw new BusinessException("请至少选择一条请假申请！");
        }

        //如果有提交后的数据，则不允许删除
        boolean exists = apprevDao.findExistNotSubmited(ids);
        if(exists){
            throw new BusinessException("存在已提交的请假申请！");
        }

        apprevDao.deleteLeave(ids);
    }

    @Override
    public void submitLeave(Long id) {
        apprevDao.updateLeaveStatus(id, LeaveStatusEnum.SUBMITED.getCode());
    }

    @Override
    public void backLeave(Long id) {
        apprevDao.backLeave(id,LeaveStatusEnum.REVERSE.getCode());
    }
}
