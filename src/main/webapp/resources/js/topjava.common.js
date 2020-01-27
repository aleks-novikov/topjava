

function makeEditable() {
    $(".delete").click(function () {
        deleteRow($(this).attr("id"));
    });
}

function add() {
    $("#detailsForm").find(":input").val("");
    $("#editRow").modal();
}

function deleteRow(id) {
    //ajax - обертка JQuery над XML HTTP-запросом
    //полная форма ajax'а
    $.ajax({
        url: ajaxUrl + id,
        type: "DELETE",
        success: function () { //при получении ответа выполняем функцию
            updateTable();
        }
    });
}

function updateTable() {
    //сокращенная форма (просто передаём параметры)
    $.get(ajaxUrl, function (data) {
        datatableApi.clear().rows.add(data).draw();
    });
}

function save() {
    var form = $("#detailsForm");
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize(),
        success: function () {
            $("#editRow").modal("hide");
            updateTable();
        }
    });
}