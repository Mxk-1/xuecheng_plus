package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.CourseCategoryTreeDto;

import java.util.List;

/**
 * @author MXK
 * @version 1.0
 * @description 课程分类操作相关的service
 * @date 2023/2/28 14:38
 */

public interface CourseCategoryService {
    /**
     * 课程分类查询
     *
     * @param id 根节点id
     * @return 根节点下边所有的子节点
     */
    List<CourseCategoryTreeDto> queryTreeNodes(String id);
}
