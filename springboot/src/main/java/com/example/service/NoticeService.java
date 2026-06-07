/**
 * NoticeService
 *
 * @author Feiqi
 * @date 2025/1/7  14:04
 */
package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Notice;
import com.example.mapper.NoticeMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 公告业务处理
 */
@Service
public class NoticeService {
    @Resource
    private NoticeMapper noticeMapper;

    /**
     * 查询所有公告
     */
    public List<Notice> selectAll(Notice notice) {
        return noticeMapper.selectAll(notice);
    }

    /**
     * 分页
     *
     * @param pageNum  当前页码
     * @param pageSize 当前页面大小
     * @return 分页对象（包含数据和分页参数）
     */
    public PageInfo<Notice> selectPage(Notice notice, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Notice> list = noticeMapper.selectAll(notice);
        return PageInfo.of(list);
    }

    /**
     * 新增
     */
    public void add(Notice notice) {
        notice.setTime(DateUtil.now());
        noticeMapper.insert(notice);
    }

    /**
     * 修改
     */
    public void updateById(Notice notice) {
        notice.setUpdateTime(DateUtil.now());
        noticeMapper.updateById(notice);
    }

    /**
     * 根据id删除
     */
    public void deleteById(Integer id) {
        noticeMapper.deleteById(id);
    }
}
