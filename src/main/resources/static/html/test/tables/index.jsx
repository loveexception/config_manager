let { Icon, Spin, Modal, Button, Table, message, Popover } = antd;

var domId = '';
var scripts = document.getElementsByTagName('script');
for (var i = 0; i < scripts.length; i++) {
	var script = scripts[i];
	if (script && script.getAttribute('type') == 'text/babel') {
		domId = script.getAttribute('domId');
	}
}
$.modal.openFull = function(title, url, width, height) {
	//如果是移动端，就使用自适应大小弹窗
	if (navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)) {
		width = 'auto';
		height = 'auto';
	}
	if ($.common.isEmpty(title)) {
		title = false;
	}
	if ($.common.isEmpty(url)) {
		url = '/404.html';
	}
	if ($.common.isEmpty(width)) {
		width = 800;
	}
	if ($.common.isEmpty(height)) {
		height = $(window).height() - 50;
	}
	var index = layer.open({
		type: 2,
		area: [width + 'px', height + 'px'],
		fix: true,
		//不固定
		maxmin: false,
		shade: 0.3,
		title: title,
		content: url,
		// btn: ['确定', '关闭'],
		// 弹层外区域关闭
		shadeClose: false,
		yes: function(index, layero) {
			var iframeWin = layero.find('iframe')[0];
			iframeWin.contentWindow.submitHandler();
		},
		cancel: function(index, layero) {
			var iframeWin = layero.find('iframe')[0];
			return iframeWin.contentWindow.cancelHandler();
			// return true;
		}
	});
	layer.full(index);
};
