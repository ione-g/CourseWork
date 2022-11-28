$('document').ready(function () {
    $('table #editButton').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $.get(href,function (category,status){
            $('#edit-id').val(category.id);
            $('#edit-name').val(category.name);
        });
        $('#editCategoryNameModal').modal();
    });
});

