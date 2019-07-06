var domId = '';
var scripts = document.getElementsByTagName('script');
for (var i = 0; i < scripts.length; i++) {
	var script = scripts[i];
	if (script && script.getAttribute('type') == 'text/babel') {
		domId = script.getAttribute('domId');
	}
}
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

$.modal.open = function(title, url, width, height) {
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
	layer.open({
		type: 2,
		area: [width + 'px', height + 'px'],
		fix: false,
		//不固定
		maxmin: true,
		shade: 0.3,
		title: title,
		content: url,
		btn: ['保存', '取消'],
		// 弹层外区域关闭
		shadeClose: false,
		yes: function(index, layero) {
			var iframeWin = layero.find('iframe')[0];
			iframeWin.contentWindow.submitHandler();
		},
		cancel: function(index) {
			return true;
		}
	});
};
let {
	Steps,
	Button,
	message,
	Input,
	Descriptions,
	Upload,
	Icon,
	Cascader,
	Table,
	Popconfirm,
	Form,
	Radio,
	Modal,
	Row,
	Col
} = antd;
const { Step } = Steps;
const { confirm } = Modal;

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
class BasicInformation extends React.PureComponent {
	state = {
		cascader_data: []
	};
	calibrationMethod = callback => {
		this.props.form.validateFields((err, values) => {
			if (!err) {
				console.log('基本信息校验成功');
				callback(values);
			}
			message.error('基本信息填写错误', 0.5);
			callback();
		});
	};
	resetForm = () => {
		if (this.props.form) {
			this.props.form.resetFields();
		}
	};
	componentDidMount() {
		this.props.onRef && this.props.onRef(this);
		$.ajax({
			url: `/iot/kind/treeObject?level=${4}`,
			cache: false,
			contentType: false,
			processData: false,
			type: 'GET',
			success: results => {
				if (results.code != 0) {
					message.error('接口错误');
					return;
				}
				// const LISTDATA2 = [
				// 	{
				// 		value: '0.7686050876320942',
				// 		label: '分类一',
				// 		children: [
				// 		]
				// 	},
				// ];
				let cascader_data =
					(results.data && results.data.children) || [];
				this.setState({
					cascader_data
				});
			}
		});
	}
	normFile = e => {
		console.log('读取文件开始...');
		let fileList = [...e.fileList];
		// Only to show two recent uploaded files, and old ones will be replaced by the new
		fileList = fileList.slice(-1);
		e.fileList = fileList;
		// if (e.file.status === 'done') {
		// 	console.log('读取文件完毕...', e.file.response);
		// }
		if (Array.isArray(e)) {
			return e;
		}
		return e && e.fileList;
	};

	render() {
		let { show, title } = this.props;
		const { getFieldDecorator } = this.props.form;
		const formItemLayout = [
			{
				labelCol: {
					xs: { span: 24 },
					sm: { span: 6 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span: 18 }
				}
			},
			{
				labelCol: {
					xs: { span: 24 },
					sm: { span: 3 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span: 21 }
				}
			}
		];
		console.log('cascader_data', this.state.cascader_data);
		return (
			<div
				className="drive-add-basic-information-box"
				style={{
					display: show ? 'block' : 'none'
				}}
			>
				<div className="title">{title}</div>
				<Form {...formItemLayout[1]} className="content">
					{this._getLi([
						<Form.Item
							label="驱动文件"
							// extra="上传文件类型为.py"
						>
							{getFieldDecorator('驱动文件', {
								valuePropName: 'fileList',
								getValueFromEvent: this.normFile,
								rules: [
									{
										required: true,
										message: '驱动文件不为空'
									}
								]
							})(
								<Upload
									name="Filedata"
									action={'/iot/driver/driver_upload'}
									headers={{
										authorization: 'authorization-text'
									}}
									className="upload"
								>
									<Button>
										<Icon type="upload" /> 点击上传
									</Button>
								</Upload>
							)}
						</Form.Item>
					])}
					{this._getLi([
						<Form.Item label="驱动名称">
							{getFieldDecorator('驱动名称', {
								rules: [
									{
										required: true,
										message: '驱动名称不为空'
									}
								]
							})(<Input />)}
						</Form.Item>
					])}
					{this._getLi([
						<Form.Item label="版本号">
							{getFieldDecorator('版本号', {
								rules: [
									{
										required: true,
										message: '版本号不为空'
									}
								]
							})(<Input />)}
						</Form.Item>
					])}

					{this._getLi([
						<Form.Item label="采集设备信息">
							{getFieldDecorator('采集设备信息', {
								rules: [
									{
										required: true,
										message: '采集设备信息不为空'
									}
								]
							})(
								<Cascader
									fieldNames={{
										label: 'cnName',
										value: 'cnName',
										children: 'children'
									}}
									options={this.state.cascader_data}
									placeholder="请选择"
									style={{
										width: '100%'
									}}
								/>
							)}
						</Form.Item>
					])}
				</Form>
			</div>
		);
	}
	_getLi(data = []) {
		return (
			<Row className="content-li">
				{data.map((item, index, _d) => {
					let span = 24 / _d.length;
					return (
						<Col key={index} span={span}>
							{item}
						</Col>
					);
				})}
			</Row>
		);
	}
}
const BasicInformationForm = Form.create({ name: 'drive_basic_info' })(
	BasicInformation
);

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
			dataSource: LISTDATA
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
	onExportExcel(headers, data, fileName = '指标项.xlsx') {
		const _headers = headers
			.map((item, i) =>
				Object.assign(
					{},
					{
						key: item.dataIndex,
						title: item.title,
						position: String.fromCharCode(65 + i) + 1
					}
				)
			)
			.reduce(
				(prev, next) =>
					Object.assign({}, prev, {
						[next.position]: { key: next.key, v: next.title }
					}),
				{}
			);

		const _data = data
			.map((item, i) =>
				headers.map((item2, j) =>
					Object.assign(
						{},
						{
							content: item[item2.dataIndex],
							position: String.fromCharCode(65 + j) + (i + 2)
						}
					)
				)
			)
			// 对刚才的结果进行降维处理（二维数组变成一维数组）
			.reduce((prev, next) => prev.concat(next))
			// 转换成 worksheet 需要的结构
			.reduce(
				(prev, next) =>
					Object.assign({}, prev, {
						[next.position]: { v: next.content }
					}),
				{}
			);
		// 合并 headers 和 data
		const output = Object.assign({}, _headers, _data);
		// 获取所有单元格的位置
		const outputPos = Object.keys(output);
		// 计算出范围 ,["A1",..., "H2"]
		const ref = `${outputPos[0]}:${outputPos[outputPos.length - 1]}`;

		// 构建 workbook 对象
		const wb = {
			SheetNames: ['Sheet1'],
			Sheets: {
				Sheet1: Object.assign({}, output, {
					'!ref': ref,
					'!cols': [
						{ wpx: 200 },
						{ wpx: 300 },
						{ wpx: 300 },
						{ wpx: 200 },
						{ wpx: 150 }
					]
				})
			}
		};

		// 导出 Excel
		XLSX.writeFile(wb, fileName);
	}

	onImportExcel = file => {
		// 获取上传的文件对象
		const { files } = file.target;
		// 通过FileReader对象读取文件
		const fileReader = new FileReader();
		fileReader.onload = event => {
			try {
				const { result } = event.target;
				// 以二进制流方式读取得到整份excel表格对象
				const workbook = XLSX.read(result, { type: 'binary' });
				// 存储获取到的数据
				let data = [];
				// 遍历每张工作表进行读取（这里默认只读取第一张表）
				for (const sheet in workbook.Sheets) {
					// esline-disable-next-line
					if (workbook.Sheets.hasOwnProperty(sheet)) {
						// 利用 sheet_to_json 方法将 excel 转成 json 数据
						data = data.concat(
							XLSX.utils.sheet_to_json(workbook.Sheets[sheet])
						);
						// break; // 如果只取第一张表，就取消注释这行
					}
				}
				// 最终获取到并且格式化后的 json 数据
				message.success('导入成功！', 0.5);
				let dataSource = [];
				data.map((item, index) => {
					dataSource.push({ ...item, key: index });
				});
				this.setState({
					dataSource
				});
				// console.log(data);
			} catch (e) {
				// 这里可以抛出文件类型错误不正确的相关提示
				message.error('文件类型不正确！', 0.5);
			}
		};
		// 以二进制方式打开文件
		fileReader.readAsBinaryString(files[0]);
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
				<div
					className="editable-table-content"
					style={{
						margin: '16px 0',
						display: 'flex',
						justifyContent: 'space-between'
					}}
				>
					<Button
						className="btn-1"
						onClick={this.handleAdd}
						type="primary"
					>
						新增
					</Button>
					<div className="right-btn-box">
						<Button className="upload-wrap btn-3">
							<input
								className={'file-uploader'}
								type="file"
								accept=".xlsx, .xls"
								onChange={this.onImportExcel}
							/>
							<span className={'upload-text'}>导入</span>
						</Button>
						<Button
							className="btn-3"
							onClick={() => {
								if (dataSource.length < 1) {
									message.info('未选择下载数据', 0.5);
									return;
								}
								let columns_new = columns.filter(
									(item, index, _d) => index != _d.length - 1
								);
								this.onExportExcel(
									columns_new,
									dataSource,
									'测试指标项.xlsx'
								);
							}}
						>
							导出
						</Button>
						<Button className="btn-3">模板下载</Button>
					</div>
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

class AlarmConfiguration extends React.PureComponent {
	state = {
		data: []
	};
	componentDidMount() {
		this.setState({
			data: LISTDATA
		});
	}
	calibrationMethod = () => {
		if (this.state.data.length <= 0) {
			message.error('未添加指标项', 0.5);
			return;
		}
		return this.state.data;
	};
	render() {
		let { show, title } = this.props;
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
			<div
				className="drive-add-indicators-box"
				style={{
					display: show ? 'block' : 'none'
				}}
			>
				<div className="title">{title}</div>
				<Table
					bordered
					columns={columns}
					dataSource={data}
					pagination={{
						simple: true
					}}
				/>
			</div>
		);
	}
}
class Indicators extends React.PureComponent {
	render() {
		let { show, title, onRef } = this.props;
		return (
			<div
				className="drive-add-indicators-box"
				style={{
					display: show ? 'block' : 'none'
				}}
			>
				<div className="title">{title}</div>

				<EditableTable ref={el => onRef(el)} />
			</div>
		);
	}
}
class AddBox extends React.PureComponent {
	constructor(props) {
		super(props);
		this.state = {
			current: 0
		};
	}
	next() {
		const current = this.state.current + 1;
		this.setState({ current });
	}

	prev() {
		const current = this.state.current - 1;
		this.setState({ current });
	}

	render = () => {
		const steps = [
			{
				title: '基本信息'
			},
			{
				title: '添加指标项'
			},
			{
				title: '告警配置'
			}
		];
		const { current } = this.state;
		return (
			<div className="drive-add-body">
				<div className="steps-content">
					<Steps current={current}>
						{steps.map((item, index) => (
							<Step
								key={item.title}
								title={item.title}
								icon={<span>{index + 1}</span>}
							/>
						))}
					</Steps>
					<div className="steps-content-body">
						<BasicInformationForm
							onRef={el => (this.basicInformation = el)}
							title={steps[current].title}
							show={steps[current].title == '基本信息'}
						/>
						<Indicators
							onRef={el => (this.indicators = el)}
							title={steps[current].title}
							show={steps[current].title == '添加指标项'}
						/>
						{this.indicators && (
							<AlarmConfiguration
								ref={el => (this.alarmConfiguration = el)}
								title={steps[current].title}
								show={steps[current].title == '告警配置'}
							/>
						)}
					</div>
				</div>
				<div className="steps-action">
					{current > 0 && (
						<Button
							style={{
								width: 180,
								height: 40,
								marginRight: 20
							}}
							onClick={() => this.prev()}
						>
							上一步
						</Button>
					)}
					{current < steps.length - 1 && (
						<Button
							style={{ width: 180, height: 40 }}
							type="primary"
							onClick={() => {
								if (steps[current].title == '基本信息') {
									// this.next();
									if (this.basicInformation) {
										this.basicInformation.calibrationMethod(
											data => {
												if (data) {
													let _file =
														(data['驱动文件'] ||
															[])[0] || {};
													let _info =
														data['采集设备信息'] ||
														[];
													let file_response =
														_file.response || {};
													let params = [
														{
															cnName:
																data[
																	'驱动名称'
																],
															driverVer:
																data['版本号'],
															enName: (
																file_response.data ||
																{}
															).name,
															kindCompany:
																_info[2],
															kindKind: _info[0],
															kindSubkind:
																_info[1],
															kindType: _info[3],
															path: (
																file_response.data ||
																{}
															).url
														}
													];
													console.log(params);
													// $.ajax({
													// 	url: '/iot/driver/driver_insert_all',
													// 	data: {...params},
													// 	cache: false,
													// 	contentType: false,
													// 	processData: false,
													// 	type: 'GET',
													// 	success: results => {
													// 		if (results.code != 0) {
													// 			message.error('接口错误');
													// 			return;
													// 		}
													// 		this.setState({
													// 			data: results.rows
													// 		});
													// 	}
													// });
													// this.next();
												}
											}
										);
									}
								} else if (
									steps[current].title == '添加指标项'
								) {
									this.next();
									// if (this.indicators) {
									// 	let data = this.indicators.calibrationMethod();
									// 	if (data) {
									// 		console.log(data);
									// 		this.next();
									// 	}
									// }
								}
							}}
						>
							下一步
						</Button>
					)}
					{current === steps.length - 1 && (
						<Button
							style={{ width: 180, height: 40 }}
							type="primary"
							onClick={() => {
								message.success('操作完成', 0.5);
								if (this.alarmConfiguration) {
									let data = this.alarmConfiguration.calibrationMethod();
									if (data) {
										iframeClose();
									}
								}
							}}
						>
							完成
						</Button>
					)}
				</div>
			</div>
		);
	};
}
if (domId) {
	ReactDOM.render(<AddBox />, document.getElementById(domId));
} else {
	console.log('domId 未输入');
}
