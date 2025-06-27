$(document).ready(function() {
    // 检查本地存储中是否有记住的用户名
    if(localStorage.getItem('rememberMe') === 'true') {
        $('#username').val(localStorage.getItem('username'));
        $('#rememberMe').prop('checked', true);
    }

    // 登录表单提交
    $('#loginForm').on('submit', function(e) {
        e.preventDefault();

        const username = $('#username').val();
        const password = $('#password').val();
        const rememberMe = $('#rememberMe').is(':checked');

        // 简单的验证
        if(!username || !password) {
            alert('请输入用户名和密码');
            return;
        }

        // 如果勾选了记住我，保存用户名到本地存储
        if(rememberMe) {
            localStorage.setItem('rememberMe', 'true');
            localStorage.setItem('username', username);
        } else {
            localStorage.removeItem('rememberMe');
            localStorage.removeItem('username');
        }

        // 这里应该是实际的登录逻辑，现在只是模拟
        console.log('登录信息:', {username, password});
        alert('登录成功！');

        // 登录成功后跳转到首页或其他页面
        // window.location.href = 'home.html';
    });

    // 忘记密码点击事件
    $('#forgotPassword').on('click', function(e) {
        e.preventDefault();
        alert('忘记密码功能暂未实现');
    });

    // 注册点击事件
    $('#register').on('click', function(e) {
        e.preventDefault();
        alert('注册功能暂未实现');
    });
});