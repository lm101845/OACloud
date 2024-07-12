package org.xdq.demo.api.info;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.xdq.demo.common.page.PageVo;
import org.xdq.demo.common.vo.R;
import org.xdq.demo.dto.DepDto;
import org.xdq.demo.dto.DepQueryDto;
import org.xdq.demo.model.Dep;
import org.xdq.demo.service.info.DepService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/info/dep")
public class DepController {

    private final DepService depService;

    @GetMapping("")
    public R<PageVo<Dep>> depList(DepQueryDto depQueryDto){
        PageVo<Dep> page = depService.getDepPage(depQueryDto);
        return R.OK(page);
    }

    @PostMapping("")
    public R<?> execAdd(@RequestBody DepDto depDto){
        depService.addDep(depDto);
        return R.OK();
    }
    @PutMapping("")
    public R<?> execUpd(@RequestBody DepDto depDto){
        depService.updateDep(depDto);
        return R.OK();
    }

    @DeleteMapping("/{id}")
    public R<?> execDel(@PathVariable Integer id){
        depService.removeDep(id);
        return R.OK();
    }

    @DeleteMapping("")
    public R<?> execDel(@RequestBody Integer[] ids){
        depService.removeDep(ids);
        return R.OK();
    }

    @PutMapping("/{id}/{status}")
    public R<?> changeStatus(@PathVariable Integer id,@PathVariable Integer status){
        depService.changeStatus(id,status);
        return R.OK();
    }

}
