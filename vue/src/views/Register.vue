<template>
    <div class="login-container">
        <div class="login-box">
            <div style="font-weight: bold; font-size: 24px; text-align: center; margin-bottom: 30px; color: #1450aa">欢 迎 注 册</div>
            <div style="text-align: right;margin-top: -20px;margin-bottom: 10px"><a href="/login">返回登录</a></div>
            <el-form ref="formRef" :model="data.form" :rules="data.rules">
                <el-form-item prop="username">
                    <el-input v-model="data.form.username" :prefix-icon="User" placeholder="请输入账号" size="large"/>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input v-model="data.form.password" :prefix-icon="Lock" placeholder="请输入密码" show-password size="large"/>
                </el-form-item>
                <el-form-item prop="confirmPassword">
                    <el-input v-model="data.form.confirmPassword" :prefix-icon="Lock" placeholder="请确认密码" show-password size="large" @keyup.enter.native="register"/>
                </el-form-item>
                <el-form-item>
                    <el-button size="large" style="width: 100%" type="primary" @click="register">注 册</el-button>
                </el-form-item>
            </el-form>
        </div>

    </div>
</template>

<script setup>
import {reactive, ref} from "vue";
import {User, Lock} from "@element-plus/icons-vue";
import request from "@/utils/request";
import {ElMessage} from "element-plus";
import router from "@/router";

const validatePass = (rule, value, callback) => {
    if (!value) {
        callback(new Error('请确认密码'))
    } else if (value !== data.form.password) {
        callback(new Error('两次输入密码不一致'))
    } else {
        callback()
    }
}

const data = reactive({
    form: {role: 'USER'},
    rules: {
        username: [
            {required: true, message: '请输入账号', trigger: 'blur'},
        ],
        password: [
            {required: true, message: '请输入密码', trigger: 'blur'},
        ],
        confirmPassword: [
            {validator: validatePass, trigger: 'blur'},
        ],
    }
})


const formRef = ref()

/*点击登录按钮时触发*/
const register = () => {
    formRef.value.validate((valid => {
        if (valid) {
            /*调用后台的接口*/
            request.post('/register', data.form).then(res => {
                if (res.code === '200') {
                    ElMessage.success("注册成功！")
                    router.push('/login')
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
    background-image: url("../assets/imgs/傍晚.png");
    /*background: linear-gradient(to bottom, #a8c0ff, #3f2b96);*/
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
