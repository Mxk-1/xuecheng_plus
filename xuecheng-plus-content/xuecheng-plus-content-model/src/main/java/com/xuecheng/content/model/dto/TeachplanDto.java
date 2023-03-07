package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.model.po.TeachplanMedia;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author MXK
 * @version 1.0
 * @description TODO
 * @date 2023/3/4 19:14
 */

@Data
@ToString
public class TeachplanDto extends Teachplan {

    // 课程计划关联的媒咨信息
    TeachplanMedia teachplanMedia;

    // 子节点
    List<TeachplanDto> teachPlanTreeNodes;
}

    