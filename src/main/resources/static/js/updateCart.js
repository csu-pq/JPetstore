var xmlHttpRequest;
function creatXMLHttpRequest()
{
    if(window.XMLHttpRequest)
    {
        xmlHttpRequest = new XMLHttpRequest();
    }
    else if(window.ActiveObject)
    {
        xmlHttpRequest =new ActiveObject("Msxml2.XMLHTTP");
    }
    else
    {
        xmlHttpRequest = new ActiveObject("Microsoft.XMLHTTP");
    }
}
function updateCart()
{
    var quantity = document.getElementById("quantity").value;
    sendRequest("updateCartJSServlet?quantity="+ quantity);
}
function sendRequest(url)
{
    creatXMLHttpRequest();
    xmlHttpRequest.open("GET",url,true);
    xmlHttpRequest.onreadystatechange = processResponse;
    xmlHttpRequest.send(null);
}
function processResponse()
{
    if(xmlHttpRequest.readyState==4)
    {
        if(xmlHttpRequest.status==200)
        {
            var resp =xmlHttpRequest.responseText;
            var array =resp.split(",");
            var quantity = document.getElementById("quantity");
            var total = document.getElementById("total");
            var subtotal = document.getElementById("subtotal");

            quantity.innerText = array[0];
            total.innerText = array[1];
            subtotal.innerText = array[2];
        }
    }
}