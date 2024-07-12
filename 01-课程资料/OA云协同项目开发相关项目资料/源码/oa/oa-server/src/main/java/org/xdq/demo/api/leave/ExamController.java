package org.xdq.demo.api.leave;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.xdq.demo.common.LeaveStatusEnum;
import org.xdq.demo.common.currentuser.CurrentUser;
import org.xdq.demo.common.page.PageVo;
import org.xdq.demo.common.vo.R;
import org.xdq.demo.dto.LeaveDto;
import org.xdq.demo.dto.LeaveQueryDto;
import org.xdq.demo.model.Leave;
import org.xdq.demo.service.leave.ExamService;

import java.util.Date;

@RequiredArgsConstructor
@RestController
@RequestMapping("/leave/exam")
public class ExamController {

    private final ExamService examService;

    private final CurrentUser currentUser;

    @GetMapping("")
    public R<PageVo<Leave>> examList(LeaveQueryDto dto){
        dto.setLeader_id(currentUser.getUserId());
        PageVo<Leave> page = examService.getExamPage(dto);
        return R.OK(page);
    }

    @PutMapping("/pass")
    public R<?> execPass(@RequestBody LeaveDto dto){
        dto.setL_status(LeaveStatusEnum.PASS.getCode());
        dto.setL_spr(currentUser.getUserName());
        dto.setL_sp_date(new Date());
        examService.spLeave(dto);
        return R.OK();
    }

    @PutMapping("/reject")
    public R<?> execReject(@RequestBody LeaveDto dto){
        dto.setL_status(LeaveStatusEnum.REJECTED.getCode());
        dto.setL_spr(currentUser.getUserName());
        dto.setL_sp_date(new Date());
        examService.spLeave(dto);
        return R.OK();
    }
}
