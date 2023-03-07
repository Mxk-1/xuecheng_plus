package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuecheng.content.mapper.TeachplanMapper;
import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.service.TeachplanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author MXK
 * @version 1.0
 * @description 课程计划service接口实现类
 * @date 2023/3/4 19:45
 */

@Slf4j
@Service
public class TeachplanServiceImpl implements TeachplanService {

    @Autowired
    TeachplanMapper teachplanMapper;

    /**
     * @param courseId
     * @return java.util.List<com.xuecheng.content.model.dto.TeachplanDto>
     * @description 展示所有课程计划
     * @author MXK
     * @date 2023/3/4 20:37
     */
    @Override
    public List<TeachplanDto> findTeachplanTree(Long courseId) {
        List<TeachplanDto> teachplanDtos = teachplanMapper.selectTreeNodes(courseId);
        return teachplanDtos;
    }

    /**
     * @param teachplanDto
     * @return void
     * @description 新增课程计划
     * @author MXK
     * @date 2023/3/4 20:36
     */
    @Override
    @Transactional
    public void saveTeachplan(SaveTeachplanDto teachplanDto) {

        // 课程计划id
        Long id = teachplanDto.getId();

        // 修改课程计划
        if (id != null) {
            Teachplan teachplan = teachplanMapper.selectById(id);
            BeanUtils.copyProperties(teachplanDto, teachplan);
            teachplanMapper.updateById(teachplan);
        } else {
            // 取出父同级别的课程计划数量
            int count = getTeachplanCount(teachplanDto.getCourseId(), teachplanDto.getParentid());
            Teachplan teachplanNew = new Teachplan();
            teachplanNew.setOrderby(count + 1);
            BeanUtils.copyProperties(teachplanDto, teachplanNew);
            teachplanMapper.insert(teachplanNew);
        }
    }

    /**
     * @param courseId 课程id
     * @param parentId 父课程id
     * @return int 最新排序号
     * @description 获取最新的排序号
     * @author MXK
     * @date 2023/3/4 20:34
     */
    private int getTeachplanCount(Long courseId, Long parentId) {
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teachplan::getCourseId, courseId);
        queryWrapper.eq(Teachplan::getParentid, parentId);
        Integer count = teachplanMapper.selectCount(queryWrapper);
        return count;

    }
}

    