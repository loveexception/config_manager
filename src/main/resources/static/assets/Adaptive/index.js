(function (doc, win) {
	var deviceWidth;
	setHtmlFontSize();

	if (win.addEventListener) {
		win.addEventListener(
			'resize',
			function () {
				setHtmlFontSize();
			},
			false
		);
	}
	function setHtmlFontSize() {
		// 1366是设计稿的宽度，当大于1366时采用1366宽度，比例也是除以13.66
		deviceWidth = doc.documentElement.clientWidth <= 1366 ? 1366 : doc.documentElement.clientWidth;
		win.html_font_size = deviceWidth / 13.66;
		doc.getElementsByTagName('html')[0].style.cssText = 'font-size:' + deviceWidth / 13.66 + 'px !important';
	}
})(document, window);