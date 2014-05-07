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
<link rel="shortcut icon" href="../../docs-assets/ico/favicon.png">

<title>Master Life Coach</title>

<!-- Bootstrap core CSS -->
<link href="css/bootstrap.css" rel="stylesheet">

<!-- Just for debugging purposes. Don't actually copy this line! -->
<!--[if lt IE 9]><script src="../../docs-assets/js/ie8-responsive-file-warning.js"></script><![endif]-->

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->

<link href="css/carousel.css" rel="stylesheet">
</head>

<!-- NAVBAR
================================================== -->
<body>
	<div class="navbar-wrapper">
		<!-- <div class="container"> -->

		<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
			<div class="container">

				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse"
						data-target=".navbar-collapse">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="#">Life Coach</a>
				</div>

				<div class="navbar-collapse collapse">

					 <ul class="nav navbar-nav">
					 	<%
					 	String username= (String) session.getAttribute("username");
						if (username != null) {
					 	%>
					 	<li class="active"><a href="#"><%=username%></a></li>
							<li><a href="selfmonitoring/measure.jsp" id="usermeasure">Measure</a></li>
							<li><a href="goaltracking/goaltracking.jsp">Goal tracking</a></li>
							<li><a href="meallog/meal.jsp">Meal Log</a></li>
							<li><a href="schedule/schedule.jsp">Schedule</a></li>
							<li><a href="#about">About</a></li>
							<li><a href="#contact">Contact</a></li>
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
							id="btnSignOutUser">Sign out</button><!-- <a href="#"  id="btnSignOutUser">Sign out</a> --></li>
								</ul></li>
						</ul>
					   <%}else{ %>							
							<li><a href="#about">About</a></li>
							<li><a href="#contact">Contact</a></li>
						</ul> 
					<form class="navbar-form navbar-right" role="form">

						<div class="form-group">
							<input type="text" id="loginUsername" placeholder="Username"
								class="form-control">
						</div>
						<div class="form-group">
							<input type="password" placeholder="Password" id="loginPassword"
								class="form-control">
						</div>
						<button type="submit" class="btn btn-success" id="btnSignInUser">Sign in</button>
						<button class="btn btn-primary" data-toggle="modal"
							data-target="#signupmodal">Sign up</button>
					</form>
					<%} %>
				</div>
			</div>
		</div>

		<!-- </div> -->
	</div>


	<!-- Carousel
    ================================================== -->
	<div id="myCarousel" class="carousel slide" data-ride="carousel">
		<!-- Indicators -->
		<ol class="carousel-indicators">
			<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
			<li data-target="#myCarousel" data-slide-to="1"></li>
			<li data-target="#myCarousel" data-slide-to="2"></li>
		</ol>
		<div class="carousel-inner">
			<div class="item active">
				<img src="images/slide0.jpg" alt="First slide">
				<div class="container">
					<div class="carousel-caption">
						<h1 id="head1">Life Coach Service</h1>
						<p>Life coach service helps you to lifestyle and health self
							monitoring, goal tracking, log everyday mean, feedback based on
							your progres, and so much more.</p>
						<p>
							<a class="btn btn-lg btn-primary" href="#" role="button">Sign
								up today</a>
						</p>
					</div>
				</div>
			</div>

			<div class="item">
				<img src="images/goaltree.jpg" alt="Second slide">
				<div class="container">
					<div class="carousel-caption">

						<h1>Life style and Health self monitoring</h1>

						<p>Add a life style and health measure and start monitoring
							your progress every hour and everyday.</p>
						<div class="progress">
							<div class="progress-bar progress-bar-success" role="progressbar"
								aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
								style="width: 60%;">
								<span class="sr-only">60% Complete</span>
							</div>
						</div>
						<div class="progress">
							<div class="progress-bar progress-bar-warning" role="progressbar"
								aria-valuenow="20" aria-valuemin="0" aria-valuemax="100"
								style="width: 20%">
								<span class="sr-only">20% Complete (warning)</span>
							</div>
						</div>
						<div class="progress">
							<div class="progress-bar progress-bar-danger" role="progressbar"
								aria-valuenow="20" aria-valuemin="0" aria-valuemax="100"
								style="width: 20%">
								<span class="sr-only">20% Complete</span>
							</div>
						</div>
						<p>
							<a class="btn btn-lg btn-primary" href="#" role="button">Learn
								more</a>
						</p>

					</div>
				</div>
			</div>
			<div class="item">
				<img src="images/slide1.jpg" alt="Third slide">
				<div class="container">
					<div class="carousel-caption">

						<h1>Goal Tracking.</h1>
						<p>Cras justo odio, dapibus ac facilisis in, egestas eget
							quam. Donec id elit non mi porta gravida at eget metus. Nullam id
							dolor id nibh ultricies vehicula ut id elit.</p>

						<div class="progress">
							<div class="progress-bar progress-bar-success" role="progressbar"
								aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
								style="width: 60%;">
								<span class="sr-only">60% Complete</span>
							</div>
						</div>
						<div class="progress">
							<div class="progress-bar progress-bar-warning" role="progressbar"
								aria-valuenow="20" aria-valuemin="0" aria-valuemax="100"
								style="width: 20%">
								<span class="sr-only">20% Complete (warning)</span>
							</div>
						</div>
						<div class="progress">
							<div class="progress-bar progress-bar-danger" role="progressbar"
								aria-valuenow="20" aria-valuemin="0" aria-valuemax="100"
								style="width: 20%">
								<span class="sr-only">20% Complete</span>
							</div>
						</div>


						<p>
							<a class="btn btn-lg btn-primary" href="#" role="button">Browse
								gallery</a>
						</p>
					</div>
				</div>
			</div>
		</div>
		<a class="left carousel-control" href="#myCarousel" data-slide="prev"><span
			class="glyphicon glyphicon-chevron-left"></span></a> <a
			class="right carousel-control" href="#myCarousel" data-slide="next">
			<span class="glyphicon glyphicon-chevron-right"></span>
		</a>
	</div>
	<!-- /.carousel -->

	<!-- Marketing messaging and featurettes
    ================================================== -->
	<!-- Wrap the rest of the page in another container to center all the content. -->

	<div class="container marketing">

		<!-- Three columns of text below the carousel -->
		<div class="row">
			<div class="col-lg-4">
				<img class="img-circle" src="images/lifestyle1.jpg"
					alt="Happy life style">
				<h2>Self monitoring</h2>
				<p>The Self-Care Assessment can be used to examine the ways in
					which you are practicing self-care and whether there are imbalances
					across different domains of well-being. It may also give you ideas
					for additional things you can do in the future to help prevent
					stress, burnout, and compassion fatigue, and also to maintain and
					enhance your well-being.</p>
				<p>
					<a class="btn btn-default" href="#" role="button">View details
						&raquo;</a>
				</p>
			</div>
			<!-- /.col-lg-4 -->
			<div class="col-lg-4">
				<img class="img-circle" src="images/goaltracking.jpg"
					alt="Generic placeholder image">
				<h2>Goal Tracking</h2>
				<p>You may find greater success in putting your self-care plan
					into action if you set and work toward specific goals. Many people
					find that first setting definite goals and then breaking each goal
					into small, manageable steps makes change less overwhelming and
					more achievable. When setting goals, the important to keep them
					realistic and achievable. When working toward a goal, its important
					to create a plan, monitor your progress, and adjust accordingly
					over time.</p>
				<p>
					<a class="btn btn-default" href="#" role="button">View details
						&raquo;</a>
				</p>
			</div>
			<!-- /.col-lg-4 -->
			<div class="col-lg-4">
				<img class="img-circle" src="images/meallog3.jpg"
					alt="Generic placeholder image">
				<h2>Meal log</h2>
				<p>Donec sed odio dui. Cras justo odio, dapibus ac facilisis in,
					egestas eget quam. Vestibulum id ligula porta felis euismod semper.
					Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum
					nibh, ut fermentum massa justo sit amet risus.</p>
				<p>
					<a class="btn btn-default" href="#" role="button">View details
						&raquo;</a>
				</p>
			</div>
			<!-- /.col-lg-4 -->
		</div>
		<!-- /.row -->



		<!-- START THE FEATURETTES -->

		<hr class="featurette-divider">

		<div class="row featurette">
			<div class="col-md-7">
				<h2 class="featurette-heading">
					Self monitoring <span class="text-muted"> check your progress graphically.</span>
				</h2>
				<p class="lead">Monitor your everyday activity, Donec ullamcorper nulla non metus auctor
					fringilla. Vestibulum id ligula porta felis euismod semper.
					Praesent commodo cursus magna, vel scelerisque nisl consectetur.
					Fusce dapibus, tellus ac cursus commodo.</p>
			</div>
			<div class="col-md-5">
				<div id="placeholer" style="width: 500px; height: 300px;"></div>

			</div>
		</div>

		<hr class="featurette-divider">


		<div class="row featurette">
			<div class="col-md-5">
				<img class="featurette-image img-responsive"
					src="images/walking.jpg" alt="Generic placeholder image">
			</div>
			<div class="col-md-7">
				<h2 class="featurette-heading">
					Goal Tracking. <span class="text-muted">See for
						yourself.</span>
				</h2>
				<p class="lead">Donec ullamcorper nulla non metus auctor
					fringilla. Vestibulum id ligula porta felis euismod semper.
					Praesent commodo cursus magna, vel scelerisque nisl consectetur.
					Fusce dapibus, tellus ac cursus commodo.</p>
			</div>
		</div>

		<hr class="featurette-divider">


		<div class="row featurette">
			<div class="col-md-7">
				<h2 class="featurette-heading">
					And lastly, Meal log. <span class="text-muted">Checkmate.</span>
				</h2>
				<p class="lead">Donec ullamcorper nulla non metus auctor
					fringilla. Vestibulum id ligula porta felis euismod semper.
					Praesent commodo cursus magna, vel scelerisque nisl consectetur.
					Fusce dapibus, tellus ac cursus commodo.</p>
			</div>
			<div class="col-md-5">
				<img class="featurette-image img-responsive"
					src="images/meal.jpg" alt="Generic placeholder image">
			</div>
		</div>

		<hr class="featurette-divider">

		<!-- /END THE FEATURETTES -->




		<!-- FOOTER -->
		<footer>
			<p class="pull-right">
				<a href="#">Back to top</a>
			</p>
			<p>
				&copy; 2013 E&K, Inc. &middot; <a href="#">Privacy</a> &middot; <a
					href="#">Terms</a>
			</p>
		</footer>

	</div>
	<!-- /.container -->


	<!-- Button trigger sign up modal 
=========================================================================================-->



	<!-- Modal -->
	<div class="modal fade" id="signupmodal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Create New Account</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form">
						<div class="form-group">
							<label for="inputName" class="col-sm-3 control-label">Name<em>*</em>
							</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="inputName"
									placeholder="Name">
							</div>
						</div>
						<div class="form-group">
							<label for="inputUsername" class="col-sm-3 control-label">Username<em>*</em></label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="inputUsername"
									placeholder="Username">
							</div>
						</div>

						<div class="form-group">
							<label for="inputPassword" class="col-sm-3 control-label">Password<em>*</em></label>
							<div class="col-sm-9">
								<input type="password" class="form-control" id="inputPassword"
									placeholder="Password">
							</div>
						</div>

						<div class="form-group">
							<label for="inputEmail" class="col-sm-3 control-label">Email</label>
							<div class="col-sm-9">
								<input type="email" class="form-control" id="inputEmail"
									placeholder="Email">
							</div>
						</div>


						<div class="form-group">
							<label for="inputDateOfBirth" class="col-sm-3 control-label">Date
								Of Birth</label>
							<div class="col-sm-4">
								<input type="datetime" name="bdaytime" id="inputDateOfBirth"
									class="form-control" placeholder="Date of birth">
							</div>
						</div>

						<div class="form-group">
							<label for="gender" class="col-sm-3 control-label">Gender</label>
							<div class="col-sm-9">
								<div class="radio" class="col-sm-4">
									<label><input type="radio" name="inputGender"
										id="inputGenderM" value="M">M </label>
								</div>
								<div class="radio" class="col-sm-4">
									<input type="radio" name="inputGender" id="inputGenderF"
										value="F"><label>F</label>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label for="inputLocation" class="col-sm-3 control-label">Location</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="inputLocation"
									placeholder="Location">
							</div>
						</div>

						<div class="form-group">
							<label for="inputMobile" class="col-sm-3 control-label">Mobile
								No.</label>
							<div class="col-sm-4">
								<input type="tel" class="form-control" id="inputMobile"
									placeholder="Mobile">
							</div>
						</div>

					</form>
				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary" id="btnSignupNew">Sign up</button>
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
	<script src="js/jquery-2.1.0.min.js"></script>
	<script src="js/jquery.flot.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/holder.js"></script>
	<script src="js/account/login.js"></script>
	<script type="text/javascript">
		var data, data1, options, chart, data2, data3;
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
					show : true
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
			chart = $.plot($("#placeholer"), data, options);
			$('#signin').popover(popopt);
		});
	</script>
</body>
</html>