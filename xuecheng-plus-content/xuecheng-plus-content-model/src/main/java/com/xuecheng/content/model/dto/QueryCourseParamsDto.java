package com.xuecheng.content.model.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @author MXK
 * @version 1.0
 * @description 课程查询参数DTO
 * @date 2023/2/24 20:57
 */

@Data
@ToString
public class QueryCourseParamsDto {
    // 审核状态
    private String auditStatus;
    // 课程名称
    private String courseName;
    // 发布状态
    private String publishStatus;
}

    