let { Button, PageHeader, List, Avatar, Table, Input, InputNumber, Popconfirm, Form } = antd;
console.log(window.myComponent);
let { Mbutton } = myComponent;
const data = [];
for (let i = 0; i < 100; i++) {
	data.push({
		key: i.toString(),
		name: `Edrward ${i}`,
		age: 32,
		address: `London Park no. ${i}`
	});
}

const EditableContext = React.createContext();
let { useEffect, useState } = React;

//头部 列表
function HeaderList(props) {
	return (
		<PageHeader
			style={{
				border: '1px solid rgb(235, 237, 240)'
			}}
			onBack={() => null}
			title="敲钟列表"
			subTitle="<button>按钮</button>"
		>
			<Mbutton>123</Mbutton>
			<myButton>全部确认</myButton>
		</PageHeader>
	);
}
function myButton(props) {
	return <a className="my-button">{props.text}</a>;
}

// Tablist
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
		return <EditableContext.Consumer>{this.renderCell}</EditableContext.Consumer>;
	}
}

class EditableTable extends React.Component {
	constructor(props) {
		super(props);
		this.state = { data, editingKey: '' };
		this.columns = [
			{
				title: 'name',
				dataIndex: 'name',
				width: '25%',
				editable: true
			},
			{
				title: 'age',
				dataIndex: 'age',
				width: '15%',
				editable: true
			},
			{
				title: 'address',
				dataIndex: 'address',
				width: '40%',
				editable: true
			},
			{
				title: 'operation',
				dataIndex: 'operation',
				render: (text, record) => {
					const { editingKey } = this.state;
					const editable = this.isEditing(record);
					return editable ? (
						<span>
							<EditableContext.Consumer>
								{form => (
									<a onClick={() => this.save(form, record.key)} style={{ marginRight: 8 }}>
										Save
									</a>
								)}
							</EditableContext.Consumer>
							<Popconfirm title="Sure to cancel?" onConfirm={() => this.cancel(record.key)}>
								<a>Cancel</a>
							</Popconfirm>
						</span>
					) : (
						<a disabled={editingKey !== ''} onClick={() => this.edit(record.key)}>
							Edit
						</a>
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
			<EditableContext.Provider value={this.props.form}>
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
			</EditableContext.Provider>
		);
	}
}

const EditableFormTable = Form.create()(EditableTable);
class Toll extends React.PureComponent {
	// let [x, setX] = React.useContext(0
	render() {
		return (
			<div>
				<HeaderList />
				<EditableFormTable />;
			</div>
		);
	}
}
ReactDOM.render(<Toll />, document.querySelector('#toll-list-box'));
