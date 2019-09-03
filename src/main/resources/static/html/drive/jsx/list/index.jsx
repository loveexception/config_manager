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
let { Table, Select, Button, Input, Icon, message } = antd;
class ListBox extends React.PureComponent {
	state = {
		old_data: [],
		data: [],
		loading: false,
		column: '-1',
		search_data: [
			{
				title: '驱动名称',
				dataIndex: 'cnName'
			},
			{
				title: '驱动文件',
				dataIndex: 'enName'
			},
			{
				title: '驱动版本',
				dataIndex: 'driverVer'
			},
			{
				title: '分类',
				dataIndex: 'kindKind'
			},
			{
				title: '子类',
				dataIndex: 'kindSubkind'
			},
			{
				title: '采集设备品牌',
				dataIndex: 'kindCompany'
			},
			{
				title: '设备型号',
				dataIndex: 'kindType'
			},
			{
				title: '更新时间',
				dataIndex: 'updateTime'
			}
		],
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
		this.setState({ loading: true });
		$.ajax({
			url: `/iot/driver/driver_page?pageNum=${pageNum}&pageSize=${pageSize}&orderByColumn=updateTime&isAsc=desc`,
			// data: {},
			cache: false,
			contentType: false,
			processData: false,
			type: 'GET',
			success: results => {
				if (results.code != 0) {
					message.error('接口错误');
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
	render = () => {
		const columns = [
			...this.state.search_data,
			{
				title: '操作',
				key: 'operation',
				fixed: 'right',
				width: 100,
				render: (text, record) => {
					return (
						<div>
							<Button
								onClick={() => {
									$.modal.openFull('修改驱动', `/html/drive/edit.html?driver_id=${record.id}`);
								}}
							>
								修改
							</Button>
							<Button
								onClick={() => {
									$.ajax({
										cache: true,
										type: 'POST',
										url: '/iot/driver/driver_remove',
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
											message.info(results.msg);
											this.init();
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
		];
		let { data = [], table_search = {} } = this.state;
		return (
			<div className="drive-list-body">
				<div className="drive-list-content">
					<div className="drive-list-content-li">
						<Select
							className="select-box"
							value={this.state.column}
							style={{ width: 114 }}
							onChange={column => {
								this.setState({
									column
								});
							}}
							placeholder="请选择分类"
						>
							<Select.Option value="-1">全部</Select.Option>
							{this.state.search_data.map((item, index) => {
								return (
									<Select.Option key={index} value={item.dataIndex}>
										{item.title}
									</Select.Option>
								);
							})}
						</Select>
						<Input.Search
							className="input-box"
							placeholder="请输入关键字"
							onSearch={value => {
								let _data = this.state.old_data;
								if (this.state.column != '-1') {
									this.setState({ loading: true });
									if (value) {
										let data_new = _.filter(_data, { [this.state.column]: value });
										_data = data_new;
									}
									this.setState(
										{
											data: _data
										},
										() => {
											this.setState({ loading: false });
										}
									);
								} else {
									message.info('请选择分类');
								}
							}}
							style={{ width: 254 }}
						/>
					</div>
					<div className="drive-list-content-li">
						<Button
							type="primary"
							onClick={() => {
								$.modal.openFull('新增驱动', '/html/drive/add.html');
							}}
						>
							新增
						</Button>
					</div>
				</div>
				<Table
					rowKey={record => record.id}
					loading={this.state.loading}
					bordered
					columns={columns}
					dataSource={data}
					scroll={{ x: '150%' }}
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
									this.init();
								}
							);
						}
					}}
				/>
			</div>
		);
	};
}
ReactDOM.render(<ListBox />, document.getElementById(domId));
