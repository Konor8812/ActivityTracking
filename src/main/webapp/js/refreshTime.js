var timeDisplay = document.getElementById("dateTime");
function refreshTime() {
    var dateString = new Date().toLocaleString("uk-UA", {timeZone: "Europe/Kiev"});
    var formattedString = dateString.replace(", ", " - ");
    timeDisplay.innerHTML = formattedString;
}
setInterval(refreshTime, 1000);