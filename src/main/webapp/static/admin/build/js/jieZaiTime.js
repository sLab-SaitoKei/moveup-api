jQuery.validator.addMethod("dateCompare",function(value,element,params) {
    if(new Date(params[0].val()).getTime()>new Date(params[1].val()).getTime()){
        return false;
    }else{
        return true;
    }

}, $.validator.format("有効な日付を入力してください。"));


$("#publishStart").change(function(){
    $("#publishStart").attr("value",$(this).val());
});

$("#publishEnd").change(function(){
    $("#publishEnd").attr("value",$(this).val());
});

$("#publishEnd").blur(function(){

    $("#publishStart").css("color","#000");
    $("#publishStart-error").hide();
    if($("#publishEnd-error").css("display") != 'none' && $("#publishEnd-error").length >0){
        $("#publishEnd").css("color","#ff0000");
    }else{
        $("#publishEnd").css("color","#000");
    }
});
$("#publishStart").blur(function(){
    $("#publishEnd").css("color","#000");
    $("#publishEnd-error").hide();
    if($("#publishStart-error").css("display") != 'none' && $("#publishStart-error").length >0){
        $("#publishStart").css("color","#ff0000");
    }else{
        $("#publishStart").css("color","#000");
    }
});