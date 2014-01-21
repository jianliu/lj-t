var background = {
    type: 'linearGradient',
    x0: 0,
    y0: 0,
    x1: 0,
    y1: 1,
    colorStops: [
        { offset: 0, color: '#d2e6c9' },
        { offset: 1, color: 'white'}
    ]
};
$(document).ready(function () {
    //初始化select选项框
    initSelector();
    //载入图表
    makeQuery()
});
function initChart(data) {
    $('#jqChart').jqChart({
        title: { text: '抓取 走势图' },
        border: { strokeStyle: '#6ba851' },
        background: background,
        labelX : ["X1","X2","X3","X4","X5","X6","X7"],
        scaleY : {min: 0,max:1000,gap:200},
        width: 700, //有影响
        height: 500, //有影响
        paddingL : 100, //有影响
        paddingT : 100, //有影响
        animation: { duration: 1 },
        axes: [
            {
                type: 'category',
                location: 'bottom',
                categories: data.charts[0].categories
            }
        ],
        series: [
            {
                title: data.charts[0].name,
                type: 'spline',
                data: data.charts[0].info
            }
        ]
    });
}
function init8Chart(data) {
    var data1 = data.charts;
    var ch1 = data1[0];
    var ch2 = data1[1];
    var ch3 = data1[2];
    var ch4 = data1[3];
    var ch5 = data1[4];
    var ch6 = data1[5];
    var ch7 = data1[6];
    var ch8 = data1[7];
    $('#jqChart').jqChart({
        title: { text: '抓取 走势图' },
        border: { strokeStyle: '#6ba851' },
        background: background,
        scaleY : {min: 0,max:1000,gap:200},
        width: 700, //有影响
        height: 500, //有影响
        paddingL : 100, //有影响
        paddingT : 100, //有影响
        tooltips: { type: 'shared' },
        crosshairs: {
            enabled: true,
            hLine: false,
            vLine: { strokeStyle: '#cc0a0c' }
        },
        animation: { duration: 1 },
        axes: [
            {
                type: 'category',
                location: 'bottom',
                zoomEnabled: true,
                categories: data.charts[0].categories
            }
        ],
        series: [
            {
                title: ch1.name,
                type: 'spline',
                data: ch1.info ,
                cursor: 'pointer'
            },
            {
                title: ch2.name,
                type: 'spline',
                data: ch2.info
            },
            {
                title: ch3.name,
                type: 'spline',
                data: ch3.info
            },
            {
                title: ch4.name,
                type: 'spline',
                data: ch4.info
            },
            {
                title: ch5.name,
                type: 'spline',
                data: ch5.info
            },
            {
                title: ch6.name,
                type: 'spline',
                data: ch6.info
            },
            {
                title: ch7.name,
                type: 'spline',
                data: ch7.info
            },
            {
                title: ch8.name,
                type: 'spline',
                data: ch8.info
            }
        ]
    });
}
function init2Chart(data) {
    var data1 = data.charts2;
    var ch1 = data1[0];
    var ch2 = data1[1];
    $('#jqChart2').jqChart({
        title: { text: 'deal shop总量趋势图' },
        border: { strokeStyle: '#6ba851' },
        background: background,
        labelX : ["X1","X2","X3","X4","X5","X6","X7","X8"],
        scaleY : {min: 0,max:1000,gap:200},
        width: 700, //有影响
        height: 500, //有影响
        paddingL : 100, //有影响
        paddingT : 100, //有影响
        animation: { duration: 1 },
        axes: [
            {
                type: 'category',
                location: 'bottom',
                categories: data.charts2[0].categories
            }
        ],
        series: [
            {
                title: ch1.name,
                type: 'spline',
                data: ch1.info
            },
            {
                title: ch2.name,
                type: 'spline',
                data: ch2.info
            }
        ]
    });

}
function initSelector() {
    //    $("#datepicker").datepicker();   <option value=\"\">请选择</option>
    var options = "";
    $.ajax({
        type: 'post',
        datatype: 'json',
        url: 'getSites.json',
        success: function(data1) {
            $("#sites").empty();

            try {
                //    var b = eval('(' + data1 + ')');
                $.each(data1.sites, function(i, o) {

                    options += "<option  value=\"" + o._1 + "\">" + o._2 + "</option>";
                });
                $("#sites").append(options);
            }
            catch (e) {
                alert(e);
            }
        }
    });
    var options2 = "<option value=\"-1\">全部情况</option>";
    $.ajax({
        type: 'post',
        datatype: 'json',
        url: 'getTypes.json',
        success: function(data1) {
            $("#types").empty();

            try {
                //    var b = eval('(' + data1 + ')');
                $.each(data1.types, function(i, o) {

                    options2 += "<option  value=\"" + o._1 + "\">" + o._2 + "</option>";
                });
                $("#types").append(options2);
            }
            catch (e) {
                alert(e);
            }
        }
    });
}
function makeQuery() {
    var sites = $('#sites option:selected').val();
    if ($('#sites option:selected').length <= 0)
        sites = (-1);
    var queryType = $('#types option:selected').val();
    if ($('#types option:selected').length <= 0)
        queryType = (-1);
    var seriesSize = $('#ttd option:selected').val();
    if ($('#ttd option:selected').length <= 0)
        seriesSize = (-1);
    var startDate = $('#beginDateid').val();
    var endDate = $('#endDateid').val();
    $.ajax({
        cache:false,
        type: "POST",
        url: "/chart.json",
        data: 'seriesSize=' + seriesSize + '&siteIds=' + sites + '&queryType=' + queryType + '&startDate=' + startDate + '&endDate=' + endDate + "&refresh=" + Math.random(),
        dataType: 'json',
        success: function (data) {
            if (data.hasData == '0') {
                $('#wrongmessage').show();
                $('#jqChart').hide();
                $('#jqChart2').hide();
            } else {
                $('#wrongmessage').hide();
                init2Chart(data);
                if (data.seriesSize == '1')
                    initChart(data);
                else
                    init8Chart(data);
                $('#jqChart').show();
                $('#jqChart2').show();
            }
        }
    });
}

