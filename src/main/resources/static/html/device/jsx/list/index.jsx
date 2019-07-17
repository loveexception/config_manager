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
let { Table, Select, Button, Input, Icon, Cascader, Dropdown, Menu, message, Modal } = antd;
class ListBox extends React.PureComponent {
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
									// $.modal.openFull(
									// 	'修改驱动',
									// 	'/html/device/edit.html'
									// );
								}}
							>
								修改
							</Button>
							<Button
								onClick={() => {
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
		table_search: {
			total: 0,
			pageSize: 10,
			pageNum: 1
		},
		upload_visible: false
	};
	componentDidMount() {
		this.getSelectList();
		this.getTable();
	}
	getTable = () => {
		let { pageNum, pageSize } = this.state.table_search || {};
		$.ajax({
			url: `/iot/device/device_list?pageNum=${pageNum}&pageSize=${pageSize}`,
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
						data = data.concat(XLSX.utils.sheet_to_json(workbook.Sheets[sheet]));
						// break; // 如果只取第一张表，就取消注释这行
					}
				}
				// 最终获取到并且格式化后的 json 数据
				message.success('导入成功！', 0.5);
				// let dataSource = [];
				// data.map((item, index) => {
				// 	dataSource.push({ ...item, key: index });
				// });
				// this.setState({
				// 	dataSource
				// });
				console.log(data);
			} catch (e) {
				// 这里可以抛出文件类型错误不正确的相关提示
				message.error('文件类型不正确！', 0.5);
			}
		};
		// 以二进制方式打开文件
		fileReader.readAsBinaryString(files[0]);
	};
	render = () => {
		let { data = [], columns = [], select_list = [], cascader_list = [], table_search = {} } = this.state;
		const rowSelection = {
			onChange: (selectedRowKeys, selectedRows) => {
				console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
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
				<Menu.Item>
					<span>更新驱动</span>
				</Menu.Item>
				<Menu.Item>
					<span>添加业务类型</span>
				</Menu.Item>
				<Menu.Item>
					<span>网关解绑</span>
				</Menu.Item>
			</Menu>
		);
		return (
			<div className="device-list-body">
				<div
					className="device-list-content"
					style={{
						height: '90px'
					}}
				>
					<div className="device-list-content-li-2">
						<Select
							style={{ width: 120 }}
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
						<Button type="parimay">确认选择</Button>
					</div>
				</div>
				<div className="device-list-content">
					<div className="device-list-content-li">
						<Select
							className="select-box"
							// defaultValue="请选择分类"
							style={{ width: 114 }}
							// onChange={handleChange}
							placeholder="请选择分类"
						>
							{/* <Select.Option value="">请选择分类</Select.Option> */}
							<Select.Option value="a">驱动名称</Select.Option>
							<Select.Option value="b">设备分类</Select.Option>
							<Select.Option value="c">设备子类</Select.Option>
							<Select.Option value="d">设备品牌</Select.Option>
							<Select.Option value="e">设备型号</Select.Option>
						</Select>
						<Input.Search className="input-box" placeholder="请输入关键字" onSearch={value => console.log('点击搜索', value)} style={{ width: 254 }} />
					</div>
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
						// showQuickJumper: true,
					}}
					// pagination={{
					// 	current: 1,
					// 	pageSize: 10,
					// 	total: 10,
					// 	simple: true,
					// 	onChange: (page, pageSize) => {
					// 		console.log(page, pageSize);
					// 	}
					// }}
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
					onCancel={() => {
						this.toggleModal(false);
					}}
				>
					<div className="device-upload-modal-content">
						<Button className="upload-wrap">
							<input className={'file-uploader'} type="file" accept=".xlsx, .xls" onChange={this.onImportExcel} />
							<span className={'upload-text'}>导入</span>
						</Button>
						<Input />
					</div>
				</Modal>
			</div>
		);
	};
}
ReactDOM.render(<ListBox />, document.getElementById(domId));
