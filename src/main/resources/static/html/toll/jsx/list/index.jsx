let { Select, Radio, Button, Icon, PageHeader, List, Avatar, Table, Input, InputNumber, Popconfirm, Form,message } = antd;
const { Option } = Select;
let MIcon = function(props) {
	//重写 Icon  字体大小保持一直 样式 公共设置 等
	return <Icon style={{ fontSize: '0.12rem' }} {...props} />;
};
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

class FooterEditableTable extends React.Component {
	constructor(props) {
		super(props);
		this.handleClick = (e, text, mes, index) => {
			props.handleEdit && props.handleEdit(mes, index, e);
		};
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
				align: 'center'
			},
			{
				title: '升级条件',
				dataIndex: 'countDown',
				width: '30%',
				editable: true,
				align: 'center'
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
								<MIcon type="delete" />
							</div>
						</div>
					);
				}
			}
		];
	}

	componentWillReceiveProps=()=>{
		this.handleProps()

	}
	isEditing = record => record.key === this.state.editingKey;

	cancel = () => {
		this.setState({ editingKey: '' });
	};

	save(form, key) {
		form.validateFields((error, row) => {
			if (error) {
				return;
			}
			const newData = [...this.state.data];
			const index = newData.findIndex(item => key === item.key);
			if (index > -1) {
				const item = newData[index];
				newData.splice(index, 1, {
					...item,
					...row
				});
				this.setState({ data: newData, editingKey: '' });
			} else {
				newData.push(row);
				this.setState({ data: newData, editingKey: '' });
			}
		});
	}

	edit(key) {
		this.setState({ editingKey: key });
	}
	handleProps =()=>{
		let p = this.props;
		let data = this.state.data;
		!_.isEmpty(p.rowData) & p.rowData.forEach(e=>{
			//拿到数据 确定是几级告警;
			let gra = data[e.grade]
			gra.cycle = e.cycle ? e.cycle+"分钟" : 0;
			gra.countDown = e.countDown ? e.countDown+"分钟" : 0;
			
		})
		this.setState({
			data
		})
	}
	render() {
		// console.log(this.props,"footer props")
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
const FooterEditableFormTable = Form.create()(FooterEditableTable);

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
	let [strategy, setStrategy] = React.useState({ grade: 0, countDown: 0, cycle: 0 });
	let [rowData,setRowData] = useState([])
	let [isAdd,setIsAdd] = useState(true)
	const radio = ['紧急', '重要', '次要 ', '提示'];
	function handleChange(e) {
		console.log(e.target.value,"选项发生改变了")

		setStrategy({...strategy,grade:e.target.value});
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
								<Radio key={index} value={index} size="large" style={{ fontSize: '0.1rem', color: '#999' }}>
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
			if(!_.isEmpty(results.rows)){
				setRowData(results.rows);
				let suObj = results.rows[0];
				let {grade,cycle,countDown }  = suObj 
				let obj = {
					...suObj,
					grade:grade,
					cycle:cycle,
					countDown:countDown
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
		console.log(strategy,'strategy')
		debugger 

		let  reset  =()=>{this.props.listReq && this.props.listReq()}
		if (isAdd){
			$.post('/mao/upgrades/addDo',strategy,function(results){
				if(results.code === 0){
					message.success(results.msg,0.5)
					reset()
				}else{
					message.error(results.msg,0.5)
					reset()
				}
			})
		}else{
			$.post('/mao/upgrades/editDo',strategy,function(results){
				console.log(results,'results')
			})
		}
		// strategy.countDown ?
		// let data = 
		// if(strategy.){
		// }
		// console.log('strategy',strategy)
		
		return 

		
	};
	let handleEdit = (mes, index, e) => {
		setInitValue(index);
		setStrategy({ ...strategy,...mes, grade: index });
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
				<FooterEditableFormTable listReq={listReq} rowData={rowData} handleEdit={handleEdit} className="footer-table" />
			</div>
		</div>
	);
}

class Toll extends React.PureComponent {
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
