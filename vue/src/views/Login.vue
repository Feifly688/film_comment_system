<template>
    <div class="login-container">
        <div class="login-box">
            <div style="font-weight: bold; font-size: 24px; text-align: center; margin-bottom: 30px; color: #1450aa">电 影 评 论 系 统</div>
            <el-form ref="formRef" :model="data.form" :rules="data.rules" @submit.native.prevent>
                <el-form-item prop="username">
                    <el-input v-model="data.form.username" :prefix-icon="User" placeholder="请输入账号" size="large" @keyup.enter.native="login"/>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input v-model="data.form.password" :prefix-icon="Lock" placeholder="请输入密码" show-password size="large" @keyup.enter.native="login"/>
                </el-form-item>
                <el-form-item prop="role">
                    <el-select v-model="data.form.role" size="large" style="width: 100%">
                        <el-option label="管理员" style="background-color: #F9B44C" value="管理员"></el-option>
                        <el-option label="普通用户" value="普通用户"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button size="large" style="width: 100%" type="primary" @click="login">登 录</el-button>
                </el-form-item>
            </el-form>
            <div style="text-align: right;">
                还没有账号？请 <a href="/register">注册</a>
            </div>
        </div>

    </div>
</template>

<script setup>
import {reactive, ref} from "vue";
import {Lock, User} from "@element-plus/icons-vue";
import request from "../utils/request";
import {ElMessage} from "element-plus";
import router from "../router";

const data = reactive({
    form: {role: '普通用户'},
    rules: {
        username: [
            {required: true, message: '请输入账号', trigger: 'blur'},
        ],
        password: [
            {required: true, message: '请输入密码', trigger: 'blur'},
        ],
    }
})

const formRef = ref()

/*点击登录按钮时触发*/
const login = () => {
    formRef.value.validate((valid => {
        if (valid) {
            // 调用后台的接口
            request.post('/login', data.form).then(res => {
                if (res.code === '200') {
                    ElMessage.success("登录成功！")
                    router.push('/')
                    localStorage.setItem('currentUser', JSON.stringify(res.data))
                } else {
                    ElMessage.error(res.msg)
                }
            })
        }
    })).catch(error => {
        console.error(error)
    })
}

</script>

<style scoped>
.login-container {
    height: 100vh;
    overflow: hidden;
    display: flex;
    justify-content: center;
    align-items: center;
    background-image: url("../assets/imgs/白天.png");
    /*background: linear-gradient(to top, #7f7fd5, #86a8e7, #91eae4);*/
    background-size: cover;
}

.login-box {
    width: 350px;
    padding: 50px 30px;
    border-radius: 5px;
    box-shadow: 0 0 10px rgba(0, 0, 0, .1);
    background-color: rgba(255, 255, 255, .5);
}
</style>
