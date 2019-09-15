/**
 * 
 */
	function drawPieChart() {
		am4core.ready(function() {
	
		am4core.useTheme(am4themes_frozen);
		am4core.useTheme(am4themes_animated);
	
		var chart = am4core.create("chartdiv", am4charts.PieChart);
	
		chart.data = answerData; //Gets from game.js
		chart.legend = new am4charts.Legend();
		console.log("chart data");
		console.log(chart.data);
		var pieSeries = chart.series.push(new am4charts.PieSeries());
		pieSeries.dataFields.value = "values";
		pieSeries.dataFields.category = "question";
		pieSeries.dataFields.colorField = "color";
		pieSeries.slices.template.stroke = am4core.color("#fff");
		pieSeries.slices.template.strokeWidth = 2;
		pieSeries.slices.template.strokeOpacity = 1;
		
		pieSeries.labels.disabled = true;
		pieSeries.ticks.template.disabled = true;
		
		pieSeries.labels.template.text = "{question: value}";
		/*
		pieSeries.labels.template.radius = am4core.percent(-40);
		pieSeries.labels.template.fill = am4core.color("black");
		pieSeries.alignLabels = false;*/

		if (answerData.length > 2) {
			pieSeries.colors.list = [
		    new am4core.color('#ff4040'),
		    new am4core.color('#ffff00'),
		    new am4core.color('#007bff'),
		    new am4core.color('#36cb2f'),
			];
		} else {
			pieSeries.colors.list = [
			             		    new am4core.color('#ff4040'),
			             		   new am4core.color('#36cb2f'),
			             		    new am4core.color('#007bff'),
			             		   new am4core.color('#ffff00')
			             			];			
		}
	
		pieSeries.hiddenState.properties.opacity = 1;
		pieSeries.hiddenState.properties.endAngle = -90;
		pieSeries.hiddenState.properties.startAngle = -90;
	
		}); 
/*		am4core.ready(function() {

			// Themes begin
			am4core.useTheme(am4themes_animated);
			// Themes end

			// Create chart instance
			var chart = am4core.create("chartdiv", am4charts.XYChart);

			// Add data
			chart.data = answerData;
			console.log(chart.data);
			// Create axes

			var categoryAxis = chart.xAxes.push(new am4charts.CategoryAxis());
			categoryAxis.dataFields.value = "values";
			categoryAxis.dataFields.category = "question";
			categoryAxis.renderer.grid.template.location = 0;
			categoryAxis.renderer.minGridDistance = 30;

			categoryAxis.renderer.labels.template.adapter.add("dy", function(dy, target) {
			  if (target.dataItem && target.dataItem.index & 2 == 2) {
			    return dy + 25;
			  }
			  return dy;
			});

			var valueAxis = chart.yAxes.push(new am4charts.ValueAxis());

			// Create series
			var series = chart.series.push(new am4charts.ColumnSeries());
			series.dataFields.valueY = "values";
			series.dataFields.categoryX = "question";
			series.columns.template.tooltipText = "{categoryX}: [bold]{valueY}[/]";
			series.columns.template.fillOpacity = .8;

			var columnTemplate = series.columns.template;
			columnTemplate.strokeWidth = 2;
			columnTemplate.strokeOpacity = 1;

			}); // end am4core.ready()
*/	}