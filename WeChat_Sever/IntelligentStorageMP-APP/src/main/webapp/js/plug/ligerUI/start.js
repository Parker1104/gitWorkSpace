/**
 * Created by Administrator on 2015/10/10.
 */
var indexdata =[];
var tab = null;
var accordion = null;
var tree = null;
var tabItems = [];
var childPageControl={
    "getchildWidth":function(){return document.body.clientWidth*0.85},
    "rolesAll":null,
    "openNewPage":function(role,istop){//如果istop为true,则表示点击的为一级菜单，左侧菜单发生变化

        if(tab.isTabItemExist(role) && istop==undefined){
            tab.reload(role);
        }else{
            $('a[name="'+role+'"]').trigger('click');
        }
    }/*,
    "":function(){

    }*/
}
$(function (){
   // console.log(document.body.clientHeight);
    $("#framecenter").ligerTab({
        height: document.body.clientHeight-90,
        showSwitchInTab : true,
        showSwitch:false,
        onAfterAddTabItem: function (tabdata)
        {
            tabItems.push(tabdata);
            //saveTabStatus();
        },
        onAfterRemoveTabItem: function (tabid)
        {
            for (var i = 0; i < tabItems.length; i++)
            {
                var o = tabItems[i];
                if (o.tabid == tabid)
                {
                    tabItems.splice(i, 1);
                    //saveTabStatus();
                    break;
                }
            }
        },
        onReload: function (tabdata)
        {
            var tabid = tabdata.tabid;
            addFrameSkinLink(tabid);
        }
    });

    tab = liger.get("framecenter");
    accordion = liger.get("accordion1");
    tree = liger.get("tree1");
    $("#pageloading").hide();

    //css_init();
    //pages_init();
});


function openNew(url)
{
    var jform = $('#opennew_form');
    if (jform.length == 0)
    {
        jform = $('<form method="post" />').attr('id', 'opennew_form').hide().appendTo('body');
    } else
    {
        jform.empty();
    }
    jform.attr('action', url);
    jform.attr('target', '_blank');
    jform.trigger('submit');
};

function f_heightChanged(options)
{
    if (tab)
        tab.addHeight(options.diff);
    if (accordion && options.middleHeight - 24 > 0)
        accordion.setHeight(options.middleHeight - 24);
}
function f_addTab(tabid, text, url)
{
    tab.addTabItem({
        tabid: tabid,
        text: text,
        url: url,
        callback: function ()
        {
            //addShowCodeBtn(tabid);
            addFrameSkinLink(tabid);
        }
    });
}
function addShowCodeBtn(tabid)
{
    var viewSourceBtn = $('<a class="viewsourcelink" href="javascript:void(0)">查看源码</a>');
    var jiframe = $("#" + tabid);
    viewSourceBtn.insertBefore(jiframe);
    viewSourceBtn.click(function ()
    {
        showCodeView(jiframe.attr("src"));
    }).hover(function ()
    {
        viewSourceBtn.addClass("viewsourcelink-over");
    }, function ()
    {
        viewSourceBtn.removeClass("viewsourcelink-over");
    });
}
function addFrameSkinLink(tabid)
{
    var prevHref = getLinkPrevHref(tabid) || "";
                var skin = getQueryString("skin");
    if (!skin) return;
    skin = skin.toLowerCase();
    attachLinkToFrame(tabid, prevHref + skin_links[skin]);
}
var skin_links = {
    "aqua": "lib/ligerUI/skins/Aqua/css/ligerui-all.css",
    "gray": "lib/ligerUI/skins/Gray/css/all.css",
    "silvery": "lib/ligerUI/skins/Silvery/css/style.css",
    "gray2014": "lib/ligerUI/skins/gray2014/css/all.css"
};
/*function pages_init()
{
    var tabJson = $.cookie('liger-home-tab');
    if (tabJson)
    {
        var tabitems = JSON2.parse(tabJson);
        for (var i = 0; tabitems && tabitems[i];i++)
        {
            f_addTab(tabitems[i].tabid, tabitems[i].text, tabitems[i].url);
        }
    }
}*/
/*function css_init()
{
    var css = $("#mylink").get(0), skin = getQueryString("skin");
    $("#skinSelect").val(skin);
    $("#skinSelect").change(function ()
    {
        if (this.value)
        {
            location.href = "index.htm?skin=" + this.value;
        } else
        {
            location.href = "index.htm";
        }
    });


    if (!css || !skin) return;
    skin = skin.toLowerCase();
    $('body').addClass("body-" + skin);
    $(css).attr("href", skin_links[skin]);
}*/
function getQueryString(name)
{
    var now_url = document.location.search.slice(1), q_array = now_url.split('&');
    for (var i = 0; i < q_array.length; i++)
    {
        var v_array = q_array[i].split('=');
        if (v_array[0] == name)
        {
            return v_array[1];
        }
    }
    return false;
}
function attachLinkToFrame(iframeId, filename)
{
    if(!window.frames[iframeId]) return;
    var head = window.frames[iframeId].document.getElementsByTagName('head').item(0);
    var fileref = window.frames[iframeId].document.createElement("link");
    if (!fileref) return;
    fileref.setAttribute("rel", "stylesheet");
    fileref.setAttribute("type", "text/css");
    fileref.setAttribute("href", filename);
    head.appendChild(fileref);
}
function getLinkPrevHref(iframeId)
{
    if (!window.frames[iframeId]) return;
    var head = window.frames[iframeId].document.getElementsByTagName('head').item(0);
    var links = $("link:first", head);
    for (var i = 0; links[i]; i++)
    {
        var href = $(links[i]).attr("href");
        if (href && href.toLowerCase().indexOf("ligerui") > 0)
        {
            return href.substring(0, href.toLowerCase().indexOf("lib") );
        }
    }
}