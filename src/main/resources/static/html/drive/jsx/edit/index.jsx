var domId = '';
var scripts = document.getElementsByTagName('script');
for (var i = 0; i < scripts.length; i++) {
	var script = scripts[i];
	if (script && script.getAttribute('type') == 'text/babel') {
		domId = script.getAttribute('domId');
	}
}

let {
	Descriptions,
	Collapse,
	Icon,
	Button,
	message,
	Input,
	Table,
	Popconfirm,
	Form,
	Radio,
	Modal
} = antd;
const { Panel } = Collapse;
const { confirm } = Modal;

const LISTDATA = [
	{
		指标项中文简称: '数据采集时间',
		指标项英文简称: 'data_create_time',
		操作码: 'nbiot_create_time',
		单位: 'ms',
		告警使能: '是',
		key: 0
	},
	{
		指标项中文简称: '双流输入连接状态',
		指标项英文简称: 'dualFlow_connectedStatus',
		操作码: 'camera[2].connected;',
		单位: '_',
		告警使能: '否',
		key: 1
	},
	{
		指标项中文简称: '麦克风静音状态',
		指标项英文简称: 'micro_mutedStatus',
		操作码: 'micro_muted',
		单位: '_',
		告警使能: '是',
		key: 2
	},
	{
		指标项中文简称: '主摄像头连接状态',
		指标项英文简称: 'mainCamera_connectedStatus',
		操作码: 'camera[0].connected',
		单位: '_',
		告警使能: '是',
		key: 3
	},
	{
		指标项中文简称: '第二个摄像头连接状态',
		指标项英文简称: 'viceCamera_connectedStatus',
		操作码: 'camera[1].connected',
		单位: '_',
		告警使能: '否',
		key: 4
	},
	{
		指标项中文简称: '全向麦连接状态',
		指标项英文简称: 'omniMic_connectedStatus',
		操作码: 'system_status_microphones',
		单位: '_',
		告警使能: '是',
		key: 5
	},
	{
		指标项中文简称: '全向麦音频输入状态',
		指标项英文简称: 'omniMic_inputStatus',
		操作码: 'micro.POLYCOM_MIC',
		单位: '_',
		告警使能: '否',
		key: 6
	},
	{
		指标项中文简称: '线性麦音频输入状态',
		指标项英文简称: 'lineMic_inputStatus',
		操作码: 'micro.LINE_IN',
		单位: '_',
		告警使能: '是',
		key: 7
	},
	{
		指标项中文简称: '与会状态',
		指标项英文简称: 'meeting_status ',
		操作码: 'system_status_inacall',
		单位: '_',
		告警使能: '否',
		key: 8
	},
	{
		指标项中文简称: '音频接收网络抖动',
		指标项英文简称: 'audioRX_jitter',
		操作码: 'meeting[1].jitter',
		单位: 'ms',
		告警使能: '是',
		key: 9
	},
	{
		指标项中文简称: '音频发送网络抖动',
		指标项英文简称: 'audioTX_jitter',
		操作码: 'meeting[0].jitter',
		单位: 'ms',
		告警使能: '是',
		key: 10
	},
	{
		指标项中文简称: '音频发送丢包数',
		指标项英文简称: 'audioTx_packetLoss',
		操作码: 'meeting[0].packetLoss',
		单位: '个',
		告警使能: '是',
		key: 11
	},
	{
		指标项中文简称: '音频接收丢包数',
		指标项英文简称: 'audioRx_packetLoss',
		操作码: 'meeting[1].packetLoss',
		单位: '个',
		告警使能: '是',
		key: 12
	},
	{
		指标项中文简称: '音频接收丢包率',
		指标项英文简称: 'audioRX_percentPacketLoss',
		操作码: 'meeting[1].percentPacketLoss',
		单位: '%',
		告警使能: '是',
		key: 13
	},
	{
		指标项中文简称: '音频传输丢包率',
		指标项英文简称: 'audioTX_percentPacketLoss',
		操作码: 'meeting[0].percentPacketLoss',
		单位: '%',
		告警使能: '是',
		key: 14
	},
	{
		指标项中文简称: '音频发送延迟',
		指标项英文简称: 'audioTX_latency',
		操作码: 'meeting[0].latency',
		单位: 'ms',
		告警使能: '是',
		key: 15
	},
	{
		指标项中文简称: '音频接收延迟',
		指标项英文简称: 'audioRX_latency',
		操作码: 'meeting[1].latency',
		单位: 'ms',
		告警使能: '是',
		key: 16
	},
	{
		指标项中文简称: '视频接收网络抖动',
		指标项英文简称: 'videoRX_jitter',
		操作码: 'meeting[3].jitter',
		单位: 'ms',
		告警使能: '是',
		key: 17
	},
	{
		指标项中文简称: '视频传输网络抖动',
		指标项英文简称: 'videoTX_jitter',
		操作码: 'meeting[2].jitter',
		单位: 'ms',
		告警使能: '是',
		key: 18
	},
	{
		指标项中文简称: '视频发送丢包数',
		指标项英文简称: 'videoTx_packetLoss',
		操作码: 'meeting[2].packetLoss',
		单位: 'ms',
		告警使能: '是',
		key: 19
	},
	{
		指标项中文简称: '视频接收丢包数',
		指标项英文简称: 'videoRx_packetLoss',
		操作码: 'meeting[3].packetLoss',
		单位: 'ms',
		告警使能: '是',
		key: 20
	},
	{
		指标项中文简称: '视频接收丢包率',
		指标项英文简称: 'videoRx_percentPacketLoss',
		操作码: 'meeting[3].percentPacketLoss',
		单位: '%',
		告警使能: '是',
		key: 21
	},
	{
		指标项中文简称: '视频传输丢包率',
		指标项英文简称: 'videoTx_percentPacketLoss',
		操作码: 'meeting[2].percentPacketLoss',
		单位: '%',
		告警使能: '是',
		key: 22
	},
	{
		指标项中文简称: '视频发送延迟',
		指标项英文简称: 'videoTX_latency',
		操作码: 'meeting[2].latency',
		单位: 'ms',
		告警使能: '是',
		key: 23
	},
	{
		指标项中文简称: '视频接收延迟',
		指标项英文简称: 'videoRX_latency',
		操作码: 'meeting[3].latency',
		单位: 'ms',
		告警使能: '是',
		key: 24
	},
	{
		指标项中文简称: '音频发送质量',
		指标项英文简称: 'audioTx_qualityIndicator',
		操作码: 'meeting[0].qualityIndicator',
		单位: '_',
		告警使能: '是',
		key: 25
	},
	{
		指标项中文简称: '音频接收质量',
		指标项英文简称: 'audioRx_qualityIndicator',
		操作码: 'meeting[1].qualityIndicator',
		单位: '_',
		告警使能: '是',
		key: 26
	},
	{
		指标项中文简称: '视频发送质量',
		指标项英文简称: 'videoTx_qualityIndicator',
		操作码: 'meeting[2].qualityIndicator',
		单位: '_',
		告警使能: '是',
		key: 27
	},
	{
		指标项中文简称: '视频接收质量',
		指标项英文简称: 'videoRx_qualityIndicator',
		操作码: 'meeting[3].qualityIndicator',
		单位: '_',
		告警使能: '是',
		key: 28
	},
	{
		指标项中文简称: '视频发送码率',
		指标项英文简称: 'videoTx_actualBitRate',
		操作码: 'meeting[2].actualBitRate',
		单位: 'Kb',
		告警使能: '是',
		key: 29
	},
	{
		指标项中文简称: '视频接收码率',
		指标项英文简称: 'videoRx_actualBitRate',
		操作码: 'meeting[3].actualBitRate',
		单位: 'Kb',
		告警使能: '是',
		key: 30
	},
	{
		指标项中文简称: '使用时长',
		指标项英文简称: 'usageTime_statistics',
		操作码: 'comm_statistics_totaltimeincalls',
		单位: '_',
		告警使能: '否',
		key: 31
	},
	{
		指标项中文简称: '音频发送质量',
		指标项英文简称: 'audioTx_meetingquality',
		操作码: 'meeting[0].qualityIndicator',
		单位: '_',
		告警使能: '否',
		key: 32
	},
	{
		指标项中文简称: '音频接收质量',
		指标项英文简称: 'audioRx_meetingquality',
		操作码: 'meeting[1].qualityIndicator',
		单位: '_',
		告警使能: '否',
		key: 33
	},
	{
		指标项中文简称: '视频发送质量',
		指标项英文简称: 'videoTx_meetingquality',
		操作码: 'meeting[2].qualityIndicator',
		单位: '_',
		告警使能: '否',
		key: 34
	},
	{
		指标项中文简称: '视频接收质量',
		指标项英文简称: 'videoRx_meetingquality',
		操作码: 'meeting[3].qualityIndicator',
		单位: '_',
		告警使能: '否',
		key: 35
	}
];

var _list_data = {};

function submitHandler() {
	console.log('--点击确定--');
}
var add_confirm = void 0;
function cancelHandler() {
	// console.log('--点击取消--', add_confirm);
	if (!add_confirm) {
		add_confirm = confirm({
			title: '确认关闭吗?',
			content: '关闭后会丢失添加数据',
			cancelText: '取消',
			okText: '确认',
			onOk() {
				iframeClose();
			},
			onCancel() {}
		});
	} else {
		add_confirm.destroy();
		add_confirm = confirm({
			title: '确认关闭吗?',
			content: '关闭后会丢失添加数据',
			cancelText: '取消',
			okText: '确认',
			onOk() {
				iframeClose();
			},
			onCancel() {}
		});
	}

	return false;
}
function iframeClose() {
	if (parent.layer) {
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}
}
class AlarmConfiguration extends React.PureComponent {
	state = {
		data: []
	};
	componentDidMount() {
		this.setState({
			data: LISTDATA
		});
	}
	render() {
		let { data = [] } = this.state;
		let columns = [
			{
				title: '指标项中文简称',
				dataIndex: '指标项中文简称',
				width: '30%',
				editable: true
			},
			{
				title: '指标项英文简称',
				width: '30%',
				dataIndex: '指标项英文简称',
				editable: true
			},
			{
				title: '操作码',
				dataIndex: '操作码',
				width: '10%',
				editable: true
			},
			{
				title: '单位',
				dataIndex: '单位',
				width: '10%',
				editable: true
			},
			{
				title: '告警使能',
				width: '10%',
				dataIndex: '告警使能',
				render: (text, record) => <EditableTableRadio data={record} />
			},
			{
				title: '操作',
				width: '10%',
				dataIndex: 'operation',
				render: (text, record) => {
					return (
						<Button
							className="btn-2"
							onClick={() => {
								_list_data = {
									record: record,
									list: data
								};
								$.modal.open(
									'告警规则设置',
									'/html/drive/alarmRules.html'
								);
							}}
						>
							告警配置
						</Button>
					);
				}
			}
		];

		return (
			<div>
				<Table
					bordered
					columns={columns}
					dataSource={data}
					pagination={{
						simple: true
					}}
				/>
				<div className="btn-group">
					<Button
						type="primary"
						onClick={() => {
							console.log(this.state);
							// if (this.indicatorsEditTable) {
							// 	let data = this.indicatorsEditTable.calibrationMethod();
							// 	if (data) {
							// 		console.log(data);
							// 	}
							// }
						}}
					>
						保存
					</Button>
				</div>
			</div>
		);
	}
}
class Indicators extends React.PureComponent {
	init = cb => {
		cb && cb(LISTDATA);
	};
	render() {
		// let { data = [] } = this.state;
		return (
			<div className="edit-indicators">
				<EditableTable
					ref={el => (this.indicatorsEditTable = el)}
					init={this.init}
				/>
				<div className="btn-group">
					<Button
						type="primary"
						onClick={() => {
							if (this.indicatorsEditTable) {
								let data = this.indicatorsEditTable.calibrationMethod();
								if (data) {
									console.log(data);
								}
							}
						}}
					>
						保存
					</Button>
				</div>
			</div>
		);
	}
}

const EditableContext = React.createContext();

const EditableRow = ({ form, index, ...props }) => (
	<EditableContext.Provider value={form}>
		<tr {...props} />
	</EditableContext.Provider>
);

const EditableFormRow = Form.create()(EditableRow);

class EditableCell extends React.PureComponent {
	state = {
		editing: false
	};

	toggleEdit = () => {
		const editing = !this.state.editing;
		this.setState({ editing }, () => {
			if (editing) {
				this.input.focus();
			}
		});
	};

	save = e => {
		const { record, handleSave, dataIndex } = this.props;
		this.form.validateFields((error, values) => {
			if (error && error[e.currentTarget.id]) {
				return;
			}
			this.toggleEdit();
			handleSave({ ...record, ...values });
		});
	};

	renderCell = form => {
		this.form = form;
		const {
			children,
			dataIndex,
			record,
			title,
			parentdata = []
		} = this.props;
		const { editing } = this.state;
		let bool = dataIndex == '指标项英文简称';
		return editing ? (
			<Form.Item style={{ margin: 0 }}>
				{form.getFieldDecorator(dataIndex, {
					rules: [
						bool
							? {
									required: true,
									// message: `${title} 不能为空.`,
									validator: (rule, value, cb) => {
										if (value.trim() == '') {
											cb(`${title} 不能为空.`);
										}

										let arr = [];
										arr = parentdata.filter(
											item => item[dataIndex] === value
										);

										arr = arr.filter(
											item => item.key != record.key
										);
										if (arr.length > 0) {
											cb(`${title} 重复了`);
										}
										cb();
									}
							  }
							: {
									required: true,
									message: `${title} 不能为空.`
							  }
					],
					initialValue: record[dataIndex]
				})(
					<Input
						ref={node => (this.input = node)}
						onPressEnter={this.save}
						onBlur={this.save}
					/>
				)}
			</Form.Item>
		) : (
			<div
				className="editable-cell-value-wrap"
				style={{ paddingRight: 24 }}
				onClick={this.toggleEdit}
			>
				{children}
			</div>
		);
	};

	render() {
		const {
			editable,
			dataIndex,
			title,
			record,
			index,
			handleSave,
			children,
			...restProps
		} = this.props;
		return (
			<td {...restProps}>
				{editable ? (
					<EditableContext.Consumer>
						{this.renderCell}
					</EditableContext.Consumer>
				) : (
					children
				)}
			</td>
		);
	}
}
class EditableTableRadio extends React.PureComponent {
	state = {
		value: '否'
	};
	componentWillMount() {
		let { data = {} } = this.props;
		this.setState({
			value: data['告警使能']
		});
	}
	render() {
		let { data = {} } = this.props;
		return (
			<Radio.Group
				onChange={e => {
					this.setState(
						{
							value: e.target.value
						},
						() => {
							data['告警使能'] = e.target.value;
						}
					);
				}}
				// defaultValue={data['告警使能']}
				value={this.state.value}
				style={{
					display: 'flex'
				}}
			>
				<Radio value={'是'}>是</Radio>
				<Radio value={'否'}>否</Radio>
			</Radio.Group>
		);
	}
}

class EditableTable extends React.PureComponent {
	constructor(props) {
		super(props);
		this.columns = [
			{
				title: '指标项中文简称',
				dataIndex: '指标项中文简称',
				width: '30%',
				editable: true
			},
			{
				title: '指标项英文简称',
				width: '30%',
				dataIndex: '指标项英文简称',
				editable: true
			},
			{
				title: '操作码',
				dataIndex: '操作码',
				width: '10%',
				editable: true
			},
			{
				title: '单位',
				dataIndex: '单位',
				width: '10%',
				editable: true
			},
			{
				title: '告警使能',
				width: '10%',
				dataIndex: '告警使能'
				// render: (text, record) => {
				// 	return <EditableTableRadio data={record} />;
				// }
			},
			{
				title: '操作',
				width: '10%',
				dataIndex: 'operation',
				render: (text, record) =>
					this.state.dataSource.length >= 1 ? (
						<Popconfirm
							cancelText="取消"
							okText="确定"
							title="确定删除?"
							onConfirm={() => this.handleDelete(record.key)}
						>
							<Button className="btn-2">删除</Button>
						</Popconfirm>
					) : null
			}
		];

		this.state = {
			dataSource: []
		};
	}

	handleDelete = key => {
		const dataSource = [...this.state.dataSource];
		this.setState({
			dataSource: dataSource.filter(item => item.key !== key)
		});
	};

	handleAdd = () => {
		const { dataSource } = this.state;
		let key = Math.random();
		const newData = {
			key,
			指标项中文简称: '',
			指标项英文简称: Math.random()
				.toString()
				.slice('10'),
			操作码: '',
			单位: '',
			告警使能: '否'
		};
		this.setState({
			dataSource: [newData, ...dataSource]
		});
	};

	handleSave = row => {
		const newData = [...this.state.dataSource];
		const index = newData.findIndex(item => row.key === item.key);
		const item = newData[index];
		newData.splice(index, 1, {
			...item,
			...row
		});
		this.setState({ dataSource: newData });
	};

	calibrationMethod = () => {
		let _d = document.querySelectorAll('.editable-table-box .has-error');
		if (this.state.dataSource.length <= 0) {
			message.error('未添加指标项', 0.5);
			return;
		}
		if (_d.length != 0) {
			message.error('添加指标项错误', 0.5);
			return;
		}
		return this.state.dataSource;
	};
	componentDidMount() {
		this.props.init &&
			this.props.init(data => {
				this.setState({
					dataSource: data
				});
			});
	}

	render() {
		const { dataSource = [] } = this.state;
		// console.log('--------', dataSource);
		const components = {
			body: {
				row: EditableFormRow,
				cell: EditableCell
			}
		};
		const columns = this.columns.map(col => {
			if (!col.editable) {
				return col;
			}

			return {
				...col,
				onCell: record => ({
					record,
					editable: col.editable,
					dataIndex: col.dataIndex,
					title: col.title,
					handleSave: this.handleSave,
					parentdata: dataSource
				})
			};
		});
		return (
			<div className="editable-table-box">
				<div className="editable-table-content">
					<Button
						className="btn-1"
						onClick={this.handleAdd}
						type="primary"
					>
						新增
					</Button>
				</div>
				<Table
					components={components}
					rowClassName={() => 'editable-row'}
					bordered
					dataSource={dataSource}
					columns={columns}
					pagination={{
						simple: true
					}}
				/>
			</div>
		);
	}
}

class BasicInformation extends React.PureComponent {
	render() {
		return (
			<Descriptions
				column={1}
				// title="User Info"
				bordered
				className="descriptions"
			>
				<Descriptions.Item label="*驱动文件：">
					驱动文件
				</Descriptions.Item>
				<Descriptions.Item label="*驱动名称：">
					驱动名称
				</Descriptions.Item>
				<Descriptions.Item label="*版本号：">版本号</Descriptions.Item>
				<Descriptions.Item label="*采集设备信息：">
					采集设备信息
				</Descriptions.Item>
			</Descriptions>
		);
	}
}
class EditBox extends React.PureComponent {
	render = () => {
		return (
			<div className="drive-edit-body">
				<div className="drive-edit-basic">
					<div className="title">基本信息</div>
					<BasicInformation />
				</div>
				<div className="drive-edit-content">
					<Collapse
						className="content-collapse"
						accordion
						bordered={false}
						defaultActiveKey={['1']}
						expandIcon={({ isActive }) => (
							<Icon
								type="caret-right"
								rotate={isActive ? 90 : 0}
							/>
						)}
					>
						<Panel
							className="panel-li"
							header={<span>指标监控项</span>}
							key="1"
						>
							<Indicators />
						</Panel>
						<Panel
							className="panel-li"
							header={<span>告警配置</span>}
							key="2"
						>
							<AlarmConfiguration />
						</Panel>
					</Collapse>
				</div>
			</div>
		);
	};
}
ReactDOM.render(<EditBox />, document.getElementById(domId));
