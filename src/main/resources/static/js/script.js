$(document).ready(function() {
//	$('[rel="tooltip"]').tooltip({ trigger: "hover" });
//
//	tinymce.init({
//		selector: '#productdescription'
//	});

$('.edit-order-link').on('click',function(event) {
        event.preventDefault();

        // Get the order ID from the data attribute
        const orderId = $(this).data('order-id');

        // Update the modal input field with the order ID
        $('#orderId').val(orderId);

        // Open the modal
        $('#editModal').modal();
    });



});



$(function() {
	$("#fileupload")
		.change(
			function() {
				if (typeof (FileReader) != "undefined") {
					var dvPreview = $("#dvPreview");
					dvPreview.html("");
					var regex = /^([a-zA-Z0-9\s_\\.\-:])+(.jpg|.jpeg|.gif|.png|.bmp)$/;
					$($(this)[0].files)
						.each(
							function() {
								var file = $(this);
								if (regex.test(file[0].name
									.toLowerCase())) {
									var reader = new FileReader();
									reader.onload = function(
										e) {
										var img = $("<img />");
										img
											.attr(
												"style",
												"height:100px;width: 100px");
										img
											.attr(
												"src",
												e.target.result);
										dvPreview
											.append(img);
									}
									reader
										.readAsDataURL(file[0]);
								} else {
									alert(file[0].name
										+ " is not a valid image file.");
									dvPreview.html("");
									return false;
								}
							});
				} else {
					alert("This browser does not support HTML5 FileReader.");
				}
			});
});


function countChar(val) {
	var len = val.value.length;
	if (len >= 500) {
		val.value = val.value.substring(0, 500);
	} else {
		$('#charNum').text(500 - len);
	}
};




// adding to cart.

function addToCart(pId, pName, pPrice) {



	let cart = localStorage.getItem("cart");
	if (cart == null) {
		let products = [];
		let product = { productId: pId, productName: pName, productPrice: pPrice, productQuantity: 1 }
		products.push(product);
		localStorage.setItem("cart", JSON.stringify(products));
	}
	else {

		let pcart = JSON.parse(cart);

		let oldProduct = pcart.find((item) => item.productId == pId)

		if (oldProduct) {

			oldProduct.productQuantity = oldProduct.productQuantity + 1;
			pcart.map((item) => {

				if (item.productId == oldProduct.productId) {
					item.productQuantity = oldProduct.productQuantity;
				}
			})

			localStorage.setItem("cart", JSON.stringify(pcart));
			console.log("Product quantity is increased");
			showToast("Item quantity is increased");


		}
		else {
			let product = { productId: pId, productName: pName, productPrice: pPrice, productQuantity: 1 }
			pcart.push(product);
			localStorage.setItem("cart", JSON.stringify(pcart));
			console.log("Another Product is added");
			showToast("Item is added to the cart");
		}

	}


	updateCart();
}

function updateCart() {

	let cartString = localStorage.getItem("cart");
	let cart = JSON.parse(cartString);
	if (cart == null || cart.length == 0) {
		console.log("Cart is empty!!");
		$(".cart-items").html("0");
		$(".cart-body").html(`<h1  class="text-center">Sorry, Cart doesn't have any items </h1>`);
		$(".checkout-btn").attr('disabled', true);
	}
	else {
		cart.map((item) => {
			console.log(cart);
			$(".cart-items").html(`${cart.length}`);
			let table = `
			<div class="table-responsive">
          <table class='table table-striped table-hover'>
          <thead class='thead-light'>
            <tr>
            <th>Item Name</th>
            <th>Item Quantity</th>
            <th>Item Price</th>
            <th>Total Price</th>
            <th>Action</th>
              </tr>
          </thead> `;

			let formatCurrency = Intl.NumberFormat('en-PK');

			let totalPrice = 0;
			cart.map((item) => {

				let productTotalPrice = item.productQuantity * item.productPrice;

				table += `
                   <tr>
                   
                   <td>${item.productName}
                   <input type='hidden' name='product_ids' value='${item.productId}'>
                   </td>
                   <td><i onclick='incQuantity(${item.productId}, ${item.productQuantity})' class='fa fa-circle-plus text-success'></i> ${item.productQuantity} <i onclick='decQuantity(${item.productId}, ${item.productQuantity})' class='fa fa-circle-minus text-danger'></i>
                   <input type='hidden' name='product_quantities' value='${item.productQuantity}'>
                   </td>
                   <td>Rs.${formatCurrency.format(item.productPrice)}/-</td>
                   <td>Rs.${formatCurrency.format(productTotalPrice)}/-</td>
                   <td><button onclick='deleteItemFromCart(${item.productId})' class='btn btn-danger btn-sm'>Remove</button></td>
                   </tr>`

				totalPrice += item.productPrice * item.productQuantity;



			})





			table = table + `<tr><td class='colspan='5' class='text-center font-weight-bold'>Total Price: Rs.${formatCurrency.format(totalPrice)}/-
             <input type='hidden' name='total_price' value='${totalPrice}'>
             </td></tr></table></div>`
			$(".cart-body").html(table);
			$(".checkout-btn").attr('disabled', false);

		})
	}
}


function deleteItemFromCart(pId) {

	let cart = JSON.parse(localStorage.getItem("cart"));
	let newCart = cart.filter((item) => item.productId != pId)
	localStorage.setItem("cart", JSON.stringify(newCart));
	updateCart();
	showToast("Item is removed from the cart");
}

function deleteAllProductsFromCart() {

	let cart = JSON.parse(localStorage.getItem("cart"));
	let newCart = cart.filter;
	localStorage.setIem("cart", JSON.stringify(newCart));
	updateCart();

}

function incQuantity(pId, pQuantity) {

	let pcart = JSON.parse(localStorage.getItem("cart"));

	let oldProduct = pcart.find((item) => item.productId == pId)

	if (oldProduct) {

		oldProduct.productQuantity = oldProduct.productQuantity + 1;
		pcart.map((item) => {

			if (item.productId == oldProduct.productId) {
				item.productQuantity = oldProduct.productQuantity;
			}
		})

		localStorage.setItem("cart", JSON.stringify(pcart));
		console.log("Product quantity is increased");
		showToast("Item quantity is increased");

		updateCart();

	}
}

function decQuantity(pId, pQuantity) {

	let pcart = JSON.parse(localStorage.getItem("cart"));

	let oldProduct = pcart.find((item) => item.productId == pId)

	if (oldProduct) {
		
		if(oldProduct.productQuantity == 1) {
			showToast("Item quantity cannot decrease anymore.");
			return;
		}

		oldProduct.productQuantity = oldProduct.productQuantity - 1;
		pcart.map((item) => {

			if (item.productId == oldProduct.productId) {
				item.productQuantity = oldProduct.productQuantity;
			}
		})

		localStorage.setItem("cart", JSON.stringify(pcart));
		console.log("Product quantity is increased");
		showToast("Item quantity is decreased.");

		updateCart();

	}
}


$(document).ready(function() {

	updateCart();

})

function showToast(content) {

	$("#snackbar").addClass("show");
	$("#snackbar").html(content);

	// After 3 seconds, remove the show class from DIV
	setTimeout(() => {
		$("#snackbar").removeClass("show");
	}, 3000);
}

function checkOut() {

	window.location = "/checkout";

}



  function showPreview(event){
  if(event.target.files.length > 0){
    var src = URL.createObjectURL(event.target.files[0]);
    var preview = document.getElementById("file-ip-1-preview");
    preview.src = src;
    preview.style.display = "";
  }
}


function toggleReviewInput() {
  var reviewInput = document.getElementById("reviewInput");
  var addReviewButton = document.getElementById("addReviewButton");
  var cancelButton = document.getElementById("cancelButton");
  var reviewForm = document.getElementById("reviewForm");
  if (reviewInput.style.display === "none") {
    reviewInput.style.display = "block";
    cancelButton.style.display = "block";
    // Change the button text to "Post Review" when the textarea is visible
    addReviewButton.innerText = "Post Review";
  } else {
    reviewInput.style.display = "none";
    cancelButton.style.display = "none";
    // Change the button text back to "Add Review" when the textarea is hidden
    addReviewButton.innerText = "Add Review";
    // Submit the form when the user clicks the "Post Review" button
  }


  // Check if the reviewForm element exists and has a submit event
  if (reviewForm && reviewForm.addEventListener && reviewForm.dispatchEvent) {
    // Add a submit event listener to the reviewForm element
    reviewForm.addEventListener('submit', (event) => {
      // Use the event object to prevent the default form submission behavior
      event.preventDefault();

      // Submit the form manually (if you want to)
      reviewForm.submit();
    });

    // Manually trigger the submit event on the reviewForm element when the "Post Review" button is clicked
    addReviewButton.addEventListener('click', (event) => {
      reviewForm.dispatchEvent(new Event('submit'));
    });
  }

  return false;
}


function showCancelConfirmation(buttonElement) {
    var dataId = buttonElement.getAttribute('data-id');
    swal({
        title: 'Confirm Order Cancellation',
        text: 'Are you sure you want to cancel this order? \n Please input reason',
        content: {
            element: "input",
            attributes: {
                placeholder: "Input reason here",
                type: "text",
            },
        },
        icon: "warning",
        buttons: {
            cancel: "No, keep it",
            confirm: "Yes, cancel it",
        },
    })
    .then((reason) => {
        if (reason) {
            // Send the reason to the backend to save to the database
            $.post('orders/cancel-order/' + dataId, { reason: reason })
                .done(function(response) {
                    swal('Order Cancelled!', 'The order has been cancelled.', 'success').then(() => {location.reload();});
                })
                .fail(function() {
                    swal('Error', 'Failed to cancel the order.', 'error').then(() => {location.reload();});
                });
        }
    });

}


// Function to add a product to the cart
function addItemToCart(productId) {
  // Test Only
//  user = {
//    id: 1,
//    fullname: "John Doe",
//    email: "john@example.com",
//  };

  request = {
//    user: user,
    productId: productId,
  };

  console.log(request);
  // Gửi yêu cầu POST đến endpoint thích hợp với thông tin sản phẩm và người dùng
  $.ajax({
    url: "/cart/add", // Điều chỉnh URL dựa trên cấu hình của bạn
    method: "POST",
    data: JSON.stringify(request),
    contentType: "application/json",
    success: function (response) {
      // Xử lý phản hồi thành công ở đây (ví dụ: cập nhật số lượng sản phẩm)
      console.log("Product added to cart:", response);

       // reload page
         location.reload();

    },
    error: function (error) {
      // Xử lý lỗi ở đây
      console.error("Error adding product to cart:", error);
    },
  });
}

// Function to remove a product from the cart
function removeItemFromCart(productId) {
//  var user = {
//    id: 1, // Thay đổi ID người dùng tùy theo tài khoản người dùng hiện tại
//    fullname: "John Doe",
//    email: "john@example.com",
//  };

  var request = {
//    user: user,
    productId: productId,
  };
  // Gửi yêu cầu DELETE đến endpoint thích hợp với thông tin sản phẩm và người dùng
  $.ajax({
    url: "/cart/remove", // Điều chỉnh URL dựa trên cấu hình của bạn
    method: "DELETE",
    data: JSON.stringify(request),
    contentType: "application/json",
    success: function () {
      // Xử lý phản hồi thành công ở đây (ví dụ: cập nhật giỏ hàng sau khi xoá sản phẩm)
      console.log("Product removed from cart:", productId);

        // reload page
            location.reload();
    },
    error: function (error) {
      // Xử lý lỗi ở đây
      console.error("Error removing product from cart:", error);
    },
  });
}



// Bắt đầu thêm sự kiện cho nút "Add" và "Remove" (đảm bảo HTML của bạn có các nút này với các hàm JavaScript được gắn vào sự kiện onclick)
$(document).ready(function () {
  // Thêm sản phẩm vào giỏ hàng khi nhấp vào nút "Add"
  $("tbody.cart-table button.btn-primary").click(function () {
    var productId = $(this).closest("tr").find("td[data-product-id]").data("product-id");
    addItemToCart(productId);
  });

  // Xoá sản phẩm khỏi giỏ hàng khi nhấp vào nút "Remove"
  $("tbody.cart-table button.btn-danger").click(function () {
    var productId = $(this).closest("tr").find("td[data-product-id]").data("product-id");
    removeItemFromCart(productId);
  });
});

//
function place (){
//    console.log("Place order");
    // Collect product ids from each row in the cart table
     var cartDetailIds = [];
     $(".cart-table tr[data-product-id]").each(function () {
         var productId = $(this).data("product-id");
         console.log("Product Id:", productId);
         cartDetailIds.push(productId);
     });
//
    // Get the total value
        var total = $(".cart-total").text().trim();
        // rusult look like this 4819.87 $
        // remove $ sign and remove space
        total = total.replace("$", "").trim();
        console.log("Total:", total);
//
//
//     // cartDetailIds parameter is required. myparam=myValue1&myparam=myValue2&myparam=myValue3
     var cartDetailIdStrings = "";
     for (var i = 0; i < cartDetailIds.length; i++) {
         cartDetailIdStrings += `${cartDetailIds[i]}`;
     }

     // convert cartDetailIds to string. look like this "123"
     cartDetailIdStrings = "\""+ cartDetailIdStrings + "\"";


     var cartDetailIdParams = "cartDetailIds=" + cartDetailIdStrings;

     var totalParam = "total=" + total;

     var params = cartDetailIdParams + "&" + totalParam;

     var redirectUrl = "/orders/place-orders?" + params;

     console.log(redirectUrl);

     // redirect to place order page
       window.location.href = redirectUrl;
};

function placeOrder() {
    // Lấy thông tin từ các input
    var fullname = document.getElementById("fullname").value;
    var phoneNumber = document.getElementById("phoneNumber").value;
    var address = document.getElementById("address").value;
    var note = document.getElementById("note").value;
    var paymentMethod = document.getElementById("paymentMethod").value;
    var total = document.getElementById("total").value;

    // Lấy cartDetailIds từ bảng cart
      var cartDetailIds = [];
         $(".cart-table tr[data-cart-detail-id]").each(function () {
             var cartId = $(this).data("cart-detail-id");
             cartDetailIds.push(cartId);
         });

     if (fullname === "" || phoneNumber === "" || address === "" ) {
                alert("Please fill in all required fields.");
                return;
     };

    // Tạo JSON object
    var orderData = {

        "order": {
            "fullname": fullname,
            "phoneNumber": phoneNumber,
            "address": address,
            "note": note,
            "paymentMethod": paymentMethod,
            "status": {
                "id": 1
            }
        },
        "cartDetailIds": cartDetailIds,
        "total": parseFloat(total)
    };

    console.log(orderData);

    // Gửi request đến server
     $.ajax({
       url: "/orders/place", // Điều chỉnh URL dựa trên cấu hình của bạn
       method: "POST",
       data: JSON.stringify(orderData),
       contentType: "application/json",
//
         success: function (response) {
            console.log("Order placed:", response);

            // redirect to home
            window.location.href = "/";

         },

       error: function (error) {
         console.error("Error placing order:", error);
       },
     });

};


