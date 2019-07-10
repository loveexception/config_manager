var domId = '';
var scripts = document.getElementsByTagName('script');
for (var i = 0; i < scripts.length; i++) {
	var script = scripts[i];
	if (script && script.getAttribute('type') == 'text/babel') {
		domId = script.getAttribute('domId');
	}
}
function submitHandler() {
	console.log('--点击确定2--');
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
let { Button, Input, Tabs, Collapse, Icon, Select, Form, message, Badge, Row, Col } = antd;
const { TabPane } = Tabs;
const { Panel } = Collapse;

class AlarmRulesBox extends React.PureComponent {
	state = {
		listData: [],
		selectNorma: {},
		tabs_data: [
			{
				title: '紧急告警',
				value: 'critical'
			},
			{
				title: '重要告警',
				value: 'major'
			},
			{
				title: '次要告警',
				value: 'minor'
			},
			{
				title: '告警提示',
				value: 'warning'
			}
		]
	};
	listData = () => {
		let { driverid, normalid } = urlArgs();
		if (driverid) {
			$.ajax({
				url: `/iot/driver/normal_list?driverid=${driverid}`,
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
					let listData = results.data || [];
					let selectNorma = listData.filter(item => item.id == normalid)[0] || {};
					this.setState({
						listData,
						selectNorma
					});
				}
			});
		}
	};
	componentDidMount() {
		this.listData();
	}
	render = () => {
		let { tabs_data = [], listData = [], selectNorma = {} } = this.state;
		return (
			<div className="drive-alarm-rules-body">
				<div className="title">{selectNorma['cnName']}</div>
				<Tabs
					className="device-tabs-box"
					onChange={key => {
						console.log(key);
					}}
					type="card"
				>
					{tabs_data.map((item, index) => {
						const Component = Form.create({
							name: `alarm_rules_${item.value}`
						})(DynamicFieldSet);

						return (
							<TabPane
								forceRender
								className="device-tabs-pane"
								tab={
									<div ref={el => (this[`rules_${item.value}_point`] = el)} className="tab-pane-title">
										{item.title}
										<sup className="sup" />
									</div>
								}
								key={index}
							>
								<Component onRef={el => (this[`rules_${item.value}`] = el)} grade={item} listData={listData} selectNorma={selectNorma} />
								{/* <WrappedDynamicFieldSet name={item.value} /> */}
								{/* <RulesCard grade={item} listData={listData} selectNorma={selectNorma} /> */}
							</TabPane>
						);
					})}
				</Tabs>
				<Button
					onClick={() => {
						let { tabs_data = [] } = this.state;
						let error = this.state.error || [];
						tabs_data.map((item, index) => {
							let d = this[`rules_${item.value}`];
							let b = this[`rules_${item.value}_point`];
							if (d) {
								d.handleSubmit(data => {
									if (data) {
										error[item.value] = false;
										b.classList.remove('point');
									} else {
										// error.push({
										// 	...item,
										// 	info:'参数未填写'
										// })
										error[item.value] = true;
										b.classList.add('point');
									}
								});
							}
						});
						// this.setState({
						// 	error: {
						// 		...error
						// 	}
						// });
					}}
				>
					dddddd
				</Button>
				{/* <Tabs
					className="device-tabs-box"
					onChange={key => {
						console.log(key);
					}}
					type="card"
				>
					{tabs_data.map((item, index) => {
						return (
							<TabPane className="device-tabs-pane" tab={item.title} key={index}>
								<RulesCard grade={item} listData={listData} selectNorma={selectNorma} />
							</TabPane>
						);
					})}
				</Tabs>
				<Button
					onClick={() => {
						console.log('---');
					}}
				>
					保存
				</Button> */}
			</div>
		);
	};
}

class DynamicFieldSet extends React.Component {
	constructor(props) {
		super(props);
		this.id = 4;
		this.state = {
			panel_data: [],
			activeKey: []
		};
	}
	componentDidMount() {
		this.props.onRef && this.props.onRef(this);
		this.getPanelList();
	}
	remove = k => {
		const { form } = this.props;
		// can use data-binding to get
		const keys = form.getFieldValue('keys');
		// We need at least one passenger
		if (keys.length === 1) {
			return;
		}

		// can use data-binding to set
		form.setFieldsValue({
			keys: keys.filter(key => key !== k)
		});
	};

	add = () => {
		let { form } = this.props;
		// can use data-binding to get
		const keys = form.getFieldValue('keys');
		let id = this.id++;
		const nextKeys = keys.concat(id);
		// can use data-binding to set
		// important! notify form to detect changes
		this.state.activeKey = [id + ''];
		form.setFieldsValue({
			keys: nextKeys
		});
	};

	handleSubmit = cb => {
		this.props.form.validateFields((err, values) => {
			if (!err) {
				const { keys, contents, rules } = values;
				let { grade = {} } = this.props;
				let data = keys.map(key => contents[key]);
				if (data.length > 0) {
					console.log(grade.title, grade.value, data, rules);
					cb(data);
				}
			} else {
				console.log(err);
				cb();
			}
		});
	};
	getPanelList = cb => {
		let { normalid } = urlArgs();
		let { grade = {} } = this.props;
		if (normalid) {
			//"grade": "normal",
			// normal,warning,minor,major,critical
			// 正常，提示，次要告警，主要告警，紧急报警
			$.ajax({
				url: `/iot/driver/grade_list?normalid=${normalid}&grade=${grade.value}`,
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
					const { form } = this.props;
					this.id = data.length;
					let keys = [...new Array(data.length).keys()];
					this.state.panel_data = data;
					this.state.activeKey = [keys[0] + ''];
					form.setFieldsValue({ keys });
				}
			});
		}
	};
	deleteRules = id => {
		$.ajax({
			cache: true,
			type: 'POST',
			url: '/iot/driver/ruler_remove',
			data: JSON.stringify({
				data: id
			}),
			dataType: 'json',
			async: false,
			success: results => {
				if (results.code != 0) {
					message.error('接口错误');
					return;
				}
				console.log('results', results);
				// if (results.data) {
				// 	this.setState(
				// 		{
				// 			driver_id: results.data.id
				// 		},
				// 		() => {
				// 			this.next();
				// 		}
				// 	);
				// }
			}
		});
	};
	render() {
		const { getFieldDecorator, getFieldValue, getFieldError } = this.props.form;
		const formItemLayout = {
			labelCol: {
				xs: { span: 24 },
				sm: { span: 3 }
			},
			wrapperCol: {
				xs: { span: 24 },
				sm: { span: 21 }
			}
		};
		const formItemLayoutWithOutLabel = {
			wrapperCol: {
				xs: { span: 24, offset: 0 },
				sm: { span: 20, offset: 4 }
			}
		};
		getFieldDecorator('keys', { initialValue: [] });
		const keys = getFieldValue('keys');
		const contents_error = getFieldError('contents') || [];
		let { activeKey = [], panel_data = [] } = this.state;
		let { listData = [], selectNorma = {} } = this.props;
		const formItems = keys.map((k, index) => {
			// console.log(rules);
			let rulers = (panel_data[k] || {}).rulers || [];
			rulers =
				rulers.length > 0
					? rulers
					: [
							{
								symble: '=',
								enName: undefined,
								val: undefined
							}
					  ];
			return (
				<Panel
					key={k}
					// forceRender
					// header={`This is panel header ${k}`}
					className="panel-li"
					header={
						<div className="panel-li-tips">
							<div className="panel-li-tips-content">
								<span className="tip">{index == 0 ? '开始' : '或'}</span>
								<Badge dot={contents_error[index] == '-1'} />
							</div>
							{index == keys.length - 1 && (
								<span
									className="add-btn"
									onClick={e => {
										e.stopPropagation();
										this.add();
									}}
								>
									添加
								</span>
							)}
						</div>
					}
					extra={
						keys.length > 1 ? (
							<Icon
								type="close"
								onClick={e => {
									e.stopPropagation();
									this.remove(k);
								}}
							/>
						) : null
					}
				>
					<Form.Item {...formItemLayout} label={'提示内容'} className="item-li">
						{getFieldDecorator(`contents[${k}]`, {
							validateTrigger: ['onChange', 'onBlur'],
							rules: [
								{
									required: true,
									whitespace: true,
									message: '-1' //'提示内容不能为空'
								}
							]
						})(
							<Input
								// onClick={e => {
								// 	e.stopPropagation();
								// }}
								placeholder="请输入提示内容"
								style={{ width: '100%' }}
							/>
						)}
					</Form.Item>
					<div className="rules-card-content-box">
						<div className="rules-card-content-body">
							{/* <div className="content-top">
									<label htmlFor="tips">提示内容：</label>
									<Input id="tips" placeholder="请输入提示内容" defaultValue={data.cnName} />
								</div> */}
							<div className="content-bottom">
								{rulers.map((item, j) => {
									return (
										<div key={j}>
											<div className="content-bottom-li">
												<div className="li-after">
													<span className="tip">且</span>
												</div>
												{rulers.length > 1 && (
													<Icon
														className="close-btn"
														type="close-circle"
														onClick={e => {
															console.log('删除', item.id);
															this.deleteRules(item.id);
															// this.removeContentLi(index);
														}}
													/>
												)}
												<Row
													style={{
														width: '100%'
													}}
												>
													<Col span={6}>
														<Form.Item
															className="item-li"
															wrapperCol={{
																xs: { span: 24 },
																sm: { span: 24 }
															}}
														>
															{getFieldDecorator(`rules[${j}].enName`, {
																initialValue: item.normalid,
																validateTrigger: ['onChange', 'onBlur'],
																rules: [
																	{
																		required: true,
																		whitespace: true,
																		message: '指标项不能为空'
																	}
																]
															})(
																<Select
																	className="select-box"
																	disabled={j == 0}
																	style={{ width: '100%' }}
																	// onChange={handleChange}
																	placeholder="请选择指标项"
																>
																	{listData.map((item, index) => {
																		return (
																			<Select.Option key={item.id} value={item.id}>
																				{item['cnName']}
																			</Select.Option>
																		);
																	})}
																</Select>
															)}
														</Form.Item>
													</Col>
													<Col span={6}>
														<Form.Item className="item-li">
															{getFieldDecorator(`rules[${j}].symble`, {
																initialValue: item.symble,
																validateTrigger: ['onChange', 'onBlur'],
																rules: [
																	{
																		required: true,
																		whitespace: true,
																		message: '分类不能为空'
																	}
																]
															})(
																<Select
																	className="select-box"
																	defaultValue={item.symble}
																	style={{ width: '100%' }}
																	// onChange={handleChange}
																	placeholder="请选择分类"
																>
																	<Select.Option value="=">等于</Select.Option>
																	<Select.Option value="!=">不等于</Select.Option>
																	<Select.Option value=">">大于</Select.Option>
																	<Select.Option value="<">小于</Select.Option>
																</Select>
															)}
														</Form.Item>
													</Col>
													<Col span={12}>
														<Form.Item className="item-li">
															{getFieldDecorator(`rules[${j}].val`, {
																initialValue: item.val,
																validateTrigger: ['onChange', 'onBlur'],
																rules: [
																	{
																		required: true,
																		whitespace: true
																		// message: '分类不能为空'
																	}
																]
															})(
																<Input
																	// onClick={e => {
																	// 	e.stopPropagation();
																	// }}
																	placeholder="请输入提示内容"
																	style={{ width: '100%' }}
																/>
															)}
														</Form.Item>
													</Col>
												</Row>
											</div>
											{j == rulers.length - 1 && (
												<span
													className="add-btn"
													onClick={e => {
														console.log('---');
														rulers.push({
															symble: '=',
															enName: undefined,
															val: undefined
														});
														this.setState({});
													}}
												>
													添加
												</span>
											)}
										</div>
									);
								})}
								{/* <RulesCardContentBottom form={this.props.form} data={(panel_data[k] || {}).rulers} listData={listData} selectNorma={selectNorma} /> */}
							</div>
						</div>
					</div>
				</Panel>
			);
		});
		let { tabs_data = [] } = this.props;
		return (
			<Form className="drive-alarm-rules-card-box">
				<Collapse
					className="card-collapse"
					accordion
					bordered={false}
					activeKey={activeKey}
					expandIcon={({ isActive }) => <Icon type="caret-right" rotate={isActive ? 90 : 0} />}
					onChange={activeKey => {
						this.setState({
							activeKey
						});
					}}
				>
					{formItems}
				</Collapse>

				{/* <Form.Item {...formItemLayoutWithOutLabel}>
					<Button type="dashed" onClick={this.add} style={{ width: '60%' }}>
						<Icon type="plus" /> Add field
					</Button>
				</Form.Item> */}
				{/* <Form.Item {...formItemLayoutWithOutLabel}>
					<Button type="primary" htmlType="submit">
						Submit
					</Button>
				</Form.Item> */}
			</Form>
		);
	}
}
ReactDOM.render(<AlarmRulesBox />, document.getElementById(domId));
