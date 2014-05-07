$(document).ready(function() {
	
	
	var data1, options, data2, data3, overalldata=[[]];
	data1 = [[ 1, 2 ], [ 2, 3 ], [ 3, 2 ], [ 4, 8 ], [ 5, 7 ] ];
	data2 = [ [ 1, 4 ], [ 2, 2 ], [ 3, 5 ], [ 4, 7 ], [ 5, 6 ] ];
	data3 = [ [ 1, 2 ], [ 2, 4 ], [ 3, 6 ], [ 4, 8 ], [ 5, 10 ] ];
	
	//overalldata = [data1, data2];
	var selectedGoal=null;
	options = {
			canvas: true,
			series : {
				lines : {
					show : true
				},
				points : {
					show : true
				},
				bars : {
					show : false
				}						
			}, 
			xaxis: {
				mode: "time",timeformat: "%Y/%m/%d" 
			},
			valueLabels: {
	              show:            true,
	              showAsHtml:      false, // Set to true if you wanna switch back to DIV usage (you need plot.css for this)
	              showLastValue:   true
			},yaxes: [ { min: 0 }, {
				position: "right",
				alignTicksWithAxis: 1,
				tickFormatter: function(value, axis) {
					return value.toFixed(axis.tickDecimals) + "kg";
				}
			} ],
			legend: { 
				position: "sw", 
				margin: 10,
				backgroundColor: "#ccc",
				sorted: function(a, b) {
				    // sort alphabetically in ascending order
				    return a.label == b.label ? 0 : (a.label > b.label ? 1 : -1);
				}
					},
			grid: { 
				hoverable: true, 
				clickable: true
				}
		};
    function showTooltip(x, y, contents) {
        $('<div id="tooltip">' + contents + '</div>').css( {
            position: 'absolute',
            display: 'none',
            top: y + 5,
            left: x + 5,
            border: '1px solid #fdd',
            padding: '2px',
            'background-color': '#fee',
            opacity: 0.80
        }).appendTo("body").fadeIn(200);
    }
    
	var previousPoint = null;
    $("#weekprogressbar").bind("plothover", function (event, pos, item) {
       /* $("#x").text(new Date(pos.x).toLocaleDateString());
        $("#y").text(pos.y.toFixed(2));*/
            if (item) {
                if (previousPoint != item.dataIndex) {
                    previousPoint = item.dataIndex;
                    
                    $("#tooltip").remove();
                    var x = item.datapoint[0],
                        y = item.datapoint[1].toFixed(2);
                    var un ="";
                    if( selectedGoal !=null)
                    	un= selectedGoal.goalValueUnit;
                    showTooltip(item.pageX, item.pageY,
                                item.series.label + " on " + new Date(x).toLocaleDateString() + " = " + y + " " +un);
                }
            }
            else {
                $("#tooltip").remove();
                previousPoint = null;            
            }
        
    });
    ///////////////////////////////////////////////////
    //////////MEASURE FOR NEW GOAL SELECT ITEM////////
    ////////////////////////////////////////////////////
    $('#goalMeasureId').ready(function(){
    	$.get("/lifecoach/ManageMeasure?q=3", function(measure) {
    		$.each(measure, function(i, m) {
    			$("#goalMeasureId").append(
						'<option value="' + m.id + '">' + m.name + '</option>');
    		});//end of iteration on measures returned
    	});  //end of get call for list of measures
    	
    });
    
    //////////////////////////////////////////////////////
    //////////////GOAL NAVIGATION /////////////////////////
    /////////////////////////////////////////////////////
	$("#goalNavigation").ready(function() {
		   //get all measures of the user 
			$.get("/lifecoach/ManageGoal?q=3", function(measure) {
				$("#goalNavigation").html('<li id="allGoalLink" class="active"><a href="#all">All</a></li><input type="hidden" name="allGoalLink" value="'+0+'" />');
				$('#allGoalLink').click(function(){
					$('#allGoal').removeClass('hide').addClass('show');
					$('#individualGoal').removeClass('show').addClass('hide');				
					
					$('#goalsContent .active').removeClass('active');
					$('#allGoalLink').addClass('active');
				});
				// get measure history and draw graph
				//drawOverall(measure);	
				//iterate returned list of measure json object
				$.each(measure, function(i, m) {
					//and append to measure navigation. For each measure append hidden input value for measure id
					$("#goalNavigation").append(
							'<li id="' + m.id
									+ 'GoalLink"><a href="#">' + m.title
									+ '</a></li><input type="hidden" name="'+ m.id
									+ 'GoalLink" value="'+m.id+'" />');
								
					//register for click event on the measure 
					$('#'+ m.id+'GoalLink').click(function(){
						$('#allGoal').removeClass('show').addClass('hide');
						$('#individualGoal').removeClass('hide').addClass('show');
						$('#goalsContent .active').removeClass('active');
						$('#'+ m.id+'GoalLink').addClass('active');
						selectedGoal = m;
						
						
						$('#updateDatapointBtnDiv').html('<button type="submit" class="btn btn-primary" id="updateDatapoint'+m.id+'">Add</button>');
						$('#updateDatapoint'+m.id).click(function(){
							var dpdata = {	
									value: $('#measureValueUpdate').val(),
									unit: $('#measureUnitUpdate').val(),
									timestamp: new Date($('#measureDayUpdate').val()).getTime()
								};
								$.post("/lifecoach/ManageGoal?q=4&id="+m.id, dpdata, function(data){		
									var dd = [[]];			
									var glabel = m.title+"";									
									dd.push([new Date(m.updatedTime), m.initialValue]);
									$.get("/lifecoach/ManageGoal?q=5&gid="+m.id, function(history) {
										$('#historyTBodyElements').html('');
										$.each(history, function(i, d){													
											dd.push([new Date(d.timestamp), d.value]);		
											$('#historyTBodyElements').append('<tr>	<td>'+ new Date(d.timestamp).toLocaleDateString()+'</td><td>'+d.value+'</td><td>'+d.valueUnit+'</td></tr>');
										});
										dd.push([new Date(m.goalDate), m.goalValue]);
										overalldata.push([{data:dd, label:glabel}]);
										var cha = $.plot($("#weekprogressbar"), [{data:dd, label: glabel}], options);
									});	
									
							    	return false;
							    });
						});
						// get measure history and draw graph
						var ddata = [[]];			
						var glabels = m.title+"";
						
						ddata.push([new Date(m.updatedTime), m.initialValue]);
						$.get("/lifecoach/ManageGoal?q=5&gid="+m.id, function(history) {
							$('#historyTBodyElements').html('');
							$.each(history, function(i, d){													
								ddata.push([new Date(d.timestamp), d.value]);		
								$('#historyTBodyElements').append('<tr>	<td>'+ new Date(d.timestamp).toLocaleDateString()+'</td><td>'+d.value+'</td><td>'+d.valueUnit+'</td></tr>');
							});			
							ddata.push([new Date(m.goalDate), m.goalValue]);
							overalldata.push([ddata]);
							var cha = $.plot($("#weekprogressbar"), [{data:ddata, label: glabels}], options);
						});	
						$('#feedbackRow').html('');
						//GET PROGRESS REPORT
						$.get("/lifecoach/ManageGoal?q=0&gid="+m.id, function(progress) {
							var per =100* parseInt((( new Date().getTime()-m.updatedTime)/(24*3600*1000) ) )/parseInt(((m.goalDate-m.updatedTime)/(24*3600*1000)));
							if(progress == 0)
							$('#feedbackRow').append('<div class="progress  progress-striped active">'+
									'<div class="progress-bar progress-bar-success" role="progressbar"'
									+'aria-valuenow="' + per+'" aria-valuemin="0" aria-valuemax="100"'
									 +'style="width: ' + per+'%">'
									+'<span >' + parseInt((( new Date().getTime()-m.updatedTime)/(24*3600*1000) ) )+' days</span></div></div>');
							else if(progress == -1){
								$('#feedbackRow').append('<div class="progress  progress-striped active">'+
										'<div class="progress-bar progress-bar-warning" role="progressbar"'
										+'aria-valuenow="' + per+'" aria-valuemin="0" aria-valuemax="100"'
										 +'style="width: ' + per+'%">'
										+'<span >' + parseInt((( new Date().getTime()-m.updatedTime)/(24*3600*1000) ) )+' days</span></div></div>');
							}else if(progress == -2){
								$('#feedbackRow').append('<div class="progress  progress-striped active">'+
										'<div class="progress-bar progress-bar-danger" role="progressbar"'
										+'aria-valuenow="' + per+'" aria-valuemin="0" aria-valuemax="100"'
										 +'style="width: ' + per+'%">'
										+'<span >' + parseInt((( new Date().getTime()-m.updatedTime)/(24*3600*1000) ) )+' days</span></div></div>');
							}
						});
						//GET EXPECTED VALUE
						$.get("/lifecoach/ManageGoal?q=6&gid="+m.id, function(expected) {
							$('#feedbackRow').append('<p  style="background-color: #3276b1;color: white;padding:8px;">By today your expected value is: <strong> '+ expected.toFixed(2) + '</strong></p>');
						});
						//GET FEEDBACK	value
						$.get("/lifecoach/ManageGoal?q=7&gid="+m.id, function(feedback) {
							if(feedback == 0){
								$('#feedbackRow').append('<p  style="background-color: #dff0d8;padding:8px;">You are doing very good. You are in the exact track now!</p>');
							}else if(feedback == 1){
								$('#feedbackRow').append('<p  style="background-color: #dff0d8;padding:8px;">You are doing Excellent! It seems you might achieve your goal early than expected!</p>');
							}else if(feedback == -1){
								$('#feedbackRow').append('<p  style="background-color: #f2dede;padding:8px;">Your progress shows you are doing less than your planned rate! You better do more!</p>');
							}else
								$('#feedbackRow').append('');
						});
					});										
				});  // end of each loop
				drawOverall(measure);
			});
		});	
	
	
	data = [ {
		data : data1,
		label : "weight"
	}, {
		data : data2,
		label : "steps"
	}, {
		data : data3,
		label : "sleep"
	} ];
	
     // var chart = $.plot($("#weekprogressbarx"), data, options);
      
	$('#btnSaveNewGoal').click(function(){
		var mdata = {
			title: $('#goalTitle').val(),
			mid: $('#goalMeasureId').val(),
			initialValue: $('#initialGoalValue').val(),
			initialValueUnit: $('#initialGoalValueUnit').val(),
			goalValue: $('#goalValue').val(),
			goalValueUnit: $('#goalValueUnit').val(),
			goalDate: new Date($('#goalDate').val()).getTime(),
			goalAmount: $('#goalAmount').val(),
			goalAmountUnit: $('#goalAmountUnit').val(),
			planRate: $('#planRate').val(),
			planRateUnit: $('#planRateUnit').val(),
			planRateType:$('#planRateType').val(),
			goalRateType: $('#goalRateType').val()
		};
	
		$.post("/lifecoach/ManageGoal?q=1", mdata, function(data){		
			$('#addGoalModal').hide();
	    	return true;
	    });
	});
	
	
	$('#showHistoryBtnDiv').click(function(){
		$('#goalGraphDiv').removeClass('show').addClass('hide');
		$('#measureHistoryDiv').removeClass('hide').addClass('show');																				
	});
	$('#backToMeasure').click(function(){
		$('#goalGraphDiv').removeClass('hide').addClass('show');
		$('#measureHistoryDiv').removeClass('show').addClass('hide');		
	});
	
	
	/*$('#measureName').typeahead(
			[ {
				name : 'measures',
				local : [ "weight", "height","steps", "sleep",
					"blood presure", "heart rate", "water consumption" ]
			} ]);*/

	function drawOverall(measure){
		var dataset = new Object();
		$.each(measure, function(i, m) {
			var ddata = [[]];			
			var glabels = m.title+"";
			
			ddata.push([new Date(m.updatedTime), m.initialValue]);
			$.get("/lifecoach/ManageGoal?q=5&gid="+m.id, function(history) {
				$('#historyTBodyElements').html('');
				$.each(history, function(i, d){													
					ddata.push([new Date(d.timestamp), d.value]);		
					$('#historyTBodyElements').append('<tr>	<td>'+ new Date(d.timestamp).toLocaleDateString()+'</td><td>'+d.value+'</td><td>'+d.valueUnit+'</td></tr>');
				});			
				ddata.push([new Date(m.goalDate), m.goalValue]);
				dataset.add({data:ddata});							
			});	
		});
		//JSON.Stringify(dataset);
		
		var cha = $.plot($("#weekprogressbarx"), [dataset], options);
		return 0;
	}
	

});