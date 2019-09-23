var domId = '';
var scripts = document.getElementsByTagName('script');
for (var i = 0; i < scripts.length; i++) {
	var script = scripts[i];
	if (script && script.getAttribute('type') == 'text/babel') {
		domId = script.getAttribute('domId');
	}
}

let { Descriptions, Collapse, Icon, Button, message, Input, Table, Popconfirm, Form, Radio, Modal } = antd;
const { Panel } = Collapse;
const { confirm } = Modal;

function submitHandler() {
	console.log('--点击确定--');
}
var edit_confirm = void 0;
function cancelHandler() {
	// console.log('--点击取消--', add_confirm);
	if (!edit_confirm) {
		edit_confirm = confirm({
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
		edit_confirm.destroy();
		edit_confirm = confirm({
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

function urlArgs() {
	let args = {};
	let query = location.search.substring(1);
	let pairs = query.split('&');
	for (let i = 0; i < pairs.length; i++) {
		let pos = pairs[i].indexOf('=');
		if (pos == -1) continue;
		let name = pairs[i].substring(0, pos);
		let value = pairs[i].substring(pos + 1);
		value = decodeURIComponent(value);
		args[name] = value;
	}
	return args;
}

function iframeClose() {
	if (parent.layer) {
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}
}
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
		// btn: ['保存', '取消'],
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
class AlarmConfiguration extends React.PureComponent {
	state = {
		data: []
	};
	componentDidMount() {
		this.init();
	}

	init = () => {
		let { driver_id } = urlArgs();
		if (driver_id) {
			$.ajax({
				url: `/iot/driver/normal_list?driverid=${driver_id}`,
				// data: {},
				cache: false,
				contentType: false,
				processData: false,
				type: 'GET',
				success: results => {
					if (results.code != 0) {
						message.error('接口错误', 0.5);
						return;
					}
					this.setState({
						data: results.data
					});
				}
			});
		}
	};
	render() {
		let { data = [] } = this.state;

		let columns = [
			{
				title: '指标项中文简称',
				dataIndex: 'cnName',
				width: '30%',
				editable: true
			},
			{
				title: '指标项英文简称',
				width: '30%',
				dataIndex: 'enName',
				editable: true
			},
			{
				title: '操作码',
				dataIndex: 'operateKey',
				width: '10%',
				editable: true
			},
			{
				title: '单位',
				dataIndex: 'unit',
				width: '10%',
				editable: true
			},
			{
				title: '告警使能',
				width: '10%',
				dataIndex: 'status',
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
								let { driver_id } = urlArgs();
								$.modal.open('告警规则设置', `/html/drive/alarmRules.html?driverid=${driver_id}&normalid=${record.id}`);
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
					rowKey={record => record.id}
					bordered
					columns={columns}
					dataSource={data}
					pagination={{
						simple: true
					}}
				/>
				{/* <div className="btn-group">
					<Button type="primary" onClick={() => {}}>
						保存
					</Button>
				</div> */}
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
		const { children, dataIndex, record, title, parentdata = [] } = this.props;
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
										arr = parentdata.filter(item => item[dataIndex] === value);

										arr = arr.filter(item => item.key != record.key);
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
				})(<Input ref={node => (this.input = node)} onPressEnter={this.save} onBlur={this.save} />)}
			</Form.Item>
		) : (
			<div className="editable-cell-value-wrap" style={{ paddingRight: 24 }} onClick={this.toggleEdit}>
				{children}
			</div>
		);
	};

	render() {
		const { editable, dataIndex, title, record, index, handleSave, children, ...restProps } = this.props;
		return <td {...restProps}>{editable ? <EditableContext.Consumer>{this.renderCell}</EditableContext.Consumer> : children}</td>;
	}
}
class EditableTableRadio extends React.PureComponent {
	// state = {
	// 	value: 'false'
	// };
	// componentWillMount() {
	// }
	render() {
		let { data = {} } = this.props;
		let value = data['status'];
		return (
			<Radio.Group
				onChange={e => {
					data['status'] = e.target.value;
					$.ajax({
						cache: true,
						type: 'POST',
						url: '/iot/driver/normal_update_all',
						data: JSON.stringify({
							start: data.orderNum,
							data: [
								{
									...data
								}
							]
						}),
						dataType: 'json',
						async: false,
						success: results => {
							if (results.code != 0) {
								message.error('接口错误', 0.5);
								return;
							}
							this.setState({
								value: e.target.value
							});
						}
					});
				}}
				// defaultValue={data['告警使能']}
				value={value}
				style={{
					display: 'flex'
				}}
			>
				<Radio value={'true'}>是</Radio>
				<Radio value={'false'}>否</Radio>
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
				dataIndex: 'cnName',
				width: '30%',
				editable: true
			},
			{
				title: '指标项英文简称',
				width: '30%',
				dataIndex: 'enName',
				editable: true
			},
			{
				title: '操作码',
				dataIndex: 'operateKey',
				width: '10%',
				editable: true
			},
			{
				title: '单位',
				dataIndex: 'unit',
				width: '10%',
				editable: true
			},
			{
				title: '告警使能',
				width: '10%',
				dataIndex: 'status',
				render: (text, record) => {
					return text == 'true' ? '是' : '否';
				}
			},
			{
				title: '操作',
				width: '10%',
				dataIndex: 'operation',
				render: (text, record) =>
					this.state.dataSource.length >= 1 ? (
						<Popconfirm cancelText="取消" okText="确定" title="确定删除?" onConfirm={() => this.handleDelete(record)}>
							<Button className="btn-2">删除</Button>
						</Popconfirm>
					) : null
			}
		];
		this.state = {
			dataSource: []
		};
	}

	handleDelete = record => {
		const dataSource = [...this.state.dataSource];
		if (record.id) {
			$.ajax({
				cache: true,
				type: 'POST',
				url: '/iot/driver/normal_remove',
				data: JSON.stringify({
					data: [record.id]
				}),
				dataType: 'json',
				async: false,
				success: results => {
					if (results.code != 0) {
						message.error('接口错误', 0.5);
						return;
					}
					// message.error('删除成功',0.5);
				}
			});
			this.setState({
				dataSource: dataSource.filter(item => item.id !== record.id)
			});
		} else {
			this.setState({
				dataSource: dataSource.filter(item => item.key !== record.key)
			});
		}
	};

	handleAdd = () => {
		const { dataSource } = this.state;
		let key = new Date().getTime();
		const newData = {
			key,
			cnName: '',
			enName: key,
			operateKey: '',
			unit: '',
			status: 'false'
		};
		this.setState({
			dataSource: [newData, ...dataSource]
		});
	};

	handleSave = row => {
		const newData = [...this.state.dataSource];
		let index = newData.findIndex(item => row.key === item.key);
		if (row.id) {
			index = newData.findIndex(item => row.id === item.id);
		}
		const item = newData[index];
		newData.splice(index, 1, {
			...item,
			...row
		});
		this.setState({ dataSource: newData });
	};

	calibrationMethod = callback => {
		let _d = document.querySelectorAll('.editable-table-box .has-error');
		if (this.state.dataSource.length <= 0) {
			message.error('未添加指标项', 0.5);
			callback();
			return;
		}
		if (_d.length != 0) {
			message.error('添加指标项错误', 0.5);
			callback();
			return;
		}
		callback(this.state.dataSource);
	};
	componentDidMount() {
		this.props.onRef && this.props.onRef(this);
		this.init();
	}
	init = () => {
		let { driver_id } = urlArgs();
		if (driver_id) {
			$.ajax({
				url: `/iot/driver/normal_list?driverid=${driver_id}`,
				// data: {},
				cache: false,
				contentType: false,
				processData: false,
				type: 'GET',
				success: results => {
					if (results.code != 0) {
						message.error('接口错误', 0.5);
						return;
					}
					this.setState({
						dataSource: results.data
					});
				}
			});
		}
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
				<div className="editable-table-content">
					<Button className="btn-1" onClick={this.handleAdd} type="primary">
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
	state = { data: {}, kind_list: [] };
	componentDidMount() {
		let { driver_id } = urlArgs();
		if (driver_id) {
			$.ajax({
				url: `/iot/driver/driver_one?id=${driver_id}`,
				// data: {},
				cache: false,
				contentType: false,
				processData: false,
				type: 'GET',
				success: results => {
					if (results.code != 0) {
						message.error('接口错误');
						return;
					}
					let data = results.data || {};
					if (data.kindid) {
						$.ajax({
							url: `/iot/kind/select_parent?id=${data.kindid}`,
							// data: {},
							cache: false,
							contentType: false,
							processData: false,
							type: 'GET',
							success: results => {
								if (results.code != 0) {
									message.error('接口错误');
									return;
								}
								let kind_list = results.data || [];
								this.setState({
									kind_list
								});
							}
						});
					}
					this.setState({
						data
					});
				}
			});
		}
	}
	getInfoComponent = () => {
		let { kind_list = [] } = this.state;
		let result = [];
		kind_list.map(item => {
			result.push(item.cnName);
		});
		return <Input disabled value={result} />;
	};
	render() {
		let { data = {} } = this.state;
		return (
			<Descriptions
				column={1}
				// title="User Info"
				bordered
				className="descriptions"
			>
				<Descriptions.Item label="*驱动文件：">{data.enName}</Descriptions.Item>
				<Descriptions.Item label="*驱动名称：">{data.cnName}</Descriptions.Item>
				<Descriptions.Item label="*版本号：">{data.driverVer}</Descriptions.Item>
				{/* 视频 / 视频会议终端 / 宝丽通 / hdx7000 */}
				<Descriptions.Item label="*采集设备信息：">{this.getInfoComponent()}</Descriptions.Item>
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
						defaultActiveKey={['indicators']}
						expandIcon={({ isActive }) => <Icon type="caret-right" rotate={isActive ? 90 : 0} />}
						onChange={type => {
							if (type == 'indicators') {
								this.indicators && this.indicators.init();
							} else if (type == 'alarmConfiguration') {
								this.alarmConfiguration && this.alarmConfiguration.init();
							}
						}}
					>
						<Panel className="panel-li" header={<span>指标监控项</span>} key="indicators">
							{/* <Indicators onRef={el => (this.indicators = el)} /> */}
							<div className="edit-indicators">
								<EditableTable onRef={el => (this.indicators = el)} />
								<div className="btn-group">
									<Button
										type="primary"
										onClick={() => {
											if (this.indicators) {
												let { driver_id } = urlArgs();
												this.indicators.calibrationMethod(data => {
													if (data) {
														let data_id_list = data.filter(item => item.id);
														let data_notid_list = data.filter(item => !item.id);
														// if (data_id_list.length > 0) {
														$.ajax({
															cache: true,
															type: 'POST',
															url: '/iot/driver/normal_change',
															data: JSON.stringify({
																driverid: driver_id,
																insert: data_notid_list,
																update: data_id_list
															}),
															dataType: 'json',
															async: false,
															success: results => {
																if (results.code != 0) {
																	message.error('接口错误', 0.5);
																	return;
																}
																this.indicators.init(results.data);
																if (this.alarmConfiguration) {
																	this.alarmConfiguration.init(driverid);
																	this.next();
																}
															}
														});
													}
												});
											}
										}}
									>
										保存
									</Button>
								</div>
							</div>
						</Panel>
						<Panel className="panel-li" header={<span>告警配置</span>} key="alarmConfiguration">
							<AlarmConfiguration ref={el => (this.alarmConfiguration = el)} />
						</Panel>
					</Collapse>
				</div>
			</div>
		);
	};
}
ReactDOM.render(<EditBox />, document.getElementById(domId));
