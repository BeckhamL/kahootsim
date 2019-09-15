/**
 * 
 */

am4core.ready(function() {
	am4core.useTheme(am4themes_frozen);
	am4core.useTheme(am4themes_animated);

	var chart = am4core.create("chartdiv", am4charts.PieChart);

	chart.data = [ {
		"question" : "Answer 1",
		"values" : 2,
		"color" : "#ff0000"
	}, {
		"question" : "Answer 2",
		"values" : 1
	}, {
		"question" : "Answer 3",
		"values" : 1
	}, {
		"question" : "Answer 4",
		"values" : 1
	} ];

	var pieSeries = chart.series.push(new am4charts.PieSeries());
	pieSeries.dataFields.value = "values";
	pieSeries.dataFields.category = "question";
	pieSeries.dataFields.colorField = "color";
	pieSeries.slices.template.stroke = am4core.color("#fff");
	pieSeries.slices.template.strokeWidth = 2;
	pieSeries.slices.template.strokeOpacity = 1;

	pieSeries.colors.list = [ new am4core.color('#007bff'),
	                          new am4core.color('#36cb2f'), new am4core.color('#ff4040'),
	                          new am4core.color('#ffff00'), ];

	pieSeries.hiddenState.properties.opacity = 1;
	pieSeries.hiddenState.properties.endAngle = -90;
	pieSeries.hiddenState.properties.startAngle = -90;

});