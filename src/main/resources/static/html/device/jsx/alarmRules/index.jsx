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
function iframeClose() {
	if (parent.layer) {
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}
}
let { Button, Input, Tabs, Collapse, Icon, Select, Form, message, Badge, Row, Col, Empty } = antd;
const { TabPane } = Tabs;
const { Panel } = Collapse;

class AlarmRulesBox extends React.PureComponent {
	state = {
		select_key: 'critical',
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
		],
		TabComponent: {
			critical: Form.create({
				name: 'alarm_rules_critical'
			})(DynamicFieldSet),
			major: Form.create({
				name: 'alarm_rules_major'
			})(DynamicFieldSet),
			minor: Form.create({
				name: 'alarm_rules_minor'
			})(DynamicFieldSet),
			warning: Form.create({
				name: 'alarm_rules_warning'
			})(DynamicFieldSet)
		},
		_ids: {}
	};
	listData = () => {
		let { personid } = urlArgs();
		if (personid) {
			$.ajax({
				url: `/iot/person/person_id?id=${personid}`,
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
					let { deviceid, normalid, normal = {} } = results.data || [];
					let { driverid } = normal;
					this.setState({
						_ids: {
							deviceid,
							normalid,
							driverid,
							personid
						}
					});
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
			<div className="device-alarm-rules-body">
				<div className="title">{selectNorma['cnName']}</div>
				<Tabs
					className="device-tabs-box"
					onChange={key => {
						this.setState(
							{
								select_key: key
							},
							() => {
								let value = (key || '').split('-')[0];
								if (value) {
									let tab = this[`rules_${value}`];
									tab && tab.getPanelList();
								}
							}
						);
					}}
					type="card"
				>
					{tabs_data.map((item, index) => {
						let Component = this.state.TabComponent[item.value];
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
								key={`${item.value}-${index}`}
							>
								<Component onRef={el => (this[`rules_${item.value}`] = el)} _ids={this.state._ids} grade={item} listData={listData} selectNorma={selectNorma} />
							</TabPane>
						);
					})}
				</Tabs>
				<div className="device-btn-box">
					<Button
						type="primary"
						onClick={() => {
							let { tabs_data = [] } = this.state;
							let all_data = [];
							let num = 0;
							tabs_data.map((item, index) => {
								let rules_form = this[`rules_${item.value}`];
								let rules_point = this[`rules_${item.value}_point`];
								if (rules_form) {
									rules_form.handleSubmit(data => {
										if (data) {
											rules_point.classList.remove('point');
											all_data.push(data);
											num++;
										} else {
											rules_point.classList.add('point');
										}
									});
								}
							});
							if (num == 4) {
								let all_data_new = _.flattenDepth(all_data, 1) || [];
								if (all_data_new.length > 0) {
									$.ajax({
										cache: true,
										type: 'POST',
										url: '/iot/person/grade_all_save',
										data: JSON.stringify({
											data: all_data_new
										}),
										dataType: 'json',
										async: false,
										success: results => {
											if (results.code != 0) {
												message.error('接口错误');
												return;
											}
											let value = (this.state.select_key || '').split('-')[0];
											if (value) {
												let tab = this[`rules_${value}`];
												tab && tab.getPanelList();
												message.success('保存成功', 0.5);
											}
											// this.getPanelList();
										}
									});
								}
							} else {
								message.error('表单填写错误', 0.5);
							}
						}}
					>
						保存
					</Button>
					<Button
						onClick={() => {
							iframeClose();
						}}
					>
						取消
					</Button>
				</div>
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
	componentDidUpdate(prevProps, prevState) {
		if (prevProps._ids != this.props._ids) {
			this.getPanelList();
		}
	}

	remove = (index, record = {}) => {
		if (record.id) {
			console.log('有grade_id');

			$.ajax({
				cache: true,
				type: 'POST',
				url: '/iot/driver/grade_remove',
				data: {
					id: record.id
				},
				async: false,
				success: results => {
					if (results.code != 0) {
						message.error('接口错误');
						return;
					}
					const { form } = this.props;
					const keys = form.getFieldValue('keys');
					if (keys.length === 1) {
						return;
					}
					form.setFieldsValue({
						keys: keys.filter(key => key !== index)
					});
					// this.getPanelList();
				}
			});
		}
	};

	add = () => {
		let { grade = {} } = this.props;
		let { normalid } = urlArgs();
		if (normalid) {
			console.log('有normal_id');
			$.ajax({
				cache: true,
				type: 'POST',
				url: '/iot/driver/grade_add',
				data: JSON.stringify({
					data: { cnName: '', grade: grade.value, normalid }
				}),
				dataType: 'json',
				async: false,
				success: results => {
					if (results.code != 0) {
						message.error('接口错误');
						return;
					}
					const { form } = this.props;
					// can use data-binding to get
					const keys = form.getFieldValue('keys');
					const nextKeys = keys.concat(this.id++);
					// can use data-binding to set
					// important! notify form to detect changes
					form.setFieldsValue({
						keys: nextKeys
					});
					this.getPanelList();
				}
			});
		}
	};

	getPanelList = () => {
		let { personid } = this.props._ids;
		let { grade = {} } = this.props;
		if (personid) {
			//"grade": "normal",
			// normal,warning,minor,major,critical
			// 正常，提示，次要告警，主要告警，紧急报警
			$.ajax({
				url: `/iot/person/grade_list?personid=${personid}&grade=${grade.value}`,
				// url: `/iot/device/person/find_add_one?deviceid=03a188c952dc40dd980008646dacd8e2&normalid=2e0d81666674402e89a99d732319814e`,
				// url: `/iot/driver/grade_list?normalid=${normalid}&grade=${grade.value}`,
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
					// this.state.activeKey = ['0', '1', '2', '3', '4', '5'];
					form.setFieldsValue({ keys });
				}
			});
		}
	};
	addRules = (index, grade = {}) => {
		if (grade.id) {
			console.log('有grade_id');
			$.ajax({
				cache: true,
				type: 'POST',
				url: '/iot/person/ruler_add',
				data: JSON.stringify({
					data: [
						{
							logic: 'and',
							symble: '=',
							normalid: '',
							val: ''
						}
					],
					gradeid: grade.id
				}),
				dataType: 'json',
				async: false,
				success: results => {
					if (results.code != 0) {
						message.error('接口错误');
						return;
					}
					this.getPanelList();
				}
			});
		}
	};
	deleteRules = (k, index, record = {}, rulers) => {
		if (record.id) {
			$.ajax({
				cache: true,
				type: 'POST',
				url: '/iot/person/ruler_remove',
				data: {
					id: record.id
				},
				async: false,
				success: results => {
					if (results.code != 0) {
						message.error('接口错误');
						return;
					}
					const { form } = this.props;
					const rules = form.getFieldValue(`rules[${k}]`);
					form.setFieldsValue({
						[`rules[${k}]`]: rules.filter((key, i) => i !== index)
					});
					this.getPanelList();
				}
			});
		}
	};
	handleSubmit = cb => {
		this.props.form.validateFields((err, values) => {
			if (!err) {
				const { keys, contents, rules = [] } = values;
				let data = keys.map(key => contents[key]);
				console.log('---', data);
				let data_new = [];
				if (data.length > 0) {
					data.map((item, i) => {
						let rulers_new = [];
						let rulers_old = rules[i] || [];
						rulers_old.map((item2, j) => {
							rulers_new.push({
								...item2.data,
								normalid: item2.normalid,
								symble: item2.symble,
								val: item2.val
							});
						});
						data_new.push({
							...item.data,
							cnName: item.cnName,
							rulers: rulers_new
						});
					});
				}
				cb(data_new);
			} else {
				cb();
			}
		});
	};
	render() {
		let { activeKey = [], panel_data = [] } = this.state;
		let { listData = [], selectNorma = {}, grade = {}, form = {} } = this.props;
		const { getFieldDecorator, getFieldValue, getFieldError } = form;
		let { normalid } = urlArgs();
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
		// const formItemLayoutWithOutLabel = {
		// 	wrapperCol: {
		// 		xs: { span: 24, offset: 0 },
		// 		sm: { span: 20, offset: 4 }
		// 	}
		// };
		getFieldDecorator('keys', { initialValue: [] });
		const keys = getFieldValue('keys');
		const formItems = keys.map((k, index) => {
			let item_k = panel_data[k] || {};
			let rulers = item_k.rulers || [];
			getFieldDecorator(`contents[${k}].data`, {
				initialValue: {
					// normalid,
					// grade: grade.value,
					...item_k
				}
			});
			const contents_error = getFieldError(`contents[${k}].cnName`) || [];
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
								<Badge dot={contents_error[0] == '-1'} />
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
									this.remove(k, item_k);
								}}
							/>
						) : null
					}
				>
					<Form.Item {...formItemLayout} label={'提示内容'} className="item-li">
						{getFieldDecorator(`contents[${k}].cnName`, {
							initialValue: item_k.cnName,
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
							<div className="content-bottom">
								{rulers.length > 0 ? (
									rulers.map((item_j, j) => {
										console.log(item_j);
										let select = [];
										if (j == 0) {
											select = listData.filter(it => it.id == item_j.normalid);
										}
										getFieldDecorator(`rules[${k}][${j}].data`, { initialValue: { ...item_j } });
										return (
											<div key={j}>
												<div className="content-bottom-li">
													<div className="li-after">
														<span className="tip">且</span>
													</div>
													{/* {rulers.length > 1 && ( */}
													<Icon
														className="close-btn"
														type="close-circle"
														onClick={e => {
															console.log('删除', item_j.id);
															this.deleteRules(k, j, item_j, rulers);
															// this.removeContentLi(index);
														}}
													/>
													{/* )} */}
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
																{getFieldDecorator(`rules[${k}][${j}].normalid`, {
																	initialValue: item_j.normalid,
																	validateTrigger: ['onChange', 'onBlur'],
																	rules: [
																		{
																			required: true,
																			whitespace: true,
																			message: '指标项不能为空'
																		}
																	]
																})(
																	<Select className="select-box" disabled={select.length > 0} style={{ width: '100%' }} placeholder="请选择指标项">
																		{listData.map(item => {
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
																{getFieldDecorator(`rules[${k}][${j}].symble`, {
																	initialValue: item_j.symble,
																	validateTrigger: ['onChange', 'onBlur'],
																	rules: [
																		{
																			required: true,
																			whitespace: true,
																			message: '分类不能为空'
																		}
																	]
																})(
																	<Select className="select-box" style={{ width: '100%' }} placeholder="请选择分类">
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
																{getFieldDecorator(`rules[${k}][${j}].val`, {
																	initialValue: item_j.val,
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
															this.addRules(j, item_k);
															// rulers.push({
															// 	symble: '=',
															// 	normalid: undefined,
															// 	val: undefined
															// });
															// this.setState({});
														}}
													>
														添加
													</span>
												)}
											</div>
										);
									})
								) : (
									<div className="no-children">
										<span
											className="add-btn"
											onClick={e => {
												console.log('no-children');
												this.addRules(0, item_k);
												// rulers.push({
												// 	symble: '=',
												// 	normalid: undefined,
												// 	val: undefined
												// });
												// this.setState({});
											}}
										>
											添加
										</span>
									</div>
								)}
								{/* <RulesCardContentBottom form={this.props.form} data={(panel_data[k] || {}).rulers} listData={listData} selectNorma={selectNorma} /> */}
							</div>
						</div>
					</div>
				</Panel>
			);
		});
		return (
			<Form className="device-alarm-rules-card-box">
				{keys.length <= 0 && (
					<div className="empty-box">
						<div>
							<span
								className="add-btn"
								onClick={e => {
									this.add();
								}}
							>
								添加
							</span>
						</div>
						<Empty image={Empty.PRESENTED_IMAGE_SIMPLE} style={{ margin: 0 }} />
					</div>
				)}
				<Collapse
					className="card-collapse"
					// accordion
					bordered={false}
					defaultActiveKey={['0', '1', '2', '3', '4', '5']}
					expandIcon={({ isActive }) => <Icon type="caret-right" rotate={isActive ? 90 : 0} />}
					// onChange={activeKey => {
					// 	this.setState({
					// 		activeKey
					// 	});
					// }}
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
