var domId = '';
var scripts = document.getElementsByTagName('script');
for (var i = 0; i < scripts.length; i++) {
	var script = scripts[i];
	if (script && script.getAttribute('type') == 'text/babel') {
		domId = script.getAttribute('domId');
	}
}
let {
	Steps,
	Button,
	message,
	Input,
	Descriptions,
	Upload,
	Icon,
	Cascader,
	Table,
	Popconfirm,
	Form,
	Radio
} = antd;
const { Step } = Steps;
function submitHandler() {
	console.log('--点击确定--');
}
function cancelHandler() {
	console.log('--点击取消--');
	return true;
}
class BasicInformationUpload extends React.PureComponent {
	state = {
		fileList: []
	};
	handleChange = info => {
		let fileList = [...info.fileList];

		// 1. Limit the number of uploaded files
		// Only to show two recent uploaded files, and old ones will be replaced by the new
		fileList = fileList.slice(-1);

		// 2. Read from response and show file link
		fileList = fileList.map(file => {
			if (file.response) {
				// Component will show file.url as link
				file.url = file.response.url;
			}
			return file;
		});

		this.setState({ fileList });
		if (info.file.status !== 'uploading') {
			console.log(info.file, info.fileList);
		}
		if (info.file.status === 'done') {
			message.success(`${info.file.name} 文件上传成功`);
		} else if (info.file.status === 'error') {
			message.error(`${info.file.name} 文件上传失败`);
		}
	};
	render() {
		const props = {
			name: 'file',
			// action:
			// 	'https://www.mocky.io/v2/5cc8019d300000980a055e76',
			// headers: {
			// 	authorization: 'authorization-text'
			// },
			onChange: this.handleChange
		};
		return (
			<Upload
				{...props}
				className="upload"
				fileList={this.state.fileList}
			>
				<Button>
					<Icon type="upload" /> 点击上传
				</Button>
			</Upload>
		);
	}
}
class BasicInformationCascader extends React.PureComponent {
	render() {
		const options = [
			{
				value: '0.7686050876320942',
				label: '分类一',
				children: [
					{
						value: 'hangzhou',
						label: '子类一',
						children: [
							{
								value: '0.6497000345160961',
								label: '品牌一',
								children: [
									{
										value: '0.12439953739115461',
										label: '型号一'
									},
									{
										value: '0.1911278735970452',
										label: '型号二'
									}
								]
							}
						]
					}
				]
			},
			{
				value: '0.5416814158011563',
				label: '分类二',
				children: [
					{
						value: '0.9090047796735239',
						label: '子类一',
						children: [
							{
								value: '0.7937843230672577',
								label: '品牌一',
								children: [
									{
										value: '0.7971342102079821',
										label: '型号三'
									},
									{
										value: '0.06762476699147046',
										label: '型号四'
									}
								]
							},
							{
								value: '0.5549236152120482',
								label: '品牌二',
								children: [
									{
										value: '0.9267693982102065',
										label: '型号五'
									},
									{
										value: '0.8490652019739944',
										label: '型号六'
									}
								]
							}
						]
					},
					{
						value: '0.4772728975306575',
						label: '子类二',
						children: [
							{
								value: '0.7045117916921957',
								label: '品牌一',
								children: [
									{
										value: '0.061172155525954786',
										label: '型号七'
									},
									{
										value: '0.7963683741000656',
										label: '型号八'
									}
								]
							}
						]
					}
				]
			}
		];
		return (
			<Cascader
				options={options}
				placeholder="请选择"
				style={{
					width: '100%'
				}}
			/>
		);
	}
}
class BasicInformation extends React.PureComponent {
	render() {
		let { show, title } = this.props;
		return (
			<div
				className="drive-add-basic-information-box"
				style={{
					display: show ? 'block' : 'none'
				}}
			>
				<div className="title">{title}</div>
				<Descriptions
					column={1}
					// title="User Info"
					bordered
					className="descriptions"
				>
					<Descriptions.Item label="驱动文件：">
						<BasicInformationUpload />
					</Descriptions.Item>
					<Descriptions.Item label="驱动名称：">
						<Input placeholder="请输入" />
					</Descriptions.Item>
					<Descriptions.Item label="版本号：" span={3}>
						<Input placeholder="请输入" />
					</Descriptions.Item>
					<Descriptions.Item label="采集设备信息：">
						<BasicInformationCascader />
					</Descriptions.Item>
				</Descriptions>
			</div>
		);
	}
}

const EditableContext = React.createContext();

const EditableRow = ({ form, index, ...props }) => (
	<EditableContext.Provider value={form}>
		<tr {...props} />
	</EditableContext.Provider>
);

const EditableFormRow = Form.create()(EditableRow);

class EditableCell extends React.PureComponent {
	state = {
		editing: false
	};

	toggleEdit = () => {
		const editing = !this.state.editing;
		this.setState({ editing }, () => {
			if (editing) {
				this.input.focus();
			}
		});
	};

	save = e => {
		const { record, handleSave } = this.props;
		this.form.validateFields((error, values) => {
			if (error && error[e.currentTarget.id]) {
				return;
			}
			this.toggleEdit();
			handleSave({ ...record, ...values });
		});
	};

	renderCell = form => {
		this.form = form;
		const { children, dataIndex, record, title } = this.props;
		const { editing } = this.state;
		return editing ? (
			<Form.Item style={{ margin: 0 }}>
				{form.getFieldDecorator(dataIndex, {
					rules: [
						// {
						// 	required: true,
						// 	message: `${title} 不能为空`
						// }
					],
					initialValue: record[dataIndex]
				})(
					<Input
						ref={node => (this.input = node)}
						onPressEnter={this.save}
						onBlur={this.save}
					/>
				)}
			</Form.Item>
		) : (
			<div
				className="editable-cell-value-wrap"
				style={{ paddingRight: 24 }}
				onClick={this.toggleEdit}
			>
				{children}
			</div>
		);
	};

	render() {
		const {
			editable,
			dataIndex,
			title,
			record,
			index,
			handleSave,
			children,
			...restProps
		} = this.props;
		return (
			<td {...restProps}>
				{editable ? (
					<EditableContext.Consumer>
						{this.renderCell}
					</EditableContext.Consumer>
				) : (
					children
				)}
			</td>
		);
	}
}
class EditableTableRadio extends React.PureComponent {
	state = {
		value: '否'
	};
	componentWillMount() {
		let { data = {} } = this.props;
		this.setState({
			value: data['告警使能']
		});
	}
	render() {
		let { data = {} } = this.props;
		return (
			<Radio.Group
				onChange={e => {
					this.setState(
						{
							value: e.target.value
						},
						() => {
							data['告警使能'] = e.target.value;
						}
					);
				}}
				// defaultValue={data['告警使能']}
				value={this.state.value}
				style={{
					display: 'flex'
				}}
			>
				<Radio value={'是'}>是</Radio>
				<Radio value={'否'}>否</Radio>
			</Radio.Group>
		);
	}
}

class EditableTable extends React.PureComponent {
	constructor(props) {
		super(props);
		this.columns = [
			{
				title: '指标项中文简称',
				dataIndex: '指标项中文简称',
				width: '30%',
				editable: true
			},
			{
				title: '指标项英文简称',
				width: '30%',
				dataIndex: '指标项英文简称',
				editable: true
			},
			{
				title: '操作码',
				dataIndex: '操作码',
				width: '10%',
				editable: true
			},
			{
				title: '单位',
				dataIndex: '单位',
				width: '10%',
				editable: true
			},
			{
				title: '告警使能',
				width: '10%',
				dataIndex: '告警使能',
				render: (text, record) => {
					return <EditableTableRadio data={record} />;
				}
			},
			{
				title: '操作',
				width: '10%',
				dataIndex: 'operation',
				render: (text, record) =>
					this.state.dataSource.length >= 1 ? (
						<Popconfirm
							title="确定删除?"
							onConfirm={() => this.handleDelete(record.key)}
						>
							<a href="javascript:;">删除</a>
						</Popconfirm>
					) : null
			}
		];

		this.state = {
			dataSource: []
		};
	}

	handleDelete = key => {
		const dataSource = [...this.state.dataSource];
		this.setState({
			dataSource: dataSource.filter(item => item.key !== key)
		});
	};

	handleAdd = () => {
		const { dataSource } = this.state;
		let key = Math.random();
		const newData = {
			key,
			指标项中文简称: '',
			指标项英文简称: '',
			操作码: '',
			单位: '',
			告警使能: '否'
		};
		this.setState({
			dataSource: [newData, ...dataSource]
		});
	};

	handleSave = row => {
		const newData = [...this.state.dataSource];
		const index = newData.findIndex(item => row.key === item.key);
		const item = newData[index];
		newData.splice(index, 1, {
			...item,
			...row
		});
		this.setState({ dataSource: newData });
	};
	onExportExcel(headers, data, fileName = '指标项.xlsx') {
		const _headers = headers
			.map((item, i) =>
				Object.assign(
					{},
					{
						key: item.dataIndex,
						title: item.title,
						position: String.fromCharCode(65 + i) + 1
					}
				)
			)
			.reduce(
				(prev, next) =>
					Object.assign({}, prev, {
						[next.position]: { key: next.key, v: next.title }
					}),
				{}
			);

		const _data = data
			.map((item, i) =>
				headers.map((item2, j) =>
					Object.assign(
						{},
						{
							content: item[item2.dataIndex],
							position: String.fromCharCode(65 + j) + (i + 2)
						}
					)
				)
			)
			// 对刚才的结果进行降维处理（二维数组变成一维数组）
			.reduce((prev, next) => prev.concat(next))
			// 转换成 worksheet 需要的结构
			.reduce(
				(prev, next) =>
					Object.assign({}, prev, {
						[next.position]: { v: next.content }
					}),
				{}
			);
		// 合并 headers 和 data
		const output = Object.assign({}, _headers, _data);
		// 获取所有单元格的位置
		const outputPos = Object.keys(output);
		// 计算出范围 ,["A1",..., "H2"]
		const ref = `${outputPos[0]}:${outputPos[outputPos.length - 1]}`;

		// 构建 workbook 对象
		const wb = {
			SheetNames: ['Sheet1'],
			Sheets: {
				Sheet1: Object.assign({}, output, {
					'!ref': ref,
					'!cols': [
						{ wpx: 200 },
						{ wpx: 300 },
						{ wpx: 300 },
						{ wpx: 200 },
						{ wpx: 150 }
					]
				})
			}
		};

		// 导出 Excel
		XLSX.writeFile(wb, fileName);
	}

	onImportExcel = file => {
		// 获取上传的文件对象
		const { files } = file.target;
		// 通过FileReader对象读取文件
		const fileReader = new FileReader();
		fileReader.onload = event => {
			try {
				const { result } = event.target;
				// 以二进制流方式读取得到整份excel表格对象
				const workbook = XLSX.read(result, { type: 'binary' });
				// 存储获取到的数据
				let data = [];
				// 遍历每张工作表进行读取（这里默认只读取第一张表）
				for (const sheet in workbook.Sheets) {
					// esline-disable-next-line
					if (workbook.Sheets.hasOwnProperty(sheet)) {
						// 利用 sheet_to_json 方法将 excel 转成 json 数据
						data = data.concat(
							XLSX.utils.sheet_to_json(workbook.Sheets[sheet])
						);
						// break; // 如果只取第一张表，就取消注释这行
					}
				}
				// 最终获取到并且格式化后的 json 数据
				message.success('导入成功！');
				let dataSource = [];
				data.map((item, index) => {
					dataSource.push({ ...item, key: index });
				});
				this.setState({
					dataSource
				});
				// console.log(data);
			} catch (e) {
				// 这里可以抛出文件类型错误不正确的相关提示
				message.error('文件类型不正确！');
			}
		};
		// 以二进制方式打开文件
		fileReader.readAsBinaryString(files[0]);
	};
	render() {
		const { dataSource = [] } = this.state;

		const components = {
			body: {
				row: EditableFormRow,
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
					editable: col.editable,
					dataIndex: col.dataIndex,
					title: col.title,
					handleSave: this.handleSave
				})
			};
		});
		return (
			<div className="editable-table-box">
				<div
					className="editable-table-content"
					style={{
						margin: '16px 0',
						display: 'flex',
						justifyContent: 'space-between'
					}}
				>
					<Button onClick={this.handleAdd} type="primary">
						新增
					</Button>
					<div className="right-btn-box">
						<Button className={'upload-wrap'}>
							<input
								className={'file-uploader'}
								type="file"
								accept=".xlsx, .xls"
								onChange={this.onImportExcel}
							/>
							<span className={'upload-text'}>导入</span>
						</Button>
						<Button
							onClick={() => {
								if (dataSource.length < 1) {
									message.info('未选择下载数据');
									return;
								}
								let columns_new = columns.filter(
									(item, index, _d) => index != _d.length - 1
								);
								this.onExportExcel(
									columns_new,
									dataSource,
									'测试指标项.xlsx'
								);
							}}
						>
							导出
						</Button>
						<Button>模板下载</Button>
					</div>
				</div>
				<Table
					components={components}
					rowClassName={() => 'editable-row'}
					bordered
					dataSource={dataSource}
					columns={columns}
					pagination={{
						simple: true
					}}
				/>
			</div>
		);
	}
}

class AlarmConfiguration extends React.PureComponent {
	render() {
		let { show, title, data = [] } = this.props;
		let columns = [
			{
				title: '指标项中文简称',
				dataIndex: '指标项中文简称',
				width: '30%',
				editable: true
			},
			{
				title: '指标项英文简称',
				width: '30%',
				dataIndex: '指标项英文简称',
				editable: true
			},
			{
				title: '操作码',
				dataIndex: '操作码',
				width: '10%',
				editable: true
			},
			{
				title: '单位',
				dataIndex: '单位',
				width: '10%',
				editable: true
			},
			{
				title: '告警使能',
				width: '10%',
				dataIndex: '告警使能'
			},
			{
				title: '告警配置操作',
				width: '10%',
				dataIndex: 'operation',
				render: () => {
					return (
						<Button
							onClick={() => {
								console.log('---');
							}}
						>
							配置
						</Button>
					);
				}
			}
		];
		return (
			<div
				className="drive-add-indicators-box"
				style={{
					display: show ? 'block' : 'none'
				}}
			>
				<div className="title">{title}</div>
				<Table columns={columns} dataSource={data} />
			</div>
		);
	}
}
class Indicators extends React.PureComponent {
	render() {
		let { show, title, onRef } = this.props;
		return (
			<div
				className="drive-add-indicators-box"
				style={{
					display: show ? 'block' : 'none'
				}}
			>
				<div className="title">{title}</div>

				<EditableTable ref={el => onRef(el)} />
			</div>
		);
	}
}
class AddBox extends React.PureComponent {
	constructor(props) {
		super(props);
		this.state = {
			current: 0
		};
	}
	next() {
		const current = this.state.current + 1;
		this.setState({ current });
	}

	prev() {
		const current = this.state.current - 1;
		this.setState({ current });
	}

	render = () => {
		const steps = [
			{
				title: '基本信息'
			},
			{
				title: '添加指标项'
			},
			{
				title: '告警配置'
			}
		];
		const { current } = this.state;
		return (
			<div className="drive-add-body">
				<div className="steps-content">
					<Steps current={current}>
						{steps.map((item, index) => (
							<Step
								key={item.title}
								title={item.title}
								icon={<span>{index + 1}</span>}
							/>
						))}
					</Steps>
					<div className="steps-content-body">
						<BasicInformation
							title={steps[current].title}
							show={steps[current].title == '基本信息'}
						/>
						<Indicators
							onRef={el => (this.indicators = el)}
							title={steps[current].title}
							show={steps[current].title == '添加指标项'}
						/>
						{this.indicators && (
							<AlarmConfiguration
								title={steps[current].title}
								show={steps[current].title == '告警配置'}
								data={this.indicators.state.dataSource || []}
							/>
						)}
					</div>
				</div>
				<div className="steps-action">
					{current > 0 && (
						<Button
							style={{
								width: 180,
								height: 40,
								marginRight: 20
							}}
							onClick={() => this.prev()}
						>
							上一步
						</Button>
					)}
					{current < steps.length - 1 && (
						<Button
							style={{ width: 180, height: 40 }}
							type="primary"
							onClick={() => {
								let dataSource =
									this.indicators.state.dataSource || [];
								if (
									dataSource.length < 1 &&
									steps[current].title == '添加指标项'
								) {
									message.info('未添加数据');
									return;
								}
								this.next();
							}}
						>
							下一步
						</Button>
					)}
					{current === steps.length - 1 && (
						<Button
							style={{ width: 180, height: 40 }}
							type="primary"
							onClick={() => message.success('操作完成')}
						>
							完成
						</Button>
					)}
				</div>
			</div>
		);
	};
}
if (domId) {
	ReactDOM.render(<AddBox />, document.getElementById(domId));
} else {
	console.log('domId 未输入');
}
