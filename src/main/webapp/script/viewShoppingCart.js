$(document).ready(function () {

    $('.goods-list').on('keyup', '.GoodsCount', function () {

        var currentRowForm = $(this).parents(".good").serialize();

        $.get("set-goods-count?" + currentRowForm,
            function (data) {
                $(".GoodsCount").parents(".good").find(".goods-price#" + data.id + "").html(data.cost);
                $(".total").html(data.total);
            });
    });

    $('.goods-list-edit').on('keyup', '.GoodsCount', function () {

        var currentRowForm = $(this).parents(".good").serialize();

        $.get("set-edit-goods-count?" + currentRowForm,
            function (data) {
                $(".GoodsCount").parents(".good").find(".goods-price#" + data.id + "").html(data.cost);
                $(".total").html(data.total);
            });
    });

    $('.goods-list').on('click', '.del', function () {

        var form = $(this).parents('form');
        form.css('border-width', '0');
        form.slideUp(300);

        $.get("delete-goods",form.serialize(),
            function (data) {
                $(".total").html(data.total);
                $("#goodscountincart").html(data.goodscountincart);
            });
    });

    $('.goods-list').on('click', '.button-continue-order', function () {
        location.href = "completion-order";
    });
});