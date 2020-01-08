
let { Select, Radio, Button, Icon, PageHeader, List, Avatar, Table, Input, InputNumber, Popconfirm, Form,message } = antd;
const { Option } = Select;
let MIcon = function(props) {
	//重写 Icon  字体大小保持一直 样式 公共设置 等
	return <Icon style={{ fontSize: '0.12rem' }} {...props} />;
};
// console.log( jsmini_pubsub.name,'PubSub')
// let isAdd = true;

// const data = [];
const textFormat = <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>;

const EditableContext = React.createContext();
let { useEffect, useState } = React;
let setInitValue = () => {};
//demo TabList

const FooterEditableContext = React.createContext();

class EditableCell extends React.Component {
	getInput = () => {
		if (this.props.inputType === 'number') {
			return <InputNumber />;
		}
		return <Input />;
	};

	renderCell = ({ getFieldDecorator }) => {
		const { editing, dataIndex, title, inputType, record, index, children, ...restProps } = this.props;
		return (
			<td {...restProps}>
				{editing ? (
					<Form.Item style={{ margin: 0 }}>
						{getFieldDecorator(dataIndex, {
							rules: [
								{
									required: true,
									message: `Please Input ${title}!`
								}
							],
							initialValue: record[dataIndex]
						})(this.getInput())}
					</Form.Item>
				) : (
					children
				)}
			</td>
		);
	};

	render() {
		return <FooterEditableContext.Consumer>{this.renderCell}</FooterEditableContext.Consumer>;
	}
}

class FooterEditableFormTable extends React.Component {
	// static getDeriveStateFromProps(){
	// 	console.log("======")
	// }
	constructor(props) {
		super(props);
		this.handleClick = (e, text, mes, index) => {
			props.handleEdit && props.handleEdit(mes, index, e);
			// props.listReq()
		}
		
		this.state = {
			data: [
				{
					index: '01',
					grade: '紧急告警',
					cycle: 0,
					countDown: 0
				},
				{
					index: '02',
					grade: '重要告警',
					cycle: 0,
					countDown: 0
				},
				{
					index: '03',
					grade: '次要告警',
					cycle: 0,
					countDown: 0
				},
				{
					index: '04',
					grade: '告警提示',
					cycle: 0,
					countDown: 0
				}
			],
			editingKey: ''
		};
		this.columns = [
			{
				title: '序号',
				dataIndex: 'index',
				width: '11%',
				editable: true,
				align: 'center'
			},
			{
				title: '级别',
				dataIndex: 'grade',
				width: '15%',
				editable: true,
				align: 'center'
			},
			{
				title: '推送条件',
				dataIndex: 'cycle',
				width: '30%',
				editable: true,
				align: 'center',
				render:(d)=>{
					return d+"分钟"
				}
			},
			{
				title: '升级条件',
				dataIndex: 'countDown',
				width: '30%',
				editable: true,
				align: 'center',
				render:(d)=>{
					return d+'分钟'
				}
			},
			{
				title: '操作',
				align: 'center',
				dataIndex: 'operation',
				render: (...value) => {
					return (
						<div className="table-edit-box">
							<div className="table-icon-box">
								<MIcon
									type="form"
									onClick={e => {
										this.handleClick(e, ...value);
									}}
								/>
								<MIcon type="delete" onClick={
									()=>{
										if(!value[1].id){
											return
										}
										$.post('/mao/upgrades/remove',{ids:value[1].id},(results)=>{
											if(results.code === 0){
												message.success(results.msg,0.5)
												this.props.listReq&& this.props.listReq()
												// console.log(this.props,'--------')
												// this.props && this.props.listReq()
											}else{
												message.error(results.msg,0.5);
												// this.setState({editingKey:''})
												 this.props.listReq&& this.props.listReq()
												// this.props && this.props.listReq()
											}
										})
									}
								} />
							</div>
						</div>
					);
				}
			}
		];
	}
	componentDidMount(){
		let d =  this.state.data;
	}
	componentWillReceiveProps(){
		this.handleProps()
	}
	componentWillUpdate(p,s){
		let d =  this.state.data;
		// let isUpdata = false;
		// !_.isEmpty(d) && d.forEach((e,i)=>{
		// 	Object.keys(s.data).forEach((item,index)=>{
		// 		if( !(s[item]== e[item]) ){
		// 			isUpdata = true
		// 		}
		// 	})
		// })
		// console.log(d,s.data)
		// // console.log(s,'value')
		// if(isUpdata){
			PubSub.publish('initData',d);
		// }
	}
	
	isEditing = record => record.key === this.state.editingKey;

	cancel = () => {
		this.setState({ editingKey: '' });
	};

	handleProps =()=>{
		let p = this.props;
		let data = [
			{
				index: '01',
				grade: '紧急告警',
				cycle: 0,
				countDown: 0
			},
			{
				index: '02',
				grade: '重要告警',
				cycle: 0,
				countDown: 0
			},
			{
				index: '03',
				grade: '次要告警',
				cycle: 0,
				countDown: 0
			},
			{
				index: '04',
				grade: '告警提示',
				cycle: 0,
				countDown: 0
			}
		]
		!_.isEmpty(p.rowData) & p.rowData.forEach(e=>{
			//拿到数据 确定是几级告警;
			let gra = data[e.grade]
			if(gra===undefined ){
				return	
			}
			gra.cycle = e.cycle ? e.cycle: 0;
			gra.countDown = e.countDown ? e.countDown : 0;
			gra.id =e.id;
		})

		this.setState({
			data
		})
		// PubSub.publish('initData',data);

	}
	render() {
		const components = {
			body: {
				cell: EditableCell
			}
		};

		const columns = this.columns.map(col => {
			if (!col.editable) {
				return col;
			}
			return {
				...col,
				onCell: record => ({
					record,
					inputType: col.dataIndex === 'age' ? 'number' : 'text',
					dataIndex: col.dataIndex,
					title: col.title,
					editing: this.isEditing(record)
				})
			};
		});

		return (
			<FooterEditableContext.Provider value={this.props.form}>
				<Table components={components} bordered dataSource={this.state.data} columns={columns} rowClassName="editable-row" pagination={false} />
			</FooterEditableContext.Provider>
		);
	}
}

//头部 列表
function HeaderList(props) {
	return <div className="toll-list-title">{textFormat}无升级策略,告警发生时不进行自助升级</div>;
}
function myButton(props) {
	return <a className="my-button-style">{props.text}</a>;
}
const commitTimeArr = [
	[0, 0],
	[5, '5分钟'],
	[15, '15分钟'],
	[30, '30分钟']
];
//input
let InputCom = function(props) {
	let [value, setValue] = useState(0);
	let { strategy , setStrategy,cycle, countDown , data } = props;
	let key = data === '2'? 'cycle':'countDown';
	useEffect(() => {
		if (data === '2') {
			setValue(cycle);
		} else if (data === '3') {
			setValue(countDown);
		}
	}, [props]);
	let handleChange = function(value) {
		setStrategy({...strategy,[key]:value})
		setValue(value);
	};
	return (
		<div>
			<Select style={{ width: '0.8rem' }} onChange={handleChange} value={strategy[key] ? strategy[key]+"分钟":0} defaultValue={0}>
				{commitTimeArr.map(arr => (
					<Option value={arr[0]} key={arr[1]}>
						{arr[1]}
					</Option>
				))}
			</Select>
		</div>
	);
};
// InputCom = React.memo(InputCom, function(p, nextP) {
// 	console.log(nextP, 'nextP', 'p', p);
// 	return true;
// });
function MyButton(props) {
	return (
		<a className="My-button-style" {...props}>
			{props.text}
		</a>
	);
}
FooterEditableFormTable = Form.create()(FooterEditableFormTable); 

// Tablist
function EditableFormTable(props) {
	let [initValue, setInitValue] = useState(0);
	let [data, setData] = React.useState([
		{
			key: '1',
			name: '告警级别:',
			money: '1'
		},
		{
			key: '2',
			name: '推送周期:',
			money: '2'
		},
		{
			key: '3',
			name: '升级计时:',
			money: '3'
		}
	]);
	let [strategy, setStrategy] = React.useState({ grade: '', countDown: 0, cycle: 0 });
	let [rowData,setRowData] = useState([]);
	let [isAdd,setIsAdd] = useState(true)
	let [isRadio,setIsRadio] = useState([true,true,true,true])
	const radio = ['紧急', '重要', '次要 ', '提示'];
	function handleChange(e) {
		let grade= e.target.value;
		// let cycle  = rowData[grade].cycle;
		// let countDown  = rowData[grade].countDown;
		PubSub.unsubscribe(window.PubSubx);
		window.PubSubx = PubSub.subscribe('initData', function(m,d){
			let target = d[grade];
			if(isAdd === (target.cycle== 0 && target.countDown==0)){
					return
			}
			setIsAdd(target.cycle== 0 && target.countDown==0); 
		})
		setStrategy({...strategy,grade});
	}
	const columns = [
		{
			title: 'Name',
			dataIndex: 'name',
			align: 'center',
			width: '0.80rem',
			height: '0.2rem'
		},
		{
			title: 'Cash Assets',
			className: 'column-content',
			dataIndex: 'money',
			render: data => {
				if (data === '1') {
					return (
						<Radio.Group name="radiogroup" onChange={handleChange} value={strategy.grade}>
							{radio.map((item, index) => (
								// 
								<Radio disabled={!isRadio[index]} key={index} value={index} size="large" style={{ fontSize: '0.1rem', color: '#999' }}>
									{item}
								</Radio>
							))}
						</Radio.Group>
					);
				} else {
					return <InputCom setStrategy={setStrategy} strategy={strategy} data={data}></InputCom>;
				}
			}
		}
	];
	function listReq(){
		$.post('/mao/upgrades/list',(results)=>{
			if(Array.isArray(results.rows)){
				let isRadio = [true,true,true,true];
				// console.log(results.rows,'xxxxxxxxxxxxx')
				results.rows.forEach((e)=>{
					isRadio[e.grade] = false
				})
				// debugger
				setRowData(results.rows);
				setIsRadio(isRadio);
				// let suObj = results.rows[0];
				// let {grade,cycle,countDown }  = suObj;
					// setIsAdd( countDown == 0 && cycle ==0)
				let obj = {
					...strategy,
				// 	grade:grade,
				// 	cycle:cycle,
				// 	countDown:countDown
				}
				setStrategy({...strategy,...obj})
			}
		})
	}
	useEffect(() => {
		listReq()
		return () => {};
	}, []);
	let handleClick = () => {
		if(!(typeof strategy.grade === 'number')){
			message.error('没有grade')
			return 
		}
		
		// console.log(this)
		// let  reset =()=>{props.listReq && props.listReq()}
		if (isAdd){
			$.post('/mao/upgrades/addDo',strategy,function(results){
				if(results.code === 0){
					message.success(results.msg,0.5)
					// reset()
					// console.log(props)
					listReq()
				}else{
					message.error(results.msg,0.5)
					// reset()
					listReq()
				}
			})
		}else{
			$.post('/mao/upgrades/editDo',strategy,function(results){
				if(results.code === 0){
					message.success(results.msg,0.5)
					listReq()
					
				}else{
					message.error(results.msg,0.5)
					listReq()
				}
			})
		}
		return 
	};
	let handleEdit = (mes, index, e) => {
		console.log({...mes},'msg')
		setInitValue(index);
		let filterMes = {...mes};
		setStrategy({ ...strategy,...filterMes, grade: index });
		// console.log({ ...strategy,...filterMes, grade: index },'===')
	};
	let headerFn = () => {
		let { isUpgradeFn } = props;
		return (
			<div className="table-title-content">
				<div className="table-title-text">{textFormat}添加升级策略</div>
				<MIcon
					onClick={
						isUpgradeFn &&
						function() {
							isUpgradeFn(arguments);
						}
					}
					style={{ fontSize: '0.2rem' }}
					type="close-circle"
					theme="twoTone"
				/>
			</div>
		);
	};
	let footerFn = () => {
		return (
			<div className="tab-footer-box">
				<div className="tab-button-box">
					<Button type="primary" shape="round" size={'large'} onClick={handleClick}>
						{isAdd ? "添加" : "修改"}
					</Button>
					{/* <Button shape="round" size={'large'}>
						取消
					</Button> */}
				</div>
			</div>
		);
	};
	return (
		<div className="middle-table">
			<div className="middle-table-box">
				<Table pagination={false} columns={columns} dataSource={data} bordered title={headerFn} footer={footerFn} pagination={false} />
			</div>
			<div className="footer-table-box">
				<FooterEditableFormTable  setIsAdd={function(s){setIsAdd(s)}} listReq={listReq} rowData={rowData} handleEdit={handleEdit} className="footer-table" />
			</div>
		</div>
	);
}

class Toll extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			isUpgrade: true
		};
	}
	componentDidMount(){
		this.init()
	}
	init = () => {
	
	};
	handleClick = () => {};
	isUpgradeFn = () => {
		this.setState({ isUpgrade: !this.isUpgradeFn });
	};
	render() {
		let { isUpgrade } = this.state;
		return (
			<div>
				<div className="bottom-margin">
					<Button
						className="title-button"
						onClick={() => {
							this.setState({
								isUpgrade: true
							});
						}}
					>
						+添加升级策略
					</Button>
				</div>

				<div>
					<div className="bottom-margin">
						<HeaderList />
					</div>
					{isUpgrade ? (
						<div className="bottom-margin">
							<EditableFormTable isUpgradeFn={this.isUpgradeFn} />
						</div>
					) : (
						''
					)}
				</div>
			</div>
		);
	}
}
ReactDOM.render(<Toll />, document.querySelector('#toll-list-box'));
