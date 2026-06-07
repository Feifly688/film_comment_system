/**
 * CategoryService
 *
 * @author Feiqi
 * @date 2025/1/8  18:45
 */
package com.example.service;

import com.example.entity.Category;
import com.example.mapper.CategoryMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 电影类别业务处理
 **/
@Service
public class CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 查询所有公告
     */
    public List<Category> selectAll(Category category) {
        return categoryMapper.selectAll(category);
    }

    /**
     * 分页
     *
     * @param pageNum  当前页码
     * @param pageSize 当前页面大小
     * @return 分页对象（包含数据和分页参数）
     */
    public PageInfo<Category> selectPage(Category category, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Category> list = categoryMapper.selectAll(category);
        return PageInfo.of(list);
    }

    /**
     * 新增
     */
    public void add(Category category) {
        categoryMapper.insert(category);
    }

    /**
     * 修改
     */
    public void updateById(Category category) {
        categoryMapper.updateById(category);
    }

    /**
     * 根据id删除
     */
    public void deleteById(Integer id) {
        categoryMapper.deleteById(id);
    }
}

