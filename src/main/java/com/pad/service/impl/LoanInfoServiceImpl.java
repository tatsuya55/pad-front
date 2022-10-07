package com.pad.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pad.entity.LoanInfo;
import com.pad.mapper.LoanInfoMapper;
import com.pad.service.LoanInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 贷款信息表 服务实现类
 * </p>
 *
 * @author F4
 * @since 2022-09-12
 */
@Service
public class LoanInfoServiceImpl extends ServiceImpl<LoanInfoMapper, LoanInfo> implements LoanInfoService {
    @Override
    public void findBy(Page<LoanInfo> page, String cNo) {
        baseMapper.findBy(page,cNo);
    }

    /*判断贷款申请是否审核*/
    @Override
    public Integer selectStatus(String cNo) {
        return baseMapper.getStatus(cNo);
    }

    //将贷款和材料删除 设置为已完成
    @Override
    public void complete(String id) {
        baseMapper.updateComplete(id);
    }
}
