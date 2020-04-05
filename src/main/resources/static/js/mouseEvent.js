var xmlHttpRequest;
function createXMLHttpRequest()
{
    if (window.XMLHttpRequest) //非IE浏览器
    {
        xmlHttpRequest = new XMLHttpRequest();
    }
    else if (window.ActiveObject)//IE6以上版本的IE浏览器
    {
        xmlHttpRequest = new ActiveObject("Msxml2.XMLHTTP");
    }
    else //IE6及以下版本IE浏览器
    {
        xmlHttpRequest = new ActiveObject("Microsoft.XMLHTTP");
    }
}

function showInform(categoryId) {
    console.log(categoryId);
    sendRequest("/mouseShow?categoryId=" + categoryId);
}

function sendRequest(url) {
    createXMLHttpRequest();
    xmlHttpRequest.open("GET", url, true);
    xmlHttpRequest.onreadystatechange = processResponse;
    xmlHttpRequest.send(null);
}

function processResponse() {
    if (xmlHttpRequest.readyState == 4) {
        if (xmlHttpRequest.status == 200) {
            var resp = xmlHttpRequest.responseText;
            //显示悬浮层
            var inform = document.getElementById("inform");
            inform.innerText = resp;
            inform.style.display = "block";
        }
    }
}

//隐藏悬浮层
function hiddenInform(event){
    var informDiv = document.getElementById('inform');
    var x=event.clientX;
    var y=event.clientY;
    var divx1 = informDiv.offsetLeft;
    var divy1 = informDiv.offsetTop;
    var divx2 = informDiv.offsetLeft + informDiv.offsetWidth;
    var divy2 = informDiv.offsetTop + informDiv.offsetHeight;
    if( x < divx1 || x > divx2 || y < divy1 || y > divy2){
        document.getElementById('inform').style.display='none';
    }
}