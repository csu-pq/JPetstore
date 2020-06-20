function sendVerificationCode(){

    var phoneNumber = document.getElementById("phoneNumber").value;
    $.ajax({
        type: "POST",
        url: "/account/sendVerificationCode",
        dataType:"json",
        data:{
            "phoneNumber":phoneNumber
        },
        success: function (result) {
            alert("success");
        }
    })
}