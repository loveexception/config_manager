
// 修改信息组建
// function 
let { _import = {} } = window;
let { EditComponent } = _import;
let { message } = antd;
let isEdit = location.search.split('?')[1];
// console.log(location.search)
// let isEdit = false;
isEdit = isEdit && JSON.parse(isEdit.split('&').reduce((pre, element, index) => {
	if (element.indexOf('edit') != -1) {
		pre += element.split('=')[1]
	}
	return pre
}, ''));
let id = location.search.split('?')[1];
id = id && id.split('&').reduce((pre, element, index) => {

	if (element.indexOf('id') != -1) {
		pre += element.split('=')[1]
	}
	return pre
}, '')
let { saveMeetingLevel, updateMeetingLevel } = window.backgroundInterface;
function dateUtil(time) {
	if (typeof time === "number") {
		time = new Date(time)
	}
	let timeSeconds = time.getSeconds() > 9 ? time.getSeconds() : `0${time.getSeconds()}`
	let result = time.getFullYear() + '-' + (time.getMonth() - 0 + 1) + '-' + time.getDate() + ' ' + time.getHours() + ':' + time.getMinutes() + ':' + timeSeconds;
	return result;
}

function App(props) {
	let [inputMessage, setInputMessage] = React.useState({})
	React.useEffect(() => {
		console.log(parent.currentEditObj, 'parent.currentEditObj')
	}, [])
	let liObj = React.useRef([{
		leftName: '会议级别',
		required: true,
		placeholder: '请输入级别',
		key: 'name',
		type: 'input',  //type  select time inpupt  ...radio
	}, {
		leftName: '权限字符',
		required: true,
		placeholder: '请输入权限字符',
		key: 'en_name',
		type: 'input',
		// isStart: true
	},
	{
		leftName: '级别描述',
		required: false,
		placeholder: '请输入描述',
		key: "remark"
	}
	])

	function commitFn(data) {
		//发送请求
		if (isEdit === true) {
			// updateMeetingLevel({ ...data, id, begin_time: dateUtil(data.begin_time), end_time: dateUtil(data.end_time) }, (res) => {
			updateMeetingLevel({ ...data, id, begin_time: data.begin_time, end_time: data.end_time }, (res) => {
				// updateMeetingLevel({
				// 	"name": "会议3",
				// 	"begin_time": "2020-04-11 08:10:00",
				// 	"end_time": "2020-04-11 08:40:00",
				// 	"level": 1,
				// 	"user_name": "苏苏"
				// }, (res) => {
				if (res.success) {
					parent.getTableData()
					message.success('添加成功')
					$.modal.close()
				} else {
					message.error('添加失败')
				}
			})
		} else if (isEdit === false) {
			// saveMeetingLevel({ ...data, begin_time: dateUtil(data.begin_time), end_time: dateUtil(data.end_time) }, (res) => {
			saveMeetingLevel({ ...data, begin_time: data.begin_time, end_time: data.end_time }, (res) => {
				// saveMeetingLevel({
				// 	"name": "会议3",
				// 	"begin_time": "2020-04-11 08:10:00",
				// 	"end_time": "2020-04-11 08:40:00",
				// 	"level": 1,
				// 	"user_name": "苏苏"
				// }, (res) => {
				if (res.success) {
					message.success('添加成功')
					parent.getTableData()
					$.modal.close()
				} else {
					message.error('添加失败')
				}
			})
		}
		// 成功后关闭窗口
	}
	return <div className="meeting-level-edit-box">
		{isEdit ?
			<EditComponent formName={`meeting-level-edit`} inputMessage={inputMessage} liObj={liObj.current} title={isEdit ? '修改基本信息' : '新增基本信息'} commitFn={commitFn} />
			:
			<EditComponent formName={`meeting-level-add`} inputMessage={inputMessage} liObj={liObj.current} title={isEdit ? '修改基本信息' : '新增基本信息'} commitFn={commitFn} />
		}
	</div>
}

ReactDOM.render(<App />, window.rootNode);