package com.xuecheng.content.service.impl;

import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.service.CourseCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author MXK
 * @version 1.0
 * @description TODO
 * @date 2023/2/28 14:40
 */

@Service
@Slf4j
public class CourseCategoryServiceImpl implements CourseCategoryService {

    @Autowired
    CourseCategoryMapper courseCategoryMapper;

    @Override
    public List<CourseCategoryTreeDto> queryTreeNodes(String id) {
        // 得到了根节点下边所有的子节点
        List<CourseCategoryTreeDto> categoryTreeDtos = courseCategoryMapper.selectTreeNodes(id);

        //定义 一个List作为最终返回的数据
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = new ArrayList<>();

        //为了方便找子结点的父结点，定义 一个map
        HashMap<String, CourseCategoryTreeDto> nodeMap = new HashMap<>();

        //将数据封装到List中，只包括了根结点的直接下属结点
        categoryTreeDtos.stream().forEach(item -> {
            nodeMap.put(item.getId(), item);
            if (item.getParentid().equals(id)) {
                courseCategoryTreeDtos.add(item);
            }
            // 找到该结点的父结点
            String parentid = item.getParentid();
            //找到该结点的父结点对象
            CourseCategoryTreeDto parentNode = nodeMap.get(parentid);
            if (parentNode != null) {
                List childrenTreeNodes = parentNode.getChildrenTreeNodes();
                if (childrenTreeNodes == null) {
                    parentNode.setChildrenTreeNodes(new ArrayList<CourseCategoryTreeDto>());
                }
                // 找到子节点，放到父节点的childrenTreeNodes属性中
                parentNode.getChildrenTreeNodes().add(item);
            }
        });
        // 返回List中只包含根节点的直接下属节点
        return courseCategoryTreeDtos;
    }
}

