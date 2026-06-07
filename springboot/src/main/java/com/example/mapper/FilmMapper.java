/**
 * FilmMapper
 *
 * @author Feiqi
 * @date 2025/1/9  9:47
 */

package com.example.mapper;

import com.example.entity.Film;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 操作电影信息相关数据接口
 */
public interface FilmMapper {
    /**
     * 查询所有
     */
    List<Film> selectAll(Film film);

    /**
     * 新增
     */
    void insert(Film film);

    /**
     * 修改
     */
    void updateById(Film film);

    /**
     * 删除
     */
    void deleteById(Integer id);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    Film selectById(Integer id);
}
