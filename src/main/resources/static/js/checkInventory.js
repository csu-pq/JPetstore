var xhr;
function check() {
    var itemId = document.getElementById('itemId').value;
    xhr = new XMLHttpRequest();
    xhr.onreadystatechange = process;//3
    xhr.open("GET","cart/checkInventory?itemId="+itemId,true);//1
    xhr.send(null);//2
}

function process() {
    if (xhr.readyState == 4){
        if (xhr.status == 200){
            var responseInfo = xhr.responseText;
            var msg = document.getElementById('isEmpty')
            var num=document.getElementById('quantity').value;
            if(responseInfo<num){
                msg.innerText='False'
            }else {
                msg.innerText='True';
            }
        }
    }
}