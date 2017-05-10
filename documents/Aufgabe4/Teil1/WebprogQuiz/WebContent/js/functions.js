function doesFileExist(urlToFile)
{
    var xhr = new XMLHttpRequest();
    xhr.open('HEAD', urlToFile, false);
    xhr.send();
     
    if (xhr.status == "404") {
        return false;
    } else {
        return true;
    }
}

(function () {
    
    var body = document.getElementsByTagName("body")[0];
    
    body.addEventListener("click", function() {
       alert("LOL"); 
    }, true);

    
    
}());