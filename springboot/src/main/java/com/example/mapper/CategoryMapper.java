/**
 * CategoryMapper
 *
 * @author Feiqi
 * @date 2025/1/8  18:46
 */

package com.example.mapper;

import com.example.entity.Category;

import java.util.List;

/**
 * 操作电影类别相关数据接口
 */
public interface CategoryMapper {
    /**
     * 查询所有
     */
    List<Category> selectAll(Category category);

    /**
     * 新增
     */
    void insert(Category category);

    /**
     * 修改
     */
    void updateById(Category category);

    /**
     * 删除
     */
    void deleteById(Integer id);
}
