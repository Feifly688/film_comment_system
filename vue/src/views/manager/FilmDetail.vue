<!--
*@
*@author Feiqi
*@date 2025/1/10 16:51
-->
<template>
    <div>
        <div style="display: flex; align-items: flex-start;grid-gap: 10px;">
            <div style="flex: 1;width: 0">
                <div class="card" style="padding: 20px;">
                    <div style="display: flex;align-items: center;margin-bottom: 10px">
                        <div style="font-weight: bold;font-size: 20px;flex: 1;">
                            {{ data.film.name }}
                            <el-rate v-model="data.film.score" allow-half disabled show-score style="margin-left: 70px"></el-rate>
                        </div>
                        <div>
                            <el-button effect="dark" plain type="success" @click="writeComment('短评')">写短评</el-button>
                            <el-button effect="dark" plain type="warning" @click="writeComment('长评')">写影评</el-button>
                        </div>
                    </div>
                    <!---------------------------->
                    <!--展示电影信息-->
                    <div style="display: flex; flex-wrap: wrap; align-items: flex-start; margin-bottom: 20px;">
                        <el-image :preview-src-list="[data.film.cover]" :src="data.film.cover" preview-teleported style="width: 200px; height: auto; border-radius: 5px; margin-bottom: 10px;"></el-image>
                        <div style="flex: 1; color: #666; padding-left: 20px;">
                            <div style="margin: auto"><strong>导演：{{ data.film.director }}</strong></div>
                            <div style="margin: auto"><strong>主演：{{ data.film.actors }}</strong></div>
                            <div style="margin: auto"><strong>类型：{{ data.film.categoryName }}</strong></div>
                            <div style="margin: auto"><strong>制片国家/地区：{{ data.film.country }}</strong></div>
                            <div style="margin: auto"><strong>语言：{{ data.film.language }}</strong></div>
                            <div style="margin: auto"><strong>上映日期：{{ data.film.date }}</strong></div>
                            <div style="margin: auto"><strong>片长：{{ data.film.duration }}</strong></div>
                            <div style="margin: auto"><strong>IMDb：{{ data.film.imdb }}</strong></div>
                        </div>
                    </div>

                    <div style="font-size: 20px; font-weight: bold; color: #1967e3;margin-bottom: 10px">
                        {{ data.film.name }}的剧情简介：
                    </div>
                    <div style="text-align: justify;font-size: 15px;font-family: 'Microsoft YaHei UI';text-indent: 1rem;line-height: 24px">
                        {{ data.film.description }}
                    </div>
                </div>
                <!---------------------------->
                <!--展示短评-->
                <div class="card" style="padding: 20px;margin-bottom: 10px">
                    <div style="font-size: 20px; font-weight: bold; color: #239013;margin-bottom: 10px">
                        {{ data.film.name }}的短评<span style="font-size: 14px;color: #333">（共{{ data.totalShort }}条）</span>：
                    </div>
                    <div v-for="item in data.commentShortList" :key="item.id" style="border-bottom: 1px solid #eee;padding: 20px 0">
                        <div style="display: flex; align-items: center; margin-bottom: 10px">
                            <el-image :src="item.avatar ? item.avatar : 'http://localhost:9090/files/download/默认头像.jpg'" style="width: 30px;height: auto"></el-image>
                            <span style="margin-right: 10px;font-weight: bold">{{ item.userName }}</span>
                            <el-rate v-model="item.score" allow-half disabled style="margin: 0 10px"></el-rate>
                            <span style="color: #666;">{{ item.time }}</span>
                        </div>
                        <div style="line-height: 24px;color: #666;text-indent: 1rem;">
                            {{ item.comment }}
                        </div>
                    </div>
                    <el-pagination v-model:current-page="data.pageNumShort" v-model:page-size="data.pageSizeShort" :total="data.totalShort" layout="prev, pager, next, total" @current-change="loadShortComment"/>
                </div>
                <!---------------------------->
                <!--展示长评-->
                <div class="card" style="padding: 20px;">
                    <div style="font-size: 20px; font-weight: bold; color: #f8b34c;margin-bottom: 10px">
                        {{ data.film.name }}的长评<span style="font-size: 14px;color: #333">（共{{ data.totalLong }}条）</span>：
                    </div>
                    <div v-for="item in data.commentLongList" :key="item.id" style="border-bottom: 1px solid #eee;padding: 20px 0">
                        <div style="display: flex; align-items: center; margin-bottom: 10px">
                            <span style="margin-right: 10px;font-weight: bold">{{ item.userName }}</span>
                            <el-rate v-model="item.score" allow-half disabled style="margin: 0 10px"></el-rate>
                            <span style="color: #666;">{{ item.time }}</span>
                        </div>
                        <div class="line3" style="line-height: 24px;color: #666;margin-bottom: 5px;text-indent: 1rem;" v-html="item.comment"></div>
                        <div><span style="color: #1967e3;cursor: pointer" @click="viewLongComment(item.comment)">查看更多...</span></div>
                    </div>
                    <el-pagination v-model:current-page="data.pageNumLong" v-model:page-size="data.pageSizeLong" :total="data.totalLong" layout="prev, pager, next, total" @current-change="loadLongComment"/>
                </div>
                <!---------------------------->
            </div>
            <!--推荐电影-->
            <div class="card" style="width: 300px;align-items: center;padding: 20px;margin-bottom: 10px">
                <div style="font-size: 20px;font-family: 'Microsoft YaHei',serif">推荐电影</div>
                <div v-for="item in data.recommendFilms" :key="item.id" style="margin-bottom: 20px;cursor: pointer" @click="getDetail(item.id)">
                    <el-image :src="item.cover" alt="" style="width: 100%;height: auto;margin-bottom: 5px;border-radius: 5px"></el-image>
                    <div style="font-size: 18px">{{ item.name }}</div>
                    <el-rate v-model="item.score" allow-half disabled show-score></el-rate>
                </div>
            </div>
            <!---------------------------->
        </div>
        <!--写评论弹窗-->
        <el-dialog v-model="data.formVisible" title="写评论" width="40%">
            <el-form :model="data.form" label-width="80px" style="padding-right: 20px" @submit.native.prevent>
                <el-form-item label="评分">
                    <el-rate v-model="data.form.score" allow-half show-score></el-rate>
                </el-form-item>
                <el-form-item v-if="data.form.type === '短评'" label="评论内容">
                    <el-input v-model="data.form.comment" :rows="5" autocomplete="off" placeholder="请输入评论内容" type="textarea" @keyup.enter.native="save"/>
                </el-form-item>
                <el-form-item v-if="data.form.type === '长评'" label="评论内容" prop="comment">
                    <div style="border: 1px solid #ccc;width: 100%">
                        <Toolbar
                                :editor="editorRef"
                                :mode="mode"
                                style="border-bottom: 1px solid #ccc"
                        />
                        <Editor
                                v-model="data.form.comment"
                                :defaultConfig="editorConfig"
                                :mode="mode"
                                style="height: 500px; overflow-y: hidden;"
                                @onCreated="handleCreated"
                        />
                    </div>
                </el-form-item>
            </el-form>
            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="data.formVisible = false">取 消</el-button>
                    <el-button type="primary" @click="save">保 存</el-button>
                </div>
            </template>
        </el-dialog>

        <!--显示完整长评-->
        <el-dialog v-model="data.formVisibleView" title="长评" width="50%">
            <div style="line-height: 24px">
                <div class="longComment" v-html="data.comment"></div>
            </div>
            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="data.formVisibleView = false">关 闭</el-button>
                </div>
            </template>
        </el-dialog>
    </div>
</template>

<script setup>
import request from "../../utils/request";
import router from "../../router";
import {ElMessage} from "element-plus";
import '@wangeditor/editor/dist/css/style.css'
import {onBeforeUnmount, ref, shallowRef} from "vue";
import {Editor, Toolbar} from '@wangeditor/editor-for-vue'

const data = reactive({
    id: router.currentRoute.value.query.id,
    film: {},
    recommendFilms: [],
    formVisible: false,
    form: {},
    pageNumShort: 1,
    pageSizeShort: 3,
    totalShort: 0,
    commentShortList: [],
    user: JSON.parse(localStorage.getItem('currentUser') || '{}'),
    pageNumLong: 1,
    pageSizeLong: 2,
    totalLong: 0,
    commentLongList: [],
    formVisibleView: false,
    comment: null
})
/* wangEditor5 初始化开始 */
const baseUrl = import.meta.env.VITE_BASE_URL
const editorRef = shallowRef()  // 编辑器实例，必须用 shallowRef
const mode = 'default'
const editorConfig = {MENU_CONF: {}}
// 图片上传配置
editorConfig.MENU_CONF['uploadImage'] = {
    server: baseUrl + '/files/wang/upload',  // 服务端图片上传接口
    fieldName: 'file'  // 服务端图片上传接口参数
}
// 组件销毁时，也及时销毁编辑器，否则可能会造成内存泄漏
onBeforeUnmount(() => {
    const editor = editorRef.value
    if (editor == null) return
    editor.destroy()
})
// 记录 editor 实例，重要！
const handleCreated = (editor) => {
    editorRef.value = editor
}
/* wangEditor5 初始化结束 */

request.get('/film/selectById/' + data.id).then(res => {
    data.film = res.data
})
/*获取推荐电影*/
request.get('/film/selectRecommend/' + data.id).then(res => {
    data.recommendFilms = res.data
})
/*点击查看电影详情*/
const getDetail = (id) => {
    location.href = '/filmdetail?id=' + id;
}
/*写评论*/
const writeComment = (type) => {
    data.form = {type: type}
    data.formVisible = true
}

/*新增评论*/
const save = () => {
    data.form.filmId = data.id
    data.form.userId = data.user.id
    request.post('/comment/add', data.form).then(res => {
        if (res.code === '200') {
            loadShortComment()
            loadLongComment()
            ElMessage.success('评论成功！')
            data.formVisible = false
        } else {
            ElMessage.error(res.msg)
        }
    })
}

/*加载短评*/
const loadShortComment = () => {
    request.get('/comment/selectPage', {
        params: {
            pageNum: data.pageNumShort,
            pageSize: data.pageSizeShort,
            filmId: data.id,
            type: '短评',
        }
    }).then(res => {
        data.commentShortList = res.data.list
        data.totalShort = res.data.total
    })
}
/*加载长评*/
const loadLongComment = () => {
    request.get('/comment/selectPage', {
        params: {
            pageNum: data.pageNumLong,
            pageSize: data.pageSizeLong,
            filmId: data.id,
            type: '长评',
        }
    }).then(res => {
        data.commentLongList = res.data.list
        data.totalLong = res.data.total
    })
}
/*查看长评*/
const viewLongComment = (comment) => {
    data.comment = comment
    data.formVisibleView = true
}
loadShortComment()
loadLongComment()

/*禁止回退*/
window.location.hash="no-back";
window.location.hash="Again-No-back-button";
window.onhashchange=function(){window.location.hash="no-back";}
</script>

<style scoped>
</style>
