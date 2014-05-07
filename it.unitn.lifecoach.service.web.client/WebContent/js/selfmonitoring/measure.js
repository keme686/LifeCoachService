$(document).ready(function() {
					
	var data, data1, options, data2, data3;
	data1 = [ [ 1, 2 ], [ 2, 3 ], [ 3, 2 ], [ 4, 8 ], [ 5, 7 ] ];
	data2 = [ [ 1, 4 ], [ 2, 2 ], [ 3, 5 ], [ 4, 7 ], [ 5, 6 ] ];
	data3 = [ [ 1, 2 ], [ 2, 4 ], [ 3, 6 ], [ 4, 8 ], [ 5, 10 ] ];
	var selectedMeasure=null;
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
	              showLastValue:   false
			},yaxes: [ { min: 0 }, {
				position: "right",
				alignTicksWithAxis: 1,
				tickFormatter: function(value, axis) {
					return value.toFixed(axis.tickDecimals) + "kg";
				}
			} ],
			legend: { position: "sw", margin: 10,backgroundColor: "#ccc" },
			grid: { hoverable: true, clickable: true },
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
                    if( selectedMeasure !=null)
                    	un= selectedMeasure.valueUnit;
                    showTooltip(item.pageX, item.pageY,
                                item.series.label + " on " + new Date(x).toLocaleDateString() + " = " + y + " " +un);
                }
            }
            else {
                $("#tooltip").remove();
                previousPoint = null;            
            }
        
    });
    $.get("/lifecoach/ManageMeasure?q=6", function(bmi) {
		$('#bmiInfo').html('<p><strong>BMI: '+bmi+'</strong></p>');
	});
			$("#measureNavigation").ready(function() {
						
					   //get all measures of the user 
						$.get("/lifecoach/ManageMeasure?q=3", function(measure) {
							$("#measureNavigation").html('<li id="allMeasureLink" class="active"><a href="#all">All</a></li><input type="hidden" name="allMeasureLink" value="'+0+'" />');
							$('#allMeasureLink').click(function(){
								$('#allMeasure').removeClass('hide').addClass('show');
								$('#weightMeasure').removeClass('show').addClass('hide');				
								
								$('#measuresContent .active').removeClass('active');
								$('#allMeasureLink').addClass('active');
							});
							
							//iterate returned list of measure json object
							$.each(measure, function(i, m) {
								//and append to measure navigation. For each measure append hidden input value for measure id
								$("#measureNavigation").append(
										'<li id="' + m.id
												+ 'MeasureLink"><a href="#">' + m.name
												+ '</a></li><input type="hidden" name="'+ m.id
												+ 'MeasureLink" value="'+m.id+'" />');
								//register for click event on the measure 
								$('#'+ m.id+'MeasureLink').click(function(){
									$('#allMeasure').removeClass('show').addClass('hide');
									$('#weightMeasure').removeClass('hide').addClass('show');
									$('#measuresContent .active').removeClass('active');
									$('#'+ m.id+'MeasureLink').addClass('active');
									selectedMeasure=m;
									
									$('#measureValueUpdate').val(m.value);
									$('#measureUnitUpdate').val(m.valueUnit);
									$('#measureDayUpdate').val(new Date(m.updateTime).getTime());
									
									$('#currentMeasureNameHistory').html(m.name);
									
									var glabel = m.name+"";
									$('#updateDatapointBtnDiv').html('<button type="submit" class="btn btn-primary" id="updateDatapoint'+m.id+'">Add</button>');
									$('#showHistoryBtnDiv').html('<button type="submit" class="btn btn-primary" id="btnMeasureHistory'+m.id+'">History</button>');
									$('#updateDatapoint'+m.id).click(function(){
										var dpdata = {
												id: m.id,
												name: m.name,
												value: $('#measureValueUpdate').val(),
												unit: $('#measureUnitUpdate').val(),
												timestamp: new Date($('#measureDayUpdate').val()).getTime()
											};
											$.post("/lifecoach/ManageMeasure?q=4", dpdata, function(data){	
												var dd = [[]];	
												$.get("/lifecoach/ManageMeasure?q=5&mid="+m.id, function(history) {
													$('#historyTBodyElements').html('');
													$.each(history, function(i, d){													
														dd.push([new Date(d.timestamp), d.value]);		
														$('#historyTBodyElements').append('<tr>	<td>'+ new Date(d.timestamp).toLocaleDateString()+'</td><td>'+d.value+'</td><td>'+d.valueUnit+'</td></tr>');
													});		
													$('#measureValueUpdate').val(m.value);
													$('#measureUnitUpdate').val(m.valueUnit);
													$('#measureDayUpdate').val(new Date(m.updateTime).toLocaleDateString());
													var cha = $.plot($("#weekprogressbar"), [{data:dd, label: glabel}], options);
													return true;
												});	
										    	return true;
										    });
											
											return true;
									});
									
									// get measure history and draw graph
									var dd = [[]];	
									$.get("/lifecoach/ManageMeasure?q=5&mid="+m.id, function(history) {
										$('#historyTBodyElements').html('');
										$.each(history, function(i, d){													
											dd.push([new Date(d.timestamp), d.value]);		
											$('#historyTBodyElements').append('<tr>	<td>'+ new Date(d.timestamp).toLocaleDateString()+'</td><td>'+d.value+'</td><td>'+d.valueUnit+'</td></tr>');
										});		
										$('#measureValueUpdate').val(m.value);
										$('#measureUnitUpdate').val(m.valueUnit);
										$('#measureDayUpdate').val(new Date(m.updateTime).getTime());
										var cha = $.plot($("#weekprogressbar"), [{data:dd, label: glabel}], options);
										return false;
									});	
									return false;
									
								});
								
								
							});
												
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
			var chart = $.plot($("#weekprogressbarx"), data, options);
			var popopt = {
				animation : true,
				placement : "bottom",
				trigger : "click",
				title : "Sign In",
				html : true,
				container : 'body'
			};
			
			$('#btnSaveNewMeasure').click(function(){
				var mdata = {
					name: $('#measureName').val(),
					value: $('#measureValuex').val(),
					unit: $('#measureUnit').val(),
					timestamp: new Date($('#measureDate').val()).getTime()
				};
				$.post("/lifecoach/ManageMeasure?q=1", mdata, function(data){			
					$('#addMeasureModal').hide();
			    	return false;
			    });
			});
			
			$('#showHistoryBtnDiv').click(function(){
				$('#measureGraphDiv').removeClass('show').addClass('hide');
				$('#measureHistoryDiv').removeClass('hide').addClass('show');																				
			});
			$('#backToMeasure').click(function(){
				$('#measureGraphDiv').removeClass('hide').addClass('show');
				$('#measureHistoryDiv').removeClass('show').addClass('hide');		
			});
			$('#measureName').typeahead(
					[ {
						name : 'measures',
						local : [ "weight", "height","steps", "sleep",
							"blood presure", "heart rate", "water consumption" ]
					} ]);

		});