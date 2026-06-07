<!--
*@
*@author Feiqi
*@date 2025/1/9 13:27
-->
<template>
    <div>
        <div>
            <div class="card" style="margin-bottom: 5px;">
                <el-input v-model="data.filmName" placeholder="请输入电影名称查询" style="width: 200px; margin-right: 10px" @keyup.enter.native="load"></el-input>
                <el-button type="primary" @click="load">
                    <el-icon>
                        <Search/>
                    </el-icon>
                </el-button>
                <el-button style="margin: 0 10px" type="info" @click="reset">重置</el-button>
            </div>
            <div class="card" style="margin-bottom: 10px">
                <el-table :data="data.tableData" stripe>
                    <el-table-column label="电影名称" prop="filmName">
                    </el-table-column>
                    <el-table-column label="电影评分" prop="score">
                        <template #default="scope">
                            <el-rate v-model="scope.row.score" allow-half disabled/>
                        </template>
                    </el-table-column>
                    <el-table-column label="电影评论" prop="comment">
                        <template #default="scope">
                            <el-button @click="preview(scope.row.comment)">查看评论</el-button>
                        </template>
                    </el-table-column>
                    <el-table-column label="评论用户" prop="userName"/>
                    <el-table-column label="评论时间" prop="time"/>
                    <el-table-column label="评论类型" prop="type">
                        <template #default="scope">
                            <el-tag v-if="scope.row.type==='短评'" effect="dark" type="success">短评</el-tag>
                            <el-tag v-if="scope.row.type==='长评'" effect="dark" type="warning">长评</el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column align="center" label="操作" width="160">
                        <template #default="scope">
                            <el-button type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <div class="card">
                <el-pagination v-model:current-page="data.pageNum" v-model:page-size="data.pageSize" :total="data.total" layout="prev, pager, next, total" @current-change="load"/>
            </div>
        </div>
        <!--显示完整评论-->
        <el-dialog v-model="data.formVisibleComment" title="电影评论" width="40%">
            <div class="longComment" v-html="data.commentContent"></div>
            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="data.formVisibleComment = false">关 闭</el-button>
                </div>
            </template>
        </el-dialog>
    </div>
</template>

<script setup>
import {reactive} from "vue";
import request from "../../utils/request";
import {ElMessage, ElMessageBox} from "element-plus";

const data = reactive({
    user: JSON.parse(localStorage.getItem('currentUser') || '{}'),
    tableData: [],
    total: 0,
    pageNum: 1,
    pageSize: 10,
    form: {},
    filmName: null,
    formVisible: false,
    formVisibleComment: false,
    commentContent: null,
})
/*加载页面*/
const load = () => {
    if (data.user.role === '管理员') {
        request.get('/comment/selectPage', {
            params: {
                pageNum: data.pageNum,
                pageSize: data.pageSize,
                filmName: data.filmName,
                userId: null
            }
        }).then(res => {
            data.tableData = res.data.list
            data.total = res.data.total
        })
    } else {
        request.get('/comment/selectPage', {
            params: {
                pageNum: data.pageNum,
                pageSize: data.pageSize,
                filmName: data.filmName,
                userId: data.user.id
            }
        }).then(res => {
            data.tableData = res.data.list
            data.total = res.data.total
        })
    }
}

/*重置查询*/
const reset = () => {
    data.filmName = null
    load()
}

/*弹窗保存*/
const save = () => {
    /*有id就是更新，没有就是新增*/
    data.form.id ? update() : add()
}
/*新增*/
const handleAdd = () => {
    data.form = {}
    data.formVisible = true
}
/*编辑*/
const handleEdit = (row) => {
    data.form = JSON.parse(JSON.stringify(row))
    data.formVisible = true
}
/*删除*/
const handleDelete = (id) => {
    ElMessageBox.confirm('删除后数据无法恢复，您确定删除吗?', '删除确认', {type: 'warning'}).then(res => {
        request.delete('/comment/delete/' + id).then(res => {
            if (res.code === '200') {
                load()
                ElMessage.success('删除成功！')
            } else {
                ElMessage.error(res.msg)
            }
        })
    }).catch(err => {
    })
}
/*新增保存*/
const add = () => {
    if (data.form.title != null && data.form.title !== "") {
        request.post('/comment/add', data.form).then(res => {
            if (res.code === '200') {
                load()
                ElMessage.success('添加成功！')
                data.formVisible = false
            } else {
                ElMessage.error(res.msg)
            }
        })
    } else {
        ElMessage.warning("标题不能为空！")
    }
}
/*编辑保存*/
const update = () => {
    if (data.form.title != null && data.form.title !== "") {
        request.put('/comment/update', data.form).then(res => {
            if (res.code === '200') {
                load()
                ElMessage.success('修改成功！')
                data.formVisible = false
            } else {
                ElMessage.error(res.msg)
            }
        })
    } else {
        ElMessage.warning("标题不能为空！")
    }
}
/*查看评论*/
const preview = (comment) => {
    data.commentContent = comment
    data.formVisibleComment = true
}
load()
</script>

<style scoped>

</style>

