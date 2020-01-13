let { useEffect, useState, PureComponent } = React;
let { Select, Radio, Button, Icon, Switch, PageHeader, List, Slider, Checkbox, Avatar, Table, Input, InputNumber, Rate, Upload, Popconfirm, Form, Row, Col } = antd;
const { Option } = Select;
const textFormat = <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>;


// filterFn 
let filterFn = (possible,_this,before)=>{
	let arr = [];
	if(!before){
		arr.push({type:"list",level:possible.L})
		for(let i = 0;i<2;i++){
			if(_this.state[i]===false){
				continue
			}
			i == 0 ? arr.push({type:"window",level:possible.W}) :arr.push({type:"audio",level:possible.A})
		}
	}else{
	 ///优化处理	
	}
	
	return arr
}
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
	return (
		<Form.Item label={props.label}>
			{props.getFieldDecorator(props.label, {
				initialValue: 0
			})(<Slider min={1} max={4} marks={props.config} step={null} />)}
		</Form.Item>
	);
}

class PushFrom extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			0:false,
			1:true,
			_data: {
				text: [ '紧急告警', '重要告警', '次要告警', '告警提示'],
				numPar: [1,2,3,4],
				numCol: ['弹窗', '响铃', '列表'],
				column: [],
				dataSource: [
					{
						radio: '弹窗'
					}
				]
			}
		};
	}
	handleSubmit = e => {
		e.preventDefault();
		this.props.form.validateFields((err, values) => {
		let arrData = filterFn(values,this)
			$.post("/mao/pushs/addDo",arrData,(r)=>{
				console.log(r,'rrr')
			})
			if (!err) {
				// console.log('Received values of form: ', values);
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
										this.props.form && this.props.form.resetFields();
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
								render: (value, mes, i) =>{
									if(i ==2){
									    return value
									}
									return (
										<div className="center-box">
											<Checkbox  
											onClick={()=>{
												return this.setState({
													[i]:!this.state[i]
												})}} checked ={this.state[i]} value={i} key={i}>
												{value}
											</Checkbox>
										</div>	
								)}
							},
							{
								dataIndex: 'level',
								align: 'left',
								className: 'polling-level',
								title: `告警级别`,
								render: (v, m, i) => {
									let config = {};
									_data.numPar.map((item, index) => {
										config[item] = _data.text[index];
									});
									return <FromItem key={i} label={i == 0 ? 'W' : i == 1 ? 'A' : 'L'} getFieldDecorator={getFieldDecorator} config={config}></FromItem>;
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
