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

function changeUserStatus(checkBox, id) {
    let enabled = checkBox.is(':checked');

    $.ajax({
        url: context.ajaxUrl + id,
        type: "POST",
        data: "enabled=" + enabled
    }).done(function () {
        successNoty(enabled ? "Enabled" : "Disabled");
    }).fail(function () {
        checkBox.is(':checked', !enabled);
    });
}