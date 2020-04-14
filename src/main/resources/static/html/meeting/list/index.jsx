let { useRef, useState, useEffect, useLayoutEffect, PureComponent } = React;
let allHeight = 150;// 图高度设置 不包括饼图
let DiagramAction = window.DiagramAction;
let { Popconfirm, Upload, Form, message, Icon, Input, InputNumber, Button, Table, Tooltip, Modal, DatePicker, BackTop } = antd;
let { confirm } = Modal;
let Message = message;
let { RangePicker } = DatePicker;
// 日历
class DateRange extends React.Component {
	state = {
		startValue: null,
		endValue: null,
		endOpen: false,
	};

	disabledStartDate = startValue => {
		const { endValue } = this.state;
		if (!startValue || !endValue) {
			return false;
		}
		return startValue.valueOf() > endValue.valueOf();
	};

	disabledEndDate = endValue => {
		const { startValue } = this.state;
		if (!endValue || !startValue) {
			return false;
		}
		return endValue.valueOf() <= startValue.valueOf();
	};

	onChange = (field, value) => {
		this.setState({
			[field]: value,
		});
	};

	onStartChange = value => {
		this.onChange('startValue', value);
	};

	onEndChange = value => {
		this.onChange('endValue', value);
	};

	handleStartOpenChange = open => {
		if (!open) {
			this.setState({ endOpen: true });
		}
	};

	handleEndOpenChange = open => {
		this.setState({ endOpen: open });
	};

	render() {
		const { startValue, endValue, endOpen } = this.state;
		return (
			<div style={{
				display: 'flex',
				textAlign: 'center',
				lineHeight: '0.3rem'
			}}>
				<span className="data-title">会议数据统计表</span>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<DatePicker
					disabledDate={this.disabledStartDate}
					showTime
					format="YYYY-MM-DD HH:mm"
					value={startValue}
					placeholder="开始时间"
					onChange={this.onStartChange}
					onOpenChange={this.handleStartOpenChange}
				/>
					至
				<DatePicker
					ranges={{
						Today: [moment(), moment()],
						'This Month': [moment().startOf('month'), moment().endOf('month')],
					}}
					disabledDate={this.disabledEndDate}
					showTime
					format="YYYY-MM-DD HH:mm"
					value={endValue}
					placeholder="结束时间"
					onChange={this.onEndChange}
					open={endOpen}
					onOpenChange={this.handleEndOpenChange}
				/>
			</div>
		);
	}
}
// modal 显示
function showConfirm(_ok, _EditableTable) {
	confirm({
		title: '你确定要删除这几张图片吗',
		okText: '确认',
		cancelText: '取消',
		// content: 'Some descriptions',
		onOk() {
			// console.log('OK');
			// _ok()
			if (_ok()) {
				_EditableTable.state.checkArr = []
			}
			// flag = true
		},
		onCancel() {
		},
	});
}
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
const pageSize = 4; //条数
const align = 'left';

// 删除调用函数
let deleteFn = (arr = undefined, ListFn, _EditableTable) => {
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
			// _EditableTable.state.checkArr = []
			setTimeout(function () {

				ListFn && ListFn()
			}, 0)
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
		message.info('请选择下载文件.', .5)
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
			checkArr: [],
			sortFlagName: true,
			currentPage: 1
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
						return
					}
					let strNumber = (text / 1024 / 1024).toFixed(2);
					// console.log((text / 1024 / 1024).toFixed(2), '(text / 1024 / 1024).toFixed(2)')
					if (strNumber == 0) {
						strNumber = strNumber.slice(0, strNumber.length - 1) + '1'
					}
					return strNumber + 'MB'
				}
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
						return
					}
					return moment(text).format('YYYY-MM-DD HH:mm:ss')
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
										<Icon type="eye" onClick={() => {
											$.modal.openFull(record.name, `/html/diagram/view/view.html?id=${record.id}&name=${record.name}`)
											// $.modal.open(record.name + '查看', `/html / diagram / view / view.html ? id = ${ record.id } & name= ${ record.name } `)
											// router.push(`view ? id = ${ record.id }& name=${record.name}`);
										}} />
									</Tooltip>
								</div>
							</li>
							<li>
								<Tooltip placement="top" title={'下载图片'}>
									<Icon type="cloud-download" onClick={() => {
										uploadFn([record]);
									}} />
								</Tooltip>

								<div
									className="svg-content"

								>
									<span className="iconfont ticobackicon-upload " style={{ fontSize: '0.22rem' }}></span>
								</div>
							</li>
							<li>
								<Popconfirm placement="top" title={'确定要删除图片吗?'} onConfirm={() => {
									deleteFn([record], this.props.reqListFn);
								}} okText="删除" cancelText="取消">
									<div
										className="svg-content"
									>
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
				return
			}
			this.setState({
				currentPage: targetCurrent
			}, () => {
				this.props.reqListFn && this.props.reqListFn(targetCurrent);
			})
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
		// console.log(form, key, xxx, 'ss')
		DiagramAction.diagramRename(
			{
				id: key,
				name,
			},
			res => {
				if (res.success) {
					Message.success('修改成功', 0.5);
					this.props.reqListFn && this.props.reqListFn(this.state.currentPage);
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
						position: 'bottom'
						// pageSizeOptions: ,
					}}
					rowKey={record => record.id}
					rowSelection={this.rowSelection}
					onChange={(selectedRowKeys, selectedRows, orderByClause) => {
						let { columnKey, order } = orderByClause;
						// console.log(this.props, 'ss'),
						this.props.reqListFn(this.state.currentPage, order ? `${columnKey} ${order.slice(0, -3)}` : '')
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
					onChange={info => {
						// console.log(info, 'info');
						if (info.file.response && info.file.response.success) {
							// console.log(_EditableTable, '_EditableTable');
							_EditableTable.props.reqListFn && _EditableTable.props.reqListFn();
						}
					}}
					accept={'.png,.bmp,.jpg,.tif,.gif,.pcx,.tga,.exif,.fpx,.svg,.psd,.raw'}
					headers={{
						dept_id: localStorage.getItem('deptId')
					}}
					action="http://172.16.16.9/api/backgroundinterface/topology/upload"
					method="post"
					beforeUpload={(file, fileList) => {
						let reg = /\.(jpg|gif|bmp|tif|png|pcx|jpeg|exif|tga|svg|raw|psd|)+$/
						// console.log(file, 'file.type ')
						// console.log(file.type.split('/')[1])

						let isImg = reg.test(file.name);
						if (isImg) {
							return true
						} else {
							message.error('文件格式不对', .5)
							return false
						}
						// console.log(file.type, fileList, 'file, fileList')
					}}
					enctype="multipart/form-data"
					onChange={(info, info2) => {
						// console.log(info, 'info')
						if (
							info.file.status === undefined
						) {
							return
						}
						if (info.file.status !== 'uploading') {
							message.loading(`${info.file.name} 上传中...`);
						}
						if (info.file.status === 'done') {
							_EditableTable.props.reqListFn()
							message.destroy()
							message.success(`${info.file.name} 上传成功.`, .5);

						} else if (info.file.status === 'error') {
							message.destroy()
							message.error(`${info.file.name} 上传失败.`, .5);
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
								message.info('请选择要删除的图片.', .5)
								return
							}
							showConfirm(
								function () { deleteFn(_EditableTable.state.checkArr, _EditableTable.props.reqListFn, _EditableTable) },
							)
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

					_EditableTable.props.searchFn && _EditableTable.props.searchFn(value);
					// console.log(value);
				}}
				style={{ float: 'right', width: 200 }}
			/>
		</div >
	);
}
//diagramRename
// 总函数
function Topo() {
	let [data, setData] = useState([]);
	let [loading, setLoading] = useState(true)
	let [_pagination, set_pagination] = useState({
		current: 1,
		pageSize
	});
	// setTimeout(() => {
	// 	setData(dataSource);
	// }, 2000);
	// 请求函数1
	function filterFn(data = []) {
		// for (let i = 0; i < pageSize + 1 - data.length; i++) {
		// 	data.push({})
		// }
		return data
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
						arr.forEach(e => {
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
		setLoading(true)
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
						_pagination
					},
					function (res) {
						if (res.success) {
							let data = res.data;
							let arr = data.result;
							Array.isArray(arr) &&
								arr.forEach(e => {
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
				let pagenum = _pagination.current * pageSize
				let params = {
					// orderByClause: orderByClause ? orderByClause : 'time asc',
					orderByClause: orderByClause ? orderByClause : 'mod_time desc',
					page_num: pagenum,
					page_size: pageSize,
					orderByClause: page_num.sortMes
				}

				// if (page_num.asc) {

				// }
				DiagramAction.diagramListSort(params, function (res) {
					if (res.success) {
						let data = res.data;
						let arr = data.result;
						Array.isArray(arr) &&
							arr.forEach(e => {
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
				})
			}
		}
		// console.log(EditableFormTable)
		setLoading(false)
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





//圆柱图 && 饼图
function VisualTop(props) {
	let [count, setCount] = useState(1);
	// 圆柱
	useEffect(() => {
		G2.registerShape('interval', 'border-radius', {
			draw(cfg, container) {
				const points = cfg.points;
				let path = [];
				path.push(['M', points[0].x, points[0].y]);
				path.push(['L', points[1].x, points[1].y]);
				path.push(['L', points[2].x, points[2].y]);
				path.push(['L', points[3].x, points[3].y]);
				path.push('Z');
				path = this.parsePath(path); // 将 0 - 1 转化为画布坐标

				const group = container.addGroup();
				group.addShape('rect', {
					attrs: {
						x: path[1][1], // 矩形起始点为左上角
						y: path[1][2],
						width: path[2][1] - path[1][1],
						height: path[0][2] - path[1][2],
						fill: cfg.color,
						radius: (path[2][1] - path[1][1]) / 2,
					},
				});

				return group;
			},
		});
		const data = [
			{ year: '1991', value: 3 },
			{ year: '1992', value: 4 },
			{ year: '1993', value: 3.5 },
			{ year: '1994', value: 5 },
		];
		const chart = new G2.Chart({
			container: 'circle',
			// width: 50,
			//	自适应
			autoFit: true,
			height: allHeight,
			// width: 15
		});

		chart.data(data);
		chart.tooltip({
			//showMarkers: false
		});
		// 滑块选取
		// chart.interaction('brush');
		// 控制大小site
		chart.interval().position('year*value').color('l(90) 0:#4c71fe 1:rgba(76, 113, 254, 0.64) ').size(5).shape('date*actual', (date, val) => {
			if (val === 0) {
				return;
			}
			return 'border-radius';
		});
		chart.render();
	}, [])
	//饼图

	useEffect(() => {
		const data = [
			{ item: '事例一', count: 40, percent: 0.4 },
			{ item: '事例二', count: 21, percent: 0.21 },
			{ item: '事例三', count: 17, percent: 0.17 },
			{ item: '事例四', count: 13, percent: 0.13 },
			{ item: '事例五', count: 9, percent: 0.09 },
		];

		const chart = new G2.Chart({
			container: 'pie',
			// width: 250,
			autoFit: true,
			height: 220,
		});

		chart.coordinate('theta', {
			radius: 0.75,
		});

		chart.data(data);

		chart.scale('percent', {
			formatter: (val) => {
				val = val * 100 + '%';
				return val;
			},
		});
		chart.legend({
			position: 'right',
			offsetX: -20
		});
		chart.tooltip({
			showTitle: false,
			showMarkers: false,
		});

		chart
			.interval()
			.position('percent')
			.color('item')
			.label('percent', {
				offset: -20,
				style: {
					textAlign: 'center',
					fontSize: 12,
					shadowBlur: 2,
					shadowColor: 'rgba(0, 0, 0, .45)',
					fill: '#fff',
				},
			})
			.adjust('stack');

		chart.interaction('element-active');

		chart.render();
	}, [])
	return <div className="visual-top-box">
		<div className="circle-box padding-view">
			<div className="view-title">
				会议级别时长
		</div>
			<span className="view-unit">
				单位: 小时
		</span>
			<div id="circle" >

			</div>
		</div>
		<div className="pie-box padding-view">
			<div className="view-title">
				会议级别统计
		</div>
			<span className="view-unit">
				单位: 次
		</span>
			<div className="all-count">
				<div className="all-title">总次数
				<br />
					<span className="all-number">{count}</span></div>
			</div>
			<div id="pie"></div>
		</div>
	</div >




}

// 折线图
function VisualBottom(props) {
	useEffect(() => {
		const data = [
			{ year: '1991', value: 3 },
			{ year: '1992', value: 4 },
			{ year: '1993', value: 3.5 },
			{ year: '1994', value: 5 },
			{ year: '1995', value: 4.9 },
			{ year: '1996', value: 6 },
			{ year: '1997', value: 7 },
			{ year: '1998', value: 9 },
			{ year: '1999', value: 13 },
		];
		const chart = new G2.Chart({
			container: 'line-content',
			autoFit: true,

			height: allHeight,
		});

		chart.data(data);
		chart.scale({
			year: {
				range: [0, 1],
			},
			value: {
				min: 0,
				nice: true,
			},
		});

		chart.tooltip({
			showCrosshairs: true, // 展示 Tooltip 辅助线
			shared: true,
		});

		chart.line().position('year*value').label('value');
		chart.point().position('year*value');
		chart.area().position('year*value').style({
			// fill: 'r(0.5,1.3,1.3) 0:#ffffff 1:#1890ff',
			fill: "l(90) 0:#1890FF 1:#f7f7f7",
			//stroke: 'l(0) 0:#ffffff 0.5:#7ec2f3 1:#1890ff'
		})
		chart.tooltip({
			// showCrosshairs: true, // 展示 Tooltip 辅助线
			shared: true,
			showTitle: false,
			itemTpl: '<li class="test">{year} 有 {value} 个</li>',
		});

		chart.render();
	}, [])
	return <div className="line-box ">
		<div className="line-title view-title">
			会议保障时长
		</div>
		<span className="line-unit view-unit">
			单位: 小时
		</span>
		<div id="line-content">
		</div>
	</div>
}
function App() {
	let node = useRef(null);
	let [isShowBtn, setIsShowBtn] = useState(false)
	function handleScroll(e) {

		node.current.scrollTop > 0 ? setIsShowBtn(true) : setIsShowBtn(false)
	}
	useEffect(function () {
		// chart.on('tooltip:show', (ev) => {
		// 	// x: 当前鼠标的 x 坐标,
		// 	// y: 当前鼠标的 y 坐标,
		// 	// tooltip: 当前的 tooltip 对象
		// 	// items: 数组对象，当前 tooltip 显示的每条内容
		// 	// title: tooltip 标题
		// 	const { tooltip, items, title, x, y } = ev;
		// });
	}, [])


	return <div onScroll={handleScroll} className="meeting-list-box" style={{
		// height: '200%'
	}} ref={node}>
		<div className="to-top" style={{
			position: 'fixed',
			right: '0',
			bottom: '0.7rem'
		}}>
			<Button style={{
				display: isShowBtn ? '' : 'none'
			}} onClick={function () {
				node.current.scroll(0, 0)
			}}>
				<Icon type="to-top" />
			</Button>
		</div>
		<div className="table-box">
			<Topo />
		</div>
		<div className="visual-box">
			<div className="visual-select-box">
				<DateRange />
			</div>
			<div className="visual-top">
				<VisualTop />
			</div>
			<div className="visual-bottom padding-view">
				<VisualBottom />
			</div>

		</div>

	</div>
}

ReactDOM.render(<App />, window.rootNode);



// ReactDOM.render(<div style={{
// 	height: '300%'
// }}>

// 	Scroll down to see the bottom-right
// <strong style={{ color: '#1088e9' }}> blue </strong>
// button.
// </div>, window.rootNode)