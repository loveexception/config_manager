let { useEffect, useState, PureComponent } = React;
let { Select, Radio, Button, Icon, Switch, PageHeader, List, Slider, Checkbox, Avatar, Table, Input, InputNumber, Rate, Upload, Popconfirm, Form, Row, Col } = antd;
const { Option } = Select;
const textFormat = <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>;

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
	console.log(props);
	return (
		<Form.Item label={props.label}>
			{props.getFieldDecorator(props.label, {
				initialValue: ['0']
			})(<Slider marks={props.config} step={null} />)}
		</Form.Item>
	);
}

class PushFrom extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			data: {
				text: ['取消告警', '紧急告警', '重要告警', '次要告警', '告警提示'],
				numPar: [0, 25, 50, 75, 100],
				numCol: ['弹窗', '响铃', '列表']
			}
		};
	}
	handleSubmit = e => {
		e.preventDefault();
		this.props.form.validateFields((err, values) => {
			if (!err) {
				console.log('Received values of form: ', values);
			}
		});
	};

	normFile = e => {
		console.log('Upload event:', e);
		if (Array.isArray(e)) {
			return e;
		}
		return e && e.fileList;
	};

	render() {
		let { data } = this.state;
		const { getFieldDecorator } = this.props.form;
		const formItemLayout = {
			labelCol: { span: 6 },
			wrapperCol: { span: 14 }
		};
		return (
			<Form {...formItemLayout} onSubmit={this.handleSubmit}>
				<Row>
					<Col span={5}>
						<Form.Item label="checkbox">
							{getFieldDecorator('checkbox', {
								initialValue: [data.text[0]]
							})(
								<Checkbox.Group style={{ width: '100%' }}>
									<Row>
										{data.numCol.map((e, i) => {
											return (
												<Checkbox value={i} key={i}>
													{e}
												</Checkbox>
											);
										})}
									</Row>
								</Checkbox.Group>
							)}
						</Form.Item>
					</Col>
					<Col span={19}>
						{data.numCol.map((e, i) => {
							let config = {};
							data.numPar.map((item, index) => {
								config[item] = data.text[index];
							});
							return <FromItem key={i} label={i == 0 ? 'T' : i == 1 ? 'X' : 'L'} getFieldDecorator={getFieldDecorator} config={config}></FromItem>;
						})}
						{/* <Form.Item label="Slider3">
							{getFieldDecorator('slider3')(
								<Slider
									marks={{
										0: 'A',
										20: 'B',
										40: 'C',
										100: 'F'
									}}
								/>
							)}
						</Form.Item> */}
					</Col>
				</Row>

				<Form.Item wrapperCol={{ span: 24, offset: 20 }}>
					<Button type="primary" htmlType="submit" style={{ marginRight: '0.2rem' }}>
						保存
					</Button>
					<Button
						onClick={() => {
							this.props.form && this.props.form.resetFields();
						}}
					>
						重置
					</Button>
				</Form.Item>
			</Form>
		);
	}
}

const WrappedDemo = Form.create({ name: 'pushfrom' })(PushFrom);

function FooterTable(props) {}

//js class
class UpgradeBox extends PureComponent {
	constructor(props) {
		super(props);
		this.state = {
			data: [
				{
					key: '0',
					index: '01',
					pushWay: '弹窗',
					level: '无'
				},
				{
					key: '1',
					index: '02',
					pushWay: '弹窗',
					level: '无'
				},
				{
					key: '2',
					index: '03',
					pushWay: '弹窗',
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
	render() {
		let { data, columns } = this.state;
		return (
			<div className="push-list-box">
				<MTitle text="设置推送策略" />
				<WrappedDemo />
				<MTitle text="推送策略列表" />
				<MTable config={{ data, columns, pagination: false }}></MTable>
			</div>
		);
	}
}

ReactDOM.render(<UpgradeBox />, document.getElementById('push-list'));
