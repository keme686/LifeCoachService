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

<title>Master Life Coach</title>

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

	String username= (String) session.getAttribute("username");
	if (username == null) {
		session.invalidate();
		response.sendRedirect(getServletContext().getContextPath() + "/index.jsp");
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
							<li class="active"><a href="#"><%=username%></a></li>
							<li><a href="../selfmonitoring/measure.jsp" id="usermeasure">Measure</a></li>
							<li><a href="../goaltracking/goaltracking.jsp">Goal tracking</a></li>
							<li><a href="#meallog">Meal Log</a></li>
							<li><a href="#contact">Suggestions</a></li>
						</ul>
						<!-- <button type="submit"
							class="btn navbar-btn btn-warning navbar-right"
							id="btnSignOutUser">Sign out</button> -->
						<ul class="nav navbar-nav navbar-right">
							<li class="dropdown"><a href="#" class="dropdown-toggle"
								data-toggle="dropdown">Account <b class="caret"></b></a>
								<ul class="dropdown-menu">
									<li><a href="#">Profile</a></li>
									<li><a href="#">Settings</a></li>
									<li class="divider"></li>
									<li><button type="submit"
							class="btn navbar-btn btn-warning  navbar-right"
							id="btnSignOutUser">Sign out</button><!-- <a href="#"  id="btnSignOutUser">Sign out</a> --></li>
								</ul></li>
						</ul>
					</div>
				</div>
			</div>

		</div>

		<div class="container jumbotron">
			<div class="row featurette">
				<div class="col-md-6">
					<h2 class="featurette-heading">
						Weekly progress.<span class="text-muted">You are doing
							GREAT!</span>
					</h2>
					<p class="lead">It seems this week you are doing very good.
						Your data entry history says:</p>
					<div class="progress  progress-striped active">
						<div class="progress-bar progress-bar-success" role="progressbar"
							aria-valuenow="80" aria-valuemin="0" aria-valuemax="100"
							style="width: 80%;">
							<span class="sr-only">80% Complete</span>
						</div>
					</div>
				</div>
				<div class="col-md-5">
					<div id="weekprogressbar" style="width: 400px; height: 300px;"></div>

				</div>
			</div>

			<div class="row">
				<div class="col-xs-6 col-md-3">
					<div class="thumbnail">
						<div id="weekprogressbar1" style="width: 200px; height: 200px;"></div>
						<div class="caption">
							<h3>Weight</h3>
							<p>description</p>
						</div>
					</div>
				</div>
				<div class="col-xs-6 col-md-3">
					<div class="thumbnail">
						<div id="weekprogressbar2" style="width: 200px; height: 200px;"></div>
						<div class="caption">
							<h3>Thumbnail label</h3>
							<p>description</p>
						</div>
					</div>
				</div>
				<div class="col-xs-6 col-md-3">
					<div class="thumbnail">
						<div id="weekprogressbar3" style="width: 200px; height: 200px;"></div>
						<div class="caption">
							<h3>Thumbnail label</h3>
							<p>description</p>
						</div>
					</div>
				</div>
				<div class="col-xs-6 col-md-3">
					<div class="thumbnail">
						<div id="weekprogressbar4" style="width: 200px; height: 200px;"></div>
						<div class="caption">
							<h3>Thumbnail label</h3>
							<p>description</p>
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>
	<!-- main container ends -->
	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="../js/jquery-2.1.0.min.js"></script>
	<script src="../js/jquery.flot.min.js"></script>
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/holder.js"></script>
	<script src="../js/account/login.js"></script>

	<script type="text/javascript">
		var data, data1, options, chart, chart1, chart2, chart3, chart4, data2, data3;
		data1 = [ [ 1, 2 ], [ 2, 3 ], [ 3, 2 ], [ 4, 8 ], [ 5, 7 ] ];
		data2 = [ [ 1, 4 ], [ 2, 2 ], [ 3, 5 ], [ 4, 7 ], [ 5, 6 ] ];
		data3 = [ [ 1, 2 ], [ 2, 4 ], [ 3, 6 ], [ 4, 8 ], [ 5, 10 ] ];
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
		options = {
			series : {
				lines : {
					show : true
				},
				points : {
					show : false
				},
				bars : {
					show : false
				}
			}
		};
		var popopt = {
			animation : true,
			placement : "bottom",
			trigger : "click",
			title : "Sign In",
			html : true,
			container : 'body'
		};

		$(document).ready(function() {
			chart = $.plot($("#weekprogressbar"), data, options);
			chart1 = $.plot($("#weekprogressbar1"), [ {
				data : data1,
				label : "weight"
			} ], options);
			chart2 = $.plot($("#weekprogressbar2"), [ data2 ], options);
			chart3 = $.plot($("#weekprogressbar3"), [ data3 ], options);
			chart4 = $.plot($("#weekprogressbar4"), [ data3 ], options);
		});
	</script>
</body>
</html>