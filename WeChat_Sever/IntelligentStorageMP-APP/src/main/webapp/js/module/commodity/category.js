
function subStringBlank(theName){
   return theName.substring(theName.indexOf(' '),theName.length);
}

function getNowDate(month){//month为true，表示要获取当前月的1号
    var date=new Date();
    var dataString=date.getFullYear()+'-'+addData0(date.getUTCMonth()+1);
    if(month){
        return dataString+'-01';
    }else{
        return dataString+'-'+addData0(date.getDate());
    }
}

function addData0(num){
    if(num<10){
        return '0'+num;
    }else{
        return num;
    }
}

function testNum(val,msg,id){

    var reg = new RegExp("^[0-9]*$");
    if($.trim(val)=='' ||  !reg.test(val)){
        if(msg!=null)
            showMsg(msg,id);
        id.value='';
        return false;
    }else
        return true;
}

function testPressNum(e){
    if(e.keyCode!=13){
        e.target.value=e.target.value.replace(/[^\d]/g,'');
    }
}

function testPressMoney(e){
    if(e.keyCode!=13){
        e.target.value=e.target.value.replace(/[^.^\d]/g,'');
    }
}

function testText(val,msg,id){
    if($.trim(val)==''){
        var d = dialog({
            content: msg,
            quickClose: true// 点击空白处快速关闭
        });
        d.width(150);
        d.show(document.getElementById(id));
        return false;
    }else
        return true;

}

/*function showMsg(themsg,id){
    var d = dialog({
        content:themsg,
        quickClose: true
    });
    if(id!=null){
        d.show(id);
    }else
        d.show();
    setTimeout(function () {
        d.close().remove();
    }, 2000);
}*/

function showMsg(themsg,id,type,redirect){//当type为1时，表示提示型对话框,redirect为对应的确定按钮的回调函数，id为绑定的位置
    var d=null;
    var isTwice=true;
    if(1==type){
        d = dialog({
            title: '提示',
            content: themsg,
            okValue:"确定",
            cancelValue: '取消',
            ok: function(){
                if(typeof redirect=='function' && isTwice){
                    isTwice=false;
                    redirect();
                    return true;
                }else
                    return true;
            },
            onremove:function(){
                if(typeof redirect=='function' && isTwice){
                    isTwice=false;
                    redirect();
                }
            }
           //cancelValue:'',
            /*cancel:function(){
                if(typeof redirect=='function'){
                    redirect();
                }else
                    return true;
            }*/
        });
        /*if(themsg.length<17){
        }*/
        d.width(200);
        d.showModal();
    }else{
        d = dialog({
            content:themsg,
            quickClose: true
        });
        if(id!=null){
            id.focus();
            d.show(id);
        }else{
            d.show();
        }
        //d.show();
        var time=themsg.length;
        if(time<5){
            time=5;
        }
        setTimeout(function () {
            d.close().remove();
        }, themsg.length*300);
    }
}
function formToJson(id,isAll){//all表示即使输入框没有内容，json里面也有
    var formArray=$('#'+id).serializeArray();//数组里面首先放入充值记录表的内容
    var formJson=new Object();
    if(isAll!=null && isAll){
        $.each(formArray,function(i,n){//把formarray转换为json
            if(n.name!=undefined&&n.name!=""){
                formJson[n.name] = n.value;
            }
        });
    }else{
        $.each(formArray,function(i,n){//把formarray转换为json
            if((n.name!=undefined&&n.name!="") && n.value!=""){
                formJson[n.name] = n.value;
            }
        });
    }
    return formJson;
}