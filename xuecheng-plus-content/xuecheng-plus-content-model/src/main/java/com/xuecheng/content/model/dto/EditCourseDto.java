package com.xuecheng.content.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author MXK
 * @version 1.0
 * @description TODO
 * @date 2023/3/4 16:41
 */

@Data
@ApiModel(value = "EditCourseDto", description = "修改课程基本信息")
public class EditCourseDto extends AddCourseDto {

    // 课程id
    @ApiModelProperty(value = "课程名称", required = true)
    private Long id;
}

    