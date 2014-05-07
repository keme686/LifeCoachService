$(document).ready(function() {
	
	
	$.get("/lifecoach/ManageMealLog?q=6", function(bmi) {
		$('#caloriesInfo').html('<p><strong>Calories: '+bmi+'</strong></p>');
	});
	
	$('#allMeasureLink').click(function(){
		$('#allMeasure').removeClass('hide').addClass('show');
		$('#weightMeasure').removeClass('show').addClass('hide');				
		
		$('#measureNavigation .active').removeClass('active');
		$('#allMeasureLink').addClass('active');
	});
	
	$('#allBreakfast').click(function(){
		$('#allMeasure').removeClass('show').addClass('hide');
		$('#weightMeasure').removeClass('hide').addClass('show');
		$('#measureNavigation .active').removeClass('active');
		$('#allBreakfast').addClass('active');
		$('#currentMeasureNameHistory').html("Breakfast");
		gatMealDataFor(1);		
	});
	
	$('#allMorningSnack').click(function(){
		$('#allMeasure').removeClass('show').addClass('hide');
		$('#weightMeasure').removeClass('hide').addClass('show');
		$('#measureNavigation .active').removeClass('active');
		$('#allMorningSnack').addClass('active');
		$('#currentMeasureNameHistory').html("Morning Snack");
		gatMealDataFor(2);
		
	});	
	$('#allLunch').click(function(){
		$('#allMeasure').removeClass('show').addClass('hide');
		$('#weightMeasure').removeClass('hide').addClass('show');
		$('#measureNavigation .active').removeClass('active');
		$('#allLunch').addClass('active');
		$('#currentMeasureNameHistory').html("Lunh");
		gatMealDataFor(3);
		
	});
	$('#allAfternoonSnack').click(function(){
		$('#allMeasure').removeClass('show').addClass('hide');
		$('#weightMeasure').removeClass('hide').addClass('show');
		$('#measureNavigation .active').removeClass('active');
		$('#allAfternoonSnack').addClass('active');
		$('#currentMeasureNameHistory').html("Afternoon Snack");
		gatMealDataFor(4);
		
	});
	$('#allDinner').click(function(){
		$('#allMeasure').removeClass('show').addClass('hide');
		$('#weightMeasure').removeClass('hide').addClass('show');
		$('#measureNavigation .active').removeClass('active');
		$('#allDinner').addClass('active');
		$('#currentMeasureNameHistory').html("Dinner");
		gatMealDataFor(5);
		
	});
	$('#allEveningSnack').click(function(){
		$('#allMeasure').removeClass('show').addClass('hide');
		$('#weightMeasure').removeClass('hide').addClass('show');
		$('#measureNavigation .active').removeClass('active');
		$('#allEveningSnack').addClass('active');
		$('#currentMeasureNameHistory').html("Evening Snack");
		gatMealDataFor(6);
		
	});	
	
	$('#allMeasure').ready(function(){
	$.get("/lifecoach/ManageMealLog?q=3", function(history) {	
		$('#logsTBodyElements').html('');
		$.each(history, function(i, d){
			$('#logsTBodyElements').append('<tr><td>'+ new Date(d.updatedTime).toLocaleDateString()+'</td><td>'+d.name+'</td><td>'+d.servingSize + ' ' + d.servingSizeUnit + '</td><td>'+d.calories+'</td><td>'+d.typeOfMeal + '</td><td><button type="submit" class="btn btn-primary" id="editMeal"'+ d.id  +'>Edit</button><button type="submit" class="btn btn-primary" id="deleteMeal"'+ d.id  +'>Delete</button></td></tr>');
		});		
	});
	});
	
	
	function gatMealDataFor(x){		
		// get measure history and draw graph
		$.get("/lifecoach/ManageMealLog?q=5&cmd="+x, function(history) {	
			$('#historyTBodyElements').html('');
			$.each(history, function(i, d){
				$('#historyTBodyElements').append('<tr>	<td>'+ new Date(d.updatedTime).toLocaleDateString()+'</td><td>'+d.name+'</td><td>'+d.servingSize + ' ' + d.servingSizeUnit + '</td><td>'+d.calories+'</td><td>'+d.typeOfMeal + '</td><td><button type="submit" class="btn btn-primary" id="editMeal"'+ d.id  +'>Edit</button><button type="submit" class="btn btn-primary" id="deleteMeal"'+ d.id  +'>Delete</button></td></tr>');
			});		
			return false;
		});	
		return false;
	}
	
	$('#btnLogNewMealLog').click(function(){
		var mdata = {
				name: $('#mealName').val(),
				servingTime: $('#servingTime').val(),
				servingSize: $('#mealServingSize').val(),
				servingUnit: $('#mealServingSizeUnit').val(),
				calories: $('#calories').val(),
				type: $('#mealType').val(),
				date: new Date($('#servingDate').val()).getTime()
			};
			$.post("/lifecoach/ManageMealLog?q=1", mdata, function(data){			
				$('#addMeasureModal').hide();		    	
		    });
	});
	/*$('mealName').typeahead({
		name: 'mealName',
		remote : 'http://localhost:8280/lifecoach/ManageMealLog?q=7'
		});*/
	

		$('#mealName').ready(function() {

						var mealData = [];
						var mealSize = [];
		$.get("/lifecoach/ManageMealLog?q=7", function(meal) {
			$.each(meal, function(i, d) {
				mealData[i] = d.prodName;
								// mealSize[d.prodName] = d.prodSize;
				});
			});
			$('#mealName').typeahead([ {
				name : 'mealName',
				local : mealData
					} ]);
		});
});