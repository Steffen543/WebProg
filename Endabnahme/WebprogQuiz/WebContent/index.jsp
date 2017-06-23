<%@ page import="de.fhwgt.quiz.application.*" %>
<%@ page import="de.fhwgt.quiz.error.*" %>
<%@ page import="java.util.*" %>
<%@ page import="de.fhwgt.quiz.loader.*" %>
<%@ page import="org.jdom2.*" %>
<%
	Quiz mainQuiz = Quiz.getInstance();
	String location= application.getRealPath("/") + "/catalogs";
	FilesystemLoader fsLoader = new FilesystemLoader(location);
	mainQuiz.initCatalogLoader(fsLoader);
	Map<String, Catalog> allCatalogsMap = mainQuiz.getCatalogList();
%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>Web-Quiz</title>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<meta name="description" content="" />
		<meta name="keywords" content="" />
		<link rel="stylesheet" type="text/css" href="css/style.css">
		<script src="https://use.fontawesome.com/8f2f9e9aa4.js"></script>
	
	</head>
	<body>
		<!-- Header -->
		<header id="header">
			<h1 id="headline" ><i class="fa fa-gamepad" aria-hidden="true"></i> Web-Quiz</h1>
		</header>
		<!-- Main -->
		<div id="main">
			<section class="container">
                
				<!-- login -->
				<div class="login" id="loginPanel">
                    <h2>Login</h2>
					<section>
					
						<header><h3><i class="fa fa-user-circle" aria-hidden="true"></i> Username </h3></header>
						<input name="username" type="text" id="input_loginName" placeholder="Username" />
						<br />
						<button id="loginButton" type="submit" class="button special"><i class="fa fa-sign-in" aria-hidden="true"></i> Login</button>
					</section>
				</div>
				<div id="startGamePanel" class="start">
					<button id="startGameButton" style="width:350px;height:75px;" type="submit" class="button special"><i class="fa fa-play" ></i> Spiel starten</button>
					
				</div>
                <!-- game panel -->
                <div class="game" id="gamePanel">
                    <h2 id="questionHeader">Frage?</h2>
                    <section>
                        <div class="answerDiv">
                            <label> <input name="sq" data-an="0" type="radio" id="a1" > <span id="a1text" class="answer">Antwort</span></label>
                        </div>
                        <div class="answerDiv">
                           <label> <input name="sq" data-an="1" type="radio" id="a2" >  <span id="a2text" class="answer">Antwort</span></label>
                        </div>
                        <div class="answerDiv">
                            <label> <input name="sq" data-an="2" type="radio" id="a3" >  <span id="a3text" class="answer">Antwort</span></label>
                        </div>
                        <div class="answerDiv">
                           <label> <input name="sq" data-an="3" type="radio" id="a4" >  <span id="a4text" class="answer">Antwort</span></label>
                        </div>
                        <div id="timeoutPanel" style="display:none;">
                           <h2 style="color:red;">Zeit ist abgelaufen!</h2>
                        </div>
                        
                        <button id="sendQuestionButton" type="submit" class="button special"> 
							<i class="fa fa-arrow-right" aria-hidden="true"></i> Frage absenden
						</button>
                    </section>
                </div>
				<!-- Sidebar -->
				<div class="sidebar">
					<section id="catalogPanel">
						<header>
							<h4><i class="fa fa-th-list" aria-hidden="true"></i> Catalogs</h4>
						</header>
						<div id="catalogPlaceHolder" ></div>
						
					</section>
					<section>
						<header>
							<h4><i class="fa fa-trophy" aria-hidden="true"></i> Spieler</h4>
						</header>
						<table class="score" id="playerTable">
							<tr>
								<th>Player</th>
								<th>Score</th>
							</tr>
							
						</table>
					</section>
				</div>
			</section>
		</div>
		<!-- Footer -->
		<footer id="footer">
			<span class="copyright">&copy; Copyright 2017. Steffen Koch, Andreas Muss &amp; Artur Eichler</span>
		</footer>
		<script type="text/javascript" src="js/catalogLoader.js"></script>
		<script type="text/javascript" src="js/rfc.js"></script>
		<script type="text/javascript" src="js/rfc_commands.js"></script>
		<script type="text/javascript" src="js/moveHeadline.js"></script>
		<script type="text/javascript" src="js/mainsocket.js"></script>

	</body>
</html>

