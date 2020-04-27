
// 修改信息组建
// function 
let { message } = antd;

let { _import = {} } = window;
let { EditComponent } = _import;
let { saveMeeting, updateMeeting, getMeetingLevelByDept } = window.backgroundInterface;
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
	let [selectDept, setSelectDept] = React.useState([]); // object key value
	let liObj = [{
		leftName: '会议名称',
		required: true,
		placeholder: '请输入会议名称',
		key: 'name',
		type: 'input',  //type  select time inpupt  ...radio
	}, {
		leftName: '开始时间',
		required: true,
		placeholder: '请选择开始时间',
		key: 'begin_time',
		type: 'time',
		isStart: true,
		rule: true
	}, {
		leftName: '结束时间',
		required: true,
		placeholder: '请选择结束时间',
		key: 'end_time',
		type: 'time',
		isStart: false,
		rule: true

	},
	{
		leftName: '会议级别',
		required: true,
		placeholder: '请选择级别',
		key: 'level_id',
		type: 'select',
		// selectArr: [
		// 	{ key: '年中', value: 0 },
		// 	{ key: '年终', value: 1 },
		// 	{ key: '季度', value: 2 },
		// 	{ key: '月例会', value: 3 },
		// 	{ key: '周例会', value: 4 },
		// 	{ key: '迎峰度夏', value: 5 },
		// 	{ key: '迎峰度冬', value: 6 },
		// ] //更改 
		selectArr: selectDept
	},
	{
		leftName: '保障人员',
		required: true,
		placeholder: '请输入保障人员',
		key: 'user_name'
	},
	{
		leftName: '描述',
		required: false,
		placeholder: '请输入描述',
		key: "remark"
	}
	]
	React.useEffect(() => {
		if (isEdit) {
			setInputMessage(parent.currentEditObj)
		} else {
		}
		// console.log(parent.currentEditObj, 'parent.currentEditObj')
		// liObjliObj.map
	}, [isEdit])

	React.useEffect(() => {
		setSelectDept(
			parent.meetingLevelObj.map((e) => {
				return { key: e.name, value: e.id }
			})
		)
	}, [])

	function commitFn(data) {
		//发送请求
		if (isEdit === true) {
			// updateMeeting({ ...data, id, begin_time: dateUtil(data.begin_time), end_time: dateUtil(data.end_time) }, (res) => {
			let level = selectDept.find((e) => {
				console.log(e.key, data.level_id)
				return e.key == data.level_id
			})
			console.log(level, 'level')
			updateMeeting({ ...data, id, begin_time: data.begin_time, end_time: data.end_time, level_id: level.value }, (res) => {
				// updateMeeting({
				// 	"name": "会议3",
				// 	"begin_time": "2020-04-11 08:10:00",
				// 	"end_time": "2020-04-11 08:40:00",
				// 	"level": 1,
				// 	"user_name": "苏苏"
				// }, (res) => {
				if (res.success) {
					$.modal.close()
					parent.getTableData()
					message.success('添加成功')
				} else {
					message.error('添加失败')
				}
			})
		} else if (isEdit === false) {
			// saveMeeting({ ...data, begin_time: dateUtil(data.begin_time), end_time: dateUtil(data.end_time) }, (res) => {
			saveMeeting({ ...data, begin_time: data.begin_time, end_time: data.end_time }, (res) => {
				// saveMeeting({
				// 	"name": "会议3",
				// 	"begin_time": "2020-04-11 08:10:00",
				// 	"end_time": "2020-04-11 08:40:00",
				// 	"level": 1,
				// 	"user_name": "苏苏"
				// }, (res) => {
				$.modal.close()
				if (res.success) {
					message.success('添加成功')
					parent.getTableData()
				} else {
					message.error('添加失败')
				}
			})
		}
		// 成功后关闭窗口
	}
	return <div className="meeting-edit-box">
		<EditComponent formName={"meeting-edit-box"} inputMessage={inputMessage} liObj={liObj} title={isEdit ? '修改基本信息' : '新增基本信息'} commitFn={commitFn} />
	</div>
}

ReactDOM.render(<App />, window.rootNode);