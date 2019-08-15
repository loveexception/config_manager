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
let { Button, Input, Tabs, Collapse, Icon, Select, Form, message, Badge, Row, Col, Empty, Table, Radio, Checkbox } = antd;
const { TabPane } = Tabs;
const { Panel } = Collapse;

class AlarmRulesBox extends React.PureComponent {
	state = {};

	componentDidMount() {
		let { deviceid } = urlArgs();
		this.alarmConfiguration && this.alarmConfiguration.init(deviceid);
	}

	render = () => {
		let { deviceid } = urlArgs();
		return (
			<div className="device-alarm-rules-body">
				<AlarmConfiguration device_id={deviceid} ref={el => (this.alarmConfiguration = el)} title={'驱动详情'} show={true} />
			</div>
		);
	};
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
			<Checkbox checked={this.state.checked} onChange={this.onChange} className="switch-custom-box">
				<span className={`text ${this.state.checked ? '' : 'text-checked'}`}>{!this.state.checked ? '开启' : '关闭'}</span>
			</Checkbox>
		);
	}
}
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

ReactDOM.render(<AlarmRulesBox />, document.getElementById(domId));
