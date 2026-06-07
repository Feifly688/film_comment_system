<template>
    <div>
        <div style="height: 60px; background-color: #fff; display: flex; align-items: center; border-bottom: 1px solid #ddd">
            <div style="flex: 1">
                <div style="padding-left: 20px; display: flex; align-items: center">
                    <img alt="" src="../assets/imgs/logo.png" style="width: 40px">
                    <div style="font-weight: bold; font-size: 24px; margin-left: 5px;color: #7f7fd5">电影评论系统</div>
                </div>
            </div>
            <div style="width: fit-content; padding-right: 10px; display: flex; align-items: center;">
                <el-dropdown>
                    <el-button style="background-color: transparent;border-color: transparent">
                        <img :src="data.user.avatar || 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'" alt="" style="width: 30px; height: 30px; border-radius: 50%">
                        <span style="margin-left: 5px">{{ data.user.name }}</span>
                        <el-icon class="el-icon--right">
                            <arrow-down/>
                        </el-icon>
                    </el-button>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </div>
        </div>

        <div style="display: flex">
            <div style="width: 200px; border-right: 1px solid #ddd; min-height: calc(100vh - 60px)">
                <el-menu
                        :default-active="router.currentRoute.value.path"
                        :default-openeds="['1','2']"
                        router
                        style="border: none"
                >
                    <el-menu-item index="/home">
                        <el-icon>
                            <HomeFilled/>
                        </el-icon>
                        <span>系统首页</span>
                    </el-menu-item>
                    <el-menu-item index="/filmview">
                        <el-icon>
                            <VideoCameraFilled/>
                        </el-icon>
                        <span>电影列表</span>
                    </el-menu-item>
                    <el-menu-item index="/comment">
                        <el-icon>
                            <EditPen/>
                        </el-icon>
                        <span>电影评论</span>
                    </el-menu-item>
                    <el-sub-menu v-if="data.user.role === '管理员'" index="1">
                        <template #title>
                            <el-icon>
                                <Menu/>
                            </el-icon>
                            <span>信息管理</span>
                        </template>
                        <el-menu-item index="/notice">
                            <el-icon>
                                <Bell/>
                            </el-icon>
                            <span>公告信息</span>
                        </el-menu-item>
                        <el-menu-item index="/category">
                            <el-icon>
                                <Ticket/>
                            </el-icon>
                            <span>电影分类</span>
                        </el-menu-item>
                        <el-menu-item index="/film">
                            <el-icon>
                                <Film/>
                            </el-icon>
                            <span>电影信息</span>
                        </el-menu-item>
                    </el-sub-menu>

                    <el-sub-menu v-if="data.user.role==='管理员'" index="2">
                        <template #title>
                            <el-icon>
                                <Memo/>
                            </el-icon>
                            <span>用户管理</span>
                        </template>
                        <el-menu-item index="/admin">
                            <el-icon>
                                <Avatar/>
                            </el-icon>
                            <span>管理员信息</span>
                        </el-menu-item>
                        <el-menu-item index="/user">
                            <el-icon>
                                <User/>
                            </el-icon>
                            <span>普通用户信息</span>
                        </el-menu-item>
                    </el-sub-menu>
                    <el-menu-item index="/person">
                        <el-icon>
                            <User/>
                        </el-icon>
                        <span>个人资料</span>
                    </el-menu-item>
                    <el-menu-item index="/password">
                        <el-icon>
                            <Lock/>
                        </el-icon>
                        <span>修改密码</span>
                    </el-menu-item>
                    <el-menu-item @click="logout">
                        <el-icon>
                            <SwitchButton/>
                        </el-icon>
                        <span>退出系统</span>
                    </el-menu-item>
                </el-menu>
            </div>

            <div style="flex: 1; width: 0; background-color: #f8f8ff; padding: 10px">
                <router-view @updateUser="updateUser"/>
            </div>
        </div>

    </div>
</template>

<script setup lang="ts">
import {reactive,onMounted,onBeforeUnmount} from "vue";
import router from "../router";
import {ElMessage, ElMessageBox} from "element-plus";

const data = reactive({
    user: JSON.parse(localStorage.getItem('currentUser') || '{}')
})

if (!data.user?.id) {
    ElMessage.error('请登录！')
    router.push('/login')
}

const updateUser = () => {
    data.user = JSON.parse(localStorage.getItem('currentUser') || '{}')
}

const logout = () => {
    ElMessageBox.confirm('确定要退出系统吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
    }).then(() => {
        ElMessage.success('退出成功！');
        localStorage.removeItem('currentUser');
        router.push('/login');
    }).catch(() => {
        ElMessage.info('取消退出');
    });
}


let beforeTime = 0, leaveTime = 0;

/*关闭浏览器时清除localstorage*/
window.onunload = () => {
    leaveTime = new Date().getTime() - beforeTime;
    if (leaveTime <= 5) {
        console.log("====关闭=====");
        localStorage.clear();
    } else {
        console.log("====刷新=====");
    }
};

window.onbeforeunload = () => {
    beforeTime = new Date().getTime();
};
</script>

<style scoped>
.el-menu-item.is-active {
    background-color: #e0edfd !important;
}

.el-menu-item:hover {
    color: #1967e3;
}

:deep(th) {
    color: #333;
}
</style>
