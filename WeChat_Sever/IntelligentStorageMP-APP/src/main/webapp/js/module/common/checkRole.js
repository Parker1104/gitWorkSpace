/**
 * Created by Administrator on 2015/9/9.
 */
function pageRole(roleName){
    var myself=this;
    this.myRole=roleName;
    this.count=0;
    this.isShow=function(code){
        if(myself.allRoles.indexOf(','+code)>-1){
            myself.count+=1;
            return true;
        }else{
            return false;
        }
    }
    var roleObj=window.parent.document.getElementById("buttons_"+roleName);
    if(roleObj!=null){
        this.allRoles = roleObj.innerText;
    }else{
        this.allRoles = '';
    }
}