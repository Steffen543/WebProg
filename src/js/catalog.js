var selectedCatalog;
var questionNumber = 0;

(function () {
    
    function InitColorChange() {
        var catalogItems = document.getElementsByClassName("catalog-select-element");
        
        function changeColor(eventArgs) {
            var clickedItem = eventArgs.target;
            
            // reset all colors
            for (var i = 0; i < catalogItems.length; i++) {
                catalogItems[i].classList.add("unselected");
                catalogItems[i].classList.remove("selected");
            }
            // set colors of clicked item
            clickedItem.classList.remove("unselected");
            clickedItem.classList.add("selected");
            selectedCatalog = clickedItem.innerHTML;
        }
        
        // add event listener
        for (i = 0; i < catalogItems.length; i++) {
            catalogItems[i].addEventListener("click", changeColor);
        }
    }
    
    function InitStartGame() {
        
        function startGame() {
            if (selectedCatalog) {
                if(loadCatalog(selectedCatalog))
                {
                     document.getElementById("loginPanel").style.display = "none";
                    document.getElementById("gamePanel").style.display = "block";
                    document.getElementById("catalogPanel").style.display = "none";
                }
            } 
            else {
                alert("Es muss noch ein Fragenkatalog ausgewählt werden!");
            }
        }

        function loadCatalog(catalogName) {
  
            var catalogNamePath = "catalogs/" + selectedCatalog + ".xml";
    
            if(!doesFileExist(catalogNamePath))
            {
                alert(catalogName + ".xml wurde nicht gefunden");
                return false;
            }

            var xmlhttp = new XMLHttpRequest();
            xmlhttp.open("GET", catalogNamePath, false);
            xmlhttp.setRequestHeader('Content-Type', 'text/xml')
            xmlhttp.send("");
            xmlDoc = xmlhttp.responseXML;
            
            // load the current question
            var list_questions = xmlDoc.getElementsByTagName("question");
            var currentQuestion = list_questions[questionNumber];
            
            // get questions answers and question
            var questionText = currentQuestion.getElementsByTagName("questiontext")[0].textContent;
            var questionTimeout = currentQuestion.getElementsByTagName("timeout")[0].textContent;
            var answers = currentQuestion.getElementsByTagName("answer");
           
            document.getElementById("questionHeader").innerHTML = questionText;
            answerItems = document.getElementsByClassName("answer");

            // hide checkboxes
            for (i = 0; i < answerItems.length; i++) {
                answerItems[i].parentElement.parentElement.style.display = "none";
            }
            
            
            // set checkboxes and show
            for (i = 0; i < answerItems.length; i++) {
                answerItems[i].innerHTML = answers[i].textContent;
                answerItems[i].parentElement.parentElement.style.display = "block";
            }
            
            return true;
        }
        
        var startButton = document.getElementById("startGameButton");
        startButton.addEventListener("click", startGame);
    }
    
    InitColorChange();
    InitStartGame();
}());