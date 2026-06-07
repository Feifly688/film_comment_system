/**
 * CommentService
 *
 * @author Feiqi
 * @date 2025/1/9  13:32
 */
package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Comment;
import com.example.mapper.CommentMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 电影评论业务处理
 **/
@Service
public class CommentService {
    @Resource
    private CommentMapper commentMapper;

    /**
     * 查询所有公告
     */
    public List<Comment> selectAll(Comment comment) {
        return commentMapper.selectAll(comment);
    }

    /**
     * 分页
     *
     * @param pageNum  当前页码
     * @param pageSize 当前页面大小
     * @return 分页对象（包含数据和分页参数）
     */
    public PageInfo<Comment> selectPage(Comment comment, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> list = commentMapper.selectAll(comment);
        return PageInfo.of(list);
    }

    /**
     * 新增
     */
    public void add(Comment comment) {
comment.setTime(DateUtil.now());
        commentMapper.insert(comment);
    }

    /**
     * 修改
     */
    public void updateById(Comment comment) {
        commentMapper.updateById(comment);
    }

    /**
     * 根据id删除
     */
    public void deleteById(Integer id) {
        commentMapper.deleteById(id);
    }
}
