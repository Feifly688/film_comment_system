/**
 * CommentMapper
 *
 * @author Feiqi
 * @date 2025/1/9  13:32
 */

package com.example.mapper;

import com.example.entity.Comment;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 操作电影评论相关数据接口
 */
public interface CommentMapper {
    /**
     * 查询所有
     */
    List<Comment> selectAll(Comment comment);

    /**
     * 新增
     */
    void insert(Comment comment);

    /**
     * 修改
     */
    void updateById(Comment comment);

    /**
     * 删除
     */
    void deleteById(Integer id);

    /**
     * 当前电影的评论数量
     *
     * @param filmId
     * @return
     */
    @Select("select count(*) from comment where film_id = #{filmId}")
    int selectTotal(Integer filmId);

    /**
     * 当前电影总评分
     *
     * @param filmId
     * @return
     */
    @Select("select sum(score) from comment where film_id = #{filmId}")
    double selectSum(Integer filmId);
}
