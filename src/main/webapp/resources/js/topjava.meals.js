
let filterForm;

$(function () {

    filterForm = $('#meals_filter_form');

    makeEditable({
        ajaxUrl: 'ajax/profile/meals/',
        datatableApi: $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        }),
    });

    $('#apply_meals_filter').click(function () {
        filterMeals();
    });

    $('#clear_meals_filter').click(function () {
        filterForm.find('input').val('');
        updateTable();
    });
});

function filterMeals() {
    $.ajax({
        type: "GET",
        url: 'ajax/profile/meals/filter',
        data: filterForm.serialize()
    }).done(function (filteredMeals) {
        context.datatableApi.clear().rows.add(filteredMeals).draw();
    });
}