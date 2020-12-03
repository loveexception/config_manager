(function (w) {
	// 地址

	const href =  w.locationParams.ip + '/api/backgroundinterface/meeting/';
	const href2 = w.locationParams.ip + '/api/backgroundinterface/meetingLevel/'
	// /batchDeleteMeeting
	w.backgroundInterface = {
		getMeetingByPage,
		saveMeeting,
		updateMeeting,
		getDurationStats,
		batchDeleteMeeting,
		getMeetingLevelByDept,
		getMeetingLevelStats,
		getDownLoadUrl,
		downLoad
	};
	function getDeptId() {
		// let id = localStorage.getItem('deptId');
		// if (id === null) {
		// 	console.log('deptId 为空 请重新登陆')
		// }

		return w.locationParams.dept_id
	}
	function getDownLoadUrl(params, callback) {
		$.ajax({
			cache: true,
			type: 'POST',
			url: href + 'downLoadMeetingTemplate',
			data: JSON.stringify(params),
			dataType: 'json',
			async: false,
			headers: { 'Content-Type': 'application/json;charset=utf8', 'dept_id': getDeptId() },
			contentType: "application/json",
			success: callback
		})
	}
	function downLoad(url, callback) {
		$.ajax({
			cache: true,
			type: 'POST',
			url,
			// data: JSON.stringify(params),
			responseType: 'blob',
			dataType: 'json',
			async: false,
			headers: { 'Content-Type': 'application/json;charset=utf8', 'dept_id': getDeptId() },
			contentType: "application/json",
			success: callback
		})
	}

	function getDurationStats(params, callback) {  // 持续时间状态
		$.ajax({
			cache: true,
			type: 'POST',
			url: href + 'getDurationStats',
			data: JSON.stringify(params),
			dataType: 'json',
			async: false,
			headers: { 'Content-Type': 'application/json;charset=utf8', 'dept_id': getDeptId() },
			contentType: "application/json",
			success: callback
		})

	}
	function getMeetingLevelStats(params, callback) {  // 持续时间状态
		$.ajax({
			cache: true,
			type: 'POST',
			url: href + 'getMeetingLevelStats',
			data: JSON.stringify(params),
			dataType: 'json',
			async: false,
			headers: { 'Content-Type': 'application/json;charset=utf8', 'dept_id': getDeptId() },
			contentType: "application/json",
			success: callback
		})

	}
	function getMeetingLevelByDept(params, callback) {  // 持续时间状态
		$.ajax({
			cache: true,
			type: 'POST',
			url: href2 + 'getMeetingLevelByDept',
			data: JSON.stringify(params),
			dataType: 'json',
			async: false,
			headers: { 'Content-Type': 'application/json;charset=utf8', 'dept_id': getDeptId() },
			contentType: "application/json",
			success: callback
		})

	}
	function batchDeleteMeeting(params, callback) {  //删除
		$.ajax({
			cache: true,
			type: 'POST',
			url: href + 'batchDeleteMeeting',
			data: JSON.stringify(params),
			dataType: 'json',
			async: false,
			headers: { 'Content-Type': 'application/json;charset=utf8', 'dept_id': getDeptId() },
			contentType: "application/json",
			success: callback
		})

	}
	function getMeetingByPage(params, callback) {  //列表
		$.ajax({
			cache: true,
			type: 'POST',
			url: href + 'getMeetingByPage',
			data: JSON.stringify(params),
			dataType: 'json',
			async: false,
			headers: { 'Content-Type': 'application/json;charset=utf8', 'dept_id': getDeptId() },
			contentType: "application/json",
			success: callback
		})
	}
	function saveMeeting(params, callback) {  ///保存函数
		$.ajax({
			cache: true,
			type: 'POST',
			url: href + 'saveMeeting',
			data: JSON.stringify(params),
			dataType: 'json',
			async: false,
			// 加东西 
			headers: { 'Content-Type': 'application/json;charset=utf8', 'dept_id': getDeptId() },
			contentType: "application/json",
			success: callback
		})

	}
	function updateMeeting(params, callback) {  // 更新函数
		$.ajax({
			cache: true,
			type: 'POST',
			url: href + 'updateMeeting',
			data: JSON.stringify(params),
			dataType: 'json',
			async: false,
			// 加东西 

			headers: { 'Content-Type': 'application/json;charset=utf8', 'dept_id': getDeptId() },
			contentType: "application/json",
			success: callback
		})
	}

})(window)