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
let { Table, Select, Button, Input, Icon, Cascader, Dropdown, Menu } = antd;
class ListBox extends React.PureComponent {
	state = { data: [] };
	componentDidMount() {
		this.setState({
			data: [
				{
					name: 'John Brown',
					age: 32,
					address: 'New York Park'
				},
				{
					name: 'Jim Green',
					age: 40,
					address: 'London Park'
				}
			]
		});
	}
	render = () => {
		let { data = [] } = this.state;
		const columns = [
			{
				title: '设备编号',
				dataIndex: '设备编号',
				width: 150,
				fixed: 'left'
			},

			{
				title: 'SN编号',
				dataIndex: 'SN编号'
			},
			{ title: '名称', dataIndex: '名称' },
			{ title: '品牌', dataIndex: '品牌' },
			{ title: '型号', dataIndex: '型号' },
			{ title: '业务类型', dataIndex: '业务类型' },
			{ title: '驱动名称', dataIndex: '驱动名称' },
			{ title: '位置信息', dataIndex: '位置信息' },
			{ title: '所属组织', dataIndex: '所属组织' },
			{ title: '所属网关', dataIndex: '所属网关' },
			{ title: 'IP地址', dataIndex: 'IP地址' },
			{ title: '采集频率', dataIndex: '采集频率' },
			{ title: '价格', dataIndex: '价格' },
			{ title: '购入日期', dataIndex: '购入日期' },
			{ title: '使用年限', dataIndex: '使用年限' },
			{ title: '报废时间', dataIndex: '报废时间' },
			{ title: '资产状态', dataIndex: '资产状态' },
			{ title: '激活状态', dataIndex: '激活状态' },
			{ title: '告警状态', dataIndex: '告警状态' },
			{
				title: '更新时间',
				dataIndex: '更新时间'
			},
			{
				title: '操作',
				key: 'operation',
				fixed: 'right',
				width: 120,
				render: () => {
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
							<Button>删除</Button>
						</div>
					);
				}
			}
		];
		const rowSelection = {
			onChange: (selectedRowKeys, selectedRows) => {
				console.log(
					`selectedRowKeys: ${selectedRowKeys}`,
					'selectedRows: ',
					selectedRows
				);
			},
			getCheckboxProps: record => ({
				disabled: record.name === 'Disabled User', // Column configuration not to be checked
				name: record.name
			})
		};
		const options = [
			{
				value: '国网',
				label: '国网',
				children: [
					{
						value: '北京市',
						label: '北京市',
						children: [
							{
								value: '东城区',
								label: '东城区',
								children: [
									{
										value: '公司一',
										label: '公司一',
										children: [
											{
												value: '会议室一',
												label: '会议室一'
											}
										]
									}
								]
							}
						]
					}
				]
			}
			// {
			// 	value: 'jiangsu',
			// 	label: 'Jiangsu',
			// 	children: [
			// 		{
			// 			value: 'nanjing',
			// 			label: 'Nanjing',
			// 			children: [
			// 				{
			// 					value: 'zhonghuamen',
			// 					label: 'Zhong Hua Men'
			// 				}
			// 			]
			// 		}
			// 	]
			// }
		];

		const menu = (
			<Menu>
				<Menu.Item>
					<span>批量导入</span>
				</Menu.Item>
				<Menu.Item>
					<span>批量导出</span>
				</Menu.Item>
				<Menu.Item>
					<span>模版下载</span>
				</Menu.Item>
				<Menu.Item>
					<span>更新驱动</span>
				</Menu.Item>
				<Menu.Item>
					<span>添加标签</span>
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
						<Cascader
							defaultValue={[
								'国网',
								'北京市',
								'东城区',
								'公司一',
								'会议室一'
							]}
							options={options}
							// onChange={onChange}
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
						<Input.Search
							className="input-box"
							placeholder="请输入关键字"
							onSearch={value => console.log('点击搜索', value)}
							style={{ width: 254 }}
						/>
					</div>
					<div
						className="device-list-content-li"
						style={{
							display: 'flex',
							alignItems: 'center'
						}}
					>
						<Button
							type="primary"
							onClick={() => {
								$.modal.openFull(
									'新增驱动',
									'/html/device/add.html'
								);
							}}
						>
							新增
						</Button>
						<Dropdown
							className="content-li-dropdown"
							overlay={menu}
						>
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
						simple: true
					}}
				/>
			</div>
		);
	};
}
ReactDOM.render(<ListBox />, document.getElementById(domId));
