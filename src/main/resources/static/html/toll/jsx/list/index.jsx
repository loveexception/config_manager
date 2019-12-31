let { Select, Radio, Button, Icon, PageHeader, List, Avatar, Table, Input, InputNumber, Popconfirm, Form } = antd;
const { Option } = Select;
let MIcon = function(props) {
	//重写 Icon  字体大小保持一直 样式 公共设置 等
	return <Icon style={{ fontSize: '0.12rem' }} {...props} />;
};

// const data = [];
const textFormat = <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>;

const EditableContext = React.createContext();
let { useEffect, useState } = React;

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
					level: '紧急告警',
					condition: '',
					upgrade: ''
				},
				{
					index: '02',
					level: '重要告警',
					condition: '',
					upgrade: ''
				},
				{
					index: '03',
					level: '次要告警',
					condition: '',
					upgrade: ''
				},
				{
					index: '04',
					level: '告警提示',
					condition: '',
					upgrade: ''
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
				dataIndex: 'level',
				width: '15%',
				editable: true,
				align: 'center'
			},
			{
				title: '推送条件',
				dataIndex: 'condition',
				width: '30%',
				editable: true,
				align: 'center'
			},
			{
				title: '升级条件',
				dataIndex: 'upgrade',
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
				<Table
					components={components}
					bordered
					dataSource={this.state.data}
					columns={columns}
					rowClassName="editable-row"
					pagination={{
						onChange: this.cancel
					}}
				/>
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
	[0, '无'],
	[5, '5分钟'],
	[15, '15分钟'],
	[30, '30分钟']
];
//input
let InputCom = function(props) {
	let [value, setValue] = useState('无');
	let { condition = '', upgrade = '', data = '' } = props;
	useEffect(() => {
		if (data === '2') {
			setValue(condition);
		} else if (data === '3') {
			setValue(upgrade);
		}
	}, [props]);
	let handleChange = function(value) {
		setValue(value);
	};
	return (
		<div>
			<Select style={{ width: '0.8rem' }} onChange={handleChange} value={value} defaultValue={0}>
				{commitTimeArr.map(arr => (
					<Option value={arr[0]} key={arr[1]}>
						{arr[1]}
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
const FooterEditableFormTable = Form.create()(FooterEditableTable);

// Tablist
function EditableFormTable(props) {
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
	let [strategy, setStrategy] = React.useState({ level: 0, upgrade: '', condition: '' });
	const radio = ['紧急', '重要', '次要 ', '提示'];
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
						<Radio.Group name="radiogroup" defaultValue={0}>
							{radio.map((item, index) => (
								<Radio
									onClick={e => {
										console.log(e.target.parent);
									}}
									key={index}
									value={index}
									size="large"
									style={{ fontSize: '0.1rem', color: '#999' }}
								>
									{item}
								</Radio>
							))}
						</Radio.Group>
					);
				} else {
					return <InputCom strategy={strategy} data={data}></InputCom>;
				}
			}
		}
	];

	useEffect(() => {
		return () => {};
	}, [strategy]);
	let handleClick = () => {
		$.get();
	};
	let handleEdit = (mes, index, e) => {
		setStrategy({ ...mes });
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
						添加
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
				<Table pagination={false} columns={columns} dataSource={data} bordered title={headerFn} footer={footerFn} />
			</div>
			<div className="footer-table-box">
				<FooterEditableFormTable handleEdit={handleEdit} className="footer-table" />
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

	init = () => {};
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
