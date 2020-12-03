let { PureComponent, useState } = React;
let DiagramAction = window.DiagramAction;
let { Popconfirm, Upload, Form, message, Icon, Input, InputNumber, Button, Table, Tooltip, Modal } = antd;
let { confirm } = Modal;
let Message = message;
// modal 显示
function showConfirm(_ok, _EditableTable) {
	confirm({
		title: '你确定要删除这些图片吗',
		okText: '确认',
		cancelText: '取消',
		// content: 'Some descriptions',
		onOk() {
			// console.log('OK');
			// _ok()
			if (_ok()) {
				_EditableTable.state.checkArr = [];
			}
			// flag = true
		},
		onCancel() {},
	});
}
// let PubSub  = pubsub-js
// import {  } from '@/utils/customFunction.jsx';

function toZip(imgSrcList, fileName) {
	let zip = new JSZip(); //实例化一个压缩文件对象
	let imgFolder = zip.folder(fileName); //新建一个图片文件夹用来存放图片，参数为文件名
	for (let i = 0; i < imgSrcList.length; i++) {
		let currentImg = imgSrcList[i];
		let src = imgSrcList[i].file_url;
		let tempImage = new Image();
		tempImage.src = src;
		tempImage.crossOrigin = '*';
		tempImage.onload = () => {
			imgFolder.file(currentImg.name + '.' + currentImg.suffix_name, getBase64Image(tempImage), { base64: true });
			if (imgSrcList.length === i + 1) {
				zip.generateAsync({ type: 'blob' }).then(function (content) {
					saveAs(content, 'zip');
				});
			}
		};
	}
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
let deleteFn = (arr = undefined, ListFn, _EditableTable) => {
	if (!arr) {
		return;
	}
	let reqArr = [];
	Array.isArray(arr) &&
		arr.forEach((e) => {
			reqArr.push(e.id);
		});
	DiagramAction.diagramMRmImg({ ids: reqArr }, (res) => {
		if (res.success) {
			Message.success('操作成功', 0.5);
			// _EditableTable.state.checkArr = []
			setTimeout(function () {
				ListFn && ListFn();
			}, 0);
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
	if (arr.length === 0) {
		message.info('请选择下载文件.', 0.5);
	}

	let reqArr = [];
	Array.isArray(arr) &&
		arr.forEach((e) => {
			reqArr.push(e.id);
		});
	// downloadFile('POST', 1, '你好', {})
	DiagramAction.diagramMDownImg({ ids: reqArr }, (res) => {
		if (res.success) {
			let data = res.data;
			let file_url_list = _.map(data, 'file_url');
			// console.log(res.data, 'file_url_list');
			toZip(data, '系统拓扑图');
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
	getInput = (record) => {
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
			checkArr: [],
			sortFlagName: true,
			currentPage: 1,
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
				align: 'center',
				// sorter: (current, next, asd) => {
				// 	let { currentPage } = this.state;
				// 	if (asd === 'ascend') {
				// 		this.props.reqListFn(currentPage, 'name asc')
				// 	} else {
				// 		this.props.reqListFn(currentPage, 'name desc')
				// 	}
				// 	// console.log(arguments)
				// },
				sorter: true,
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
				align: 'center',
				// sorter: (current, next, asd, xxx) => {

				// 	// console.log(current, next, asd, xxx, 'current')
				// 	// return
				// 	let { currentPage } = this.state;
				// 	if (asd === 'ascend') {
				// 		this.props.reqListFn(currentPage, 'file_size asc')
				// 	} else {
				// 		this.props.reqListFn(currentPage, 'file_size desc')
				// 	}
				// 	// console.log(arguments)
				// },
				sorter: true,

				render(text) {
					if (!text) {
						return;
					}
					let strNumber = (text / 1024 / 1024).toFixed(2);
					// console.log((text / 1024 / 1024).toFixed(2), '(text / 1024 / 1024).toFixed(2)')
					if (strNumber == 0) {
						strNumber = strNumber.slice(0, strNumber.length - 1) + '1';
					}
					return <span>{strNumber + 'MB'}</span>;
				},
				// editable: true,
			},
			{
				title: '修改时间',
				dataIndex: 'mod_time',
				width: '40%',
				align: 'center',
				// align: 'center',
				// align,
				// sorter: (current, next, asd) => {
				// 	let { currentPage } = this.state;
				// 	if (asd === 'ascend') {
				// 		this.props.reqListFn(currentPage, 'mod_time asc')
				// 	} else {
				// 		this.props.reqListFn(currentPage, 'mod_time desc')
				// 	}
				// 	// console.log(arguments)
				// },
				sorter: true,
				render(text) {
					if (!text) {
						return;
					}
					return <span>{moment(text).format('YYYY-MM-DD HH:mm:ss')}</span>;
					// return dateUtil(new Date(record));
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
									<Tooltip placement="top" title={'查看图片'}>
										<Icon
											type="eye"
											onClick={() => {
												$.modal.openFull(record.name, `/html/diagram/view/view.html?id=${record.id}&name=${record.name}` + `&dept_id=${window.locationParams.dept_id}&ip=${window.locationParams.ip}`);
												// $.modal.open(record.name + '查看', `/html / diagram / view / view.html ? id = ${ record.id } & name= ${ record.name } `)
												// router.push(`view ? id = ${ record.id }& name=${record.name}`);
											}}
										/>
									</Tooltip>
								</div>
							</li>
							<li>
								<Tooltip placement="top" title={'下载图片'}>
									<Icon
										type="cloud-download"
										onClick={() => {
											uploadFn([record]);
										}}
									/>
								</Tooltip>

								<div className="svg-content">
									<span className="iconfont ticobackicon-upload " style={{ fontSize: '0.22rem' }}></span>
								</div>
							</li>
							<li>
								<Popconfirm
									placement="top"
									title={'确定要删除图片吗?'}
									onConfirm={() => {
										deleteFn([record], this.props.reqListFn);
									}}
									okText="删除"
									cancelText="取消"
								>
									<div className="svg-content">
										<Tooltip placement="top" title={'删除图片'}>
											<Icon type="delete" />
										</Tooltip>
									</div>
								</Popconfirm>
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
		this.paginationChange = (targetCurrent, x) => {
			let { currentPage } = this.state;
			if (x === undefined) {
				return;
			}
			this.setState(
				{
					currentPage: targetCurrent,
				},
				() => {
					this.props.reqListFn && this.props.reqListFn(targetCurrent);
				}
			);
		};
	}

	isEditing = (record) => record.key === this.state.editingKey;
	rowSelection = {
		onChange: (selectedRowKeys, selectedRows, i) => {
			// console.log(SetIsOff, 'SetIsOff', );
			let param = selectedRows.length === 1 ? false : true;
			typeof UpDataData === 'function' && UpDataData(param, selectedRowKeys);
			//这里拿的是引用可以直接修改
			// selectedRows[selectedRowKeys - 1].age = Date.now();
			this.setState({ checkArr: selectedRows }); //genggai
		},
		getCheckboxProps: (record) => ({
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
		// console.log(form, key, xxx, 'ss')
		DiagramAction.diagramRename(
			{
				id: key,
				name,
			},
			(res) => {
				if (res.success) {
					Message.success('修改成功', 0.5);
					this.props.reqListFn && this.props.reqListFn(this.state.currentPage);
				} else {
					Message.error(res.data, 0.5);
				}
			}
		);
		const newData = [...this.state.data];
		const index = newData.findIndex((item) => key === item.key);
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
		const columns = this.columns.map((col) => {
			if (!col.editable) {
				return col;
			}
			return {
				...col,
				onCell: (record) => {
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
					loading={this.props.loading}
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
					rowKey={(record) => record.id}
					rowSelection={this.rowSelection}
					onChange={(selectedRowKeys, selectedRows, orderByClause) => {
						let { columnKey, order } = orderByClause;
						// console.log(this.props, 'ss'),
						this.props.reqListFn(this.state.currentPage, order ? `${columnKey} ${order.slice(0, -3)}` : '');
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
function uploadingFn(xx) {}
// refresh name  fn
function refresh() {}

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
		checkedArr.current.length = 0;
		checkedArr.current.push(...key);
		// checkedArr
	}
	UpDataData = upDataChecked;
	// SetIsOff = setIsOff;

	PubSub.subscribe('_EditableTable', (n, _this) => {
		set_EditableTable(_this);
		// _this.setState();
	});
	React.useEffect(() => {
		return;
	}, []);
	return (
		<div className="button-list-container">
			<div className="button-list-box" style={{ ...props.style }}>
				<Upload
					className="upload-btn"
					onChange={(info) => {
						// console.log(info, 'info');
						if (info.file.response && info.file.response.success) {
							// console.log(_EditableTable, '_EditableTable');
							_EditableTable.props.reqListFn && _EditableTable.props.reqListFn();
						}
					}}
					accept={'.png,.bmp,.jpg,.tif,.gif,.pcx,.tga,.exif,.fpx,.svg,.psd,.raw'}
					headers={{
						dept_id: localStorage.getItem('deptId'),
					}}
					action={window.locationParams.ip + "/api/fileserver/fileUpload/uploadImage"}
					method="post"
					beforeUpload={(file, fileList) => {
						let reg = /\.(jpg|gif|bmp|tif|png|pcx|jpeg|exif|tga|svg|raw|psd|)+$/;
						// console.log(file, 'file.type ')
						// console.log(file.type.split('/')[1])

						let isImg = reg.test(file.name);
						if (isImg) {
							return true;
						} else {
							message.error('文件格式不对', 0.5);
							return false;
						}

						// console.log(file.type, fileList, 'file, fileList')
					}}
					enctype="multipart/form-data"
					onChange={(info, info2) => {
						// console.log(info, 'info')
						if (info.file.status === undefined) {
							return;
						}
						if (info.file.status !== 'uploading') {
							message.loading(`${info.file.name} 上传中...`);
						}
						if (info.file.status === 'done') {
							try {
								let { data = {} } = info.file.response;
								let { file_name, size, url, name } = data;
								let _name = name.split('.')[0];
								let suffix_name = name.split('.')[1];
								DiagramAction.diagramuploadImg(
									{
										file_name,
										size,
										url,
										suffix_name,
										name: _name,
									},
									function (res) {
										message.destroy();
										message.success(`${info.file.name} 上传成功.`, 0.5);
									}
								);
							} catch (error) {
								message.error('添加失败');
							}

							_EditableTable.props.reqListFn();
						} else if (info.file.status === 'error') {
							message.destroy();
							message.error(`${info.file.name} 上传失败.`, 0.5);
						}
					}}
				>
					<Button type="primary" style={{ marginRight: '0.4rem' }}>
						<Icon type="cloud-upload" />
						上传
					</Button>
				</Upload>
				<ButtonGroup>
					<Button
						onClick={() => {
							uploadFn(_EditableTable.state.checkArr);
						}}
					>
						<Icon type="cloud-download" />
						下载
					</Button>
					<Button
						onClick={() => {
							if (_EditableTable.state.checkArr.length === 0) {
								message.info('请选择要删除的图片.', 0.5);
								return;
							}
							showConfirm(function () {
								deleteFn(_EditableTable.state.checkArr, _EditableTable.props.reqListFn, _EditableTable);
							});
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
				onSearch={(value) => {
					_EditableTable.props.searchFn && _EditableTable.props.searchFn(value);
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
	let [loading, setLoading] = useState(true);
	let [_pagination, set_pagination] = useState({
		current: 1,
		pageSize,
	});
	// setTimeout(() => {
	// 	setData(dataSource);
	// }, 2000);
	// 请求函数1
	function filterFn(data = []) {
		// for (let i = 0; i < pageSize + 1 - data.length; i++) {
		// 	data.push({})
		// }
		return data;
	}
	function searchFn(searchValue, orderByClause, page_num = 1) {
		let sValue = searchValue && searchValue.trim();
		DiagramAction.diagramList(
			{
				// orderByClause: orderByClause ? orderByClause : 'time asc',
				orderByClause: orderByClause ? orderByClause : 'mod_time desc',
				page_num,
				page_size: pageSize,
				name: sValue || searchValue,
			},
			function (res) {
				if (res.success) {
					let data = res.data;
					let arr = data.result;
					Array.isArray(arr) &&
						arr.forEach((e) => {
							e.key = e.id;
						});
					// console.log(res.data.result);
					setData(filterFn(data.result));
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
	function reqListFn(page_num = 1, orderByClause) {
		setLoading(true);
		let reqFlag = false;
		if (false) {
		} else {
			if (typeof page_num === 'number') {
				DiagramAction.diagramList(
					{
						// orderByClause: orderByClause ? orderByClause : 'time asc',
						orderByClause: orderByClause ? orderByClause : 'mod_time desc',
						page_num,
						page_size: pageSize,
						_pagination,
					},
					function (res) {
						if (res.success) {
							let data = res.data;
							let arr = data.result;
							Array.isArray(arr) &&
								arr.forEach((e) => {
									e.key = e.id;
								});
							// console.log(res.data.result);
							setData(filterFn(data.result));

							// setData(data.result);
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
			} else if (typeof page_num === 'object') {
				let pagenum = _pagination.current * pageSize;
				let params = {
					// orderByClause: orderByClause ? orderByClause : 'time asc',
					orderByClause: orderByClause ? orderByClause : 'mod_time desc',
					page_num: pagenum,
					page_size: pageSize,
					orderByClause: page_num.sortMes,
				};

				// if (page_num.asc) {

				// }
				DiagramAction.diagramListSort(params, function (res) {
					if (res.success) {
						let data = res.data;
						let arr = data.result;
						Array.isArray(arr) &&
							arr.forEach((e) => {
								e.key = e.id;
							});
						setData(filterFn(data.result));
						// setData(data.result);
						set_pagination({
							current: data.current_page,
							total: data.total,
							next: data.has_next,
							totalPageCount: res.total_page_count,
						});
					} else {
						Message.error('接口报错', 0.5);
					}
				});
			}
		}
		// console.log(EditableFormTable)
		setLoading(false);
	}
	React.useEffect(function () {
		// debugger
		reqListFn();
	}, []);
	return (
		<div className="diagram-list-box">
			<ButtonList />
			<EditableFormTable data={data} loading={loading} searchFn={searchFn} _pagination={_pagination} reqListFn={reqListFn} />
			{/* <Table rowSelection={rowSelection} bordered={true} dataSource={data} columns={columns} /> */}
		</div>
	);
}

ReactDOM.render(<Topo />, window.rootNode);
// React.render() Topo;
