/**
 * FilmController
 *
 * @author Feiqi
 * @date 2025/1/9  9:44
 */
package com.example.controller;

import com.example.common.Result;
import com.example.entity.Film;
import com.example.service.FilmService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 电影信息前端操作接口
 **/
@RestController
@RequestMapping("/film")
public class FilmController {
    @Resource
    private FilmService filmService;

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Film film) {
        List<Film> list = filmService.selectAll(film);
        return Result.success(list);
    }

    @GetMapping("/selectRecommend/{id}")
    public Result selectRecommend(@PathVariable Integer id) {
        List<Film> list = filmService.selectRecommend(id);
        return Result.success(list);
    }

    @GetMapping("/selectRank")
    public Result selectRank() {
        List<Film> list = filmService.selectRank();
        return Result.success(list);
    }
    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Film film = filmService.selectById(id);
        return Result.success(film);
    }

    /**
     * 分页模糊查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Film film,
                             @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Film> pageInfo = filmService.selectPage(film, pageNum, pageSize);
        return Result.success(pageInfo);
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Film film) {
        filmService.add(film);
        return Result.success();
    }

    /**
     * 更新
     *
     * @param film
     * @return
     */
    @PutMapping("/update")
    public Result update(@RequestBody Film film) {
        filmService.updateById(film);
        return Result.success();
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        filmService.deleteById(id);
        return Result.success();
    }


}
