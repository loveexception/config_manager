(function (w) {

	// 地址
	const href = w.locationParams.ip + '/api/backgroundinterface/';

	w.DiagramAction = {
		diagramList,
		diagramuploadImg,
		diagramRename,
		diagramViewImg,
		diagramODownImg,
		diagramORmImg,
		diagramMRmImg,
		diagramMDownImg,


	};
	function getDeptId() {
		// let id = localStorage.getItem('deptId');
		// if (id === null) {
		// 	console.log('deptId 为空 请重新登陆')
		// }

		return w.locationParams.dept_id
	}
	function diagramList(params, callback) {
		$.ajax({
			cache: true,
			type: 'POST',
			url: href + 'topology/list',
			headers: { 'Content-Type': 'application/json;charset=utf8', 'dept_id': getDeptId() },

			data: JSON.stringify(params),
			dataType: 'json',
			async: false,
			contentType: "application/json",
			success: callback
		})
	}
	// 上传图片
	function diagramuploadImg(params, callback) {
		$.ajax({
			cache: true,
			type: 'POST',
			contentType: "application/json",
			url: href + 'topology/upload',
			data: JSON.stringify(params),
			dataType: 'json',
			headers: { 'Content-Type': 'application/json;charset=utf8', 'dept_id': getDeptId() },

			async: false,
			success: callback
		})
	}
	//重命名
	function diagramRename(params, callback) {
		$.ajax({
			cache: true,
			type: 'POST',
			url: href + 'topology/renameById',
			contentType: "application/json",
			data: JSON.stringify(params),
			dataType: 'json',
			headers: { 'Content-Type': 'application/json;charset=utf8', 'dept_id': getDeptId() },

			async: false,
			success: callback
		})
	}
	// 点击显示图片
	function diagramViewImg(params, callback) {
		$.ajax({
			cache: true,
			type: 'POST',
			url: href + 'topology/preview',
			contentType: "application/json",
			data: JSON.stringify(params),
			dataType: 'json',
			async: false,
			headers: { 'Content-Type': 'application/json;charset=utf8', 'dept_id': getDeptId() },
			success: callback

		})
	}
	// 点击下载图片 多
	function test() {
	}
	function diagramMDownImg(params, callback) {
		$.ajax({
			cache: true,
			type: 'POST',
			url: href + 'topology/beachDownImg',
			data: JSON.stringify(params),
			dataType: 'json',
			contentType: "application/json",
			async: false,
			headers: { 'Content-Type': 'application/json;charset=utf8', 'dept_id': getDeptId() },

			success: callback
		})
	}
	// 点击下载图片 单
	function diagramODownImg(params, callback) {
		$.ajax({
			cache: true,
			type: 'GET',
			url: href + 'topology/downImg',
			data: JSON.stringify(params),
			contentType: "application/json",
			dataType: 'json',
			async: false,
			headers: { 'Content-Type': 'application/json;charset=utf8', 'dept_id': getDeptId() },

			success: callback
		})
	}
	// 点击删除图片 单
	function diagramORmImg(params, callback) {
		$.ajax({
			cache: true,
			type: 'POST',
			contentType: "application/json",
			url: href + 'topology/deleteByPrimaryKey',
			data: JSON.stringify(params),
			dataType: 'json',
			headers: { 'Content-Type': 'application/json;charset=utf8', 'dept_id': getDeptId() },

			async: false,
			success: callback
		})
	}
	// 点击删除图片 多
	function diagramMRmImg(params, callback) {
		$.ajax({
			cache: true,
			type: 'POST',
			url: href + 'topology/beachDel',
			data: JSON.stringify(params),
			contentType: "application/json",
			dataType: 'json',
			async: false,
			headers: { 'Content-Type': 'application/json;charset=utf8', 'dept_id': getDeptId() },
			success: callback

		})
	}
	//

})(window)