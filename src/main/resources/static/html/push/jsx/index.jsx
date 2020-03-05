let { useEffect, useState, PureComponent } = React;
let { Select, Radio, Button, Icon, Switch, PageHeader, List, Slider, Checkbox, Avatar, Table, Input, InputNumber, Rate, Upload, Popconfirm, Form, Row, Col, message } = antd;
const { Option } = Select;
const textFormat = <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>;

const dataConfig = {
	critical: 1,
	major: 2,
	minor: 3,
	warning: 4
};
const sliderStr = ['无', '紧急', '重要', '次要', '提示'];
const reqConfig = ['', 'critical', 'major', 'minor', 'warning'];
const strConfig = {
	L: 'list',
	W: 'window',
	A: 'audio'
};
//remove Fn
function removePushs(type, dataList) {
	let ids = [];
	Array.isArray(dataList) &&
		dataList.forEach(e => {
			e.type === type ? ids.push(e.id) : '';
		});
	$.post('/mao/pushs/remove', JSON.stringify({ ids }), results => {
		if (results.code === 0) {
			// message.success(results.msg, 0.5);
		} else {
			message.error(results.msg, 0.5);
		}
	});
}
// filterFn
let filterFn = (possible, _this, props, before) => {
	let arr = [];
	if (!before) {
		//list 必填项
		// arr.push({type:"list",level:possible.L})
		for (let i = 0; i < 3; i++) {
			if (i == 0) {
				if (_this.state[i] === false) {
					removePushs('window', props.dataList);
					continue;
				}
				for (let j = possible.W; j > 0; j--) {
					arr.push({ type: 'window', level: reqConfig[j] });
				}
			} else if (i == 1) {
				if (_this.state[i] === false) {
					removePushs('audio', props.dataList);
					continue;
				}
				for (let j = possible.A; j > 0; j--) {
					arr.push({ type: 'audio', level: reqConfig[j] });
				}
			} else {
				for (let j = possible.L; j > 0; j--) {
					arr.push({ type: 'list', level: reqConfig[j] });
				}
			}
		}
	} else {
		///优化处理
	}
	// $.ajax({
	// 	url:"/mao/pushs/editDo",
	// 	data:{
	// 		data:arr
	// 	},
	// 	success:function(results){
	// 		console.log(results,'results')
	// 	},
	// 	type:"POST"
	// })
	$.ajax({
		cache: true,
		type: 'POST',
		url: '/mao/pushs/editDo',
		data: JSON.stringify({
			data: arr
		}),
		dataType: 'json',
		async: false,
		success: results => {
			if (results.code != 0) {
				message.error('接口错误');
				return;
			}
			// console.log(_this.props.reqListFn,'_this.props.reqListFn')
			props.resetReq && props.resetReq();
			message.info(results.msg);
		}
	});
	return;
};
// filterListFn
let filterListFn = (data = [], that) => {};
// dataConfig

const levelConfig = {
	critical: '紧急',
	major: '重要',
	minor: '次要',
	warning: '提示'
};
// title
function MTitle(props) {
	return (
		<div className="table-title">
			{textFormat}
			{props.text}
		</div>
	);
}
//component Table
function MTable(props) {
	let { titleFn = false, data = [], columns = [], footerFn = false, pagination = true } = props.config;
	useEffect(() => {
		return () => {};
	}, []);
	return <div className="component-table-box">{props.config ? <Table bordered dataSource={data} pagination={pagination} columns={columns} title={titleFn} footer={footerFn}></Table> : ''}</div>;
}
//html hook
function TopTable(props) {}
// form Iten
function FromItem(props) {
	let { disableConfig, label } = props;
	useEffect(() => {}, []);
	return (
		// <Form.Item label={props.label}>
		// 	{props.getFieldDecorator(props.label, {
		// 		initialValue: props.key || props.value
		// 	})(
		<div style={{ width: '50%' }}>
			<Slider
				disabled={label === 'W' ? !disableConfig[0] : label === 'A' ? !disableConfig[1] : false}
				onChange={value => {
					// console.log(props, 'props');
					props.handleRadio(value, props.label);
				}}
				min={1}
				max={4}
				// defaultValue={props && props.initValue}
				marks={props.config}
				// defaultValue={2}
				step={null}
				value={props.initValue}
			/>
		</div>
		// 	)}
		// </Form.Item>
	);
}

class PushFrom extends React.Component {
	constructor(props) {
		super(props);
		this.flag = true;
		this.state = {
			0: false,
			1: false,
			_initValue: false,
			_data: {
				text: ['紧急告警', '重要告警', '次要告警', '告警提示'],
				numPar: [1, 2, 3, 4],
				numCol: ['弹窗', '响铃', '列表'],
				column: [],
				dataSource: [
					{
						radio: '弹窗'
					}
				]
			},
			initData: {
				window: 0,
				audio: 0,
				list: 1
			}
		};
	}
	componentDidMount() {}
	componentDidUpdate() {
		if (this.flag) {
			let { dataList } = this.props;
			this.flag = false;
			let initData = this.state.initData;
			Array.isArray(dataList) &&
				dataList.forEach((e, i) => {
					let t = e.type;
					let l = e.level;
					initData[t] > dataConfig[l] ? '' : (initData[t] = dataConfig[l]);
				});
			this.setState({
				0: !!initData.window,
				1: !!initData.audio,
				initData
			});
		}
	}
	handleSubmit = e => {
		e.preventDefault();
		this.props.form.validateFields((err, values) => {
			let arrData = filterFn(values, this, this.props); // <=
			// arrData 是 过滤后的数组 （发送请求的数组）   4个 数据 级别对应 下标
			if (!err) {
				// console.log('Received values of form: ', values);
			}
		});
	};

	handleRadio = (value, label) => {
		this.setState({
			initData: {
				...this.state.initData,
				[strConfig[label]]: value
			}
		});
	};
	normFile = e => {
		if (Array.isArray(e)) {
			return e;
		}
		return e && e.fileList;
	};

	render() {
		let { _data } = this.state;
		const { getFieldDecorator } = this.props.form;
		const formItemLayout = {
			labelCol: { span: 6 },
			wrapperCol: { span: 14 }
		};
		return (
			<Form {...formItemLayout} onSubmit={this.handleSubmit}>
				<MTable
					config={{
						pagination: false,
						footerFn: () => (
							<Form.Item wrapperCol={{ span: 24, offset: 20 }}>
								<Button type="primary" htmlType="submit" style={{ marginRight: '0.2rem' }}>
									保存
								</Button>
								<Button
									onClick={() => {
										this.setState({
											0: false,
											1: false,
											initData: {
												window: 1,
												audio: 1,
												list: 1
											}
										});
									}}
								>
									重置
								</Button>
							</Form.Item>
						),
						columns: [
							{
								dataIndex: 'radio',
								align: 'center',
								width: '13%',
								title: '通知方式',
								render: (value, mes, i) => {
									if (i == 2) {
										return value;
									}
									return (
										<div className="center-box">
											<Checkbox
												onClick={() => {
													return this.setState({
														[i]: !this.state[i]
													});
												}}
												checked={this.state[i]}
												value={i}
												key={i}
											>
												{value}
											</Checkbox>
										</div>
									);
								}
							},
							{
								dataIndex: 'level',
								align: 'left',
								className: 'polling-level',
								title: `告警级别`,
								render: (v, m, i) => {
									let config = {};
									let { initData } = this.state;
									let initValue = 0;
									switch (i) {
										case 0:
											initValue = initData && initData.window;
											break;
										case 1:
											initValue = initData && initData.audio;
											break;
										case 2:
											initValue = initData && initData.list;
											break;
										default:
											0;
											break;
									}
									_data.numPar.map((item, index) => {
										config[item] = _data.text[index];
									});
									return <FromItem disableConfig={{ 0: this.state[0], 1: this.state[1] }} handleRadio={this.handleRadio} initValue={initValue} key={i} label={i == 0 ? 'W' : i == 1 ? 'A' : 'L'} getFieldDecorator={getFieldDecorator} config={config}></FromItem>;
								}
							}
						],
						data: [
							{
								radio: '弹窗',
								level: 25
							},
							{
								radio: '响铃',
								level: 0
							},
							{
								radio: '列表',
								level: 0
							}
						] //data._dataa.dataSource
					}}
				/>
			</Form>
		);
	}
}

PushFrom = Form.create({ name: 'pushfrom' })(PushFrom);

function FooterTable(props) {}

//js class
class UpgradeBox extends PureComponent {
	constructor(props) {
		super(props);
		this.state = {
			dataList: [],
			data: [
				{
					key: 'window',
					index: '01',
					pushWay: '弹窗',
					level: '无'
				},
				{
					key: 'audio',
					index: '02',
					pushWay: '响铃',
					level: '无'
				},
				{
					key: 'list',
					index: '03',
					pushWay: '列表',
					level: '无'
				}
			],
			columns: [
				{ title: '序号', dataIndex: 'index', align: 'center', width: '10%' },
				{ title: '推送方式', dataIndex: 'pushWay', align: 'center', width: '20%' },
				{ title: '告警级别', dataIndex: 'level', align: 'center', width: '70%' }
			]
		};
	}
	reqListFn = () => {
		$.post('/mao/pushs/list', results => {
			if (results.code == 0) {
				let dataList = results.rows;
				let obj = {
					window: '',
					audio: '',
					list: ''
				};
				let initData = {
					window: 0,
					audio: 0,
					list: 0
				};
				Array.isArray(dataList) &&
					dataList.forEach((e, i) => {
						let t = e.type;
						let l = e.level;
						initData[t] == dataConfig[l] ? '' : initData[t] < dataConfig[l] ? (initData[t] = dataConfig[l]) : '';
					});
				for (let key in initData) {
					let flag = false;
					for (let i = initData[key]; i > 0; i--) {
						obj[key] += flag ? '/' + sliderStr[i] : sliderStr[i];
						flag = true;
					}
					obj[key] = obj[key] ? obj[key] : sliderStr[0];
				}
				let data = this.state.data;
				data = data.map(e => {
					e.level = obj[e.key];
					return e;
				});
				this.setState({
					dataList,
					data
				});
			} else {
				message.error('接口错误', 0.5);
			}
		});
	};
	init = () => {
		this.reqListFn();
	};
	checkBoxState = p_this => {
		p_this.setState({
			0: this[0],
			1: this[1]
		});
	};
	// 版本太低 无法使用 static getDerivedStateFromProps(){
	// 		console.log("===");
	// 	}
	componentDidMount() {
		this.init();
	}
	render() {
		let { data, columns, dataList = [] } = this.state;
		return (
			<div className="push-list-box">
				<MTitle text="设置推送策略" />
				<PushFrom resetReq={this.reqListFn} dataList={dataList} />
				<MTitle text="推送策略列表" />
				<MTable dataList={dataList} config={{ data, columns, pagination: false }}></MTable>
			</div>
		);
	}
}

ReactDOM.render(<UpgradeBox />, document.getElementById('push-list'));
