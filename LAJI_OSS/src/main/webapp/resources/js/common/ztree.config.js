var zTreeObj;
var addFont = {
		'font-family' : '微软雅黑',
		'font-size' : '12px',
		'font-weight' : 'normal',
		'font-style' : 'normal',
		'text-decoration' : 'none',
		'color' : '#666666',
		'margin-bottom' : '5px'
	};
var ztree_setting = {
	async : {
		enable : true,
		type : "post",
		dataFilter:dataFilter
	},
	view : {
		showLine : true,
		showIcon : false,
		selectedMulti : false,
		addHoverDom : addHoverDom,
		removeHoverDom : removeHoverDom,
		fontCss : addFont
	},
	edit : {
		drag : {
			prev : false,
			next : false,
			inner : false
		},
		enable : true,
		editNameSelectAll : false,
		showRemoveBtn : false,
		showRenameBtn : false
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	callback : {
		onClick : onClick,
		onCollapse : onCollapse,
		onExpand : onExpand,
		onAsyncSuccess: onAsyncSuccess
	}
};
