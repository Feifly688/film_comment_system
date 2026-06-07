<!--
*@
*@author Feiqi
*@date 2025/1/9 9:56
-->
<template>
    <div>
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
            <div class="card" style="margin-bottom: 10px">
                <el-button style="margin-bottom: 5px" type="primary" @click="handleAdd">新增</el-button>

                <el-table :data="data.tableData" stripe>
                    <!-- 添加序号列 -->
                    <el-table-column label="序号" type="index" width="80">
                        <template #default="scope">
                            {{ (data.pageNum - 1) * data.pageSize + scope.$index + 1 }}
                        </template>
                    </el-table-column>
                    <el-table-column label="电影名称" prop="name"/>
                    <el-table-column label="电影封面" prop="cover">
                        <template #default="scope">
                            <el-image :preview-src-list="[scope.row.cover]" :src="scope.row.cover" preview-teleported style="width: 40px; height: 40px; border-radius: 20%"></el-image>
                        </template>
                    </el-table-column>
                    <el-table-column label="电影描述" prop="description" show-overflow-tooltip/>
                    <el-table-column label="年份" prop="year"/>
                    <el-table-column label="导演" prop="director"/>
                    <el-table-column label="演员" prop="actors" show-overflow-tooltip/>
                    <el-table-column label="电影类别" prop="categoryName"/>
                    <el-table-column label="国家" prop="country"/>
                    <el-table-column label="语言" prop="language"/>
                    <el-table-column label="上映日期" prop="date"/>
                    <el-table-column label="片长" prop="duration"/>
                    <el-table-column label="IMDb" prop="imdb"/>
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
        <el-dialog v-model="data.formVisible" destroy-on-close title="电影信息" width="40%"> <!--[[destroy-on-close]] 关闭时清除对话框-->
            <el-form
                    :model="data.form"
                    label-width="80px" style="padding-right: 20px" @submit.native.prevent> <!--[[@submit.native.prevent]]解决input时按回车直接刷新界面-->
                <el-form-item label="电影名">
                    <el-input v-model="data.form.name" autocomplete="off" placeholder="请输入电影名" @keyup.enter.native="save"/>
                </el-form-item>
                <el-form-item label="电影封面" prop="cover">
                    <el-upload :action="uploadUrl" :on-success="handleImgSuccess" list-type="picture">
                        <el-button type="primary">上传图片</el-button>
                    </el-upload>
                </el-form-item>
                <el-form-item label="电影描述">
                    <el-input v-model="data.form.description" :rows="5" autocomplete="off" placeholder="请输入电影描述" type="textarea"/>
                </el-form-item>
                <el-form-item label="电影年份">
                    <el-input v-model="data.form.year" autocomplete="off" placeholder="请输入电影年份"/>
                </el-form-item>
                <el-form-item label="导演">
                    <el-input v-model="data.form.director" autocomplete="off" placeholder="请输入导演"/>
                </el-form-item>
                <el-form-item label="演员">
                    <el-input v-model="data.form.actors" :rows="3" autocomplete="off" placeholder="请输入演员" type="textarea"/>
                </el-form-item>
                <el-form-item label="电影类别">
                    <el-select v-model="data.form.categoryId" placeholder="请选择电影类别">
                        <el-option label="喜剧" value="1"></el-option>
                        <el-option label="动作" value="2"></el-option>
                        <el-option label="爱情" value="3"></el-option>
                        <el-option label="科幻" value="4"></el-option>
                        <el-option label="动画" value="5"></el-option>
                        <el-option label="恐怖" value="6"></el-option>
                        <el-option label="战争" value="7"></el-option>
                        <el-option label="冒险" value="8"></el-option>
                        <el-option label="剧情" value="9"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="制片国家/地区" label-width="105px" style="margin-left: -20px">
                    <el-input v-model="data.form.country" autocomplete="off" placeholder="请输入制片国家/地区"/>
                </el-form-item>
                <el-form-item label="语言">
                    <el-input v-model="data.form.language" autocomplete="off" placeholder="请输入语言"/>
                </el-form-item>
                <el-form-item label="上映日期">
                    <el-date-picker
                            v-model="data.form.date"
                            :shortcuts="shortcuts"
                            format="YYYY/MM/DD"
                            placeholder="请选择上映日期"
                            type="date"
                            value-format="YYYY-MM-DD"
                    />
                </el-form-item>
                <el-form-item label="片长">
                    <el-input v-model="data.form.duration" autocomplete="off" placeholder="请输入片长"/>
                </el-form-item>
                <el-form-item label="IMDb">
                    <el-input v-model="data.form.imdb" autocomplete="off" placeholder="请输入IMDb" @keyup.enter.native="save"/>
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

/*文件上传的接口地址*/
const uploadUrl = import.meta.env.VITE_BASE_URL + '/files/upload'

const data = reactive({
    tableData: [],
    total: 0,
    pageNum: 1,
    pageSize: 15,
    name: null,
    form: {},
    formVisible: false,
})
/*加载页面*/
const load = () => {
    request.get('/film/selectPage', {
        params: {
            pageNum: data.pageNum,
            pageSize: data.pageSize,
            name: data.name,
        }
    }).then(res => {
        data.tableData = res.data.list
        data.total = res.data.total
    })
}

/*重置查询*/
const reset = () => {
    data.name = null
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
        request.delete('/film/delete/' + id).then(res => {
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
    if (data.form.name != null && data.form.name !== "") {
        request.post('/film/add', data.form).then(res => {
            if (res.code === '200') {
                load()
                ElMessage.success('添加成功！')
                data.formVisible = false
            } else {
                ElMessage.error(res.msg)
            }
        })
    } else {
        ElMessage.warning("电影名不能为空！")
    }
}
/*编辑保存*/
const update = () => {
    if (data.form.name != null && data.form.name !== "") {
        request.put('/film/update', data.form).then(res => {
            if (res.code === '200') {
                load()
                ElMessage.success('修改成功！')
                data.formVisible = false
            } else {
                ElMessage.error(res.msg)
            }
        })
    } else {
        ElMessage.warning("电影名不能为空！")
    }
}
/*处理文件上传的钩子*/
const handleImgSuccess = (res) => {
    data.form.cover = res.data  // res.data就是文件上传返回的文件路径，获取到路径后赋值表单的属性
}

/*选择上映日期时的快捷选择*/
const shortcuts = [
    {
        text: '今天',
        value: new Date(),
    },
    {
        text: '昨天',
        value: () => {
            const date = new Date()
            date.setDate(date.getDate() - 1)
            return date
        },
    },
    {
        text: '一周前',
        value: () => {
            const date = new Date()
            date.setDate(date.getDate() - 7)
            return date
        },
    },
]
load()
</script>
<style scoped>
</style>
