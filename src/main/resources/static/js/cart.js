// Function to add a product to the cart
function addItemToCart( productId) {
    // Gửi yêu cầu POST đến endpoint thích hợp với thông tin sản phẩm và người dùng
    $.ajax({
        url: "/cart/add", // Điều chỉnh URL dựa trên cấu hình của bạn
        method: "POST",
        data: JSON.stringify({ productId: productId }),
        contentType: "application/json",
        success: function (response) {
            // Xử lý phản hồi thành công ở đây (ví dụ: cập nhật số lượng sản phẩm)
            console.log("Product added to cart:", response);
        },
        error: function (error) {
            // Xử lý lỗi ở đây
            console.error("Error adding product to cart:", error);
        }
    });
}

// Function to remove a product from the cart
function removeItemFromCart( productId) {
    // Gửi yêu cầu DELETE đến endpoint thích hợp với thông tin sản phẩm và người dùng
    $.ajax({
        url: "/cart/remove", // Điều chỉnh URL dựa trên cấu hình của bạn
        method: "DELETE",
        data: JSON.stringify({ productId: productId }),
        contentType: "application/json",
        success: function () {
            // Xử lý phản hồi thành công ở đây (ví dụ: cập nhật giỏ hàng sau khi xoá sản phẩm)
            console.log("Product removed from cart:", productId);
        },
        error: function (error) {
            // Xử lý lỗi ở đây
            console.error("Error removing product from cart:", error);
        }
    });
}

// Bắt đầu thêm sự kiện cho nút "Add" và "Remove" (đảm bảo HTML của bạn có các nút này với các hàm JavaScript được gắn vào sự kiện onclick)
$(document).ready(function () {
    // Thêm sản phẩm vào giỏ hàng khi nhấp vào nút "Add"
    $(".btn-add-to-cart").click(function () {
        var user = {
            id: 1, // Thay đổi ID người dùng tùy theo tài khoản người dùng hiện tại
            fullname: "John Doe",
            email: "john@example.com"
        };
        var productId = $(this).data("product-id");
        addItemToCart(user, productId);
    });

    // Xoá sản phẩm khỏi giỏ hàng khi nhấp vào nút "Remove"
    $(".btn-remove-from-cart").click(function () {
        var user = {
            id: 1, // Thay đổi ID người dùng tùy theo tài khoản người dùng hiện tại
            fullname: "John Doe",
            email: "john@example.com"
        };
        var productId = $(this).data("product-id");
        removeItemFromCart(user, productId);
    });
});
