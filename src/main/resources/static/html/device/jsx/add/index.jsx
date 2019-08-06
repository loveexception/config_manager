var domId = '';
var scripts = document.getElementsByTagName('script');
for (var i = 0; i < scripts.length; i++) {
	var script = scripts[i];
	if (script && script.getAttribute('type') == 'text/babel') {
		domId = script.getAttribute('domId');
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
let { Steps, Button, message, Input, Descriptions, Upload, Icon, Cascader, Table, Popconfirm, Form, Radio, Modal, Row, Col, DatePicker, InputNumber, Select, Switch, Checkbox } = antd;
const { Step } = Steps;
const { confirm } = Modal;

var _list_data = {};

function submitHandler() {
	console.log('--点击确定--');
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
		if (name.endsWith('[]')) {
			args[name.split('[]')[0]] = args[name.split('[]')[0]] || [];
			args[name.split('[]')[0]].push(value);
		} else {
			args[name] = value;
		}
	}
	return args;
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
		device_type_cascader_list: [],
		location_cascader_list: [],
		select_list: [],
		select_location: []
	};
	calibrationMethod = callback => {
		this.props.form.validateFields((err, values) => {
			if (!err) {
				console.log('基本信息校验成功');
				callback(values);
				return;
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
		this.getCascaderData();
		this.getSelectList();
		let { select_dept, select_cascader } = urlArgs();

		$.ajax({
			url: `/iot/location/select_parent?id=${select_cascader}`,
			cache: false,
			contentType: false,
			processData: false,
			type: 'GET',
			success: results => {
				if (results.code != 0) {
					message.error('接口错误', 0.5);
					return;
				}
				let data = results.data || [];
				let select_location = [];
				data.map(item => select_location.push(item.cnName));

				this.setState({
					select_location
				});
			}
		});
		this.getLocationData(select_dept);

		// /iot/location/select_parent?id=7d81a1a70bdd4d669b644106b1c5d14f
	}
	getCascaderData = () => {
		$.ajax({
			url: `/iot/kind/treeObject?level=${4}`,
			cache: false,
			contentType: false,
			processData: false,
			type: 'GET',
			success: results => {
				if (results.code != 0) {
					message.error('接口错误', 0.5);
					return;
				}
				let device_type_cascader_list = (results.data && results.data.children) || [];
				this.setState({
					device_type_cascader_list
				});
			}
		});
	};
	getSelectList = () => {
		$.ajax({
			url: `/sys/dept/tree_list`,
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
				let data = results.data || [];
				this.setState({
					select_list: data.filter(item => item.pId != '0')
				});
			}
		});
	};
	getLocationData = deptid => {
		$.ajax({
			url: `/iot/location/tree_parent?deptid=${deptid}`,
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
				let data = results.data || [];
				this.setState({
					location_cascader_list: [data]
				});
			}
		});
	};

	render() {
		let { show, title } = this.props;
		const { getFieldDecorator, setFieldsValue } = this.props.form;
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
		let { select_dept, select_cascader } = urlArgs();
		getFieldDecorator('locationid', { initialValue: select_cascader });
		getFieldDecorator('kindid', { initialValue: undefined });
		return (
			<div
				className="device-add-basic-information-box"
				style={{
					display: show ? 'block' : 'none'
				}}
			>
				<div className="title">{title}</div>
				<Form {...formItemLayout[0]} className="login-form">
					{this._getLi([
						<Form.Item label="SN编号">
							{getFieldDecorator('sno', {
								rules: [
									{
										required: true,
										message: 'SN编号不为空'
									}
								]
							})(<Input />)}
						</Form.Item>,
						<Form.Item label="设备名称">
							{getFieldDecorator('cnName', {
								rules: [
									{
										required: true,
										message: '设备名称不为空'
									}
								]
							})(<Input />)}
						</Form.Item>
					])}
					{this._getLi([
						<Form.Item label="所属组织">
							{getFieldDecorator('deptid', {
								initialValue: select_dept,
								rules: [
									{
										required: true,
										message: '所属组织不为空'
									}
								]
							})(
								<Select
									disabled
									style={{ width: '100%' }}
									placeholder="请选择组织"
									// value={this.state.select_dept}
									onSelect={value => {
										this.getLocationData(value);
									}}
								>
									{this.state.select_list.map((item, index) => {
										return (
											<Select.Option key={index} value={item.id}>
												{item.name}
											</Select.Option>
										);
									})}
								</Select>
								// <Select disabled style={{ width: '100%' }} showArrow={false}>
								// 	<Select.Option value={select_dept[1]}>{select_dept[0]}</Select.Option>
								// </Select>
							)}
						</Form.Item>,
						<Form.Item label="IP地址">
							{getFieldDecorator('ip', {
								rules: [
									{
										required: true,
										message: 'IP地址不为空'
									}
								]
							})(<Input />)}
						</Form.Item>
					])}
					{this._getLi([
						<Form.Item {...formItemLayout[1]} label="地理位置">
							{getFieldDecorator('locations', {
								initialValue: this.state.select_location,
								rules: [
									{
										required: true,
										message: '地理位置不为空'
									}
								]
							})(
								<Cascader
									disabled
									placeholder="请选择地理位置"
									fieldNames={{
										label: 'cnName',
										value: 'cnName',
										children: 'children'
									}}
									options={this.state.location_cascader_list}
									onChange={(value, selectedOptions = []) => {
										if (selectedOptions.length > 0) {
											setFieldsValue({
												kindid: (selectedOptions[selectedOptions.length - 1] || {}).id
											});
										}
									}}
								/>
							)}
						</Form.Item>
					])}
					{this._getLi([
						<Form.Item {...formItemLayout[1]} label="设备类型">
							{getFieldDecorator('kinds', {
								rules: [
									// {
									// 	required: true,
									// 	message: '设备类型不为空'
									// }
								]
							})(
								<Cascader
									fieldNames={{
										label: 'cnName',
										value: 'cnName',
										children: 'children'
									}}
									options={this.state.device_type_cascader_list}
									placeholder="请选择"
									style={{
										width: '100%'
									}}
									onChange={(value, selectedOptions = []) => {
										if (selectedOptions.length > 0) {
											setFieldsValue({
												kindid: (selectedOptions[selectedOptions.length - 1] || {}).id
											});
										}
									}}
								/>
							)}
						</Form.Item>
					])}
					{this._getLi([
						<Form.Item {...formItemLayout[1]} label="采集用户名">
							{getFieldDecorator('username', {
								rules: [
									// {
									// 	required: true,
									// 	message: '采集用户名不为空'
									// }
								]
							})(<Input />)}
						</Form.Item>
					])}
					{this._getLi([
						<Form.Item label="采集密码">
							{getFieldDecorator('password', {
								rules: [
									// {
									// 	required: true,
									// 	message: '采集密码不为空'
									// }
								]
							})(<Input />)}
						</Form.Item>,
						<Form.Item label="确认采集密码">
							{getFieldDecorator('password_confirm', {
								rules: [
									// {
									// 	required: true,
									// 	message: '确认采集密码不为空'
									// }
								]
							})(<Input />)}
						</Form.Item>
					])}
					{this._getLi([
						<Form.Item {...formItemLayout[1]} label="采集频率(毫秒)">
							{getFieldDecorator('cycle', {
								initialValue: 15000,
								rules: [
									// {
									// 	required: true,
									// 	message: '采集频率不为空'
									// }
								]
							})(
								<InputNumber
									min={15000}
									max={600000}
									formatter={value => `${value} ms`}
									// parser={value =>
									// 	value.replace(/\$\s?|(,*)/g, '')
									// }
									style={{ width: '100%' }}
								/>
							)}
						</Form.Item>
					])}
					{this._getLi([
						<Form.Item {...formItemLayout[1]} label="激活状态">
							{getFieldDecorator('status', {
								initialValue: 'true',
								rules: [
									{
										required: true,
										message: '状态不为空'
									}
								]
							})(
								<Radio.Group>
									<Radio value="true">激活</Radio>
									<Radio value="false">停用</Radio>
								</Radio.Group>
							)}
						</Form.Item>
					])}
					{this._getLi([
						<Form.Item label="价格">
							{getFieldDecorator('price', {
								initialValue: 1000,
								rules: [
									// {
									// 	required: true,
									// 	message: '价格不为空'
									// }
								]
							})(<InputNumber formatter={value => `¥ ${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')} parser={value => value.replace(/\$\s?|(,*)/g, '')} style={{ width: '100%' }} />)}
						</Form.Item>,
						<Form.Item label="购入日期">
							{getFieldDecorator('orderTime', {
								rules: [
									// {
									// 	required: true,
									// 	message: '购入日期不为空'
									// }
								]
							})(<DatePicker placeholder="选择日期" showToday={false} style={{ width: '100%' }} />)}
						</Form.Item>
					])}
					{this._getLi([
						<Form.Item label="使用年限">
							{getFieldDecorator('quality', {
								initialValue: 1,
								rules: [
									// {
									// 	required: true,
									// 	message: '使用年限不为空'
									// }
								]
							})(
								<InputNumber
									min={1}
									max={30}
									formatter={value => `${value} 年`}
									// parser={value =>
									// 	value.replace(/\$\s?|(,*)/g, '')
									// }
									style={{ width: '100%' }}
								/>
							)}
						</Form.Item>,
						<Form.Item label="报废日期">
							{getFieldDecorator('discardTime', {
								rules: [
									// {
									// 	required: true,
									// 	message: '报废时间不为空'
									// }
								]
							})(<DatePicker placeholder="选择日期" showToday={false} style={{ width: '100%' }} />)}
						</Form.Item>
					])}
				</Form>
			</div>
		);
	}
	_getLi(data = []) {
		return (
			<Row>
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
const BasicInformationForm = Form.create({ name: 'device_basic_info' })(BasicInformation);
// const EditableContext = React.createContext();

// const EditableRow = ({ form, index, ...props }) => (
// 	<EditableContext.Provider value={form}>
// 		<tr {...props} />
// 	</EditableContext.Provider>
// );

// const EditableFormRow = Form.create()(EditableRow);

// class EditableCell extends React.PureComponent {
// 	state = {
// 		editing: false
// 	};

// 	toggleEdit = () => {
// 		const editing = !this.state.editing;
// 		this.setState({ editing }, () => {
// 			if (editing) {
// 				this.input.focus();
// 			}
// 		});
// 	};

// 	save = e => {
// 		const { record, handleSave, dataIndex } = this.props;
// 		this.form.validateFields((error, values) => {
// 			if (error && error[e.currentTarget.id]) {
// 				return;
// 			}
// 			this.toggleEdit();
// 			handleSave({ ...record, ...values });
// 		});
// 	};

// 	renderCell = form => {
// 		this.form = form;
// 		const { children, dataIndex, record, title, parentdata = [] } = this.props;
// 		const { editing } = this.state;
// 		let bool = dataIndex == '指标项英文简称';
// 		return editing ? (
// 			<Form.Item style={{ margin: 0 }}>
// 				{form.getFieldDecorator(dataIndex, {
// 					rules: [
// 						bool
// 							? {
// 									required: true,
// 									// message: `${title} 不能为空.`,
// 									validator: (rule, value, cb) => {
// 										if (value.trim() == '') {
// 											cb(`${title} 不能为空.`);
// 										}

// 										let arr = [];
// 										arr = parentdata.filter(item => item[dataIndex] === value);

// 										arr = arr.filter(item => item.key != record.key);
// 										if (arr.length > 0) {
// 											cb(`${title} 重复了`);
// 										}
// 										cb();
// 									}
// 							  }
// 							: {
// 									required: true,
// 									message: `${title} 不能为空.`
// 							  }
// 					],
// 					initialValue: record[dataIndex]
// 				})(<Input ref={node => (this.input = node)} onPressEnter={this.save} onBlur={this.save} />)}
// 			</Form.Item>
// 		) : (
// 			<div className="editable-cell-value-wrap" style={{ paddingRight: 24 }} onClick={this.toggleEdit}>
// 				{children}
// 			</div>
// 		);
// 	};

// 	render() {
// 		const { editable, dataIndex, title, record, index, handleSave, children, ...restProps } = this.props;
// 		return <td {...restProps}>{editable ? <EditableContext.Consumer>{this.renderCell}</EditableContext.Consumer> : children}</td>;
// 	}
// }
class EditableTableRadio extends React.PureComponent {
	state = {
		value: 'false'
	};
	componentWillMount() {
		let { value } = this.props;
		this.setState({
			value
		});
	}
	componentDidUpdate(prevProps, prevState) {
		if (prevProps.record.status != this.props.record.status) {
			this.setState({
				value: this.props.record.status
			});
		}
	}
	render() {
		return (
			<Radio.Group
				disabled={this.props.disabled}
				onChange={e => {
					this.props.onChange &&
						this.props.onChange(e.target.value, results => {
							if (results.code != 0) {
								message.error('接口错误', 0.5);
								return;
							}
							this.setState({
								value: e.target.value
							});
						});
				}}
				// defaultValue={data['告警使能']}
				value={this.state.value}
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

class AlarmConfiguration extends React.PureComponent {
	state = {
		data: []
	};
	init = device_id => {
		if (device_id) {
			$.ajax({
				url: `/iot/device/device_one?id=${device_id}`,
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
					let driver_id = (results.data || {}).driverid;
					if (driver_id) {
						$.ajax({
							url: `/iot/person/normal_list?deviceid=${device_id}&driverid=${driver_id}`,
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
				}
			});
		}
	};
	componentDidMount() {
		// this.init('03a188c952dc40dd980008646dacd8e2');
	}
	addDataRowPerson = (id, data) => {
		let _data = [];
		this.state.data.map(item => {
			let _item = {};
			if (item.id == id) {
				_item = {
					...item,
					person: data
				};
			} else {
				_item = item;
			}
			_data.push(_item);
		});
		this.setState({
			data: _data
		});
	};
	deleteDataRowPerson = id => {
		let _data = [];
		this.state.data.map(item => {
			let _item = {};
			if (item.id == id) {
				let { person, ...other } = item;
				_item = {
					...other
				};
			} else {
				_item = item;
			}
			_data.push(_item);
		});
		this.setState({
			data: _data
		});
	};
	render() {
		let { show, title, device_id } = this.props;
		let { data = [] } = this.state;
		console.log('---ooo---');
		let columns = [
			{
				title: '指标项中文简称',
				dataIndex: 'cnName',
				width: '25%',
				editable: true
			},
			{
				title: '指标项英文简称',
				width: '25%',
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
				dataIndex: 'person.status',
				render: (text, record) => (
					<EditableTableRadio
						disabled={!record.person}
						value={text || record.status}
						record={record}
						onChange={(value, callback) => {
							$.ajax({
								cache: true,
								type: 'POST',
								url: '/iot/person/person_update',
								data: JSON.stringify({
									data: {
										id: record.person.id,
										status: value
									}
								}),
								dataType: 'json',
								async: false,
								success: results => {
									callback(results);
								}
							});
						}}
					/>
				)
			},
			// {
			// 	title: '告警使能2',
			// 	width: '10%',
			// 	dataIndex: 'person'
			// 	render: (text, record) => {
			// 		return (
			// 			<EditableTableRadio
			// 				value={`${!!text}`}
			// 				record={record}
			// 				onChange={(value, callback) => {
			// 					console.log(value);
			// 					if (value == 'true') {
			// 						$.ajax({
			// 							cache: true,
			// 							type: 'POST',
			// 							url: '/iot/person/find_add_one',
			// 							data: {
			// 								normalid: record.id,
			// 								deviceid: device_id
			// 							},
			// 							// dataType: 'json',
			// 							async: false,
			// 							success: results => {
			// 								callback(results);
			// 							}
			// 						});
			// 					} else {
			// 						$.ajax({
			// 							cache: true,
			// 							type: 'POST',
			// 							url: '/iot/person/person_remove',
			// 							data: {
			// 								id: text.id
			// 							},
			// 							// dataType: 'json',
			// 							async: false,
			// 							success: results => {
			// 								callback(results);
			// 							}
			// 						});
			// 					}
			// 					// $.ajax({
			// 					// 	cache: true,
			// 					// 	type: 'POST',
			// 					// 	url: '/iot/person/person_update',
			// 					// 	data: JSON.stringify({
			// 					// 		data: {
			// 					// 			id: record.id,
			// 					// 			status: value
			// 					// 		}
			// 					// 	}),
			// 					// 	dataType: 'json',
			// 					// 	async: false,
			// 					// 	success: results => {
			// 					// 		callback(results);
			// 					// 	}
			// 					// });
			// 				}}
			// 			/>
			// 		);
			// 	}
			// },
			{
				title: '告警配置',
				width: '10%',
				dataIndex: 'btn',
				render: (text, record) => (
					<Button
						disabled={!record.person}
						className="btn-2"
						onClick={() => {
							$.modal.open('告警规则设置', `/html/device/alarmRules.html?personid=${record.person.id}`);
						}}
					>
						个性化
					</Button>
				)
			},
			{
				title: '操作',
				width: '10%',
				dataIndex: 'operation',
				render: (text, record, index) => {
					return <SwitchCustom key={record.id} checked={!!record.person} record={record} device_id={device_id} addDataRowPerson={this.addDataRowPerson} deleteDataRowPerson={this.deleteDataRowPerson} />;
				}
			}
		];

		return (
			<div
				className="device-add-indicators-box"
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
class SwitchCustom extends React.PureComponent {
	state = {
		checked: false
	};
	componentDidMount() {
		this.setState({
			checked: this.props.checked
		});
	}
	// componentDidUpdate(prevProps, prevState) {
	// 	if (prevProps.record.person != this.props.record.person) {
	// 		this.setState({
	// 			value: this.props.record.person
	// 		});
	// 	}
	// }
	onChange = () => {
		let { record = {}, device_id } = this.props;
		let { person = {} } = record;
		if (this.state.checked) {
			console.log('删除');
			if (person.id) {
				$.ajax({
					cache: true,
					type: 'POST',
					url: '/iot/person/person_remove',
					data: {
						id: person.id
					},
					// dataType: 'json',
					async: false,
					success: results => {
						if (results.code != 0) {
							message.error('接口错误', 0.5);
							return;
						}
						this.setState({
							checked: false
						});
						this.props.deleteDataRowPerson && this.props.deleteDataRowPerson(record.id);
						// callback(results);
					}
				});
			}
		} else {
			console.log('添加');
			$.ajax({
				cache: true,
				type: 'POST',
				url: '/iot/person/find_add_one',
				data: {
					normalid: record.id,
					deviceid: device_id
				},
				// dataType: 'json',
				async: false,
				success: results => {
					if (results.code != 0) {
						message.error('接口错误', 0.5);
						return;
					}
					this.setState({
						checked: true
					});
					// this.props.record = {
					// 	...this.props.record,
					// 	person: results.data
					// };
					this.props.addDataRowPerson && this.props.addDataRowPerson(record.id, results.data);
					// callback(results);
				}
			});
		}
	};
	render() {
		return (
			<Checkbox checked={this.state.checked} onChange={this.onChange}>
				{!this.state.checked ? '添加' : '删除'}
			</Checkbox>
			// <Switch
			// 	checkedChildren="开"
			// 	unCheckedChildren="关"
			// 	loading={this.state.loading}
			// 	checked={this.state.checked}
			// 	onClick={() => {
			// 		let { person } = this.props.record;
			// 		this.setState({
			// 			loading: true
			// 		});
			// 		if (this.state.checked) {
			// 			console.log('删除');
			// 			$.ajax({
			// 				cache: true,
			// 				type: 'POST',
			// 				url: '/iot/person/person_remove',
			// 				data: {
			// 					id: person.id
			// 				},
			// 				// dataType: 'json',
			// 				async: false,
			// 				success: results => {
			// 					if (results.code != 0) {
			// 						message.error('接口错误', 0.5);
			// 						return;
			// 					}
			// 					this.setState({
			// 						checked: false,
			// 						loading: false
			// 					});
			// 					// callback(results);
			// 				}
			// 			});
			// 		} else {
			// 			console.log('添加');
			// 			$.ajax({
			// 				cache: true,
			// 				type: 'POST',
			// 				url: '/iot/person/find_add_one',
			// 				data: {
			// 					normalid: this.props.record.id,
			// 					deviceid: this.props.device_id
			// 				},
			// 				// dataType: 'json',
			// 				async: false,
			// 				success: results => {
			// 					if (results.code != 0) {
			// 						message.error('接口错误', 0.5);
			// 						return;
			// 					}
			// 					this.setState({
			// 						checked: true,
			// 						loading: false
			// 					});
			// 					// callback(results);
			// 				}
			// 			});
			// 		}
			// 	}}
			// />
		);
	}
}
class DriverCustom extends React.PureComponent {
	state = {
		driver_list: [],
		get_way_list: []
	};
	calibrationMethod = callback => {
		this.props.form.validateFields((err, values) => {
			if (!err) {
				console.log('基本信息校验成功');
				callback(values);
				return;
			}
			message.error('基本信息填写错误', 0.5);
			callback();
		});
	};
	componentDidMount() {
		this.props.onRef && this.props.onRef(this);
		this.getDriver();
		this.getGetWay();
	}
	getDriver = () => {
		$.ajax({
			url: `/iot/driver/drivers`,
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
				let { rows = [] } = results.data || {};
				this.setState({
					driver_list: rows
				});
			}
		});
	};
	getGetWay = () => {
		$.ajax({
			url: `/iot/gateway/gateway_list`,
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
				let { rows = [] } = results.data || {};
				this.setState({
					get_way_list: rows
				});
			}
		});
	};
	render() {
		let { show, title, onRef } = this.props;
		let { driver_list = [], get_way_list = [] } = this.state;
		const { getFieldDecorator } = this.props.form;

		return (
			<div
				className="device-add-indicators-box"
				style={{
					display: show ? 'block' : 'none'
				}}
			>
				<div className="title">{title}</div>
				<Form onSubmit={this.handleSubmit}>
					<Descriptions
						column={1}
						// title="User Info"
						bordered
						className="descriptions"
					>
						<Descriptions.Item label="驱动文件">
							<Form.Item>
								{getFieldDecorator('driverid', {
									// initialValue:
									rules: [{ required: true, message: '请选中驱动文件' }]
								})(
									<Radio.Group className="radio-box" buttonStyle="solid">
										{driver_list.map((item, index) => {
											return (
												<Radio.Button className="radio-li" key={index} value={item.id}>
													{item.cnName}
												</Radio.Button>
											);
										})}
									</Radio.Group>
								)}
							</Form.Item>
						</Descriptions.Item>
						<Descriptions.Item label="采集网关">
							<Form.Item>
								{getFieldDecorator('gatewayid', {
									// initialValue:
									rules: [{ required: true, message: '请选中采集网关' }]
								})(
									<Radio.Group className="radio-box" buttonStyle="solid">
										{get_way_list.map((item, index) => {
											return (
												<Radio.Button className="radio-li" key={index} value={item.id}>
													{item.cnName}
												</Radio.Button>
											);
										})}
									</Radio.Group>
								)}
							</Form.Item>
						</Descriptions.Item>
					</Descriptions>
				</Form>
			</div>
		);
	}
}
const DriverCustomForm = Form.create({ name: 'device_driver_custom' })(DriverCustom);
class AddBox extends React.PureComponent {
	constructor(props) {
		super(props);
		this.state = {
			current: 0,
			device_id: '' //'03a188c952dc40dd980008646dacd8e2'
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
				title: '添加驱动'
			},
			{
				title: '监控指标项'
			}
		];
		const { current } = this.state;
		return (
			<div className="device-add-body">
				<div className="steps-content">
					<Steps current={current}>
						{steps.map((item, index) => (
							<Step key={item.title} title={item.title} icon={<span>{index + 1}</span>} />
						))}
					</Steps>
					<div className="steps-content-body">
						<BasicInformationForm onRef={el => (this.basicInformation = el)} title={steps[current].title} show={steps[current].title == '基本信息'} />
						<DriverCustomForm onRef={el => (this.driverCustomForm = el)} title={'驱动和网关选择'} show={steps[current].title == '添加驱动'} />
						<AlarmConfiguration device_id={this.state.device_id} ref={el => (this.alarmConfiguration = el)} title={steps[current].title} show={steps[current].title == '监控指标项'} />
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
										this.basicInformation.calibrationMethod(data => {
											if (data) {
												let { locations, kinds, discardTime, orderTime, password_confirm, ...other } = data;
												let params = {
													...other,
													discardTime: discardTime && discardTime.valueOf(),
													orderTime: orderTime && orderTime.valueOf()
												};
												$.ajax({
													cache: true,
													type: 'POST',
													url: '/iot/device/device_insert_update',
													data: JSON.stringify({
														data: {
															id: this.state.device_id,
															...params
														}
													}),
													dataType: 'json',
													async: false,
													success: results => {
														if (results.code != 0) {
															message.error('接口错误', 0.5);
															return;
														}
														this.setState(
															{
																device_id: (results.data || {}).id
															},
															() => {
																this.next();
															}
														);
													}
												});
											}
										});
									}
								} else if (steps[current].title == '添加驱动') {
									// this.next();
									if (this.driverCustomForm) {
										this.driverCustomForm.calibrationMethod(data => {
											if (data) {
												if (this.state.device_id) {
													$.ajax({
														cache: true,
														type: 'POST',
														url: '/iot/device/device_insert_update',
														data: JSON.stringify({
															data: {
																id: this.state.device_id,
																...data
															}
														}),
														dataType: 'json',
														async: false,
														success: results => {
															if (results.code != 0) {
																message.error('接口错误', 0.5);
																return;
															}
															this.setState(
																{
																	device_id: (results.data || {}).id
																},
																() => {
																	if (this.alarmConfiguration) {
																		this.alarmConfiguration.init(this.state.device_id);
																		this.next();
																	}
																	// this.next();
																}
															);
														}
													});
												}
											}
										});
									}
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
								// message.success('操作完成', 0.5);
								// if (this.alarmConfiguration) {
								// 	let data = this.alarmConfiguration.calibrationMethod();
								// 	if (data) {
								// 		iframeClose();
								// 	}
								// }
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
