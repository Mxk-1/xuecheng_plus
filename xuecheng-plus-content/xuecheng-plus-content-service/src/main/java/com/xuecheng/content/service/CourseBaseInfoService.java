package com.xuecheng.content.service;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author MXK
 * @version 1.0
 * @description 课程管理serveice
 * @date 2023/2/27 16:50
 */

public interface CourseBaseInfoService {
    /**
     * @param pageParams           分页参数
     * @param queryCourseParamsDto 查询条件
     * @return com.xuecheng.base.model.PageResult<com.xuecheng.content.model.po.CourseBase>
     * @description 课程查询
     * @author MXK
     * @date 2023/2/27 16:53
     */
    PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, @RequestBody QueryCourseParamsDto queryCourseParamsDto);

    /***
     * @description 新增课程
     * @param companyId 培训机构id
     * @param addCourseDto 新增课程信息
     * @return com.xuecheng.content.model.dto.CourseBaseInfoDto 课程基本信息、营销信息
     * @author MXK
     * @date 2023/3/3 11:00
     */
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto);

    /***
     * @description 根据课程id查询课程信息
     * @param courseId 课程id
     * @return com.xuecheng.content.model.dto.CourseBaseInfoDto
     * @author MXK
     * @date 2023/3/4 16:52
     */
    public CourseBaseInfoDto getCourseBaseInfo(Long courseId);

    /***
     * @description 修改课程信息
     * @param companyId 机构id
     * @param dto 课程信息
     * @return com.xuecheng.content.model.dto.CourseBaseInfoDto
     * @author MXK
     * @date 2023/3/4 16:53
     */
    public CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto dto);

}
