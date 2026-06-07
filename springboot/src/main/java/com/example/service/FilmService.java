/**
 * FilmService
 *
 * @author Feiqi
 * @date 2025/1/9  9:46
 */
package com.example.service;

import com.example.entity.Film;
import com.example.mapper.CommentMapper;
import com.example.mapper.FilmMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 电影信息业务处理
 **/
@Service
public class FilmService {
    @Resource
    private FilmMapper filmMapper;

    @Resource
    private CommentMapper commentMapper;

    /**
     * 查询所有公告
     */
    public List<Film> selectAll(Film film) {
        return filmMapper.selectAll(film);
    }

    /**
     * 分页
     *
     * @param pageNum  当前页码
     * @param pageSize 当前页面大小
     * @return 分页对象（包含数据和分页参数）
     */
    public PageInfo<Film> selectPage(Film film, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Film> list = filmMapper.selectAll(film);
        for (Film f : list) {
            /*查询当前电影的评论数量*/
            this.setScore(f);

        }
        return PageInfo.of(list);
    }

    /**
     * 推荐三部电影
     *
     * @return
     */
    public List<Film> selectRecommend(Integer filmId) {
        List<Film> films = this.selectAll(null);
        /*排除当前电影*/
        films = films.stream().filter(film -> !film.getId().equals(filmId)).collect(Collectors.toList());
        Collections.shuffle(films);
        /*随机推荐电影*/
        List<Film> filmList = films.stream().limit(3).collect(Collectors.toList());
        for (Film film : filmList) {
            this.setScore(film);
        }
        return filmList;
    }

    public List<Film> selectRank() {
        List<Film> filmList = filmMapper.selectAll(null);
        for (Film film : filmList) {
            setScore(film);
        }
        return filmList.stream().sorted((f1, f2) -> f2.getScore().compareTo(f1.getScore())).limit(5).collect(Collectors.toList());
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    public Film selectById(Integer id) {
        Film film = filmMapper.selectById(id);
        this.setScore(film);
        return film;
    }

    /**
     * 新增
     */
    public void add(Film film) {
        filmMapper.insert(film);
    }

    /**
     * 修改
     */
    public void updateById(Film film) {
        filmMapper.updateById(film);
    }

    /**
     * 根据id删除
     */
    public void deleteById(Integer id) {
        filmMapper.deleteById(id);
    }


    /*封装获得分数方法*/
    private void setScore(Film film) {
        int total = commentMapper.selectTotal(film.getId());
        film.setCommentNum(total);
        if (total == 0) {
            film.setScore(0D);
        } else {
            double sum = commentMapper.selectSum(film.getId());
            /*计算电影平均分*/
            BigDecimal score = BigDecimal.valueOf(sum).divide(BigDecimal.valueOf(total), 1, BigDecimal.ROUND_HALF_UP);/*Java21用不了RoundingMode.HALF_UP*/
            film.setScore(score.doubleValue());
        }
    }


}
