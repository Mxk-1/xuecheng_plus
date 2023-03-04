package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.mapper.CourseMarketMapper;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.model.po.CourseCategory;
import com.xuecheng.content.model.po.CourseMarket;
import com.xuecheng.content.service.CourseBaseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author MXK
 * @version 1.0
 * @description TODO
 * @date 2023/2/27 16:54
 */
@Service
@Slf4j
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {

    @Autowired
    CourseBaseMapper courseBaseMapper;

    @Autowired
    CourseMarketMapper courseMarketMapper;

    @Autowired
    CourseCategoryMapper courseCategoryMapper;

    /***
     * @description 获取所有的课程信息并分页显示
     * @param pageParams 分页信息
     * @param queryCourseParamsDto
     * @return com.xuecheng.base.model.PageResult<com.xuecheng.content.model.po.CourseBase>
     * @author MXK
     * @date 2023/3/4 16:55
     */
    @Override
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto) {
        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();

        // 拼接查询条件
        // 根据课程名称模糊查询
        queryWrapper.like(StringUtils.isNotEmpty(queryCourseParamsDto.getCourseName()), CourseBase::getName, queryCourseParamsDto.getCourseName());

        // 根据课程名称审核状态
        queryWrapper.eq(StringUtils.isNotEmpty(queryCourseParamsDto.getCourseName()), CourseBase::getAuditStatus, queryCourseParamsDto.getAuditStatus());

        // 根据课程发布状态
        queryWrapper.eq(StringUtils.isNotEmpty(queryCourseParamsDto.getPublishStatus()), CourseBase::getStatus, queryCourseParamsDto.getPublishStatus());

        // 分页对象
        Page<CourseBase> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());

        // 分页查询 page 分页参数 @Param("ew") Wrapper<T> queryWrapper 查询条件
        Page<CourseBase> pageResult = courseBaseMapper.selectPage(page, queryWrapper);

        // 数据
        List<CourseBase> records = pageResult.getRecords();

        // 总记录数
        long total = pageResult.getTotal();

        // 准备返回数据 List<T> items, long counts, long page, long pageSize
        return new PageResult<CourseBase>(records, total, pageParams.getPageNo(), pageParams.getPageSize());
    }


    /***
     * @description 创建课程
     * @param companyId 机构id
     * @param dto 课程信息
     * @return com.xuecheng.content.model.dto.CourseBaseInfoDto
     * @author MXK
     * @date 2023/3/4 16:54
     */
    @Transactional
    @Override
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto dto) {
        // 对参数进行合法性校验
        // if (StringUtils.isBlank(dto.getName())) {
        //     // throw new RuntimeException("课程名称为空");
        //     XueChengPlusException.cast("课程名称为空");
        //     // XueChengPlusException.cast(CommonError.UNKOWN_ERROR);
        // }
        //
        // if (StringUtils.isBlank(dto.getMt())) {
        //     throw new RuntimeException("课程分类为空");
        // }
        //
        // if (StringUtils.isBlank(dto.getSt())) {
        //     throw new RuntimeException("课程分类为空");
        // }
        //
        // if (StringUtils.isBlank(dto.getGrade())) {
        //     throw new RuntimeException("课程等级为空");
        // }
        //
        // if (StringUtils.isBlank(dto.getTeachmode())) {
        //     throw new RuntimeException("教育模式为空");
        // }
        //
        // if (StringUtils.isBlank(dto.getUsers())) {
        //     throw new RuntimeException("适应人群为空");
        // }
        //
        // if (StringUtils.isBlank(dto.getCharge())) {
        //     throw new RuntimeException("收费规则为空");
        // }


        // 对数据进行封装，调用mapper进行数据持久化
        CourseBase courseBase = new CourseBase();

        // 将传入的dto数据设置到courseBase中
        // 将dto中和courseBase属性名一样的属性值拷贝到courseBase中
        BeanUtils.copyProperties(dto, courseBase);

        // 设置机构的id
        courseBase.setCompanyId(companyId);

        // 创建时间
        courseBase.setCreateDate(LocalDateTime.now());

        // 审核状态默认未提交
        courseBase.setAuditStatus("202002");
        // 发布状态默认未发布
        courseBase.setStatus("203001");

        // 课程基本表插入一条记录
        int insert = courseBaseMapper.insert(courseBase);

        // 获取课程id
        Long courseId = courseBase.getId();
        CourseMarket courseMarket = new CourseMarket();

        // 将dto中和courseMarket属性名一样的属性拷贝到courseMarket中
        BeanUtils.copyProperties(dto, courseMarket);
        courseMarket.setId(courseId);

        // 如果课程为收费，价格必须输入
        String charge = dto.getCharge();
        if (charge.equals("201001")) { // 收费 输入价格且大于零
            if (courseMarket.getCharge() == null || courseMarket.getPrice().floatValue() <= 0) {
                // throw new RuntimeException("课程收费，价格不能为空，且必须大于0");
                XueChengPlusException.cast("课程收费，价格不能为空，且必须大于0");
            }
        }

        // 课程营销表插入一条记录
        int insert1 = courseMarketMapper.insert(courseMarket);

        if (insert <= 0 || insert1 <= 0) {
            throw new RuntimeException("添加课程失败");
        }

        Object getCourseBaseInfo;

        CourseBaseInfoDto courseBaseInfo = getCourseBaseInfo(courseId);

        // 组装要返回的结果
        return courseBaseInfo;
    }

    /***
     * @description 根据课程id查询课程的基本和营销信息
     * @param courseId 课程id
     * @return 课程的信息
     * @author MXK
     * @date 2023/3/3 11:23
     */
    public CourseBaseInfoDto getCourseBaseInfo(Long courseId) {
        // 课程的基本信息
        CourseBase courseBase = courseBaseMapper.selectById(courseId);

        // 课程的营销信息
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);

        // 组装信息
        CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();

        // 属性拷贝
        BeanUtils.copyProperties(courseBase, courseBaseInfoDto);
        BeanUtils.copyProperties(courseMarket, courseBaseInfoDto);

        // 根据课程id的编号查询分类的名称
        String mt = courseBase.getMt();
        String st = courseBase.getSt();

        CourseCategory mtCategory = courseCategoryMapper.selectById(mt);
        CourseCategory stCategory = courseCategoryMapper.selectById(st);
        if (mtCategory != null) {
            // 大分类名称
            String mtName = mtCategory.getName();
            courseBaseInfoDto.setMtName(mtName);
        }

        if (stCategory != null) {
            // 小分类名称
            String stName = stCategory.getName();
            courseBaseInfoDto.setStName(stName);
        }

        return courseBaseInfoDto;

    }

    /***
     * @description 修改课程信息
     * @param companyId
     * @param dto
     * @return com.xuecheng.content.model.dto.CourseBaseInfoDto
     * @author MXK
     * @date 2023/3/4 16:56
     */
    @Override
    public CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto dto) {
        return null;
    }


}

    