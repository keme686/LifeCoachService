<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="Kemele and Elvis">

<title>Life Coach Measures</title>

<!-- Bootstrap core CSS -->
<link href="../css/bootstrap.css" rel="stylesheet">

<!-- Just for debugging purposes. Don't actually copy this line! -->
<!--[if lt IE 9]><script src="../../docs-assets/js/ie8-responsive-file-warning.js"></script><![endif]-->

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->

<link href="../css/carousel.css" rel="stylesheet">
</head>

<%
	String username = (String) session.getAttribute("username");
	if (username == null) {
		session.invalidate();
		response.sendRedirect(getServletContext().getContextPath()
				+ "/index.jsp");
	}
%>

<!-- NAVBAR
================================================== -->
<body>
	<div class="contianer">
		<div class="navbar-wrapper">

			<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
				<div class="container">

					<div class="navbar-header">
						<button type="button" class="navbar-toggle" data-toggle="collapse"
							data-target=".navbar-collapse">
							<span class="sr-only">Toggle navigation</span> <span
								class="icon-bar"></span> <span class="icon-bar"></span> <span
								class="icon-bar"></span>
						</button>
						<a class="navbar-brand" class="" href="/lifecoach">Life Coach</a>
					</div>

					<div class="navbar-collapse collapse">

						<ul class="nav navbar-nav">
							<li><a href="../account/home.jsp"><%=username%></a></li>
							<li><a href="../selfmonitoring/measure.jsp">Measure</a></li>
							<li><a href="../goaltracking/goaltracking.jsp">Goal tracking</a></li>
							<li class ="active"><a href="#">Meal Log</a></li>
							<li><a href="../schedule/schedule.jsp">Schedule</a></li>
						</ul>						
						<ul class="nav navbar-nav navbar-right">
							<li class="dropdown"><a href="#" class="dropdown-toggle"
								data-toggle="dropdown">Account <b class="caret"></b></a>
								<ul class="dropdown-menu">
									<li><a href="#">Profile</a></li>
									<li><a href="#">Settings</a></li>
									<li class="divider"></li>
									<li><button type="submit"
							class="btn navbar-btn btn-warning  navbar-right"
							id="btnSignOutUser">Sign out</button></li>
								</ul></li>
						</ul>
					</div>
				</div>
			</div>
			<!-- End of wrapper div (ending tag is this line below) -->
		</div>
		<!-- main content  -->		
		<div class="content">

			<div class="page-header">
				<h1>Your Meal Log</h1>
			<p align="right"><strong>Quote tip:</strong> <em>	<span>&quot;The more you try, the more you become
					successful&quot; --Elvis koci--</span></em>					
    		</p>
			</div>
			
			<div class="row" id="measuresContent">

				<!-- LIST OF Serving times LEFT PANEL -->
				<div class="col-sm-2">
					<ul class="nav nav-pills nav-stacked" id="measureNavigation">
						<li id="allMeasureLink" class="active"><a href="#all">All</a></li>
						<!-- <li id="allToday"><a href="#all">Todays' overall</a></li> -->
						<li  id="allBreakfast"><a href="#all">Breakfast</a></li>
						<li id="allMorningSnack"><a href="#all">Morning Snack</a></li>
						<li id="allLunch"><a href="#all">Lunch</a></li>
						<li id="allAfternoonSnack"><a href="#all">Afternoon Snack</a></li>
						<li id="allDinner"><a href="#all">Dinner</a></li>
						<li id="allEveningSnack"><a href="#all">Evening Snack</a></li>
						<!-- <li id="allThisWeek"><a href="#all">This Week</a></li>
						<li id="allMissingLogs"><a href="#all">Missing Logs</a></li> -->
					</ul>
				</div>								
				
				<div class ="col-sm-10">
					<div class="col-sm-offset-2 col-sm-1">
							<button type="submit" class="btn btn-primary" data-toggle="modal"
								data-target="#addMeasureModal">Today </button>
						</div>						
						<div class="col-sm-offset-1 col-sm-1">
							<button type="submit" class="btn btn-primary" data-toggle="modal"
								data-target="#addMeasureModal">Weekly</button>
						</div>						
						<div class="col-sm-offset-1  col-sm-1">
							<button type="submit" class="btn btn-primary" data-toggle="modal"
								data-target="#addMeasureModal">Log New Meal </button>
						</div>	
				</div>
				<!-- MEASURES CONTENT -->
				<div id="allMeasure" class="col-sm-10 show" >
					<div class="col-sm-10">										
						<div class="col-sm-12">						
					 	    <table class="table table-hover">
					 	    	<thead>
					 	    	 <tr>
					 	    	   <th>Date</th>
					 	    	   <th>Name</th>
					 	    	   <th>Size</th>
					 	    	   <th>Calories</th>
					 	    	   <th>Type</th>
					 	    	   <th>Action</th>
					 	    	 </tr>
					 	    	</thead>
					 	    	<tbody id="logsTBodyElements">					 	    	 
					 	    	</tbody>
					 	    </table>
					 	    <div class="col-sm-offset-9 col-sm-1">
								<button type="submit" class="btn btn-primary" id="backToMeasure">Graph</button>
							</div>
						</div>
						<div class="col-sm-10" style="margin-top: 30px;" id="overallProgressMsg">
							
							<div id="caloriesInfo">Calories:</div>
							<p>Your overall progress shows you are more active this week! Your score shows: </p>
							<div class="progress  progress-striped active">
							<div class="progress-bar progress-bar-success" role="progressbar"
								aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
								style="width: 60%">
								<span >60 calories</span>
							</div>							
						</div>
						</div>
					</div>
				</div>
				
				<!-- daily Meal per serving time -->
				<div id="weightMeasure" class="hide">
					<div class="col-sm-9" id="measureGraphDiv">
					
					<div class="col-sm-9">
						   <p>Today's <strong><span id="currentMeasureNameHistory"></span></strong>  Log:</p>
						</div>
						<div class="col-sm-9">						
					 	    <table class="table table-hover">
					 	    	<thead>
					 	    	 <tr>
					 	    	   <th>Date</th>
					 	    	   <th>Name</th>
					 	    	   <th>Size</th>
					 	    	   <th>Calories</th>
					 	    	   <th>Type</th>
					 	    	   <th>Action</th>
					 	    	 </tr>
					 	    	</thead>
					 	    	<tbody id="historyTBodyElements">					 	    	 
					 	    	</tbody>
					 	    </table>
					 	    <div class="col-sm-offset-10 col-sm-1">
								<button type="submit" class="btn btn-primary" id="backToMeasure">Graph</button>
							</div>
						</div>
						
					<div class="col-sm-9">
							<div class="col-sm-10" id="feedbackRow" style="margin-top: 30px;">
								<p>Your progress on measures seems constant and well
									regulated, if you want to increase or decrease you might want
									to start tracking by setting a goal in the goal tracking
									module.</p>
							</div>
						</div>
					</div>
				</div>
				
				</div>
				</div>				

<!-- End of main Container div (ending tag is below this line) -->
</div>


<!--NEW MEASURE  Modal -->
	<div class="modal fade" id="addMeasureModal" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Log New Meal</h4>
				</div>

				<div class="modal-body">
					<form class="form-horizontal" role="form">
						<div class="form-group">
							<label class="col-sm-3  control-label" for="mealName">Meal
								Name</label>
							<div class="col-sm-6">
								<input type="text" class="form-control typeahead-devs"
								  name ="mealName"	id="mealName" placeholder="Meal Name">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3  control-label" for="mealServingSize">Serving 
								Size</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" id="mealServingSize"
									placeholder="Serving size">
							</div>
								<div class="col-sm-3">
								<input type="text" class="form-control" id="mealServingSizeUnit"
									placeholder="g, ml, cup, ..">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3  control-label" for="calories">Calories</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" id="calories"
									placeholder="calories">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3  control-label" for="servingTime">Serving Time</label>
							<div class="col-sm-4">							
									<select id="servingTime"  class="form-control" >
									  <option>Breakfast</option>
									  <option>Morning Snack</option>
									  <option>Lunch</option>
									  <option>Afternoon Snack</option>
									  <option>Dinner</option>
									  <option>Evening Snack</option>
									</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3  control-label" for="servingDate">Date</label>
							<div class="col-sm-5">
								<input type="date" class="form-control" id="servingDate"
									placeholder="Day">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3  control-label" for="mealType">Type of meal</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" id="mealType"
									placeholder="Meal type">
							</div>
						</div>
					</form>
				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					<button type="button" class="btn btn-primary"
						id="btnLogNewMealLog">Log</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
	
	
	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="../js/jquery-2.1.0.min.js"></script>
	
	<script src="../js/jquery.flot.min.js"></script>
	<script src="../js/jquery.flot.time.js"></script>
	
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/typeahead.min.js"></script>
	<script src="../js/holder.js"></script>
	
	<script src="../js/account/login.js"></script>
	<script src="../js/meallog/meal.js"></script>
</body>
</html>