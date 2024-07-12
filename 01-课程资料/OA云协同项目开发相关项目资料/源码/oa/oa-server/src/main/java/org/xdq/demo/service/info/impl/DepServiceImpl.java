package org.xdq.demo.service.info.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xdq.demo.common.InfoStatusEnum;
import org.xdq.demo.common.ex.BusinessException;
import org.xdq.demo.common.page.PageVo;
import org.xdq.demo.dao.DepDao;
import org.xdq.demo.dto.DepDto;
import org.xdq.demo.dto.DepQueryDto;
import org.xdq.demo.model.Dep;
import org.xdq.demo.service.info.DepService;

@RequiredArgsConstructor
@Service
@Transactional
public class DepServiceImpl implements DepService {

    private final DepDao depDao;

    @Override
    public PageVo<Dep> getDepPage(DepQueryDto depQueryDto) {

//        QueryAction<Dep> action = new QueryAction<Dep>() {
//            @Override
//            public List<Dep> executeQuery() throws Exception {
//                return depDao.findDepList(depQueryDto);
//            }
//        };

        //QueryAction<Dep> action = ()-> depDao.findDepList(depQueryDto);

        return PageVo.getPageVo(depQueryDto,()-> depDao.findDepList(depQueryDto));
    }


    private int id;//最大编号

    @PostConstruct //（初始化方法）表示该方法在创建对象后执行，而仅执行一次
    public void init(){
        id = depDao.findMaxId();
    }

    private synchronized int newId(){
        return ++id;
    }

    @Override
    public void addDep(DepDto depDto) {
        depDto.setD_id(newId());
        depDto.setD_status(InfoStatusEnum.undetermined.getCode());
        depDao.insertDep(depDto);
    }

    @Override
    public void updateDep(DepDto depDto) {
        depDao.updateDep(depDto);
    }

    @Override
    public void removeDep(Integer... ids) {
        if(ids == null || ids.length == 0){
            throw new BusinessException("请选择至少一条数据！");
        }

        //如果被删除的数据中存在状态不是“未确定”的，应阻止删除
        boolean exists = depDao.findExistsUndetermined(ids);
        if(exists){
            throw new BusinessException("存在已确定数据！不允许删除。");
        }
        depDao.deleteDep(ids);
    }

    @Override
    public void changeStatus(Integer id, Integer status) {
        depDao.updateStatus(id,status);
    }
}
