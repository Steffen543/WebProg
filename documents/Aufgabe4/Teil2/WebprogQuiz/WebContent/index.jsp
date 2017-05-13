<%@ page import="de.fhwgt.quiz.application.*" %>
<%@ page import="de.fhwgt.quiz.error.*" %>
<%@ page import="java.util.*" %>
<%@ page import="de.fhwgt.quiz.loader.*" %>
<%@ page import="org.jdom2.*" %>
<%
	Quiz mainQuiz = Quiz.getInstance();

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
			<h1 id="headline" ><i class="fa fa-question-circle-o" aria-hidden="true"></i> Web-Quiz</h1>
		</header>
		<!-- Main -->
		<div id="main">
			<section class="container">
                
				<!-- login -->
				<div class="login" id="loginPanel">
                    <h2>Login</h2>
					<section>
						<form id="loginForm" action="index.jsp" method="post">
							<header><h3><i class="fa fa-user-circle" aria-hidden="true"></i> Username </h3></header>
							<input name="username" type="text" id="input_loginName" placeholder="Username" />
							<br />
							<button id="loginButton" type="submit" class="button special"><i class="fa fa-sign-in" aria-hidden="true"></i> Login</button>
							<button id="startGameButton" type="submit" class="button special"><i class="fa fa-play" aria-hidden="true"></i> Spiel starten</button>
						</form>
						<%
							String loginName = request.getParameter("username");
							if(loginName != null)
							{
								if(loginName.length() < 2)
								{
									out.println("<h3 style='color:#D9534F;'><i class='fa fa-info-circle' aria-hidden='true'></i>Name muss mindestens 2 Zeichen enthalten</h3>");
								}
								else
								{
									QuizError error = new QuizError();
									Player newPlayer = mainQuiz.createPlayer(loginName, error);
									
									if(error.isSet())
									{
										out.println("<h3 style='color:#D9534F;'><i class='fa fa-info-circle' aria-hidden='true'></i>" + error.getDescription() + "</h3>");
									}
								}
							}
						%>
						
					</section>
				</div>
                <!-- game panel -->
                <div class="game" id="gamePanel">
                    <h2 id="questionHeader">Frage?</h2>
                    <section>
                        <div class="answerDiv">
                            <label> <input type="checkbox" name="a1"> <span class="answer">Antwort</span></label>
                        </div>
                        <div class="answerDiv">
                           <label> <input type="checkbox" name="a2">  <span class="answer">Antwort</span></label>
                        </div>
                        <div class="answerDiv">
                            <label> <input type="checkbox" name="a3">  <span class="answer">Antwort</span></label>
                        </div>
                        <div class="answerDiv">
                           <label> <input type="checkbox" name="a4">  <span class="answer">Antwort</span></label>
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
						<%
						String location= application.getRealPath("/") + "/catalogs";
						FilesystemLoader fsLoader = new FilesystemLoader(location);
						mainQuiz.initCatalogLoader(fsLoader);
						Map<String, Catalog> allCatalogsMap = mainQuiz.getCatalogList();
						for(Map.Entry<String, Catalog> catalog : allCatalogsMap.entrySet())
						{
							out.println("<a href='#' class='catalog-select-element unselected'>" + catalog.getValue().getName() + "</a>");
						}
						%>
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
							<%
							Collection<Player> players = mainQuiz.getPlayerList();
							for(Player p : players)
							{
								out.println("<tr>");
								out.println("<td>" + p.getName() + "</td>");
								out.println("<td>" + p.getScore() + "</td>");
								out.println("</tr>");
							}
							%>
						</table>
					</section>
				</div>
			</section>
		</div>
		<!-- Footer -->
		<footer id="footer">
			<span class="copyright">&copy; Copyright 2017. Steffen Koch, Andreas Muss &amp; Artur Eichler</span>
		</footer>
		<script type="text/javascript" src="js/moveHeadline.js"></script>
		<!-- <script type="text/javascript" src="js/player.js"></script>-->
		
		<!-- <script type="text/javascript" src="js/functions.js"></script>
		<script type="text/javascript" src="js/catalog.js"></script>
		 -->
	</body>
</html>

