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

class RulesCardContentBottom extends React.PureComponent {
	state = { data: [] };
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
		let { data = [] } = this.props;
		this.setState({
			data
		});
	}
	render() {
		let { _list_data = {} } = parent;
		let { list = [], record = {} } = _list_data;
		let { data = [] } = this.state;
		return data.map((item, index) => {
			return (
				<div key={item.key}>
					<div className="content-bottom-li">
						<div className="li-after">
							<span className="tip">且</span>
						</div>
						<Select
							className="select-box"
							defaultValue={
								index == 0 ? record['指标项英文简称'] : ''
							}
							disabled={index == 0}
							style={{ width: 150 }}
							// onChange={handleChange}
							// placeholder="请选择分类"
						>
							{list.map((item, index) => {
								return (
									<Select.Option
										key={item.key}
										value={item['指标项英文简称']}
									>
										{item['指标项中文简称']}
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
						<Input
							id="tips"
							placeholder="请输入提示内容"
							defaultValue={data.content}
						/>
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
				<Collapse
					className="card-collapse"
					accordion
					bordered={false}
					defaultActiveKey={[data[0].key]}
					expandIcon={({ isActive }) => (
						<Icon type="caret-right" rotate={isActive ? 90 : 0} />
					)}
				>
					{data.map((item, index) => {
						return (
							<Panel
								key={item.key}
								className="panel-li"
								header={
									<div className="panel-li-tips">
										<span className="tip">
											{index == 0 ? '开始' : '或'}
										</span>
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
							<TabPane
								className="device-tabs-pane"
								tab={item.title}
								key={index}
							>
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
