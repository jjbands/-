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
        
        // 真实登录逻辑：请求后端接口
        $.ajax({
            url: 'http://localhost:8080/api/user/login',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ loginKey: username, password: password }),
            success: function(res) {
                if (res.status === 0 && res.data) {
                    localStorage.setItem('username', res.data.username);
                    localStorage.setItem('role', res.data.role);
                    alert('登录成功！');
                    window.location.href = 'index.html';
                } else {
                    alert(res.msg || '登录失败');
                }
            },
            error: function() {
                alert('网络错误，请稍后重试');
            }
        });
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

    // 渲染用户菜单
    function renderUserMenu() {
        const username = localStorage.getItem('username');
        const role = localStorage.getItem('role');
        if (username) {
            $('#loginText').hide();
            $('#userDropdown').show();
            $('#usernameDisplay').text(username);
            let menuHtml = '';
            if (role === 'admin') {
                menuHtml += '<a href="product_management.html">商品管理</a>';
                menuHtml += '<a href="user_management.html">用户管理</a>';
            } else {
                menuHtml += '<a href="myaccount.html">我的账户</a>';
                menuHtml += '<a href="myorders.html">我的订单</a>';
                menuHtml += '<a href="myfavorites.html">我的关注</a>';
                menuHtml += '<a href="address_management.html">地址管理</a>';
            }
            menuHtml += '<a href="#" class="logout-btn" id="logoutBtn">退出登录</a>';
            $('#dropdownMenu').html(menuHtml);
        } else {
            $('#userDropdown').hide();
            $('#loginText').show();
        }
    }

    renderUserMenu();

    // 退出登录
    $(document).on('click', '#logoutBtn', function(e) {
        e.preventDefault();
        localStorage.removeItem('username');
        localStorage.removeItem('role');
        renderUserMenu();
        window.location.reload();
    });
});