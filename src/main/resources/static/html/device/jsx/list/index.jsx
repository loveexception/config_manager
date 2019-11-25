var domId = '';
var scripts = document.getElementsByTagName('script');
for (var i = 0; i < scripts.length; i++) {
	var script = scripts[i];
	if (script && script.getAttribute('type') == 'text/babel') {
		domId = script.getAttribute('domId');
	}
}
$.modal.openFull = function(title, url, width, height) {
	//如果是移动端，就使用自适应大小弹窗
	if (navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)) {
		width = 'auto';
		height = 'auto';
	}
	if ($.common.isEmpty(title)) {
		title = false;
	}
	if ($.common.isEmpty(url)) {
		url = '/404.html';
	}
	if ($.common.isEmpty(width)) {
		width = 800;
	}
	if ($.common.isEmpty(height)) {
		height = $(window).height() - 50;
	}
	var index = layer.open({
		type: 2,
		area: [width + 'px', height + 'px'],
		fix: true,
		//不固定
		maxmin: false,
		shade: 0.3,
		title: title,
		content: url,
		// btn: ['确定', '关闭'],
		// 弹层外区域关闭
		shadeClose: false,
		yes: function(index, layero) {
			var iframeWin = layero.find('iframe')[0];
			iframeWin.contentWindow.submitHandler();
		},
		cancel: function(index, layero) {
			var iframeWin = layero.find('iframe')[0];
			return iframeWin.contentWindow.cancelHandler();
			// return true;
		}
	});
	layer.full(index);
};

var xhrOnProgress = function(fun) {
	xhrOnProgress.onprogress = fun; //绑定监听
	//使用闭包实现监听绑
	return function() {
		//通过$.ajaxSettings.xhr();获得XMLHttpRequest对象
		var xhr = $.ajaxSettings.xhr();
		//判断监听函数是否为函数
		if (typeof xhrOnProgress.onprogress !== 'function') return xhr;
		//如果有监听函数并且xhr对象支持绑定时就把监听函数绑定上去
		if (xhrOnProgress.onprogress && xhr.upload) {
			xhr.upload.onprogress = xhrOnProgress.onprogress;
		}
		return xhr;
	};
};

let { Table, Select, Button, Input, Icon, Cascader, Dropdown, Menu, message, Modal, Radio, Pagination } = antd;
class ListBox extends React.PureComponent {
	constructor(props) {
		super(props);
		this.inputRef = React.createRef();
	}
	state = {
		data: [],
		columns: [
			{
				title: '设备编号',
				dataIndex: 'id',
				width: 150,
				fixed: 'left'
			},

			{
				title: 'SN编号',
				dataIndex: 'sno'
			},
			{ title: '名称', dataIndex: 'cnName' },
			{ title: '品牌', dataIndex: 'driver[kindCompany]' },
			{ title: '型号', dataIndex: 'driver[kindType]' },
			{
				title: '业务类型',
				dataIndex: '业务类型',
				render: (text = '', record) => {
					let tags = record.tags || [];
					tags.map((item, index) => {
						text += item.cnName;
						if (index != tags.length - 1) {
							text += '-';
						}
					});
					return text;
				}
			},
			{ title: '驱动名称', dataIndex: 'driver[cnName]' },
			{ title: '位置信息', dataIndex: 'location[cnName]' },
			{ title: '所属组织', dataIndex: 'dept[deptName]' },
			{ title: '所属网关', dataIndex: 'gateway[cnName]' },
			{ title: 'IP地址', dataIndex: 'ip' },
			{
				title: '采集频率',
				dataIndex: 'cycle',
				render: (text, record) => {
					return (
						<div>
							<span>{text}</span>
							<sub>{record.unit}</sub>
						</div>
					);
				}
			},
			{ title: '价格', dataIndex: 'price' }, //--
			{ title: '购入日期', dataIndex: 'orderTime' }, //--
			{ title: '使用年限', dataIndex: 'quality' }, //--
			{ title: '报废日期', dataIndex: 'discardTime' }, //--
			{ title: '资产状态', dataIndex: 'assetStatus', render: (text, record) => (text == '0' ? '使用中' : '未使用') }, //--
			{ title: '激活状态', dataIndex: 'status', render: (text, record) => (text == 'true' ? '激活' : '停用') },
			{ title: '告警状态', dataIndex: 'alertStatus', render: (text, record) => (text == 'critical' ? '紧急告警' : text == 'major' ? '重要告警' : text == 'minor' ? '次要告警' : '告警提示') }, //--
			// {
			// 	title: '更新时间',
			// 	dataIndex: 'updateTime'
			// },
			{
				title: '操作',
				key: 'operation',
				fixed: 'right',
				width: 120,
				render: (text, record) => {
					return (
						<div>
							<Button
								onClick={() => {
									$.modal.openFull('修改设备', `/html/device/edit.html?device_id=${record.id}`);
								}}
							>
								修改
							</Button>
							<Button
								onClick={() => {
									if (record.id) {
										$.ajax({
											cache: true,
											type: 'POST',
											url: '/iot/device/device_remove',
											data: JSON.stringify({
												id: record.id
											}),
											dataType: 'json',
											async: false,
											success: results => {
												if (results.code != 0) {
													message.error('接口错误');
													return;
												}
												this.getTable();
											}
										});
									}
								}}
							>
								删除
							</Button>
						</div>
					);
				}
			}
		],
		select_list: [],
		select_cascader: [],
		select_dept: void 0,
		cascader_list: [],
		searchValue: '',
		table_search: {
			total: 0,
			pageSize: 10,
			pageNum: 1
		},
		upload_visible: false,
		loading: false,
		selectedRowKeys: []
	};
	componentDidMount() {
		this.getSelectList();
		this.getTable();
	}
	getTable = (locationid = '', deptid = '') => {
		let { searchValue = '' } = this.state;
		let { pageNum, pageSize } = this.state.table_search || {};
		this.setState({ loading: true });
		$.ajax({
			url: `/iot/device/device_list?pageNum=${pageNum}&pageSize=${pageSize}&locationid=${locationid}&deptid=${deptid}&cnName=${searchValue}&orderByColumn=updateTime&isAsc=desc`,
			// data: {},
			cache: false,
			contentType: false,
			processData: false,
			type: 'GET',
			success: results => {
				if (results.code != 0) {
					message.error('接口错误', 0.5);
					this.setState({ loading: false });
					return;
				}
				let data = results.data || [];
				let { total } = data;
				this.setState({
					loading: false,
					data: data.rows || [],
					table_search: {
						...this.state.table_search,
						total: total
					}
				});
			}
		});
	};
	getSelectList = () => {
		$.ajax({
			url: `/sys/dept/tree_list`,
			// data: {},
			cache: false,
			contentType: false,
			processData: false,
			type: 'GET',
			success: results => {
				if (results.code != 0) {
					message.error('接口错误', 0.5);
					return;
				}
				let data = results.data || [];
				this.setState({
					select_list: data.filter(item => item.pId != '0')
				});
			}
		});
	};
	getCascaderList = deptid => {
		$.ajax({
			url: `/iot/location/tree_parent?deptid=${deptid}`,
			// data: {},
			cache: false,
			contentType: false,
			processData: false,
			type: 'GET',
			success: results => {
				if (results.code != 0) {
					message.error('接口错误', 0.5);
					return;
				}
				let data = results.data || [];
				this.setState({
					select_cascader: [],
					cascader_list: [data]
				});
			}
		});
	};
	toggleModal = upload_visible => {
		this.setState({
			upload_visible: upload_visible
		});
	};

	render = () => {
		let { data = [], columns = [], select_list = [], cascader_list = [], table_search = {}, selectedRowKeys } = this.state;
		const rowSelection = {
			onChange: (selectedRowKeys, selectedRows) => {
				this.setState({
					selectedRowKeys
				});
			},
			getCheckboxProps: record => ({
				disabled: record.name === 'Disabled User', // Column configuration not to be checked
				name: record.name
			})
		};
		const menu = (
			<Menu>
				{/* <Menu.Item>
					<span>批量导入</span>
				</Menu.Item> */}
				<Menu.Item>
					<span>批量导出</span>
				</Menu.Item>
				{/* <Menu.Item>
					<span>模版下载</span>
				</Menu.Item> */}
				<Menu.Item
					onClick={() => {
						if (_.isEmpty(selectedRowKeys)) {
							message.info('请选择设备', 0.5);
							return;
						}
						Modal.confirm({
							width: '75%',
							title: '驱动列表',
							content: <PublicList ref={el => (this.device_list = el)} url="/iot/driver/driver_page" />,
							okText: '确认',
							cancelText: '取消',
							onOk: close => {
								if (this.device_list) {
									let { state = {} } = this.device_list;
									let { value } = state;
									if (_.isEmpty(value)) {
										message.info('请选择驱动', 0.5);
										return;
									}
									let params = {
										data: selectedRowKeys,
										driverid: value
									};
									$.ajax({
										cache: true,
										type: 'POST',
										url: '/iot/device/drivers_update',
										data: JSON.stringify({
											...params
										}),
										dataType: 'json',
										async: false,
										success: results => {
											if (results.code != 0) {
												message.error('接口错误');
												return;
											}
											close();
											this.getTable();
										}
									});
								}
							}
						});
					}}
				>
					<span>更新驱动</span>
				</Menu.Item>
				<Menu.Item
					onClick={() => {
						if (_.isEmpty(selectedRowKeys)) {
							message.info('请选择设备', 0.5);
							return;
						}
						Modal.confirm({
							width: '75%',
							title: '业务类型列表',
							content: <PublicList ref={el => (this.tag_list = el)} url="/iot/tag/tags" />,
							okText: '确认',
							cancelText: '取消',
							onOk: close => {
								if (this.tag_list) {
									let { state = {} } = this.tag_list;
									let { value } = state;
									if (_.isEmpty(value)) {
										message.info('请选择业务类型', 0.5);
										return;
									}
									let params = {
										data: selectedRowKeys,
										tagid: value
									};
									$.ajax({
										cache: true,
										type: 'POST',
										url: '/iot/device/drivers_tags',
										data: JSON.stringify({
											...params
										}),
										dataType: 'json',
										async: false,
										success: results => {
											if (results.code != 0) {
												message.error('接口错误');
												return;
											}
											close();
											this.getTable();
										}
									});
								}
							}
						});
					}}
				>
					<span>添加业务类型</span>
				</Menu.Item>
				<Menu.Item>
					<span>网关解绑</span>
				</Menu.Item>
			</Menu>
		);
		return (
			<div className="device-list-body">
				<div className="device-list-content">
					<div className="device-list-content-li-2 search-box">
						<div className="search-content">
							<div
								className="content-li"
								// style={{
								// 	marginBottom: 20
								// }}
							>
								<Select
									placeholder="请选择组织"
									value={this.state.select_dept}
									onSelect={value => {
										this.setState(
											{
												select_dept: value
											},
											() => {
												this.getCascaderList(value);
											}
										);
									}}
								>
									{select_list.map((item, index) => {
										return (
											<Select.Option key={index} value={item.id}>
												{item.name}
											</Select.Option>
										);
									})}
								</Select>
								<Cascader
									placeholder="请选择地理位置"
									fieldNames={{
										label: 'cnName',
										value: 'id',
										children: 'children'
									}}
									value={this.state.select_cascader}
									options={cascader_list}
									onChange={(value, selectedOptions) => {
										this.setState({
											select_cascader: value
										});
									}}
								/>
								<Input
									style={{ width: '200px' }}
									ref={this.inputRef}
									placeholder="请输入搜索内容"
									onInput={event => {
										this.setState({
											searchValue: event.target.value && event.target.value.trim() || ''
										});
									}}
								></Input>
							</div>
							{/* <div className="content-li">
								<Select
									className="select-box"
									// defaultValue="请选择分类"
									// onChange={handleChange}
									placeholder="请选择分类"
								>
									<Select.Option value="a">驱动名称</Select.Option>
									<Select.Option value="b">设备分类</Select.Option>
									<Select.Option value="c">设备子类</Select.Option>
									<Select.Option value="d">设备品牌</Select.Option>
									<Select.Option value="e">设备型号</Select.Option>
								</Select>
								<Input.Search className="input-box" placeholder="请输入关键字" onSearch={value => console.log('点击搜索', value)} style={{ width: 254 }} />
							</div> */}
						</div>
						<Button
							className="search-btn"
							type="parimay"
							onClick={() => {
								let { select_dept, select_cascader = [], searchValue = '' } = this.state;
								this.getTable(select_cascader.length > 0 ? select_cascader[select_cascader.length - 1] : '', select_dept);
							}}
						>
							确认选择
						</Button>
					</div>
				</div>
				<div className="device-list-content">
					<div
						className="device-list-content-li"
						style={{
							display: 'flex',
							alignItems: 'center'
						}}
					>
						<Button
							className="btn"
							type="primary"
							onClick={() => {
								let { select_dept, select_cascader = [], select_list = [] } = this.state;
								if (_.isEmpty(select_dept)) {
									message.error('请选择组织', 0.5);
									return;
								}
								if (_.isEmpty(select_cascader)) {
									message.error('请选择地理位置', 0.5);
									return;
								}
								// console.log(select_cascader);
								// let select_cascader_text = '';
								// select_cascader.map(item => (select_cascader_text += `select_cascader[]=${item}&`));
								$.modal.openFull('新增驱动', `/html/device/add.html?select_dept=${select_dept}&select_cascader=${select_cascader[select_cascader.length - 1]}`);
							}}
						>
							新增
						</Button>
						<Button
							onClick={() => {
								this.toggleModal(true);
							}}
						>
							导入
						</Button>
						<Button onClick={() => {}}>模版下载</Button>

						<Dropdown className="content-li-dropdown" overlay={menu}>
							<div>
								<span>批量操作</span>
								<Icon type="down" />
							</div>
						</Dropdown>
					</div>
				</div>
				<Table
					rowKey={record => record.id}
					loading={this.state.loading}
					bordered
					rowSelection={rowSelection}
					columns={columns}
					dataSource={data}
					scroll={{ x: '300%' }}
					pagination={{
						current: table_search.pageNum,
						total: table_search.total,
						pageSize: table_search.pageSize,
						simple: true,
						onChange: (page, pageSize) => {
							this.setState(
								{
									table_search: {
										...this.state.table_search,
										pageNum: page,
										pageSize
									}
								},
								() => {
									this.getTable();
								}
							);
						}
					}}
				/>
				<Modal
					className="device-upload-modal"
					title="导入文件"
					visible={this.state.upload_visible}
					okText="确定"
					cancelText="取消"
					onOk={() => {
						this.toggleModal(false);
					}}
					footer={null}
					onCancel={() => {
						this.toggleModal(false);
					}}
					destroyOnClose={true}
				>
					<ImportFile toggleModal={this.toggleModal} updateList={this.getTable} />
				</Modal>
			</div>
		);
	};
}

class PublicList extends React.PureComponent {
	state = {
		value: null,
		data: [],
		table_search: {
			total: 0,
			pageSize: 10,
			pageNum: 1
		}
	};
	componentDidMount() {
		this.init();
	}
	init = () => {
		let { pageNum, pageSize } = this.state.table_search || {};
		if (_.isEmpty(this.props.url)) {
			return;
		}
		$.ajax({
			url: `${this.props.url}?pageNum=${pageNum}&pageSize=${pageSize}&orderByColumn=updateTime&isAsc=desc`,
			// data: {},
			cache: false,
			contentType: false,
			processData: false,
			type: 'GET',
			success: results => {
				if (results.code != 0) {
					message.error('接口错误');
					return;
				}
				let data = results.data || [];
				let { total } = data;
				this.setState({
					data: data.rows || [],
					table_search: {
						...this.state.table_search,
						total: total
					}
				});
			}
		});
	};
	render() {
		let { data = [], table_search = {} } = this.state;
		return (
			<div className="devier-list-box">
				<Radio.Group
					className="radio-list"
					value={this.state.value}
					onChange={e => {
						this.setState({
							value: e.target.value
						});
					}}
				>
					{data.map((item, index) => {
						let bool = (index + 1) % 4 == 0;
						return [
							<Radio.Button key={`radio_${index}`} value={item.id} className="radio-li">
								{item.cnName}
							</Radio.Button>,
							bool && <br key={`br_${index}`} />
						];
					})}
				</Radio.Group>
				<Pagination
					className="pagination-list"
					{...{
						current: table_search.pageNum,
						total: table_search.total,
						pageSize: table_search.pageSize,
						simple: true,
						onChange: (page, pageSize) => {
							this.setState(
								{
									table_search: {
										...this.state.table_search,
										pageNum: page,
										pageSize
									}
								},
								() => {
									this.init();
								}
							);
						}
					}}
				/>
			</div>
		);
	}
}

class ImportFile extends React.PureComponent {
	state = {
		file: {}
	};
	onImportExcel = file => {
		// 获取上传的文件对象

		const { files = [] } = file.target;
		let _file = files[0] || {};
		this.setState({
			file: _file
		});
		// // 通过FileReader对象读取文件
		// const fileReader = new FileReader();
		// fileReader.onload = event => {
		// 	try {
		// 		const { result } = event.target;
		// 		// 以二进制流方式读取得到整份excel表格对象
		// 		const workbook = XLSX.read(result, { type: 'binary' });
		// 		// 存储获取到的数据
		// 		let data = [];
		// 		// 遍历每张工作表进行读取（这里默认只读取第一张表）
		// 		for (const sheet in workbook.Sheets) {
		// 			// esline-disable-next-line
		// 			if (workbook.Sheets.hasOwnProperty(sheet)) {
		// 				// 利用 sheet_to_json 方法将 excel 转成 json 数据
		// 				data = data.concat(XLSX.utils.sheet_to_json(workbook.Sheets[sheet]));
		// 				// break; // 如果只取第一张表，就取消注释这行
		// 			}
		// 		}
		// 		// 最终获取到并且格式化后的 json 数据
		// 		message.success('导入成功！', 0.5);
		// 		// let dataSource = [];
		// 		// data.map((item, index) => {
		// 		// 	dataSource.push({ ...item, key: index });
		// 		// });
		// 		// this.setState({
		// 		// 	dataSource
		// 		// });
		// 		console.log(data);
		// 	} catch (e) {
		// 		// 这里可以抛出文件类型错误不正确的相关提示
		// 		message.error('文件类型不正确！', 0.5);
		// 	}
		// };
		// // 以二进制方式打开文件
		// fileReader.readAsBinaryString(files[0]);
	};
	render() {
		return (
			<div className="modal-box">
				<div className="device-upload-modal-content">
					<Button className="upload-wrap">
						<input className={'file-uploader'} type="file" accept=".xlsx, .xls" onChange={this.onImportExcel} />
						<span className={'upload-text'}>导入</span>
					</Button>
					<Input disabled value={this.state.file.name} />
				</div>
				<div className="btn-footer">
					<Button
						onClick={() => {
							this.props.toggleModal && this.props.toggleModal(false);
						}}
					>
						取消
					</Button>
					<Button
						onClick={() => {
							console.log(this.state.file);
							if (this.state.file) {
								var formData = new FormData();
								formData.append('devices', this.state.file);
								$.ajax({
									url: `/iot/device/device_upload`,
									data: formData,
									cache: false,
									dataType: 'json',
									contentType: false,
									// contentType: 'multipart/form-data',
									processData: false,
									type: 'POST',
									xhr: xhrOnProgress(function(evt) {
										var percent = Math.floor((evt.loaded / evt.total) * 100); //计算百分比
										console.log('上传进度：', percent);
									}),
									success: results => {
										if (results.code != 0) {
											message.error('接口错误', 0.5);
											return;
										}
										console.log(this.props);
										this.props.toggleModal && this.props.toggleModal(false);
										this.props.updateList && this.props.updateList();
										// this.setState({
										// 	data: results.data || []
										// });
									}
								});
							}
						}}
						type="primary"
						style={{
							marginLeft: '10px'
						}}
					>
						确定
					</Button>
				</div>
			</div>
		);
	}
}

ReactDOM.render(<ListBox />, document.getElementById(domId));
