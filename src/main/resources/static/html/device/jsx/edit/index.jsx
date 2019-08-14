var domId = '';
var scripts = document.getElementsByTagName('script');
for (var i = 0; i < scripts.length; i++) {
	var script = scripts[i];
	if (script && script.getAttribute('type') == 'text/babel') {
		domId = script.getAttribute('domId');
	}
}
let { Button, Input, Tabs, Collapse, Icon, Select, Form, message, Badge, Row, Col, Empty, Cascader, InputNumber, Radio, DatePicker } = antd;
const { TabPane } = Tabs;
const { Panel } = Collapse;

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

class EditBox extends React.PureComponent {
	state = {};

	componentDidMount() {}
	render = () => {
		return (
			<div className="device-edit-body">
				<BasicInformationForm onRef={el => (this.basicInformation = el)} title={'编辑'} show={true} />
				<div>
					<Button
						onClick={() => {
							if (this.basicInformation) {
								this.basicInformation.calibrationMethod(data => {
									if (data) {
										let { locations, kinds, discardTime, orderTime, password_confirm, ...other } = data;
										let params = {
											...other,
											discardTime: discardTime && discardTime.valueOf(),
											orderTime: orderTime && orderTime.valueOf()
										};
										let { device_id } = urlArgs();
										console.log(params);
										if (device_id) {
											$.ajax({
												cache: true,
												type: 'POST',
												url: '/iot/device/device_insert_update',
												data: JSON.stringify({
													data: {
														id: device_id,
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
													message.info('保存成功');
												}
											});
										}
									}
								});
							}
						}}
					>
						保存
					</Button>
				</div>
			</div>
		);
	};
}
class BasicInformation extends React.PureComponent {
	state = {
		device_type_cascader_list: [],
		location_cascader_list: [],
		select_list: [],
		select_location: [],
		kinds: [],
		get_way_list: [],
		driver_list: []
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
	getDeviceList = device_id => {
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
					let _device = results.data || {};
					$.ajax({
						url: `/iot/location/select_parent_self?id=${_device.locationid}`,
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
					this.getLocationData(_device.locationid);
					this.getKindList(_device.kindid);
					this.setState({
						_device
					});
				}
			});
		}
	};
	getKindList = kind_id => {
		if (kind_id) {
			$.ajax({
				url: `/iot/kind/select_parent?id=${kind_id}`,
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
					let kinds = [];
					data.map(item => kinds.push(item.cnName));
					this.setState({
						kinds
					});
				}
			});
		}
	};
	componentDidMount() {
		this.props.onRef && this.props.onRef(this);
		let { device_id } = urlArgs();
		this.getDeviceList(device_id);
		this.getCascaderData();
		this.getSelectList();
		this.getDriverList();
		this.getGetWayList();
		// let { select_dept, select_cascader } = urlArgs();

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
		if (deptid) {
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
		}
	};
	getDriverList = () => {
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
	getGetWayList = () => {
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
		// let { select_dept, select_cascader } = urlArgs();
		let { _device = {} } = this.state;
		getFieldDecorator('locationid', { initialValue: _device.locationid });
		getFieldDecorator('kindid', { initialValue: _device.kindid });
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
								initialValue: _device.sno,
								rules: [
									{
										required: true,
										message: 'SN编号不为空'
									}
								]
							})(<Input disabled />)}
						</Form.Item>,
						<Form.Item label="设备名称">
							{getFieldDecorator('cnName', {
								initialValue: _device.cnName,
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
								initialValue: _device.deptid,
								rules: [
									{
										required: true,
										message: '所属组织不为空'
									}
								]
							})(
								<Select
									// disabled
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
								initialValue: _device.ip,
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
						<Form.Item label="绑定网关">
							{getFieldDecorator('gatewayid', {
								initialValue: _device.gatewayid,
								rules: [
									{
										required: true,
										message: '网关不为空'
									}
								]
							})(
								<Select style={{ width: '100%' }} placeholder="请选择网关">
									{this.state.get_way_list.map((item, index) => {
										return (
											<Select.Option key={index} value={item.id}>
												{item.cnName}
											</Select.Option>
										);
									})}
								</Select>
							)}
						</Form.Item>,
						<Form.Item label="设备驱动">
							{getFieldDecorator('driverid', {
								initialValue: _device.driverid,
								rules: [
									{
										required: true,
										message: '驱动不为空'
									}
								]
							})(
								<Select style={{ width: '100%' }} placeholder="请选择驱动">
									{this.state.driver_list.map((item, index) => {
										return (
											<Select.Option key={index} value={item.id}>
												{item.cnName}
											</Select.Option>
										);
									})}
								</Select>
							)}
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
												locationid: (selectedOptions[selectedOptions.length - 1] || {}).id
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
								initialValue: this.state.kinds
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
								initialValue: _device.username
							})(<Input />)}
						</Form.Item>
					])}
					{this._getLi([
						<Form.Item label="采集密码">
							{getFieldDecorator('password', {
								initialValue: _device.password
							})(<Input />)}
						</Form.Item>,
						<Form.Item label="确认采集密码">
							{getFieldDecorator('password_confirm', {
								initialValue: _device.password
							})(<Input />)}
						</Form.Item>
					])}
					{this._getLi([
						<Form.Item {...formItemLayout[1]} label="采集频率(毫秒)">
							{getFieldDecorator('cycle', {
								initialValue: _device.cycle
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
								initialValue: `${_device.status}`,
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
								initialValue: _device.price
							})(<InputNumber formatter={value => `¥ ${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')} parser={value => value.replace(/\$\s?|(,*)/g, '')} style={{ width: '100%' }} />)}
						</Form.Item>,
						<Form.Item label="购入日期">
							{getFieldDecorator('orderTime', {
								initialValue: _device.orderTime ? moment(_device.orderTime) : undefined
							})(<DatePicker placeholder="选择日期" showToday={false} style={{ width: '100%' }} />)}
						</Form.Item>
					])}
					{this._getLi([
						<Form.Item label="使用年限">
							{getFieldDecorator('quality', {
								initialValue: _device.quality
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
								initialValue: _device.discardTime ? moment(_device.discardTime) : undefined
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
ReactDOM.render(<EditBox />, document.getElementById(domId));
