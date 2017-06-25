(function () {
    function updateHeadline(headline) {
        var currentPosition = headline.style.marginLeft.replace("px", "");
        var header = document.getElementById("header");
        if (currentPosition > header.offsetWidth) {
          
            //alert("current position: " + currentPosition + ", tag offsetWidth: " + tag.innerHTML);
            currentPosition = -250;
        }

        headline.style.marginLeft = (Number(currentPosition) + 1) + "px";
    }
    setInterval(updateHeadline, 3, document.getElementById("headline"));
}());