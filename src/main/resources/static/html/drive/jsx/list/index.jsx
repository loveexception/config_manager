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
let { Table, Select, Button, Input, Icon } = antd;
class ListBox extends React.PureComponent {
	render = () => {
		const columns = [
			{
				title: '驱动名称',
				dataIndex: 'name',
				key: 'name'
			},
			{
				title: '驱动文件',
				dataIndex: 'age',
				key: 'age'
			},
			{ title: '驱动版本', dataIndex: 'address', key: '1' },
			{ title: '分类', dataIndex: 'address', key: '2' },
			{ title: '子类', dataIndex: 'address', key: '3' },
			{ title: '采集设备品牌', dataIndex: 'address', key: '4' },
			{ title: '设备型号', dataIndex: 'address', key: '5' },
			{
				title: '操作',
				key: 'operation',
				fixed: 'right',
				width: 100,
				render: () => {
					return (
						<div>
							<Icon type="edit" />
							&nbsp;&nbsp;
							<Icon type="delete" />
						</div>
					);
				}
			}
		];

		const data = [
			{
				key: '1',
				name: 'John Brown',
				age: 32,
				address: 'New York Park'
			},
			{
				key: '2',
				name: 'Jim Green',
				age: 40,
				address: 'London Park'
			}
		];
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
						<Input.Search
							className="input-box"
							placeholder="请输入关键字"
							onSearch={value => console.log('点击搜索', value)}
							style={{ width: 254 }}
						/>
					</div>
					<div className="drive-list-content-li">
						<Button
							type="primary"
							onClick={() => {
								$.modal.openFull(
									'新增驱动',
									'/html/drive/add.html'
								);
							}}
						>
							新增
						</Button>
					</div>
				</div>
				<Table
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
