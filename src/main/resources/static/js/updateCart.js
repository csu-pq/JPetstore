var xhr;
function updateQuantity(){
    var total=0;
    var table=document.getElementById("listOfCart");
    for(var i=1;i<table.rows.length-1;i++){
        var price=table.rows[i].cells[5].innerText;
        var qty=document.getElementsByTagName("input").item(i+1).value;
        var prices=price.split("$");
        table.rows[i].cells[6].innerText="$"+prices[1]*qty;
        total+=prices[1]*qty;
        xhr = new XMLHttpRequest();
        xhr.onreadystatechange = process;//3
        xhr.open("GET","checkInventory?itemId="+itemId,true);//1
        xhr.send(null);//2
    }
    document.getElementById("totalPrice").innerText="Sub Total:$"+total;
    //检查库存

}
function process() {
    if (xhr.readyState == 4){
        if (xhr.status == 200){
            var responseInfo = xhr.responseText;
            var msg = document.getElementById('isEmpty')
            var num=document.getElementById('quantity').value;
            alert("库存不足");
            if(responseInfo<num){
                alert("请输入验证码！");
            }else {
                msg.innerText='True';
            }
        }
    }
}