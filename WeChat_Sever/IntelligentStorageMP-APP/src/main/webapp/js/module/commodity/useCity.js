var pr=-1;
var cr=-1;
var ar=-1;

function setCity(){
    $("#city").find("option").remove();
    $("#area").find("option").remove();


    pr=$('#province').val();
    if(pr=='-1'){
        $('#address').val('');
        return;
    }
    var temp=provinces[pr][1];

    $("#city").append('<option value="-1">'+'--选择市--'+'</option>');
    for(var i=0;i<citys.length;i++){
        if(citys[i][0]==temp){
            $("#city").append('<option value="'+i+'">'+citys[i][2]+'</option>');
        }
    }

    $('#address').val(provinces[pr][4]);

    console.log($('#address').val());
}

function setArea(){
    $("#area").find("option").remove();

    cr=$('#city').val();
    if(cr=='-1'){
        $('#address').val('');
        return;
    }

    var temp=citys[cr][1];

    $("#area").append('<option value="-1">'+'--选择县(区)--'+'</option>');
    for(var i=0;i<areas.length;i++){
        if(areas[i][0]==temp){
            $("#area").append('<option value="'+i+'">'+areas[i][2]+'</option>');
        }
    }

    $('#address').val(citys[cr][4]);
    console.log($('#address').val());
}

function setThisValue(){
    /*if(ar=='-1'){
        $('#address').val('');
        return;
    }*/
    ar=$('#area').val();
    $('#address').val(areas[ar][4]);
    console.log($('#address').val());
}

function reverseAddress(setR,compareText,obj,id,methodName){//参数1：要为下拉菜单设置的数值坐标值，
    // 参数2：要对比的值，参数3：穿入要赋值的select（下拉列表），
    //参数4：要穿入的id标识，参数5：要触发的下一个方法。
    var count=obj.length;
    //alert(count);
    for(var j=0;j<count;j++) {
        if(obj.options[j].text == compareText) {
            cr=obj.options[j].value;
            $("#"+id+"").val(cr);
            //eval(methodName);
            return cr;
            break;
        }
    }
    return -1;
}

function vvalidationAddress(address){//替换字符串里面的-换成' '
    //var array=address.split('-');
    //var newA='';
    /*if(array.length>2){
        for(var i=1;i<array.length;i++){
            if($.trim(array[i])!=''){
                newA=newA+array[i]+" ";
            }
        }
    }*/
    return $.trim(address.replace(/-/g,' '));
}