<template>
    <div style="display: flex;grid-gap: 10px">
        <div class="card" style="width: 50%;padding: 20px">
            <div style="margin: 20px 0; font-size: 20px; font-weight: bold">电影评分榜</div>
            <div v-for="(item, index) in data.filmList" :key="item.id">
                <div style="display: flex;grid-gap: 10px;margin-bottom: 20px;border-top: 1px dashed #666;border-bottom: 1px dashed #666;padding: 10px 10px;">
                    <div style="padding-top: 10px">
                        <div v-if="index === 0" style="font-weight: bold;font-size: 18px;color: gold">1</div>
                        <div v-else-if="index === 1" style="font-weight: bold;font-size: 18px;color: silver">2</div>
                        <div v-else-if="index === 2" style="font-weight: bold;font-size: 18px;color: chocolate">3</div>
                        <div v-else style="font-weight: bold;font-size: 18px;color: #333">{{ index + 1 }}</div>
                    </div>
                    <el-image :src="item.cover" alt="" style="cursor: pointer;width: 20%;height: auto" @click="getDetail(item.id)"></el-image>
                    <div style="flex: 1">
                        <div><span class="span" style="font-size: 20px;cursor: pointer;color: #3377a9;" @click="getDetail(item.id)">{{ item.name }}</span></div>
                        <div style="color: #666;margin-bottom: 10px;margin-top: 10px">
                            {{ item.date }}({{ item.country }}) / {{ item.actors }} / {{ item.language }}
                        </div>
                        <div class="line3" style="color: #666;margin-bottom: 10px">{{ item.description }}</div>
                        <div style="display: flex; align-items: center">
                            <el-rate v-model="item.score" :max="10" allow-half disabled show-score></el-rate>
                            <span style="margin-left: 5px">（{{ item.commentNum }})人评价</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--公告部分-->
        <div style="width: 50%;padding: 20px">
            <div style="margin: 20px 0; padding-left: 50px; font-size: 20px; font-weight: bold">系统公告</div>
            <el-timeline>
                <el-timeline-item v-for="item in data.noticeList" :key="item.id" :timestamp="item.time" placement="top">
                    <div class="card">
                        <div style="font-size: 16px;font-weight: bold">{{ item.title }}</div>
                        <div style="color: #6d1f1f">{{ item.content }}</div>
                    </div>
                </el-timeline-item>
            </el-timeline>
        </div>
    </div>
</template>
<script lang="ts">

</script>
<script setup>
import {reactive} from "vue";
import request from "@/utils/request";
import router from "../../router";

const data = reactive({
    id: router.currentRoute.value.query.id,
    user: JSON.parse(localStorage.getItem('currentUser') || '{}'),
    noticeList: [],
    filmList: [],
})
request.get('/notice/selectAll').then(res => {
    data.noticeList = res.data
})

request.get('/film/selectRank').then(res => {
    data.filmList = res.data
})

const getDetail = (id) => {
    location.href = '/filmdetail?id=' + id;
}

/*禁止回退*/
window.location.hash="no-back";
window.location.hash="Again-No-back-button";
window.onhashchange=function(){window.location.hash="no-back";}

</script>

<style scoped>
.span:hover {
    background-color: #3377aa;
    color: #ffffff !important;
}
</style>
