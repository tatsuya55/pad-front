package com.pad.service.impl;

import com.pad.entity.ApprovalRecord;
import com.pad.mapper.ApprovalRecordMapper;
import com.pad.service.ApprovalRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 审批记录表 服务实现类
 * </p>
 *
 * @author F4
 * @since 2022-09-12
 */
@Service
public class ApprovalRecordServiceImpl extends ServiceImpl<ApprovalRecordMapper, ApprovalRecord> implements ApprovalRecordService {
    //获得审批留言
    @Override
    public String getMessage(String id) {
        return baseMapper.selectMessage(id);
    }
}
