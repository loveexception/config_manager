
// 修改信息组建
// function 
let { _import = {} } = window;
let { EditComponent } = _import;
let { message } = antd;
let isEdit = location.search.split('?')[1];
isEdit = isEdit && JSON.parse(isEdit.split('=')[1]);
let id = location.search.split('?')[1];
id = id && JSON.parse(isEdit.split('=')[1])
let { saveMeeting, updateMeeting } = window.backgroundInterface;
function dateUtil(time) {
	if (typeof time === "number") {
		time = new Date(time)
	}
	let result = time.getFullYear() + '-' + (time.getMonth() - 0 + 1) + '-' + time.getDate() + ' ' + time.getHours() + ':' + time.getMinutes() + ':' + time.getSeconds();
	return result;
}

function App(props) {

	React.useEffect(() => {
	}, [])
	let liObj = React.useRef([{
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
		isStart: true
	}, {
		leftName: '结束时间',
		required: true,
		placeholder: '请选择结束时间',
		key: 'end_time',
		type: 'time',
		isStart: false
	},
	{
		leftName: '会议级别',
		required: true,
		placeholder: '请选择级别',
		key: 'level',
		type: 'select',
		selectArr: [
			{ key: '年中', value: 0 },
			{ key: '年终', value: 1 },
			{ key: '季度', value: 2 },
			{ key: '月例会', value: 3 },
			{ key: '周例会', value: 4 },
			{ key: '迎峰度夏', value: 5 },
			{ key: '迎峰度冬', value: 6 },
		]
	},
	{
		leftName: '保障人员',
		required: true,
		placeholder: '请输入保障人员',
		key: 'user_name'
	},
		// {
		// 	leftName: '描述',
		// 	required: false,
		// 	placeholder: '请输入描述',
		// 	key: "remark"
		// }
	])

	function commitFn(data) {
		//发送请求
		if (isEdit === true) {
			updateMeeting({ ...data, id, begin_time: dateUtil(data.begin_time), end_time: dateUtil(data.end_time) }, (res) => {
				console.log(res)
				if (res.success) {
					parent.getTableData()
					message.success('添加成功')
					$.modal.close()
				} else {
					message.error('添加失败')
				}
			})
		} else if (isEdit === false) {
			saveMeeting({ ...data, begin_time: dateUtil(data.begin_time), end_time: dateUtil(data.end_time) }, (res) => {
				console.log(res)
				if (res.success) {
					parent.getTableData()
					$.modal.close()
					message.success('添加成功')
				} else {
					message.error('添加失败')
				}
			})
		}
		// 成功后关闭窗口
	}
	return <div className="meeting-edit-box">
		<EditComponent liObj={liObj.current} title={isEdit ? '修改基本信息' : '新增基本信息'} commitFn={commitFn} />
	</div>
}

ReactDOM.render(<App />, window.rootNode);