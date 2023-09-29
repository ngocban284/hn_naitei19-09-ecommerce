// placeOrder.js

function placeOrder() {
    const formData = new FormData(document.getElementById('placeOrderForm'));

    const orderData = {
        user: {
            id: 1 // Replace with the actual user ID
        },
        order: {
            fullname: formData.get('fullname'),
            phoneNumber: formData.get('phoneNumber'),
            address: formData.get('address'),
            note: formData.get('note'),
            paymentMethod: formData.get('paymentMethod'),
            status: {
                id: 1 // Replace with the actual status ID
            }
        },
        cartDetailIds: formData.get('cartDetailIds').split(',').map(Number),
        total: parseFloat(formData.get('total'))
    };

    console.log(orderData);

    // TODO: Send the form data to the server using AJAX
    // You can use Fetch API or jQuery.ajax, for example
}
