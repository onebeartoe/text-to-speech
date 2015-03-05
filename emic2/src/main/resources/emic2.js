
function basicCommand(command)
{
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange=function()
    {
        logServerResponse(xmlhttp);
    };
    
    var url = "/" + command;
    
    xmlhttp.open("POST", url, true);
    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlhttp.send("&p=3");    
}

function demoMessage(id)
{
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange=function()
    {
        logServerResponse(xmlhttp);
    };
    
    var url = "/demo/" + id;
    
    xmlhttp.open("POST", url, true);
    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlhttp.send("&p=3");
}

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

function setVoice(id)
{
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange=function()
    {
        logServerResponse(xmlhttp);
    };
    
    var url = "/voice/" + id;
    
    xmlhttp.open("POST", url, true);
    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlhttp.send("&p=3");
}

function showBuiltInControls()
{
    hideElement('onTheFlyControls');
    hideElement('settingsControls');
    
    showElement('demoMessageControls');
}

function showElement(id)
{
    var element = document.getElementById(id);
    element.style.display = 'block';
}

function showFreeFromControls()
{
    hideElement('demoMessageControls');
    hideElement('settingsControls');
    
    showElement('onTheFlyControls');
}

function showSettingsControls()
{
    hideElement('demoMessageControls');
    hideElement('onTheFlyControls');
    
    showElement('settingsControls');
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

function stopPlayback()
{
    var command = "stop";
    
    basicCommand(command);
}