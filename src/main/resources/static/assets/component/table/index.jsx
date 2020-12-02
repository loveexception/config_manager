(function (w) {
	// 检查依赖
	if (!antd) {
		throw new Error('自定义组件缺少antd 依赖')
	}
	if (!React) {
		throw new Error('自定义组件缺少React 依赖')
	}
	// 建议使用4.0 antd 以上
	if (!w._import) {
		w._import = {}
	}
	let { Input, Pagination, ConfigProvider, Table, Button, Tag, Select, Upload } = antd;
	const { Search } = Input;
	function HeaderBox(props) {
		let { btnArr = [], title = "", rightConfig = {}, selectConfig } = props;
		return <div className="header-box">
			<div className="header-title">{title}</div>
			<div className="use-box">
				<div className="use-left">
					{!_.isEmpty(btnArr) && btnArr.map((e, index) => {
						if (e.upload) {

							return <Upload {...e.uploadProps} key={index}>
								<Button onClick={e.click} type={e.type} size={e.size}>
									{e.text}
								</Button>
							</Upload>
						}
						return <Button key={index} onClick={e.click} type={e.type} size={e.size}>
							{e.text}
						</Button>
					})}

				</div>
				<div className="use-right">
					<Select style={{ width: 120 }} defaultValue={rightConfig.select.defaultValue} onChange={rightConfig.select.onSelectChange}>
						{
							rightConfig.select && rightConfig.select.selectArr.map((element, index) => (<Option value={element.value} key={element.key}>{element.key}</Option>))
						}

					</Select>
					<Search
						placeholder={rightConfig.searchConfig && rightConfig.searchConfig.placeholder}
						onSearch={rightConfig.searchConfig.onSearch}
						style={{ width: '2rem' }}
					/>
				</div>
			</div>

		</div>
	}
	function TableComponent(props) { // 组件名唯一
		let { title = "传入help为true获取config信息", total = 10, rightConfig, btnArr = [], help = false, rowSelection = {}, dataSource = [], data = {}, columns = [], paginationConfig = false, isBordered = false, isLoading = false, onChange = function () { } } = props;
		function handleChange(pagination, filters, sorter) {

		}
		useEffect(function () {
			if (help) {
				console.log("title :string 标题信息")
				console.log("btnArr : Array<object> 按钮信息 ")
				console.log("data : Object({}) : >>> dataSource : Array<object> ([]) >>> paginationConfig :object (false) >>> column : Array<object> >>> isBordered:boolean (false) || object   >>> data : object 直接传入组件 ")
			}
		}, [])
		return (<div className="my-table-component-box">
			<div className="test-less-import">
				您好像没引入less文件那
			</div>
			<div className="my-table-component-content">
				<HeaderBox rightConfig={rightConfig} title={title} btnArr={btnArr} />
				<div className="my-table-container">
					<ConfigProvider locale={antd.locales && antd.locales.zh_CN}>
						<Table loading={isLoading} pagination={false} bordered={isBordered} rowSelection={rowSelection} {...data} columns={columns} dataSource={dataSource} onChange={onChange} rowKey={record => record.id} />
						{paginationConfig ? <Pagination style={{
						}} {...paginationConfig} size="small" showQuickJumper /> : ""}
					</ConfigProvider>
				</div>
			</div>
		</div>)
	}
	w._import.TableComponent = TableComponent;

})(window)