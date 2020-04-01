// import React, { PureComponent, useState } from 'react';
// import { Popconfirm, Upload, Form, Message, Icon, Input, InputNumber, Button, Checkbox, Row, Col, AutoComplete, Table } from 'antd';
// import PubSub from 'pubsub-js';

// import './index.less';
let { PureComponent, useState } = React;
let DiagramAction = window.DiagramAction;
let { Popconfirm, Upload, Form, message, Icon, Input, InputNumber, Button, Table } = antd;
let Message = message;
// let PubSub  = pubsub-js
// import {  } from '@/utils/customFunction.jsx';

function toZip(imgSrcList, fileName) {
	let zip = new JSZip(); //实例化一个压缩文件对象
	let imgFolder = zip.folder(fileName); //新建一个图片文件夹用来存放图片，参数为文件名
	for (let i = 0; i < imgSrcList.length; i++) {
		let src = imgSrcList[i];
		let tempImage = new Image();
		tempImage.src = src;
		tempImage.crossOrigin = '*';
		tempImage.onload = () => {
			imgFolder.file(i + 1 + '.jpg', getBase64Image(tempImage), { base64: true });
		};
	}
	setTimeout(() => {
		zip.generateAsync({ type: 'blob' }).then(function (content) {
			saveAs(content, 'zip');
		});
	}, 3000);
}

function getBase64Image(img) {
	let canvas = document.createElement('canvas');
	canvas.width = img.width;
	canvas.height = img.height;
	let ctx = canvas.getContext('2d');
	ctx.drawImage(img, 0, 0, img.width, img.height);
	let ext = img.src.substring(img.src.lastIndexOf('.') + 1).toLowerCase();
	let dataURL = canvas.toDataURL('image/' + ext, [0.0, 1.0]).split(',')[1]; // canvas.toDataURL('image/' + ext);
	return dataURL;
}


const ButtonGroup = Button.Group;
let Search = Input.Search;

let UpDataData = null;
const pageSize = 6;
const align = 'left';

// 删除调用函数
let deleteFn = (arr = undefined, ListFn) => {
	if (!arr) {
		return;
	}
	let reqArr = [];
	Array.isArray(arr) &&
		arr.forEach(e => {
			reqArr.push(e.id);
		});
	DiagramAction.diagramMRmImg({ ids: reqArr }, res => {
		if (res.success) {
			Message.success('操作成功', 0.5);

			typeof ListFn === 'function' && ListFn();
		}
	});
};

// 下载请求 实例
//时间转换函数
function dateUtil(time) {
	let result = time.getFullYear() + '-' + (time.getMonth() - 0 + 1) + '-' + time.getDate() + ' ' + time.getHours() + ':' + time.getMinutes();
	return result;
}
// 下载调用函数
let uploadFn = (arr = undefined, ListFn) => {
	if (!arr) {
		return;
	}
	let reqArr = [];
	Array.isArray(arr) &&
		arr.forEach(e => {
			reqArr.push(e.id);
		});
	// downloadFile('POST', 1, '你好', {})
	DiagramAction.diagramMDownImg({ ids: reqArr }, res => {
		if (res.success) {
			let data = res.data;
			let file_url_list = _.map(data, 'file_url');
			toZip(file_url_list, '系统拓扑图');
		}
	});
};
//数据开始 _EditableTable.state.checkArr
const data = [];
for (let i = 0; i < 100; i++) {
	data.push({
		key: i.toString(),
		name: `Edrward ${i}`,
		age: 32,
		address: `London Park no. ${i}`,
	});
}
const EditableContext = React.createContext();

class EditableCell extends React.Component {
	constructor(props) {
		super(props);
		this.inputRef = React.createRef(null);
		this.state = {
			editing: false,
		};
	}
	getInput = record => {
		return (
			<Input
				ref={this.inputRef}
				onBlur={() => {
					this.props.record.save(this.inputRef.current.input.value, record.key);
				}}
				onPressEnter={() => {
					this.props.record.save(this.inputRef.current.input.value, record.key);
				}}
			/>
		);
	};

	renderCell = ({ getFieldDecorator }) => {
		const { editing, dataIndex, title, inputType, record, index, children, ...restProps } = this.props;

		if (editing) {
			// this.i

			setTimeout(() => {
				if (editing) {
					typeof this.inputRef.current.focus === 'function' && this.inputRef.current.focus();
				}
			}, 0);
		}
		return dataIndex === 'name' ? (
			<td {...restProps}>
				{editing ? (
					<Form.Item style={{ margin: 0 }}>
						{getFieldDecorator(dataIndex, {
							rules: [
								{
									required: true,
									message: `Please Input ${title}!`,
								},
							],
							initialValue: record[dataIndex],
						})(
							<Input
								ref={this.inputRef}
								onBlur={() => {
									this.props.record.save(this.inputRef.current.input.value, record.key);
								}}
								onPressEnter={() => {
									this.props.record.save(this.inputRef.current.input.value, record.key);
								}}
							/>
						)}
					</Form.Item>
				) : (
						children
					)}
			</td>
		) : (
				<td>{children}</td>
			);
	};

	render() {
		return <EditableContext.Consumer>{this.renderCell}</EditableContext.Consumer>;
	}
}

class EditableTable extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			data,
			editingKey: '',
			//check Arr X当前选中的 行
			checkArr: [],
		};
		//ref
		this.rename = React.createRef();
		// this.
		this.columns = [
			{
				title: '文件名',
				dataIndex: 'name',
				width: '25%',
				editable: true,
				align,
				render: (text, record) => {
					return (
						<span
							onClick={() => {
								this.edit(record.key);
							}}
						>
							{text}
						</span>
					);
				},
				// rowKey: (...value) => {
				// 	console.log(value, 'value');
				// },
			},
			{
				title: '大小',
				dataIndex: 'file_size',
				width: '15%',
				align,
				// editable: true,
			},
			{
				title: '修改时间',
				dataIndex: 'mod_time',
				width: '40%',
				// align,
				render(record) {
					return dateUtil(new Date(record));
				},

				// editable: true,
			},
			{
				title: '操作',
				dataIndex: 'operation',
				align: 'center',
				render: (text, record) => {
					const { editingKey } = this.state;
					// const editable = this.isEditing(record);
					this.record = record;
					return (
						<ul>
							<li>
								<div className="svg-content" disabled={editingKey !== ''}>
									<Icon type="eye" onClick={() => {
										$.modal.openFull(record.name, `/html/diagram/view/view.html?id=${record.id}&name=${record.name}`)
										// $.modal.open(record.name + '查看', `/html / diagram / view / view.html ? id = ${ record.id } & name= ${ record.name } `)
										// router.push(`view ? id = ${ record.id }& name=${record.name}`);
									}} />
								</div>
							</li>
							<li>
								<Icon type="cloud-download" onClick={() => {
									uploadFn([record]);
								}} />
								<div
									className="svg-content"

								>
									<span className="iconfont ticobackicon-upload " style={{ fontSize: '0.22rem' }}></span>
								</div>
							</li>
							<li>
								<div
									className="svg-content"
									onClick={() => {
										deleteFn([record], this.props.reqListFn);
									}}
								>
									<Icon type="delete" />
								</div>
							</li>
							{/* <li>
								<div className="svg-content">
									<span
										onClick={e => {
											this.edit(record.key);
											// this.handleClick();
										}}
										ref={this.rename}
										className="iconfont ticobackicon-rename"
									></span>
								</div>
							</li> */}
						</ul>
					);
				},
			},
		];
		// change 分页 pagination
		this.paginationChange = targetCurrent => {
			this.props.reqListFn && this.props.reqListFn(targetCurrent);
		};
	}

	isEditing = record => record.key === this.state.editingKey;
	rowSelection = {
		onChange: (selectedRowKeys, selectedRows, i) => {
			// console.log(SetIsOff, 'SetIsOff', );
			let param = selectedRows.length === 1 ? false : true;
			typeof UpDataData === 'function' && UpDataData(param, selectedRowKeys);
			//这里拿的是引用可以直接修改
			// selectedRows[selectedRowKeys - 1].age = Date.now();
			this.setState({ checkArr: selectedRows }); //genggai
		},
		getCheckboxProps: record => ({
			disabled: record.name === 'Disabled User', // Column configuration not to be checked
			name: record.name,
		}),
	};
	cancel = () => {
		this.setState({ editingKey: '' });
	};

	save = (form, key, xxx) => {
		// console.log(form, key, xxx, '========');
		let name = '';
		if (typeof form === 'string') {
			name = form;
		} else {
			form.validateFields((error, row) => {
				if (error) {
					return;
				}
				name = row.name;
			});
		}

		DiagramAction.diagramRename(
			{
				id: key,
				name,
			},
			res => {
				if (res.success) {
					Message.success('修改成功', 0.5);
					this.props.reqListFn && this.props.reqListFn();
				} else {
					Message.error(res.data, 0.5);
				}
			}
		);
		const newData = [...this.state.data];
		const index = newData.findIndex(item => key === item.key);
		if (index > -1) {
			const item = newData[index];
			newData.splice(index, 1, {
				...item,
				// ...row,
				name,
			});
			this.setState({ data: newData, editingKey: '' });
		} else {
			newData.push(name);
			this.setState({ data: newData, editingKey: '' });
		}
	};
	edit(key) {
		this.setState({ editingKey: key }, () => {
			// if (this.state.editing) {
			// }
		});
	}
	componentWillMount = () => {
		PubSub.publish('_EditableTable', this);
	};
	render() {
		let { _pagination = {} } = this.props;
		const components = {
			body: {
				cell: EditableCell,
			},
		};
		const columns = this.columns.map(col => {
			if (!col.editable) {
				return col;
			}
			return {
				...col,
				onCell: record => {
					record.save = this.save;
					return {
						record,
						inputType: col.dataIndex === 'age' ? 'number' : 'text',
						dataIndex: col.dataIndex,
						title: col.title,
						editing: this.isEditing(record),
					};
				},
			};
		});

		return (
			<EditableContext.Provider value={this.props.form}>
				<Table
					components={components}
					bordered
					dataSource={this.props ? this.props.data : []}
					columns={columns}
					rowClassName="editable-row"
					pagination={{
						pageSize,
						current: _pagination.current,
						total: _pagination.total,
						defaultCurrent: 1,
						onChange: this.paginationChange,
						// pageSizeOptions: ,
					}}
					rowKey={record => record.id}
					rowSelection={this.rowSelection}
					onChange={(selectedRowKeys, selectedRows) => {
						this.setState({
							checkArr: selectedRows,
						});
					}}
				/>
			</EditableContext.Provider>
		);
	}
}
//  ↓ table
const EditableFormTable = Form.create()(EditableTable);
// ajax Fn
function uploadingFn(xx) {
}
// refresh name  fn
function refresh() { }

// 按钮 排s
function ButtonList(props) {
	//refresh btn  flag
	let [isOff, setIsOff] = React.useState(true);
	let [_EditableTable, set_EditableTable] = React.useState({});
	const checkedArr = React.useRef([]);

	let _test = false;
	// setTimeout(() => {
	// 	_test = 9999;
	// }, 10);
	function upDataChecked(o, key) {
		setIsOff(o);
		// console.log(checkedArr);
		checkedArr.current.length = 0;
		// console.log(key, 'key');
		checkedArr.current.push(...key);
		// checkedArr
	}
	UpDataData = upDataChecked;
	// SetIsOff = setIsOff;

	PubSub.subscribe('_EditableTable', (n, _this) => {
		// console.log(232, n, _this, '=======');
		set_EditableTable(_this);
		// _this.setState();
		// console.log(_this, '_this');
	});
	React.useEffect(() => {
		return;
	}, []);
	return (
		<div className="button-list-container">
			<div className="button-list-box" style={{ ...props.style }}>
				<Upload
					className="upload-btn"
					onChange={info => {
						// console.log(info, 'info');
						if (info.file.response && info.file.response.success) {
							// console.log(_EditableTable, '_EditableTable');
							_EditableTable.props.reqListFn && _EditableTable.props.reqListFn();
						}
					}}
					action="http://172.16.16.9/api/webManage/topology/upload"
					method="post"
					enctype="multipart/form-data"
				>
					<Button type="primary" onClick={uploadingFn} style={{ marginRight: '0.4rem' }}>
						<Icon type="cloud-upload" />
						上传
					</Button>
				</Upload>
				<ButtonGroup>
					<Button
						onClick={() => {
							// debugger;
							// let reqArr = [];
							// Array.isArray(_EditableTable.state.checkArr) &&
							// 	_EditableTable.state.checkArr.forEach(e => {
							// 		reqArr.push(e.file_name);
							// 	});
							// DiagramAction.diagramMDownImg({ names: reqArr }, res => {
							// 	console.log(res, 'res');
							// });
							uploadFn(_EditableTable.state.checkArr);
							// console.log(_EditableTable, ' 下载');
						}}
					>
						<Icon type="cloud-download" />
						下载
					</Button>
					<Button
						onClick={() => {
							deleteFn(_EditableTable.state.checkArr, _EditableTable.props.reqListFn);
						}}
					>
						<Icon type="delete" />
						删除
					</Button>
					{/* <Button
						disabled={isOff}
						onClick={() => {
							let result = checkedArr.current.length === 1 ? typeof _EditableTable.edit === 'function' && _EditableTable.edit(checkedArr.current[0]) : '';
						}}
					>
						<Icon type="form" />
						重命名
					</Button> */}
				</ButtonGroup>
			</div>
			<Search
				placeholder="按名称搜索"
				onSearch={value => {
					_EditableTable.props.reqListFn && _EditableTable.props.reqListFn(1, value, _EditableTable);
					// console.log(value);
				}}
				style={{ float: 'right', width: 200 }}
			/>
		</div>
	);
}
//diagramRename
// 总函数
function Topo() {
	let [data, setData] = useState([]);
	let [_pagination, set_pagination] = useState({
		// pageSize: 4,
		current: 1,
	});
	// setTimeout(() => {
	// 	setData(dataSource);
	// }, 2000);
	function reqListFn(page_num = 1, searchValue) {
		let sValue = searchValue && searchValue.trim();
		if (sValue) {
			DiagramAction.diagramList(
				{
					page_num,
					page_size: pageSize,
					name: searchValue.trim(),
				},
				function (res) {
					if (res.success) {
						let data = res.data;
						let arr = data.result;
						Array.isArray(arr) &&
							arr.forEach(e => {
								e.key = e.id;
								e.file_size = (e.file_size / 1024 / 1024).toFixed(1) + 'MB';
							});
						// console.log(res.data.result);
						setData(data.result);
						set_pagination({
							current: data.current_page,
							total: data.total,
							next: data.has_next,
							totalPageCount: res.total_page_count,
						});
					} else {
						Message.error('接口报错', 0.5);
					}
				}
			);
		} else {
			DiagramAction.diagramList(
				{
					page_num,
					page_size: pageSize,
				},
				function (res) {
					if (res.success) {
						let data = res.data;
						let arr = data.result;
						Array.isArray(arr) &&
							arr.forEach(e => {
								e.key = e.id;
								e.file_size = (e.file_size / 1024 / 1024).toFixed(1) + 'MB';
							});
						// console.log(res.data.result);
						setData(data.result);
						set_pagination({
							current: data.current_page,
							total: data.total,
							next: data.has_next,
							totalPageCount: res.total_page_count,
						});
					} else {
						Message.error('接口报错', 0.5);
					}
				}
			);
		}
		// console.log(EditableFormTable)
	}
	React.useEffect(function () {
		reqListFn();
	}, []);
	return (
		<div className="diagram-list-box">
			<ButtonList />
			<EditableFormTable data={data} _pagination={_pagination} reqListFn={reqListFn} />
			{/* <Table rowSelection={rowSelection} bordered={true} dataSource={data} columns={columns} /> */}
		</div>
	);
}

ReactDOM.render(<Topo />, window.rootNode);
// React.render() Topo;
