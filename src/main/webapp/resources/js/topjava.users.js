

$(function () {

    const ajaxUrl = "ajax/admin/users/";

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

    $('tr input:checkbox').click(function () {
        let tr = $(this).closest('tr');
        let enabled = $(this).is(':checked');

        $.ajax({
            url: context.ajaxUrl + tr.attr('id'),
            type: "POST",
            data: "enabled=" + enabled
        }).done(function () {
            successNoty(enabled ? "Enabled" : "Disabled");
            tr.toggleClass('disabled_user', !enabled);
        }).fail(function () {
            $(this).is(':checked', !enabled);
        });
    });
});