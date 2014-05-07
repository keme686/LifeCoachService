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
							<li class="active"><a href="#measure">Measure</a></li>
							<li><a href="../goaltracking/goaltracking.jsp">Goal tracking</a></li>
							<li><a href="../meallog/meal.jsp">Meal Log</a></li>
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
		<!-- end of nav-wrapper (below this line) -->
		</div>

       <!-- main content  -->		
		<div class="content">

			<div class="page-header">
				<h1>Your Measure</h1>
			<p align="right"><strong>Quote tip:</strong> <em>	<span>&quot;The more you try, the more you become
					successful&quot; --Elvis koci--</span></em>
					<!-- <p id="hoverdata">Measure value
    						(<span id="x">0</span>, <span id="y">0</span>). <span id="clickdata"></span></p> -->
    		</p>
			</div>
			
			<div class="row" id="measuresContent">

				<!-- LIST OF MEASURES LEFT PANEL -->
				<div class="col-sm-2">
					<ul class="nav nav-pills nav-stacked" id="measureNavigation">
						<li id="allMeasureLink" class="active"><a href="#all">All</a></li>
					</ul>
				</div>								

				<!-- MEASURES CONTENT -->
				<div id="allMeasure" class="show">
					<div class="col-sm-7">
						<div class="col-sm-offset-6 col-sm-1">
							<button type="submit" class="btn btn-primary" data-toggle="modal"
								data-target="#addMeasureModal">New Measure</button>
						</div>						
						<div class="col-sm-7" id="weekprogressbarx"
							style="width: 700px; height: 200px;"></div>
						<div style="margin-top: 30px;" id="overallProgressMsg">
							
							<div id="bmiInfo">bmi</div>
							<p>Your overall progress shows you are more active this week! Your score shows: </p>
							<div class="progress  progress-striped active">
							<div class="progress-bar progress-bar-success" role="progressbar"
								aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
								style="width: 60%">
								<span >60 Points</span>
							</div>
							
						</div>
						</div>
					</div>
				</div>
				<!-- MEASURES CONTENT ENDS HERE -->

				<div id="weightMeasure" class="hide">
					<div class="col-sm-7" id="measureGraphDiv">
						<form class="form-inline  col-sm-offset-1" role="form"
							id="updateDatapointForm">
							<div class="form-group">
								<label class="sr-only" for="measureValueUpdate">Current
									Measure</label> <input type="text" class="form-control"
									id="measureValueUpdate" placeholder="CurrentMeasure">
							</div>
							<div class="form-group">
								<label class="sr-only" for="measureUnitUpdate">Unit</label> <input
									type="text" size="5" class="form-control"
									id="measureUnitUpdate" placeholder="kg, m, ml, steps, hrs">
							</div>
							<div class="form-group">
								<label class="sr-only" for="measureDayUpdate">Date</label> <input
									type="date" size="5" class="form-control" id="measureDayUpdate"
									placeholder="Day">
							</div>
							<div class="form-group" id="updateDatapointBtnDiv">
								<!-- <button type="submit" class="btn btn-primary" id="updateDatapoint">Update</button> -->
							</div>							
						</form>
						
						<div  id="weekprogressbar"
							style="width: 700px; height: 200px;"></div>
						<div class="col-sm-12">
							<div class="col-sm-offset-6 col-sm-1">
								<button type="submit" class="btn btn-primary"
									data-toggle="modal" data-target="#addGoalModal">New
									Goal</button>
							</div>
							<div class="col-sm-offset-3 col-sm-1">
								<button type="submit" class="btn btn-primary" id="showHistoryBtnDiv">Update</button>								
							</div>
						</div>
						<div class="col-sm-12">
							<div class="col-sm-10" id="feedbackRow" style="margin-top: 30px;">
								<p>Your progress on measures seems constant and well
									regulated, if you want to increase or decrease you might want
									to start tracking by setting a goal in the goal tracking
									module.</p>
							</div>
						</div>
					</div>
					<div id="measureHistoryDiv" class="hide">
						<div class="col-sm-7">
						   <p>Your <strong><span id="currentMeasureNameHistory"></span></strong>  History:</p>
						</div>
						<div class="col-sm-7">						
					 	    <table class="table table-hover">
					 	    	<thead>
					 	    	 <tr>
					 	    	   <th>Date</th>
					 	    	   <th>Value</th>
					 	    	   <th>Unit</th>
					 	    	 </tr>
					 	    	</thead>
					 	    	<tbody id="historyTBodyElements">					 	    	 
					 	    	</tbody>
					 	    </table>
					 	    <div class="col-sm-offset-10 col-sm-1">
								<button type="submit" class="btn btn-primary" id="backToMeasure">Graph</button>
							</div>
						</div>
					</div>
				
					<div class="col-sm-offset-1 col-sm-2" style="border: 1px solid #777; height:300px">
					    <ul>
					     <li>Daily reminder</li>
					     <li>Weekly reminder</li>
					     <li>Missing updates</li>
					    </ul>
					</div>
				</div>
				
				
			</div>
		</div>
	</div>

	<!--NEW MEASURE  Modal -->
	<div class="modal fade" id="addMeasureModal" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Add New Measure</h4>
				</div>

				<div class="modal-body">
					<form class="form-horizontal" role="form">
						<div class="form-group">
							<label class="col-sm-3  control-label" for="measureName">Measure
								Name</label>
							<div class="col-sm-6">
								<input type="text" class="form-control typeahead-devs"
									id="measureName" placeholder="Measure Name">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3  control-label" for="measureValuex">Current
								Measure</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" id="measureValuex"
									placeholder="CurrentMeasure">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3  control-label" for="measureUnit">Unit</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" id="measureUnit"
									placeholder="kg, m, ml, steps, hrs">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3  control-label" for="measureDate">Date</label>
							<div class="col-sm-5">
								<input type="date" class="form-control" id="measureDate"
									placeholder="Day">
							</div>
						</div>
					</form>
				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					<button type="button" class="btn btn-primary"
						id="btnSaveNewMeasure">Save</button>
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
	<script src="../js/selfmonitoring/measure.js"></script>
</body>
</html>