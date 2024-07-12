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
import org.xdq.demo.service.leave.ApprevService;

import java.util.Date;

@RequiredArgsConstructor
@RestController
@RequestMapping("/leave/apprev")
public class ApprevController {

    private final ApprevService apprevService;

    private final CurrentUser currentUser;

    @GetMapping("")
    public R<PageVo<Leave>> apprevList(LeaveQueryDto dto){
        dto.setE_id(currentUser.getUserId());
        PageVo<Leave> page = apprevService.getApprevPage(dto);
        return R.OK(page);
    }

    @PostMapping("")
    public R<?> execAdd(@RequestBody LeaveDto dto){
        dto.setE_id(currentUser.getUserId());
        dto.setL_submit_date(new Date());
        dto.setL_status(LeaveStatusEnum.NOT_SUBMIT.getCode());
        apprevService.addLeave(dto);
        return R.OK();

    }

    @PutMapping("")
    public R<?> execUpd(@RequestBody LeaveDto dto){
        dto.setL_submit_date(new Date());
        apprevService.updateLeave(dto);
        return R.OK();

    }

    @DeleteMapping("/{id}")
    public R<?> execDel(@PathVariable Long id){
        apprevService.removeLeave(id);
        return R.OK();
    }

    @DeleteMapping("")
    public R<?> execDel(@RequestBody Long... ids){
        apprevService.removeLeave(ids);
        return R.OK();
    }

    @PutMapping("/submit/{id}")
    public R<?> execSubmit(@PathVariable Long id){
        apprevService.submitLeave(id);
        return R.OK();
    }

    @PutMapping("/back/{id}")
    public R<?> execBack(@PathVariable Long id){
        apprevService.backLeave(id);
        return R.OK();
    }


}
