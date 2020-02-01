const ajaxUrl = "ajax/admin/users/";

$(function () {
    makeEditable({
            ajaxUrl: ajaxUrl,
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "name"
                    },
                    {
                        "data": "email"
                    },
                    {
                        "data": "roles"
                    },
                    {
                        "data": "enabled"
                    },
                    {
                        "data": "registered"
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
                        "asc"
                    ]
                ]
            })
        }
    );
});

function changeUserStatus(checkBox) {
    let tr = checkBox.closest('tr');
    let enabled = checkBox.is(':checked');

    $.ajax({
        url: context.ajaxUrl + tr.attr('id'),
        type: "POST",
        data: "enabled=" + enabled
    }).done(function () {
        successNoty(enabled ? "Enabled" : "Disabled");
        tr.toggleClass('disabled_user', !enabled);
    }).fail(function () {
        checkBox.is(':checked', !enabled);
    });
}