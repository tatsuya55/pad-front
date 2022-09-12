package com.pad.mapper;


import com.pad.dto.FaceUserInfo;
import com.pad.entity.UserFaceInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;



public interface MybatisUserFaceInfoMapper {

    List<UserFaceInfo> findUserFaceInfoList();

    void insertUserFaceInfo(UserFaceInfo userFaceInfo);

    List<FaceUserInfo> getUserFaceInfoByGroupId(Integer groupId);
}
