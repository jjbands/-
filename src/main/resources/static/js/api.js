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
        return await this.get(`${API_BASE_URL}/products/search`, { name });
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
            const categories = await ApiService.getCategories();
            this.displayCategories(categories);
        } catch (error) {
            this.showError('#categoryList', '加载分类失败，请刷新页面重试');
        }
    }

    // 显示产品分类
    displayCategories(categories) {
        if (!categories || categories.length === 0) {
            $('#categoryList').html('<li><a href="#" style="color: #666;">暂无分类</a></li>');
            return;
        }

        let html = '';
        categories.forEach(category => {
            html += `<li><a href="#" data-category-id="${category.id}">${category.name}</a></li>`;
        });
        $('#categoryList').html(html);

        // 绑定分类点击事件
        this.bindCategoryEvents();
    }

    // 绑定分类点击事件
    bindCategoryEvents() {
        $('#categoryList a').off('click').on('click', (e) => {
            e.preventDefault();
            const $link = $(e.currentTarget);
            const categoryId = $link.data('category-id');
            const categoryName = $link.text();

            // 更新选中状态
            $('#categoryList li').removeClass('active');
            $link.parent().addClass('active');

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
        alert(`产品 ${productId} 已加入购物车\n功能开发中...`);
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