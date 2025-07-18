// API处理模块
const API_BASE_URL = 'http://localhost:8080/api';

// API请求工具类
class ApiService {
    // 通用GET请求
    static async get(url, params = {}) {
        const queryString = Object.keys(params)
            .map(key => `${encodeURIComponent(key)}=${encodeURIComponent(params[key])}`)
            .join('&');

        const fullUrl = queryString ? `${url}?${queryString}` : url;

        try {
            const response = await fetch(fullUrl, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                }
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            return await response.json();
        } catch (error) {
            console.error('API请求失败:', error);
            throw error;
        }
    }

    // 获取所有产品分类
    static async getCategories() {
        return await this.get(`${API_BASE_URL}/categories`);
    }

    // 根据分类ID获取产品列表
    static async getProductsByCategory(categoryId) {
        return await this.get(`${API_BASE_URL}/products`, { categoryId });
    }

    // 根据产品名称搜索产品
    static async searchProductsByName(name) {
        return await this.get(`${API_BASE_URL}/products/searchByCategory`, { name });
    }

    // 新增商品
    static async addProduct(product) {
        const response = await fetch('http://localhost:8080/api/product/add', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(product)
        });
        return await response.json();
    }

    // 新增分类
    static async addCategory(category) {
        const response = await fetch('http://localhost:8080/api/categories/add', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(category)
        });
        return await response.json();
    }

    // 编辑分类
    static async updateCategory(category) {
        const response = await fetch('http://localhost:8080/api/categories/update', {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(category)
        });
        return await response.json();
    }

    // 删除分类
    static async deleteCategory(id) {
        const response = await fetch(`http://localhost:8080/api/categories/${id}`, {
            method: 'DELETE'
        });
        return await response.json();
    }
}

// 产品展示模块
class ProductDisplay {
    constructor() {
        this.currentCategoryId = null;
        this.currentCategoryName = '';
    }

    // 初始化页面
    init() {
        this.loadCategories();
        this.bindEvents();
    }

    // 绑定事件
    bindEvents() {
        // 搜索按钮点击事件
        $('.search-bar button').on('click', function() {
            const searchValue = $('.search-bar input').val();
            if (searchValue.trim()) {
                alert('搜索功能: ' + searchValue);
            } else {
                alert('请输入搜索关键词');
            }
        });

        // 回车键搜索
        $('.search-bar input').on('keypress', function(e) {
            if (e.which === 13) {
                $('.search-bar button').click();
            }
        });
    }

    // 加载产品分类
    async loadCategories() {
        try {
            this.showLoading('#categoryList', '正在加载分类...');
            const flatCategories = await ApiService.getCategories();
            const tree = this.listToTree(flatCategories);
            this.displayCategories(tree);
        } catch (error) {
            this.showError('#categoryList', '加载分类失败，请刷新页面重试');
        }
    }

    // 平铺数组转树结构
    listToTree(list) {
        const map = {};
        const roots = [];
        list.forEach(item => {
            map[item.id] = { ...item, children: [] };
        });
        list.forEach(item => {
            // 注意：parentId为0时是根节点
            if (item.parentId !== 0 && map[item.parentId]) {
                map[item.parentId].children.push(map[item.id]);
            } else if (item.parentId === 0) {
                roots.push(map[item.id]);
            }
        });
        return roots;
    }

    // 递归渲染树状分类
    displayCategories(tree) {
        const $container = $('#categoryList');
        $container.empty();
        this.renderCategoryTree(tree, $container[0]);
        // 绑定点击事件
        this.bindCategoryTreeEvents();
    }

    // 递归渲染
    renderCategoryTree(categories, parentUl) {
        categories.forEach(cat => {
            const li = document.createElement('li');
            li.innerHTML = `<span class="category-name" data-category-id="${cat.id}">${cat.name}</span>`;
            if (cat.children && cat.children.length > 0) {
                const childUl = document.createElement('ul');
                this.renderCategoryTree(cat.children, childUl);
                li.appendChild(childUl);
            }
            parentUl.appendChild(li);
        });
    }

    // 绑定树状分类点击事件
    bindCategoryTreeEvents() {
        $('#categoryList').off('click').on('click', '.category-name', (e) => {
            e.preventDefault();
            const $target = $(e.target);
            const categoryId = $target.data('category-id');
            const categoryName = $target.text();
            // 更新选中状态
            $('#categoryList .category-name').removeClass('active');
            $target.addClass('active');
            // 加载该分类的产品
            this.loadProducts(categoryId, categoryName);
        });
    }

    // 加载产品列表
    async loadProducts(categoryId, categoryName) {
        this.currentCategoryId = categoryId;
        this.currentCategoryName = categoryName;

        // 更新标题
        $('#categoryTitle').text(categoryName);

        // 显示加载状态
        this.showLoading('#productsContainer', '正在加载产品...');

        try {
            const products = await ApiService.getProductsByCategory(categoryId);
            this.displayProducts(products);
        } catch (error) {
            this.showError('#productsContainer', '加载产品失败，请稍后重试');
        }
    }

    // 显示产品列表
    displayProducts(products) {
        if (!products || products.length === 0) {
            $('#productsContainer').html('<div class="loading">该分类暂无产品</div>');
            return;
        }

        let html = '<div class="products-grid">';
        products.forEach(product => {
            html += this.createProductCard(product);
        });
        html += '</div>';

        $('#productsContainer').html(html);
    }

    // 创建产品卡片HTML
    createProductCard(product) {
        const imageUrl = product.iconUrl || 'https://via.placeholder.com/250x200?text=产品图片';
        const price = product.price ? `¥${product.price}` : '¥0.00';
        const stock = product.stock || 0;

        return `
            <div class="product-card" data-product-id="${product.id}">
                <img src="${imageUrl}" alt="${product.name}" class="product-image">
                <div class="product-name">${product.name}</div>
                <div class="product-price">${price}</div>
                <div class="product-stock">库存: ${stock} 件</div>
                <div class="product-actions">
                    <button class="btn-view" onclick="viewDetail(${product.id})">查看详情</button>
                    <button class="btn-add-cart" onclick="productDisplay.addToCart(${product.id})">加入购物车</button>
                </div>
            </div>
        `;
    }

    // 查看产品详情
    viewProduct(productId) {
        alert(`查看产品详情: ${productId}\n功能开发中...`);
    }


    // 加入购物车
    addToCart(productId) {
        const userId = parseInt(localStorage.getItem('userId'));
        const token = localStorage.getItem('token');

        if (isNaN(userId) || userId <= 0 || !token) {
            alert('请先登录');
            window.location.href = 'login.html?redirect=' + encodeURIComponent(window.location.href);
            return;
        }

        // 检查商品库存
        $.ajax({
            url: `http://localhost:8080/api/products/${productId}/stock`,
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + token
            },
            success: function(response) {
                if (response.status === 0 && response.data.stock > 0) {
                    const shoppingCart = {
                        userId: userId,
                        productId: productId,
                        quantity: 1 // 默认添加数量为 1
                    };

                    $.ajax({
                        url: 'http://localhost:8080/api/cart',
                        method: 'POST',
                        headers: {
                            'Authorization': 'Bearer ' + token
                        },
                        contentType: 'application/json',
                        data: JSON.stringify(shoppingCart),
                        success: function(response) {
                            if (response.status === 0) {
                                alert('商品已成功添加到购物车');
                                // 更新购物车数量显示
                                updateCartCount();
                            } else {
                                alert(response.msg || '添加到购物车失败');
                            }
                        },
                        error: function(xhr) {
                            if (xhr.status === 401) {
                                alert('登录已过期，请重新登录');
                                window.location.href = 'login.html?redirect=' + encodeURIComponent(window.location.href);
                            } else {
                                alert('添加到购物车失败: ' + (xhr.responseJSON?.msg || '未知错误'));
                            }
                        }
                    });
                } else {
                    alert('该商品库存不足，无法添加到购物车');
                }
            },
            error: function(xhr) {
                alert('检查商品库存失败，请稍后重试');
            }
        });
    }

    // 显示加载状态
    showLoading(selector, message = '加载中...') {
        $(selector).html(`<div class="loading">${message}</div>`);
    }

    // 显示错误信息
    showError(selector, message) {
        $(selector).html(`<div class="error">${message}</div>`);
    }

    // 搜索产品
    async searchProductsByName(name) {
        this.currentCategoryId = null;
        this.currentCategoryName = '';
        $('#categoryTitle').text(`搜索结果：${name}`);
        this.showLoading('#productsContainer', '正在搜索产品...');
        try {
            const products = await ApiService.searchProductsByName(name);
            this.displayProducts(products);
        } catch (error) {
            this.showError('#productsContainer', '搜索失败，请稍后重试');
        }
    }
}

// 全局变量
let productDisplay;

// 页面加载完成后初始化
$(document).ready(function() {
    productDisplay = new ProductDisplay();
    productDisplay.init();
});

window.ApiService = ApiService;

function viewDetail(productId) {
    window.location.href = `product_detail.html?id=${productId}`;
}