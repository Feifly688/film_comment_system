/**
 * NoticeMapper
 *
 * @author Feiqi
 * @date 2025/1/7  14:13
 */

package com.example.mapper;

import com.example.entity.Notice;

import java.util.List;

/**
 * 操作公告相关数据接口
 */
public interface NoticeMapper {
    /**
     * 查询所有
     */
    List<Notice> selectAll(Notice notice);

    /**
     * 新增
     */
    void insert(Notice notice);

    /**
     * 修改
     */
    void updateById(Notice notice);

    /**
     * 删除
     */
    void deleteById(Integer id);
}
