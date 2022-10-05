package com.pad.mapper;

import com.pad.entity.ApprovalRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 审批记录表 Mapper 接口
 * </p>
 *
 * @author F4
 * @since 2022-09-12
 */
public interface ApprovalRecordMapper extends BaseMapper<ApprovalRecord> {
    //获得审批留言
    String selectMessage(String id);
}
