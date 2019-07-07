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
	state = { data: [], loading: false };
	componentDidMount() {
		this.init();
	}
	init = () => {
		this.setState({ loading: true });
		$.ajax({
			url: '/iot/driver/driver_list',
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
				this.setState({
					data: results.rows,
					loading: false
				});
			},
			error: () => {
				this.setState({ loading: false });
			}
		});
	};
	render = () => {
		const columns = [
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
				title: 'updateTime',
				dataIndex: 'updateTime'
			},
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
									$.modal.openFull('修改驱动', '/html/drive/edit.html');
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
		let { data = [] } = this.state;
		return (
			<div className="drive-list-body">
				<div className="drive-list-content">
					<div className="drive-list-content-li">
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
						simple: true
					}}
				/>
			</div>
		);
	};
}
ReactDOM.render(<ListBox />, document.getElementById(domId));
