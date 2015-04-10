/**
 * V1.0.3
 * easyTable插件
 * autor : heyang
 * 免费开源,随意使用
 */
$(function() {
   $.fn.easyTable = function(settings) {
		//默认配置项
		var defaults = {
			//Event : "click",       //触发响应事件
			type : "POST",  		 //请求类型，默认POST
			url : null,			 	 //ajax请求url
			async : true,			 //是否异步
			params : null,      	 //请求参数,默认true
			showSerNum :  true,    	 //是否显示序号，默认true
			pager : {
				pageTarget : null,       //分页对象(可以是标签名，也可以是id或者name、class等, 如：$('#demo'),$('div'),$('.btn_cls'))
				page : 1,				 //当前页
				pageSize : 20			 //每页显示条数
			},
			loadingHTML : null, //列表加载时显示效果
			noDataHTML : null, //列表无数据时显示效果
			callback : null			 //回调函数
		};
		//配置合并
		var options = $.extend({}, defaults, settings || {});
		
		//绑定事件
		/*$(this).live(options.Event,function(e){
			
		});*/
		
		//无请求url,不执行后续操作
		if (!options.url) return;
		
		//表格
		var $table;
		//总条数
		var totalCount = 0;
		//总页数
		var totalPage = 1;
		//分页按钮显示数
		var pageCount = 5;
		
		//配置项
		var p = options;
		
		//结果集
		var results;
		
		//初始化参数
		if (p.params == null)
			p.params = {};
		
		//分页配置
		if (p.pager) {
			if (!p.pager.page)
				p.pager.page = 1;
			if (!p.pager.pageSize)
				p.pager.pageSize = 20;
			p.params["page"] = p.pager.page;
			p.params["pageSize"] = p.pager.pageSize;
		}
		
		//方法集
		var methods = {
			//初始化,执行请求
			init : function() {
				//移除元素
				methods.removeTag();
				//显示正在加载
				if (p.loadingHTML) {
					$table.after(p.loadingHTML);
				} else {
					$table.after("<div id='easy-table-loading' class='Data-loaded'>数据正在加载中<span class='icon-loading'></span></div>");
				}
				$.ajax({
					type: p.type,
					url:  p.url,
					data: p.params,
					async : p.async,	
					dataType: "json",
					success: function (response) {
						var data = response;
						//清除加载提示
						methods.removeTag();
						var count = data.count;
						if (count == 0) {
							methods.appendNoData($table);
						} else {
							//填充表格
							methods.fillTable(data);
							
							//创建分页
							if (p.pager.pageTarget) {
								methods.createPager(data);
							}
						}
						//回调函数
						if (p.callback) {
							eval(p.callback)(data);
						}
					},
					error: function (XMLHttpRequest, textStatus, errorThrown) {
						//try {
							//if (p.onError) p.onError(XMLHttpRequest, textStatus, errorThrown);
						//} catch (e) {}
						//alert("easyTable获取数据失败！");
					}
				});
			},
			//填充表格
			fillTable : function(data) {
				//表格标题行
				var tr = $table.find("tr:first");
				//表格标题列
				var tds = tr.children();
				//是否显示序号
				if (p.showSerNum) {
					var serNumTh = $("<" + tds.get(0).tagName + ">");
					serNumTh.attr("width", "2%").attr("id","easy-table-sernum-th");
					tds.first().before(serNumTh);
				}
				results = data.results;
				//创建列
				for (var i = 0, j = results.length; i < j; i ++) {
					var currResult = results[i];
					methods.createTr(i, tds, currResult);
				}
			},
			//创建行
			createTr : function(index, tds, record) {
				//追加列
				var tr = $("<tr></tr>");
				//创建序号
				if (p.showSerNum) {
					var serNumTd = $("<td>" + (index + 1) + "</td>")
					tr.append(serNumTd);
				}
				tds.each(function(){
					var td = $(this);
					var currTd = methods.createTd(tr, index, td, record);
					if (currTd)
						tr.append(currTd);
				});
				//追加行
				$table.append(tr);
			},
			//创建列
			createTd : function(tr, index, td, record) {
				//var id = td.attr("id");
				//列名
				var field = td.attr("field");
				//列回调函数
				var render = td.attr("render");
				//样式
				var cls = td.attr("cls");
				
				var currTd = $("<td />");;
				/*if (field) {
					currTd = $("<td id='easyTable_" + field + "_" + index + "'></td>");
				} else {
					currTd = $("<td id='easyTable_" + index + "'></td>");
				}*/
				var fieldVal;
				//执行函数
				if (render) {
					fieldVal = eval(render)(tr, index, record, field);
				}else if (field) {
					fieldVal = methods.getColumnVal(field, record);
					currTd.attr("title", fieldVal);
				} else {
					fieldVal = td.html();	
					currTd.attr("title", fieldVal);
				}
				currTd.html(fieldVal);
				if (cls) {
					currTd.addClass(cls);
				}
				//隐藏列
				if (td.is(":hidden")) {
					currTd.hide();
				}
				return currTd;
			},
			//获取属性值(支持内嵌对象,如：result.user.name)
			getColumnVal : function(field, record) {
				var fieldArr = field.split(".");
				var fieldLen = fieldArr.length;
				if (fieldLen > 1) { //内嵌
					var resultTEXT = "";
					for (var i = 0, j = fieldLen; i < j; i ++) {
						resultTEXT += "['" + fieldArr[i] + "']";
					}
					return eval("record" + resultTEXT);
				}
				return record[field];
			},
			//创建分页条
			createPager : function(data) {
				totalCount = data.count;
				totalPage = parseInt(totalCount % p.pager.pageSize == 0 ?  Number(totalCount / p.pager.pageSize) : totalCount / p.pager.pageSize + 1, 10);
				var pagination = $(p.pager.pageTarget);
				//无数据，则不显示分页按钮
				if (totalPage == 0)
					return;
				var displayHTML = "<div class='f-l total'>当前第<span class='color-red'>" + p.pager.page + "</span>页, 显示" + data.results.length + "</span>条, 共" + totalCount + "条数据</div>";
				var display = $(displayHTML);
				pagination.append(display);
				var page = $("<div />");
				page.addClass("f-r page");
				pagination.append(page);
				//第一页
				var first = $("<a />");
				first.bind("click", methods.goPage(1)).attr("href", "javascript:void(0);").html(1);
				page.append(first);
				if (p.pager.page == 1 || totalPage == 1) {
					first.addClass("cur");
				}
				if (totalPage == 1)
					return;
				if (p.pager.page != 1) {
					var prev = $("<a>上一页</a>");
					prev.bind("click", methods.prev()).attr("href", "javascript:void(0);");
					first.before(prev);
				}
				if (totalPage <= pageCount + 1) {
					methods.appendPage(page, 2, totalPage - 2);
				} else if (p.pager.page > totalPage - pageCount ) {
					methods.appendPage(page, 2, 0);
					methods.appendOmitted(page);
					methods.appendPage(page, totalPage - pageCount - 1, 6);
					
				} else if ((p.pager.page >= pageCount) && (p.pager.page <= totalPage - pageCount)) {
					methods.appendPage(page, 2, 0);
					methods.appendOmitted(page);
					methods.appendPage(page, p.pager.page - 2, 4);
					methods.appendOmitted(page);
					methods.appendPage(page, totalPage - 1, 1);
				} else {
					methods.appendPage(page, 2, 3);
					methods.appendOmitted(page);
					methods.appendPage(page, totalPage - 1, 1);
				}
				if (p.pager.page != totalPage) {
					var next = $("<a>下一页</a>");
					next.bind("click", methods.next()).attr("href", "javascript:void(0);");
					page.append(next);
				}
			},
			//上一页
			prev : function() {
				return function(){ methods.pageLoad(p.pager.page - 1) };
			},
			//下一页
			next : function() {
				return function(){ methods.pageLoad(p.pager.page + 1) };
			},
			//分页跳转
			pageLoad : function(page) {
				p.pager.page = page;
				p.params["page"] = page;
				methods.init();
			}, 
			//分页按钮点击
			goPage : function(page) {
				return function(){ methods.pageLoad(page) };
			},
			//追加分页按钮
			appendPage : function(page, begin, end) {
				for (var i = begin; i <=begin + end; i ++) {
				
					var a = $("<a />");
					a.bind("click", methods.goPage(i)).attr("href", "javascript:void(0);").html(i);
					if (p.pager.page == i)
						a.addClass("cur");
					page.append(a);
				}
			},
			//追加省略
			appendOmitted : function(page) {
				var span = $("<span>...</span>");
				page.append(span);
			},
			//追加省略
			appendNoData : function(table) {
				if (p.noDataHTML) {
					table.after(p.noDataHTML);
				} else {
					var dl = $("<dl class='tips-noData clearfix' id='easy-table-nodata'/>");
					var dt = $("<dt />");
					var span = $("<span />").addClass("icon-tips-01");
					var dd = $("<dd />").html("暂无合适的数据！");
					dt.append(span);
					dl.append(dt);
					dl.append(dd);
					table.after(dl);
				}
			},
			//移除元素
			removeTag : function() {
				//移除除表头以外的元素
				$table.find("tr:not(:first)").remove();
				$("#easy-table-loading").remove();
				$("#easy-table-sernum-th").remove();
				$("#easy-table-nodata").remove();
				//清除分页
				if (p.pager.pageTarget)
					$(p.pager.pageTarget).empty();
			}
		};
		
		//获取数据集
		this.getResults = function() {
			return resutls;
		};
		
		//获取当前页
		this.getCurrPage = function() {
			return p.pager.page;
		};
		
		//设置当前页
		this.setCurrPage = function(page) {
			p.pager.page = page;
			p.params["page"] = page;
		};
		
		//获取每页显示数
		this.getPageSize = function() {
			return p.pager.pageSize;
		};
		
		//设置每页显示数
		this.setPageSize = function(pageSize) {
			p.pager.pageSize = pageSize;
			p.params["pageSize"] = pageSize;
		};
		
		//获取当前参数集
		this.getParams = function() {
			return p.params;
		};
		
		//获取当前条件下总数量
		this.getTotalCount = function() {
			return totalCount;
		};
		
		//重新加载列表
		this.reload = function (params) {
			if (params)
				for (var key in params) {
					p.params[key] = params[key];
				}
			//再加载
			methods.init();
		};
		
		//构造表格
		return this.each(function() {
			$table = $(this);
			//初始化
			//methods.init.apply(this, options);
			methods.init($table, options);
		}); 
	};
});
