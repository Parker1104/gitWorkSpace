function findHiddenColums(colModelFromCookie, name) {
	if (colModelFromCookie) {
		for (var i = 0; i < colModelFromCookie.length; i++) {
			var colModel = colModelFromCookie[i].substring(4, colModelFromCookie[i].length);
			if (colModel == name) {
				return true;
			}
		}
	}
	return false;
}
function extendDefaultOption(defaultOption, option, colModelFromCookie) {
	defaultOption.width = screen.width - $(".ui-layout-west").outerWidth() - $(".center-left").outerWidth() - 24;
	if ($.browser.msie) {
		defaultOption.width = screen.width - $(".ui-layout-west").outerWidth() - $(".center-left").outerWidth() - 43;
		if ($.browser.version == "7.0") {
			defaultOption.width = screen.width - $(".ui-layout-west").outerWidth() - $(".center-left").outerWidth() - 30;
		}
		if ($.browser.version == "6.0") {
			defaultOption.width = screen.width - $(".ui-layout-west").outerWidth() - $(".center-left").outerWidth() - 16;
		}
	}
	// defaultOption.height =
	// $(".ui-layout-west").outerHeight()-$(".path:visible").outerHeight()-$("#nav:visible").outerHeight()-$(".submenu:visible").outerHeight()-75;
	defaultOption.height = $(".ui-layout-west").height() - $(".path").height() - $("#nav").height() - $(".submenu").height() - 100;
	if (defaultOption.pager == false) {
		defaultOption.rowNum = -1;
	}
	$.extend(defaultOption, option);

	for (var i = 0; i < defaultOption.colModel.length; i++) {
		if (colModelFromCookie && findHiddenColums(colModelFromCookie, defaultOption.colModel[i].name)) {
			defaultOption.colModel[i].hidden = false;
		} else if (colModelFromCookie) {
			defaultOption.colModel[i].hidden = true;
		}

		if (defaultOption.colModel[i].name == "id") {
			if (defaultOption.showColModelButton) {
				defaultOption.colModel[i].hidden = true;
				defaultOption.colModel[i].hidedlg = true;
			};
		};
	}
}
function extendOnSelectRow(defaultOption, option) {
	defaultOption.onSelectRow = function(rowId) {
		if (option.onSelectRow) {
			option.onSelectRow.call(null, rowId);
		}
	}
}
function getColModelFromCookie(self) {
	var colModelFromCookie;
	if ($.cookie("gridColums")) {
		var jsonData = eval("(" + $.cookie("gridColums") + ")");
		colModelFromCookie = jsonData[self.attr("id")];
	}
	return colModelFromCookie;
}
function proccessStyleWhenSetColumShow(selfId) {
	$("#ColTbl_" + selfId).css({
				height : "200px",
				overflow : "auto"
			});
	$(".ui-jqdialog").css({
				bottom : "25px",
				left : "0px"
			});
	$(".jqResize").remove();
}
function bindClickToOkButton(dialog, selfId) {
	$("#dData").click(function() {
				var dialogValue = "";
				dialog.find(".cbox").each(function() {
							if ($(this).attr("checked")) {
								dialogValue = dialogValue + "'" + $(this).attr("id") + "',";
							}
						});
				dialogValue = dialogValue.substring(0, dialogValue.length - 1);

				var cookieData = selfId + ":[" + dialogValue + "]";
				var cookieValue = $.cookie("gridColums");
				var jsonData = {};
				if (cookieValue && cookieValue != "") {
					jsonData = eval("(" + cookieValue + ")");
				}
				jsonData[selfId] = eval("([" + dialogValue + "])");
				$.cookie("gridColums", Convert.ToJSONString(jsonData));
			});
}
function bindClickToCancelButton(dialog) {
	var dialogChecked = new Array();
	dialog.find(".cbox").each(function(i, n) {
				if ($(this).attr("checked") == true) {
					dialogChecked[i] = true;
				} else {
					dialogChecked[i] = false;
				}
			});
	$("#eData").click(function() {
				dialog.find(".cbox").each(function(i, n) {
							$(this).attr("checked", dialogChecked[i]);
						});
			})
}
function proccessLabelShow(dialog) {
	var labels = $("label", dialog);
	labels.each(function(i, node) {
				var text = $(node).text();
				var index = text.lastIndexOf("(");
				if (index != -1)
					$(node).text(text.substring(0, text.lastIndexOf("(")));
			});
}

;
(function($) {
	$.fn.setPostData=function(postData){
		$(this).jqGrid('setGridParam',{postData: null});
		$(this).jqGrid("setGridParam",{'postData':postData});
	}
	$.fn.getPostData=function(){
		return jQuery(this).jqGrid('getGridParam','postData');
	}
	$.fn.jqGridFunction = function(option) {
		var defaultOption = {
			rowNum : 20,
			datatype : "json",
			rowList : [10, 15, 20, 30],
			gridview : true,
			viewrecords : true,
			jsonReader : {
				repeatitems : false,
				id : "0"
			},
			height : 'auto',
			width : 'auto',
			scrollrows : true,
			sortname : 'id',
			sortorder : "desc",
			pager : '#' + $(this).attr("id") + 'Pager'
		}
		
		//defaultOption.width = screen.width - $(".ui-layout-west").width() - $(".center-left").width() - 24;
		//defaultOption.height = $(".ui-layout-west").height() - $(".path").height() - $("#nav").height() - $(".submenu").height() - 90;
		if (defaultOption.pager == false) {
			defaultOption.rowNum = -1;
		}
		$.extend(defaultOption, option);
		//$(window).resize(function() {$(this).height($("#accordion").height() - $(".path").height() - $("#nav").height() - 90);});
		if (defaultOption.pager == false) {
			$(this).jqGrid(defaultOption);
		} else {
			$(this).jqGrid(defaultOption).navGrid('#' + $(this).attr("id") + 'Pager', {
				edit : false,
				add : false,
				del : false,
				search : false,
				refresh : false
			});
		}

	};
	$.fn.jqGridFunctionNew = function(option) {
		var self = $(this);
		var defaultOption = {
			rowNum : 20,
			datatype : "json",
			rowList : [10, 15, 20, 30],
			gridview : true,
			viewrecords : true,
			jsonReader : {
				repeatitems : false,
				id : "0"
			},
			height : 'auto',
			width : 'auto',
			scrollrows : true,
			sortname : 'id',
			sortorder : "desc",
			pager : '#' + $(this).attr("id") + 'Pager',
			showColModelButton : true,
			shrinkToFit : false
		}

		extendDefaultOption(defaultOption, option, getColModelFromCookie(self));
		self.jqGrid(defaultOption);

		window._currentGrid = self;
		if (defaultOption.pager) {
			self.navGrid('#' + $(this).attr("id") + 'Pager', {
						edit : false,
						add : false,
						del : false,
						search : false,
						refresh : false
					});
			if (defaultOption.showColModelButton) {
				var bindClick = false;
				self.navButtonAdd('#' + $(this).attr("id") + 'Pager', {
							caption : "自定义显示",
							buttonimg : "",
							onClickButton : function() {
								self.setColumns({
											width : 200,
											drag : false,
											resizable : false,
											beforeShowForm : function(dialog) {
												var selfId = self.attr("id");
												proccessStyleWhenSetColumShow(selfId);
												if (!bindClick) {
													bindClickToOkButton(dialog, selfId);
												}
												bindClickToCancelButton(dialog);
												bindClick = true;
												proccessLabelShow(dialog);
											}
										});
								$(this).jqGrid('setColumns');
							},
							position : "last"
						});
			}
		}

	};

	$.fn.jqSubGridNew = function(option) {
		function selectColor() {
			var cookie_skin = $.cookie("cssSkin");
			if (cookie_skin == null) {
				cookie_skin = "default"
			}
			switch (cookie_skin) {
				case "default" :
					return "#A6C9E2";
					break;
				case "blue" :
					return "#A5B4D3";
					break;
				case "green" :
					return "#C6E371";
					break;
			};
		}
		var self = $(this);
		var defaultOption = {
			subGridUrl : '',
			subGridPostData : {},
			rowNum : 20,
			datatype : "json",
			rowList : [10, 15, 20, 30],
			viewrecords : true,
			multiselect : false,
			subGrid : true,
			jsonReader : {
				repeatitems : false,
				id : "0"
			},
			height : 'auto',
			width : 'auto',
			sortname : 'id',
			sortorder : "desc",
			pager : '#' + $(this).attr("id") + 'Pager',
			showColModelButton : true,
			subGridRowExpanded : function(subgrid_id, row_id) {
				var colspan = $("#" + subgrid_id).parent().attr("colspan");
				$(".subgrid-cell").remove();
				$("#" + subgrid_id).parent().attr("colspan", colspan + 1);
				$.extend(this.subGridPostData, {
							id : row_id
						});

				self.find(".sgexpanded").click();

				$("#" + subgrid_id).load(this.subGridUrl, this.subGridPostData, function() {
							var scrHeight = 0;
							self.find("tr").each(function(i, n) {
										if ($(n).attr("id") == row_id) {
											return false;
										}
										scrHeight = scrHeight + $(n).outerHeight();
									});
							self.parent().parent().scrollTop(scrHeight);

						});
			},
			subGridRowColapsed : function(subgrid_id, row_id) {

			},
			ondblClickRow : function(rowid) {
				self.toggleSubGridRow(rowid);
			},
			caption : false,
			gridComplete : function() {

			}
		}
		extendDefaultOption(defaultOption, option, getColModelFromCookie(self));
		self.jqGrid(defaultOption);

		window._currentGrid = self;
		if (defaultOption.pager) {
			self.navGrid('#' + $(this).attr("id") + 'Pager', {
						edit : false,
						add : false,
						del : false,
						search : false,
						refresh : false
					});
			if (defaultOption.showColModelButton) {
				var bindClick = false;
				self.navButtonAdd('#' + $(this).attr("id") + 'Pager', {
							caption : "自定义显示",
							buttonimg : "",
							onClickButton : function() {
								self.setColumns({
											width : 200,
											drag : false,
											resizable : false,
											beforeShowForm : function(dialog) {
												var selfId = self.attr("id");
												proccessStyleWhenSetColumShow(selfId);
												if (!bindClick) {
													bindClickToOkButton(dialog, selfId);
												}
												bindClickToCancelButton(dialog);
												bindClick = true;
												proccessLabelShow(dialog);
											}
										});
								$(this).jqGrid('setColumns');
							},
							position : "last"
						});
			}
		}

	};

	$.fn.jqSubGrid = function(option) {
		function selectColor() {
			var cookie_skin = $.cookie("cssSkin");
			if (cookie_skin == null) {
				cookie_skin = "default"
			}
			switch (cookie_skin) {
				case "default" :
					return "#A6C9E2";
					break;
				case "blue" :
					return "#A5B4D3";
					break;
				case "green" :
					return "#C6E371";
					break;
			};
		}
		var self = $(this);
		var defaultOption = {
			subGridUrl : '',
			subGridPostData : {},
			rowNum : 20,
			datatype : "json",
			rowList : [10, 15, 20, 30],
			viewrecords : true,
			multiselect : false,
			subGrid : true,
			jsonReader : {
				repeatitems : false,
				id : "0"
			},
			height : 'auto',
			width : 'auto',
			sortname : 'id',
			sortorder : "desc",
			pager : '#' + $(this).attr("id") + 'Pager',
			subGridRowExpanded : function(subgrid_id, row_id) {
				var colspan = $("#" + subgrid_id).parent().attr("colspan");
				$(".subgrid-cell").remove();
				$("#" + subgrid_id).parent().attr("colspan", colspan + 1);
				$.extend(this.subGridPostData, {
							id : row_id
						});

				self.find(".sgexpanded").click();

				$("#" + subgrid_id).load(this.subGridUrl, this.subGridPostData, function() {
							var scrHeight = 0;
							self.find("tr").each(function(i, n) {
										if ($(n).attr("id") == row_id) {
											return false;
										}
										scrHeight = scrHeight + $(n).outerHeight();
									});
							self.parent().parent().scrollTop(scrHeight);

						});
			},
			subGridRowColapsed : function(subgrid_id, row_id) {

			},
			ondblClickRow : function(rowid) {
				self.toggleSubGridRow(rowid);
			},
			caption : false,
			gridComplete : function() {

			}
		}

		defaultOption.width = screen.width - $(".ui-layout-west").width() - $(".center-left").width() - 24;
		if ($.browser.msie) {
			defaultOption.width = screen.width - $(".ui-layout-west").outerWidth() - $(".center-left").outerWidth() - 43;
			if ($.browser.version == "7.0") {

				defaultOption.width = screen.width - $(".ui-layout-west").outerWidth() - $(".center-left").outerWidth() - 30;
			}
			if ($.browser.version == "6.0") {
				defaultOption.width = screen.width - $(".ui-layout-west").outerWidth() - $(".center-left").outerWidth() - 16;
			}
		}
		defaultOption.height = $(".ui-layout-west").height() - $(".tabnav").height() - $(".tabbox").height() - $(".ui-corner-all").height() - 65;
		if (defaultOption.pager == false) {
			defaultOption.rowNum = -1;
		}
		$.extend(defaultOption, option);
		defaultOption.onSelectRow = function(rowId) {
			if (option.onSelectRow) {
				option.onSelectRow.call(null, rowId);
			}
		}
		$(window).resize(function() {
					$(this).height($("#accordion").height() - $(".path").height() - $("#nav").height() - 90);
				});
		if (defaultOption.pager == false) {
			$(this).jqGrid(defaultOption);
		} else {
			$(this).jqGrid(defaultOption).navGrid('#' + $(this).attr("id") + 'Pager', {
						edit : false,
						add : false,
						del : false,
						search : false,
						refresh : false
					});
		}
	};

	$.fn.reloadSubGridByRowId = function(rowId) {
		var subGrid = $("tr[id='" + rowId + "']", $(this)).next(".ui-subgrid").find("#" + $(this).attr("id") + "_" + rowId);
		subGrid.height($("tr[id='" + rowId + "']", $(this)).next(".ui-subgrid").height());
		subGrid.html('<img src=' + RESOURCE_PATH + '"/resource/images/loading.gif" alt="加载中..." />');
		var subGridPostData = $(this).getGridParam("subGridPostData");
		$.extend(subGridPostData, {
					id : rowId
				});
		subGrid.load($(this).getGridParam("subGridUrl"), subGridPostData, function() {
					subGrid.css("height", "auto");
				});
	};
	$.fn.deleteSubGridByRowId = function(rowId) {
		$("#" + rowId, $(this)).next(".ui-subgrid").remove();
	};
	$.fn.getSelectedRowId = function() {
		var rowDom = $(this).getSelectedRowDom();
		if (!rowDom) {
			return null;
		}
		return rowDom.attr("id");
	};
	$.fn.getSelectedRowDom = function() {
		if (!$(this).jqGrid('getGridParam', 'selrow')) {
			return null;
		}
		return $("tr" + "[role='row'][id='" + $(this).jqGrid('getGridParam', 'selrow') + "']", $(this));
	};
	$.fn.getSelectedRowIds = function() {
		return $(this).getGridParam("selarrrow");
	};
	$.fn.toNext = function(url, postData, successCallBack) {
		var selfDoc = $(this);
		var rowid = selfDoc.jqGrid('getGridParam', 'selrow');
		var selectedRow = $("#" + rowid, selfDoc);
		if (url) {
			$.ajax({
						type : "post",
						url : url,
						data : postData,
						success : function(data) {
							var nextRow = selectedRow.next();
							if (nextRow.attr("id")) {
								nextRow.after(selectedRow);
							}
							if (successCallBack) {
								successCallBack.call(null, data);
							}
						}
					});
		} else {
			var nextRow = selectedRow.next();
			if (nextRow.attr("id")) {
				nextRow.after(selectedRow);
			}
		}
	};
	$.fn.toPrevious = function(url, postData, successCallBack) {
		var selfDoc = $(this);
		var rowid = selfDoc.jqGrid('getGridParam', 'selrow');
		var selectedRow = $("#" + rowid, selfDoc);
		if (url) {
			$.ajax({
						type : "post",
						url : url,
						data : postData,
						success : function(data) {
							var prevRow = selectedRow.prev();
							if (prevRow.attr("id")) {
								selectedRow.after(prevRow);
							}
							if (successCallBack) {
								successCallBack.call(null, data);
							}
						}
					});
		} else {
			var prevRow = selectedRow.prev();
			if (prevRow.attr("id")) {
				selectedRow.after(prevRow);
			}
		}
	};
	$.fn.toFirst = function(url, postData, successCallBack) {
		var selfDoc = $(this);
		var rowid = selfDoc.jqGrid('getGridParam', 'selrow');
		var selectedRow = $("#" + rowid, selfDoc);
		if (url) {
			$.ajax({
						type : "post",
						url : url,
						data : postData,
						success : function(data) {
							selfDoc.prepend(selectedRow);
							if (successCallBack) {
								successCallBack.call(null, data);
							}
						}
					});
		} else {
			$(this).prepend(selectedRow);
		}
	};
	$.fn.toEnd = function(url, postData, successCallBack) {
		var selfDoc = $(this);
		var rowid = selfDoc.jqGrid('getGridParam', 'selrow');
		var selectedRow = $("#" + rowid, selfDoc);
		if (url) {
			$.ajax({
						type : "post",
						url : url,
						data : postData,
						success : function(data) {
							selfDoc.append(selectedRow);
							if (successCallBack) {
								successCallBack.call(null, data);
							}
						}
					});
		} else {
			$(this).append(selectedRow);
		}
	};
	$.fn.getGridRowData = function() {
		return $(this).getRowData($(this).getGridParam("selrow"));
	};
	$.fn.reloadDataGrid = function(url, postData) {
		if (url == null && postData == null) {
			$(this).trigger("reloadGrid");
			return;
		}
		$(this).setGridParam({
					url : url,
					postData : postData
				});
		$(this).trigger("reloadGrid");
	};
})(jQuery);