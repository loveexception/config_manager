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
let { Button, Input, Tabs, Collapse, Icon, Select } = antd;
const { TabPane } = Tabs;
const { Panel } = Collapse;
// 获取search解析为对象导出
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
class RulesCardContentBottom extends React.PureComponent {
	state = { data: [], select_list: [] };
	addContentLi = () => {
		this.setState({
			data: [
				...this.state.data,
				{
					key: Math.random()
				}
			]
		});
	};
	removeContentLi = index => {
		let data = this.state.data;
		if (index > -1) {
			data.splice(index, 1);
		}
		this.setState({
			data: [...data]
		});
	};
	componentDidMount() {
		this.selectListData();
		let { data = [] } = this.props;
		console.log('---', data);
		this.setState({
			data
		});
	}
	selectListData = () => {
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
						select_list: results.data
					});
				}
			});
		}
	};
	render() {
		let { _list_data = {} } = this.props;
		let { list = [], record = {} } = _list_data;
		let { data = [], select_list = [] } = this.state;
		let { driver_id, id } = urlArgs();
		console.log('---pp---', driver_id, id);
		return data.map((item, index) => {
			return (
				<div key={item.key}>
					<div className="content-bottom-li">
						<div className="li-after">
							<span className="tip">且</span>
						</div>
						<Select
							className="select-box"
							defaultValue={index == 0 ? record['enName'] : ''}
							disabled={index == 0}
							style={{ width: 150 }}
							// onChange={handleChange}
							// placeholder="请选择分类"
						>
							{select_list.map((item, index) => {
								return (
									<Select.Option key={index} value={item['enName']}>
										{item['cnName']}
									</Select.Option>
								);
							})}
						</Select>
						<Select
							className="select-box"
							defaultValue="="
							style={{ width: 120 }}
							// onChange={handleChange}
							// placeholder="请选择分类"
						>
							<Select.Option value="=">等于</Select.Option>
							<Select.Option value="!=">不等于</Select.Option>
							<Select.Option value=">">大于</Select.Option>
							<Select.Option value="<">小于</Select.Option>
						</Select>
						<Input
						// placeholder="请输入提示内容"
						/>
						{data.length > 1 && (
							<Icon
								className="close-btn"
								type="close-circle"
								onClick={e => {
									this.removeContentLi(index);
								}}
							/>
						)}
					</div>
					{index == data.length - 1 && (
						<span
							className="add-btn"
							onClick={e => {
								this.addContentLi();
							}}
						>
							添加
						</span>
					)}
				</div>
			);
		});
	}
}
class RulesCardContent extends React.PureComponent {
	render() {
		let { data = {} } = this.props;
		let li_data = data.data || [];
		return (
			<div className="rules-card-content-box">
				<div className="rules-card-content-body">
					<div className="content-top">
						<label htmlFor="tips">提示内容：</label>
						<Input id="tips" placeholder="请输入提示内容" defaultValue={data.content} />
					</div>
					<div className="content-bottom">
						<RulesCardContentBottom data={li_data} />
					</div>
				</div>
			</div>
		);
	}
}
class RulesCard extends React.PureComponent {
	state = {
		data: [
			{
				key: 'a',
				content: '1',
				data: [
					{
						key: 1
					},
					{ key: 2 }
				]
			},
			{
				key: 'b',
				content: '2',
				data: [{ key: 3 }]
			},
			{
				key: 'c',
				content: '3',
				data: [{}]
			}
		]
	};
	addPanel = () => {
		this.setState({
			data: [
				...this.state.data,
				{
					key: Math.random(),
					content: '',
					data: [{}]
				}
			]
		});
	};
	removePanel = index => {
		let data = this.state.data;
		if (index > -1) {
			data.splice(index, 1);
		}
		this.setState({
			data: [...data]
		});
	};
	render() {
		let { data = [] } = this.state;
		return (
			<div className="drive-alarm-rules-card-box">
				<Collapse className="card-collapse" accordion bordered={false} defaultActiveKey={[data[0].key]} expandIcon={({ isActive }) => <Icon type="caret-right" rotate={isActive ? 90 : 0} />}>
					{data.map((item, index) => {
						return (
							<Panel
								key={item.key}
								className="panel-li"
								header={
									<div className="panel-li-tips">
										<span className="tip">{index == 0 ? '开始' : '或'}</span>
										{index == data.length - 1 && (
											<span
												className="add-btn"
												onClick={e => {
													e.stopPropagation();
													this.addPanel();
												}}
											>
												添加
											</span>
										)}
									</div>
								}
								extra={
									data.length > 1 ? (
										<Icon
											type="close"
											onClick={e => {
												e.stopPropagation();
												this.removePanel(index);
											}}
										/>
									) : null
								}
							>
								<RulesCardContent data={item} />
							</Panel>
						);
					})}
				</Collapse>
			</div>
		);
	}
}

class AlarmRulesBox extends React.PureComponent {
	render = () => {
		let tabs_data = [
			{
				title: '紧急告警'
			},
			{
				title: '重要告警'
			},
			{
				title: '次要告警'
			},
			{
				title: '告警提示'
			}
		];
		let { _list_data = {} } = parent;
		let { list = [], record = {} } = _list_data;

		return (
			<div className="drive-alarm-rules-body">
				<div className="title">{record['指标项中文简称']}</div>
				<Tabs
					className="device-tabs-box"
					onChange={key => {
						console.log(key);
					}}
					type="card"
				>
					{tabs_data.map((item, index) => {
						return (
							<TabPane className="device-tabs-pane" tab={item.title} key={index}>
								<RulesCard />
							</TabPane>
						);
					})}
				</Tabs>
			</div>
		);
	};
}
ReactDOM.render(<AlarmRulesBox />, document.getElementById(domId));
