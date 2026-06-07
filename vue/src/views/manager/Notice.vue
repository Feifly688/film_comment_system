<!--
*@
*@author Feiqi
*@date 2025/1/7 17:19
-->
<template>
    <div>
        <div>
            <div class="card" style="margin-bottom: 5px;">
                <el-input v-model="data.title" placeholder="请输入公告标题查询" style="width: 200px; margin-right: 10px" @keyup.enter.native="load"></el-input>
                <el-button type="primary" @click="load">
                    <el-icon>
                        <Search/>
                    </el-icon>
                </el-button>
                <el-button style="margin: 0 10px" type="info" @click="reset">重置</el-button>
            </div>
            <div class="card" style="margin-bottom: 10px">
                <el-button style="margin-bottom: 5px" type="primary" @click="handleAdd">新增</el-button>

                <el-table :data="data.tableData" stripe>
                    <!-- 添加序号列 -->
                    <el-table-column label="序号" type="index" width="80">
                        <template #default="scope">
                            {{ (data.pageNum - 1) * data.pageSize + scope.$index + 1 }}
                        </template>
                    </el-table-column>
                    <el-table-column label="公告标题" prop="title"/>
                    <el-table-column label="公告内容" prop="content"/>
                    <el-table-column label="发布时间" prop="time"/>
                    <el-table-column label="更新时间" prop="updateTime"/>
                    <el-table-column align="center" label="操作" width="160">
                        <template #default="scope">
                            <el-button type="primary" @click="handleEdit(scope.row)">编辑</el-button>
                            <el-button type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <div class="card">
                <el-pagination v-model:current-page="data.pageNum" v-model:page-size="data.pageSize" :total="data.total" layout="prev, pager, next, total" @current-change="load"/>
            </div>
        </div>
        <!--添加弹窗-->
        <el-dialog v-model="data.formVisible" title="系统公告" width="40%">
            <el-form :model="data.form" label-width="80px" style="padding-right: 20px" @submit.native.prevent>
                <el-form-item label="公告标题">
                    <el-input v-model="data.form.title" autocomplete="off" placeholder="请输入公告标题"/>
                </el-form-item>
                <el-form-item label="公告内容">
                    <el-input v-model="data.form.content" autocomplete="off" placeholder="请输入公告内容" type="textarea" @keyup.enter.native="save"/>
                </el-form-item>
            </el-form>
            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="data.formVisible = false">取 消</el-button>
                    <el-button type="primary" @click="save">
                        保 存
                    </el-button>
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
    tableData: [],
    total: 0,
    pageNum: 1,
    pageSize: 5,
    title: null,
    name: null,
    form: {},
    formVisible: false,
})
/*加载页面*/
const load = () => {
    request.get('/notice/selectPage', {
        params: {
            pageNum: data.pageNum,
            pageSize: data.pageSize,
            title: data.title,
        }
    }).then(res => {
        data.tableData = res.data.list
        data.total = res.data.total
    })
}

/*重置查询*/
const reset = () => {
    data.title = null
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
        request.delete('/notice/delete/' + id).then(res => {
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
        request.post('/notice/add', data.form).then(res => {
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
        request.put('/notice/update', data.form).then(res => {
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


load()
</script>

<style scoped>

</style>
