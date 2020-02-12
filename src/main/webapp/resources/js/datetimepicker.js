$(function () {
    $('#startDate, #endDate').datetimepicker({
        timepicker: false,
        format:'Y-m-d',
    });

    $('#startTime, #endTime').datetimepicker({
        datepicker: false,
        format:'H:i',
    });

    $('#dateTime').datetimepicker({
        format:'Y-m-d H:i',
    });
});