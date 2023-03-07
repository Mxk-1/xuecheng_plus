package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;

import java.util.List;

/**
 * @author MXK
 * @version 1.0
 * @description 课程基本信息管理业务接口
 * @date 2023/3/4 19:43
 */

public interface TeachplanService {
    /***
     * @description 查询课程计划树型结构
     * @param courseId
     * @return java.util.List<com.xuecheng.content.model.dto.TeachplanDto>
     * @author MXK
     * @date 2023/3/4 19:44
     */
    public List<TeachplanDto> findTeachplanTree(Long courseId);

    /**
     * @param teachplanDto
     * @return void
     * @description 只在课程计划
     * @author MXK
     * @date 2023/3/4 20:25
     */
    public void saveTeachplan(SaveTeachplanDto teachplanDto);
}
