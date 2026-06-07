<!--
*@
*@author Feiqi
*@date 2025/1/10 9:29
-->
<template>
    <div>
        <div class="card" style="margin-bottom: 5px;">
            <el-input v-model="data.name" placeholder="请输入电影名称查询" style="width: 200px; margin-right: 10px" @keyup.enter.native="load"></el-input>
            <el-button type="primary" @click="load">
                <el-icon>
                    <Search/>
                </el-icon>
            </el-button>
            <el-button style="margin: 0 10px" type="info" @click="reset">重置</el-button>
        </div>

        <div class="card" style="margin-bottom: 5px">
            <el-button :class="{ 'film-active':data.categoryId === null }" @click="loadFilmByCategory(null)">
                全部
            </el-button>
            <el-button v-for="item in data.categoryList" :key="item.id" :class="{ 'film-active':data.categoryId === item.id }" @click="loadFilmByCategory(item.id)">
                {{ item.name }}
            </el-button>
        </div>

        <div class="card" style="margin-bottom: 5px">
            <el-row :gutter="20">
                <el-col v-for="item in data.tableData" :key="item.id" :span="6" style="cursor: pointer; margin-bottom: 10px" @click="getDetail(item.id)">
                    <img :src="item.cover" alt="" style="width: 100%; height: 600px;border-radius: 5px;border: 3px dashed #666">
                    <div class="line1" style="margin:10px 0px;font-size: 18px">{{ item.name }}</div>
                    <div style="margin: 5px 0px;display: flex">
                        <el-rate v-model="item.score" allow-half disabled show-score/>
                        <div style="flex: 1;text-align: right;color: #1967e3;margin-top: 7px;margin-right: 10px">
                            {{ item.commentNum }}人评论
                        </div>
                    </div>
                    <div class="line3" style="font-size: 13px; color: #666">
                        {{ item.description }}
                    </div>
                </el-col>
            </el-row>
        </div>

        <div class="card">
            <el-pagination v-model:current-page="data.pageNum" v-model:page-size="data.pageSize" :total="data.total" layout="prev, pager, next, total" @current-change="load"/>
        </div>
    </div>
</template>

<script setup>

import request from "../../utils/request";

const data = reactive({
    name: null,
    pageNum: 1,
    pageSize: 8,
    tableData: [],
    total: 0,
    categoryList: [],
    categoryId: null,
})

const loadCategory = () => {
    request.get('/category/selectAll').then(res => {
        data.categoryList = res.data
    })
}

const loadFilmByCategory = (categoryId) => {
    data.categoryId = categoryId
    load()
}
const getDetail = (id) => {
    location.href = '/filmdetail?id=' + id;
}
const load = () => {
    request.get('/film/selectPage', {
        params: {
            pageNum: data.pageNum,
            pageSize: data.pageSize,
            name: data.name,
            categoryId: data.categoryId,
        }
    }).then(res => {
        data.tableData = res.data.list
        data.total = res.data.total
    })
}
const reset = () => {
    data.name = null
    load()
}

loadCategory()
load()
</script>

<style scoped>
.film-active {
    background-color: #1967e3;
    color: white;
}
</style>
