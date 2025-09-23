$(function () {
    calculateTotal();
});

function calculateTotal() {
    var total = 0;
    $(".totalprice").each(function () {
        var val = parseFloat($(this).text().replace(/[^\d.]/g, ""));
        if (!isNaN(val)) {
            total += val;
        }
    });
    $("#totalBill").val("₹ " + total.toFixed(2));
}

function updateQuantity(element) {
    var $input = $(element);
    var quantity = parseInt($input.val());
    var cartid = $input.data("cartid");

    if (quantity < 1 || isNaN(quantity)) {
        quantity = 1;
        $input.val(1);
    }

    $.post("/cart/update", { cartid: cartid, quantity: quantity }, function (newSubtotal) {
        var $row = $input.closest("tr");
        $row.find(".totalprice").text("₹ " + parseFloat(newSubtotal).toFixed(2));
        calculateTotal();
    });
}
