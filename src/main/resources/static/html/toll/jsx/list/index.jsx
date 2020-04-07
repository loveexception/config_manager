let { Select, Radio, Button, Icon, Popconfirm, PageHeader, List, Avatar, Table, Input, InputNumber, Form, message } = antd;
const { Option } = Select;
let MIcon = function (props) {
	//重写 Icon  字体大小保持一直 样式 公共设置 等
	return <Icon style={{ fontSize: '0.12rem' }} {...props} />;
};
// let isAdd = true;

// const data = [];
const textFormat = <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>;
const reqConfig = ['warning', 'major', 'minor', 'critical'];
const EditableContext = React.createContext();
let { useEffect, useState } = React;
let setInitValue = () => { };
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

class FooterEditableFormTable extends React.PureComponent {
	// static getDeriveStateFromProps(){
	// }
	constructor(props) {
		super(props);
		this.handleClick = (e, text, mes, index) => {
			props.isUpgradeFn(true)
			props.handleEdit && props.handleEdit(mes, index, e);
			// props.listReq()
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
				align: 'center',
				render: d => {
					/*
					[
						[-1, "不推送", "不升级"],
						[0, "立刻", "立刻"],
						[2*60, "2分钟", "2分钟"],
						[5*60, "5分钟", "5分钟"],
						[10*60, "10分钟", "10分钟"],
						[15*60, "15分钟", "15分钟"],
						[30*60, "30分钟", "30分钟"],
						[8*60*60, '8小时后', '8小时后'],
						[24*60*60, '24小时后', '24小时后'],
						[1*60, '1分钟后', '1分钟后'],
					];
										
					*/

					let result = commitTimeArr.find(e => {
						return e[0] == d;
					});
					result = result === undefined ? '无' : result[1];
					return result;
				}
			},
			{
				title: '升级条件',
				dataIndex: 'countDown',
				width: '30%',
				editable: true,
				align: 'center',
				render: d => {
					let result = commitTimeArr.find(e => {
						return e[0] == d;
					});
					result = result === undefined ? '无' : result[2];
					return result;
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
								<Popconfirm
									placement="top"
									title={'你确定要清除这条数据吗?'}
									onConfirm={() => {
										if (!value[1].id) {
											return;
										}
										$.post('/mao/upgrades/remove', { ids: value[1].id }, results => {
											if (results.code === 0) {
												message.success(results.msg, 0.5);
												this.props.listReq && this.props.listReq();
												// this.props && this.props.listReq()
												this.props.setIsAdd(true);
											} else {
												message.error(results.msg, 0.5);
												// this.setState({editingKey:''})
												this.props.listReq && this.props.listReq();
												this.props.setIsAdd(false);
											}
										});
									}}
									okText="是的"
									cancelText="取消"
								>
									<MIcon type="delete" />
								</Popconfirm>
							</div>
						</div>
					);
				}
			}
		];
	}
	componentDidMount() { }
	componentWillReceiveProps() {
		this.handleProps();
	}
	componentWillUpdate(p, s) {
		let d = this.state.data;
		PubSub.publish('initData', d);
		// let isUpdata = false;
		// !_.isEmpty(d) && d.forEach((e,i)=>{
		// 	Object.keys(s.data).forEach((item,index)=>{
		// 		if( !(s[item]== e[item]) ){
		// 			isUpdata = true
		// 		}
		// 	})
		// })
		// if(isUpdata){
		// }
	}

	isEditing = record => record.key === this.state.editingKey;

	cancel = () => {
		this.setState({ editingKey: '' });
	};

	handleProps = () => {
		let p = this.props;
		let data = [
			{
				index: '01',
				grade: '紧急告警',
				cycle: '不升级',
				countDown: '不升级'
			},
			{
				index: '02',
				grade: '重要告警',
				cycle: '不升级',
				countDown: '不升级'
			},
			{
				index: '03',
				grade: '次要告警',
				cycle: '不升级',
				countDown: '不升级'
			},
			{
				index: '04',
				grade: '告警提示',
				cycle: '不升级',
				countDown: '不升级'
			}
		];
		!_.isEmpty(p.rowData) &
			p.rowData.forEach(e => {
				//拿到数据 确定是几级告警;
				let gra = data[e.grade];
				if (gra === undefined) {
					return;
				}
				gra.cycle = e.cycle ? e.cycle : 0;
				gra.countDown = e.countDown ? e.countDown : 0;
				gra.id = e.id;
				gra.deptId = e.deptId;
			});

		this.setState({
			data
		});
		// PubSub.publish('initData',data);
	};
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
	[-1, '不推送', '不升级'],
	[0, '立刻', '立刻'],
	[2 * 60, '2分钟', '2分钟'],
	[5 * 60, '5分钟', '5分钟'],
	[10 * 60, '10分钟', '10分钟'],
	[15 * 60, '15分钟', '15分钟'],
	[30 * 60, '30分钟', '30分钟'],
	[8 * 60 * 60, '8小时后', '8小时后'],
	[24 * 60 * 60, '24小时后', '24小时后'],
	[1 * 60, '1分钟后', '1分钟后']
];
function isValue(str) {
	let res = 0;
	commitTimeArr.forEach((e, i) => {
		let f = e.forEach((item, index) => {
			if (item == str) {
				res = commitTimeArr[i][0];
			}
		});
	});
	return res;
}
//input
let InputCom = function (props) {
	let [value, setValue] = useState(0);
	let { strategy, setStrategy, cycle, countDown, data } = props;
	let key = data === '2' ? 'cycle' : 'countDown';
	useEffect(() => {
		if (data === '2') {
			setValue(cycle);
		} else if (data === '3') {
			setValue(countDown);
		}
	}, [props]);
	let handleChange = function (value) {
		setStrategy({ ...strategy, [key]: value });
		setValue(value);
	};
	return (
		<div>
			<Select style={{ width: '0.8rem' }} onChange={handleChange} value={Number(strategy[key]) ? Number(strategy[key]) : isValue(strategy[key])} defaultValue={0}>
				{commitTimeArr.map((arr, i) => (
					<Option value={arr[0]} key={i + key}>
						{arr[data - 1]}
					</Option>
				))}
			</Select>
		</div>
	);
};
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
	let [strategy, setStrategy] = React.useState({ grade: '', countDown: '不升级', cycle: '不推送' });
	let [rowData, setRowData] = useState([]);
	let [isDisplay, setIsDisplay] = useState(true);
	let [isAdd, setIsAdd] = useState(true);
	let [isRadio, setIsRadio] = useState([true, true, true, true]);
	const radio = ['紧急', '重要', '次要 ', '提示'];
	function handleChange(e) {
		let grade = e.target.value;
		// let cycle  = rowData[grade].cycle;
		// let countDown  = rowData[grade].countDown;
		// console.log(isRadio, '=========', grade, 'grade==');

		PubSub.unsubscribe(window.PubSubx);
		window.PubSubx = PubSub.subscribe('initData', function (m, d) {
			let target = d[grade];
			if (isAdd === (target.cycle == 0 && target.countDown == 0)) {
				return;
			}
		});

		setIsAdd(isRadio[grade] === true);
		setStrategy({ ...strategy, grade });
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
	function listReq() {
		$.post('/mao/upgrades/list', results => {
			if (Array.isArray(results.rows)) {
				let isRadio = [true, true, true, true];
				results.rows.forEach(e => {
					isRadio[e.grade] = false;
				});
				// debugger
				props.upDataIsPolling && props.upDataIsPolling(results.rows.length);
				setRowData(results.rows);
				setIsRadio(isRadio);
				// let suObj = results.rows[0];
				// let {grade,cycle,countDown }  = suObj;
				// setIsAdd( countDown == 0 && cycle ==0)
				let obj = {
					...strategy
					// 	grade:grade,
					// 	cycle:cycle,
					// 	countDown:countDown
				};
				setStrategy({ ...strategy, ...obj });
			}
		});
	}
	useEffect(() => {
		listReq();
		return () => { };
	}, []);
	let handleClick = () => {
		let data = Object.assign(strategy, { level: reqConfig[strategy.grade], countDown: isValue(strategy.countDown), cycle: isValue(strategy.cycle) });
		$.post('/mao/upgrades/addDo', data, function (results) {
			if (results.code === 0) {
				message.success(results.msg, 0.5);
				// reset()
				setIsAdd(false);
				listReq();
			} else {
				message.error(results.msg, 0.5);
				setIsAdd(true);
				listReq();
			}
		});
		// if (!(typeof strategy.grade === 'number')) {
		// 	message.error('没有升级项');
		// 	return;
		// }
		// if (isAdd) {
		// 	if (!isRadio[strategy.grade]) {
		// 		return;
		// 	}
		// } else {
		// 	// if(!strategy.id){
		// 	// 	strategy.grade
		// 	// }
		// 	if (!strategy.id || !strategy.deptId) {
		// 		let obj = Array.isArray(rowData) && rowData.find(e => e.grade == strategy.grade);
		// 		if (!obj) {
		// 			return;
		// 		}
		// 		strategy.id = obj.id;
		// 		strategy.deptId = obj.deptId;
		// 	}
		// 	let data = Object.assign(strategy, { level: reqConfig[strategy.grade] });
		// 	$.post('/mao/upgrades/editDo', data, function(results) {
		// 		if (results.code === 0) {
		// 			message.success(results.msg, 0.5);
		// 			listReq();
		// 		} else {
		// 			message.error(results.msg, 0.5);
		// 			listReq();
		// 		}
		// 	});
		// }
		return;
	};
	let handleEdit = (mes, index, e) => {
		setIsDisplay(false)
		// debugger
		new Promise(async function (resolve, reject) {
			let r = await setInitValue(index);
			resolve(r);
		})
			.then(r => {
				let obj = { ...strategy, ...mes, grade: index };
				obj.countDown = String(obj.countDown);
				setStrategy(obj);
			})
			.then(() => {
				setIsAdd(!mes.id);
			});
	};
	let headerFn = () => {
		let { isUpgradeFn, isUpgrade } = props;
		return (
			<div className="table-title-content">
				<div className="table-title-text">{textFormat}编辑升级策略</div>
				<MIcon
					onClick={
						isUpgradeFn &&
						function () {
							isUpgradeFn(false);
						}
					}
					style={{ fontSize: '0.2rem' }}
					// type={isUpgrade ? 'down-circle' : 'up-circle'}

					// 更改
					type={"close-circle"}

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
						{/* {isAdd ? '添加' : '修改'} */}
					提交
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
				<Table style={{ display: props.isUpgrade === false ? 'none' : 'block' }} pagination={false} columns={columns} dataSource={data} bordered title={headerFn} footer={footerFn} pagination={false} />
			</div>

			{
				<div className="footer-table-box">
					<FooterEditableFormTable
						{...props}
						className="footer-table"
						setIsAdd={function (s) {
							setIsAdd(s);
						}}
						listReq={listReq}
						rowData={rowData}
						handleEdit={handleEdit}
					/>
				</div>
			}
		</div>
	);
}
class Toll extends React.Component {
	constructor(props) {
		super(props);
		this.flag = true;
		this.state = {
			isUpgrade: true,
			isPolling: true
		};
	}
	componentDidMount() {
		this.init();
	}
	init = () => { };
	upDataIsPolling = rowLength => {
		if (this.flag) {
			this.setState({
				isUpgrade: rowLength > 0 ? true : false
			});
			this.flag = false;
		}
		if (rowLength > 0) {
			// this.setState({isUpgrade:true})
			this.setState({ isPolling: false });
		} else {
			// this.setState({isUpgrade:false})
			this.setState({ isPolling: true });
		}
	};
	handleClick = () => { };
	isUpgradeFn = (boolean) => {
		this.setState({ isUpgrade: boolean });
	};
	render() {
		let { isUpgrade } = this.state;
		return (
			<div>
				<div>
					{this.state.isPolling ? (
						<div className="bottom-margin">
							<HeaderList />
						</div>
					) : (
							''
						)}
					<div className="bottom-margin">
						<EditableFormTable upDataIsPolling={this.upDataIsPolling} isUpgrade={isUpgrade} isUpgradeFn={this.isUpgradeFn} />
					</div>
				</div>
			</div>
		);
	}
}
ReactDOM.render(<Toll />, document.querySelector('#toll-list-box'));
