
function hideElement(id)
{
    var element = document.getElementById(id);
    element.style.display = 'none';
}

function loadVoiceList()
{
    
}

function logEvent(message)
{
    var e = document.getElementById("logs");
    
    var logs = message + "<br/>" + e.innerHTML;
    
    e.innerHTML = logs;
}

function logServerResponse(xmlhttp)
{
    if (xmlhttp.readyState==4 && xmlhttp.status==200)      
    {
        var s = xmlhttp.responseText;
        logEvent(s);
    }
}

function showBuiltInControls()
{
    hideElement('onTheFlyControls');
    
    showElement('songListControls');
}

function showElement(id)
{
    var element = document.getElementById(id);
    element.style.display = 'block';
}

function showOnTheFlyControls()
{
    hideElement('songListControls');
    
    showElement('onTheFlyControls');
}

function speak(text)
{
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange=function()
    {
        logServerResponse(xmlhttp);
    };
    
    var url = "/speak" + "?" + encodeURIComponent(text);
    
    xmlhttp.open("POST", url, true);
    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlhttp.send("&p=3");    
}