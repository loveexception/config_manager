
// 修改信息组建
// function 
let { _import = {} } = window;
let { EditComponent } = _import;
// console.log(window._import, 'xxx')
function App(props) {
	let liObj = React.useRef([{
		leftName: '会议名称',
		required: true,
		placeholder: '请输入会议名称',
		key: 'meeting',
		type: 'input',  //type  select time inpupt  ...radio
	}, {
		leftName: '开始时间',
		required: true,
		placeholder: '请选择开始时间',
		key: 'startTime',
		type: 'time'
	}, {
		leftName: '结束时间',
		required: true,
		placeholder: '请选择结束时间',
		key: 'endTime',
		type: 'time'
	},
	{
		leftName: '会议级别',
		required: true,
		placeholder: '请选择级别',
		key: 'level',
		type: 'select'
	},
	{
		leftName: '保障人员',
		required: true,
		placeholder: '请输入保障人员',
		key: 'safeguard'
	},
	{
		leftName: '描述',
		required: false,
		placeholder: '请输入描述',
		key: "description"
	}
	])
	return <div className="meeting-edit-box">
		<EditComponent liObj={liObj.current} title={'基本信息'} />
	</div>
}

ReactDOM.render(<App />, window.rootNode);