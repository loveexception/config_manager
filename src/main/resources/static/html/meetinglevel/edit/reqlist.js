(function (w) {
	// 地址

	const href ="http://"+  w.locationParams.ip  + '/api/backgroundinterface/meetingLevel/';
	// /batchDeleteMeeting
	w.backgroundInterface = {
		getMeetingLevelByPage,
		getMeetingLevelByDept,
		batchDelete,
		saveMeetingLevel,
		updateMeetingLevel,
	};
	function getDeptId() {
		// let id = localStorage.getItem('deptId');
		// if (id === null) {
		// 	console.log('deptId 为空 请重新登陆')
		// }

		return w.locationParams.dept_id
	}
	function getMeetingLevelByPage(params, callback) {  // 持续时间状态
		$.ajax({
			cache: true,
			type: 'POST',
			url: href + 'getMeetingLevelByPage',
			data: JSON.stringify(params),
			dataType: 'json',
			async: false,
			headers: { 'Content-Type': 'application/json;charset=utf8', 'dept_id': getDeptId() },
			contentType: "application/json",
			success: callback
		})

	}
	function updateMeetingLevel(params, callback) {  // 持续时间状态
		$.ajax({
			cache: true,
			type: 'POST',
			url: href + 'update',
			data: JSON.stringify(params),
			dataType: 'json',
			async: false,
			headers: { 'Content-Type': 'application/json;charset=utf8', 'dept_id': getDeptId() },
			contentType: "application/json",
			success: callback
		})

	}
	function saveMeetingLevel(params, callback) {  // 持续时间状态
		$.ajax({
			cache: true,
			type: 'POST',
			url: href + 'save',
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
			url: href + 'getMeetingLevelByDept',
			data: JSON.stringify(params),
			dataType: 'json',
			async: false,
			headers: { 'Content-Type': 'application/json;charset=utf8', 'dept_id': getDeptId() },
			contentType: "application/json",
			success: callback
		})

	}
	function batchDelete(params, callback) {  // 持续时间状态
		$.ajax({
			cache: true,
			type: 'POST',
			url: href + 'batchDelete',
			data: JSON.stringify(params),
			dataType: 'json',
			async: false,
			headers: { 'Content-Type': 'application/json;charset=utf8', 'dept_id': getDeptId() },
			contentType: "application/json",
			success: callback
		})

	}

})(window)