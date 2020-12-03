let { useRef, useState, useEffect, useLayoutEffect, PureComponent } = React;
let allHeight = 150;// 图高度设置 不包括饼图
let DiagramAction = window.DiagramAction;
let { Popconfirm, Upload, Tag, UpCircleFilled, Form, message, Spin, Icon, Input, InputNumber, Button, Table, Tooltip, Modal, DatePicker, BackTop, ConfigProvider } = antd;
let { confirm } = Modal;
let Message = message;
let { RangePicker } = DatePicker;
let { FormOutlined, LoadingOutlined } = icons;

let { getMeetingLevelByPage,
	getMeetingLevelByDept,
	batchDelete,
	getMeetingLevelSave,
	getMeetingLevelUpdate, getMeetingByPage, batchDeleteMeeting, getDurationStats } = window.backgroundInterface;
let { _import = {}, } = window;
let { TableComponent, MyIcon } = _import;
moment.locale('zh-cn')

let emptyImg = <svg className="ant-empty-img-simple" width="64" height="41" viewBox="0 0 64 41" xmlns="http://www.w3.org/2000/svg"><g transform="translate(0 1)" fill="none" fill-rule="evenodd"><ellipse class="ant-empty-img-simple-ellipse" cx="32" cy="33" rx="32" ry="7"></ellipse><g class="ant-empty-img-simple-g" fill-rule="nonzero"><path d="M55 12.76L44.854 1.258C44.367.474 43.656 0 42.907 0H21.093c-.749 0-1.46.474-1.947 1.257L9 12.761V22h46v-9.24z"></path><path d="M41.613 15.931c0-1.605.994-2.93 2.227-2.931H55v18.137C55 33.26 53.68 35 52.05 35h-40.1C10.32 35 9 33.259 9 31.137V13h11.16c1.233 0 2.227 1.323 2.227 2.928v.022c0 1.605 1.005 2.901 2.237 2.901h14.752c1.232 0 2.237-1.308 2.237-2.913v-.007z" class="ant-empty-img-simple-path"></path></g></g></svg>

// 请求函数 
function chartFn({ begin_time = Date.now(), end_time = Date.now() }, upDateFn = {}) {
	typeof upDateFn.isLoadingLeft === 'function' && upDateFn.isLoadingLeft(true)
	typeof upDateFn.isLoadingright === 'function' && upDateFn.isLoadingright(true)
	typeof upDateFn.visualBottomOfLoading === 'function' && upDateFn.visualBottomOfLoading(true)
	if (moment.isMoment(begin_time)) {
		begin_time = begin_time.valueOf();
		end_time = begin_time.valueOf();
	}
	getDurationStats({ begin_time, end_time }, function (res) {
		if (res.success) {
			// typeof upDateFn.visualBottomData === 'function' && upDateFn.visualBottomData(res.data);
			typeof upDateFn.visualBottomData === 'function' && upDateFn.visualBottomData({
				"2020-04-11": {
					"count": 5,
					"sum": 296.5,
					"min": 0.0,
					"max": 144.0,
					"average": 59.3
				},
				"2020-04-14": {
					"count": 3,
					"sum": 0.0,
					"min": 0.0,
					"max": 0.0,
					"average": 0.0
				}
			});
		} else {
			message.error('接口报错', .5);
		}
	})
}
function dateUtil(time) {
	let result = time.getFullYear() + '-' + (time.getMonth() - 0 + 1) + '-' + time.getDate() + ' ' + time.getHours() + ':' + time.getMinutes();
	return result;
}
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

	onChange = (field, value, xx) => {
		let { useChartObj = {} } = this.props;
		console.log(field, value)
		this.setState({
			[field]: value,
		}, function () {
			if (this.state.endValue && this.state.startValue) {
				// loading  
				// 发请求
				chartFn({ begin_time: this.state.startValue, end_time: this.state.endValue }, useChartObj)
			}

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
				<ConfigProvider locale={antd.locales && antd.locales.zh_CN}>
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
				</ConfigProvider>
			</div>
		);
	}
}



//圆柱图 && 饼图
function VisualTop(props) {
	let [count, setCount] = useState(1);
	let [isLoadingLeft, setIsLoadingLeft] = useState(false);
	let [isLoadingRight, setIsLoadingRight] = useState(false)
	let [visualTopData, setVisualTopData] = useState([])
	let [chartObj, setChartObj] = props.useChartObj;
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
			{ year: '重要会议', value: 3 },
			{ year: '次要会议', value: 4 },
			{
				year: '紧急会议', value: 3.5
			},
			{ year: '正常例会', value: 5 },
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
			// showCrosshairs: true, // 展示 Tooltip 辅助线
			shared: true,
			showTitle: false,
			itemTpl: '<li class="top-test">{value}h</li>',
		});
		// 滑块选取
		// chart.interaction('brush');
		// 控制大小site
		chart.interval().position('year*value').color('l(90) 0:#4c71fe 1:rgba(76, 113, 254, 0.64) ').size(15).shape('date*actual', (date, val) => {
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
			// offsetX: -20
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
	//更新数据方法
	useEffect(() => {
		setChartObj({ ...chartObj, visualTopData: setVisualTopData, IsLoadingLeft: setIsLoadingLeft, isLoadingRight: setIsLoadingRight })
	}, [])
	return <div className="visual-top-box">
		{/* <Spin style={{
		}} spinning={isLoadingRight} tip={"加载中..."} indicator={
			<LoadingOutlined style={{ fontSize: 24 }} spin />
		}> */}
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
		{/* </Spin> */}
		{/* <Spin style={{
		}} spinning={isLoadingRight} tip={"加载中..."} indicator={
			<LoadingOutlined style={{ fontSize: 24 }} spin />
		}> */}
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
		{/* </Spin > */}
	</div >




}

// 折线图
function VisualBottom(props) {
	let [isLoading, setIsLoading] = useState(false);
	let [visualBottomData, setVisualBottomData] = useState({})
	let [chartObj, setChartObj] = props.useChartObj;
	let line = useRef(null);
	useEffect(() => {
		line.current.firstChild && line.current.removeChild(line.current.firstChild)
		const data = [];

		for (let key in visualBottomData) {
			data.push({ year: key, value: visualBottomData[key].sum })
		}
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
			itemTpl: '<li class="test">{value}h</li>',
		});

		chart.render();
	}, [visualBottomData])
	useEffect(() => {
		setChartObj({ ...chartObj, visualBottomData: setVisualBottomData, visualBottomOfLoading: setIsLoading })
	}, [])
	return <Spin style={{
	}} spinning={isLoading} tip={"加载中..."} indicator={
		<LoadingOutlined style={{ fontSize: 24 }} spin />
	}>
		<div className="line-box ">
			<div className="line-title view-title">
				会议保障时长
		</div>
			<span className="line-unit view-unit">
				单位: 小时
		</span>
			<div id="line-content" ref={line}>
			</div>
		</div>
	</Spin >
}

// retrun 图函数
function ImgFn(props) {
	let { showImg, } = props;
	return showImg === undefined ? '' : showImg ? <LoadingOutlined style={{ fontSize: 24 }} spin /> : <svg className="ant-empty-img-simple" width="64" height="41" viewBox="0 0 64 41" xmlns="http://www.w3.org/2000/svg"><g transform="translate(0 1)" fill="none" fill-rule="evenodd"><ellipse class="ant-empty-img-simple-ellipse" cx="32" cy="33" rx="32" ry="7"></ellipse><g class="ant-empty-img-simple-g" fill-rule="nonzero"><path d="M55 12.76L44.854 1.258C44.367.474 43.656 0 42.907 0H21.093c-.749 0-1.46.474-1.947 1.257L9 12.761V22h46v-9.24z"></path><path d="M41.613 15.931c0-1.605.994-2.93 2.227-2.931H55v18.137C55 33.26 53.68 35 52.05 35h-40.1C10.32 35 9 33.259 9 31.137V13h11.16c1.233 0 2.227 1.323 2.227 2.928v.022c0 1.605 1.005 2.901 2.237 2.901h14.752c1.232 0 2.237-1.308 2.237-2.913v-.007z" class="ant-empty-img-simple-path"></path></g></g></svg>
}
function App() {
	let [isShowBtn, setIsShowBtn] = useState(false);
	let [isLoading, setIsLoading] = useState(true);
	let [search, setSearch] = useState({})
	let [dataSource, setDataSource] = useState([]);
	let [chartObj, setChartObj] = useState({});
	let [paginationConfig, setPaginationConfig] = useState({
		current: 1,
		pageSize: 5,
		// total: 550,
		onChange(next, pageSize) {
			getTableData({}, next, pageSize)
			// setPaginationConfig({
			// 	...paginationConfig,
			// 	current: next
			// })
			// console.log(arguments, 'arguments')
		}
	})
	// let [deleteFn, setDeleteFn] = useState()
	let [rowKey, setRowKey] = useState([])
	let columns = useRef([
		{
			title: '级别名称',
			dataIndex: 'name',
			key: 'name',
			align: 'center',
			sorter: true

		},
		{
			title: '权限字符',
			dataIndex: 'en_name',
			key: 'en_name',
			// width: "10%",
			align: 'center',
			sorter: true


		},
		{
			title: '级别描述',
			dataIndex: 'remark',
			key: 'remark',
			// width: "10%",
			align: 'center',
			sorter: true


		},
		// {
		// 	title: '创建时间',
		// 	dataIndex: 'length',
		// 	key: 'length',
		// 	align: 'center',
		// 	sorter: true

		// },
		{
			title: '操作',
			key: '操作',
			width: "10%",
			align: 'center',
			render(text, record) {
				return <div style={{
					display: 'flex',
					justifyContent: 'space-around'
				}
				} >
					<MyIcon _props={{
						style: {
							fontSize: "0.18rem"
						}
					}} click={() => {
						console.log(record, 'record')
						window.currentEditObj = { ...record };
						$.modal.openFull('修改会议', `/html/meetinglevel/edit/index.html?edit=true&id=${record.id}` + `&dept_id=${window.locationParams.dept_id}&ip=${window.locationParams.ip}`)
					}} iconKey={"FormOutlined"} placement={"top"} text={"修改"} />
					<Popconfirm placement="top" title={'确定此级别吗?'} onConfirm={() => {
						// deleteFn([record], this.props.reqListFn);
						deleteIcon(record.id)
					}} okText="删除" cancelText="取消">
						<div>

							<MyIcon _props={{
								style: {
									fontSize: "0.18rem"
								}
							}} iconKey={"DeleteOutlined"} click={function () { }} placement={"top"} text={"删除"} />
						</div>
					</Popconfirm>
				</div >

			}

		}
	])
	let btnArr = [{
		type: "primary",
		size: 'default',
		text: '新增',
		click: newAdd
	}, {
		type: "dashed",
		size: 'default',
		text: '删除',
		click: function () {
			if (rowKey.length === 0) {
				message.info('请选择删除项.', .5)
				return
			}
			confirm({
				content: '你确定要删除这些数据吗?',
				onOk() {
					deleteFn()
				},
				onCancel() {
				},
			})

		}
	},
		// {
		// 	type: "dashed",
		// 	size: 'default',
		// 	text: '批量导入',
		// 	click: allImport
		// }
	];
	let selectValue = useRef({
		config: [
			{ key: '全部', value: true },
			{ key: '级别名称', value: 'name' },
			{ key: '权限字符', value: 'en_name' },
			{ key: '级别描述', value: 'remark' },
		],
		selectCurrent: true,
	})  //选择器配置 容器
	let rightConfig = useRef({
		searchConfig: {
			onSearch: searchFn,
			placeholder: "请输入搜索内容"
		},
		select: {
			selectArr: selectValue.current.config,
			onSelectChange: selectChangeFn,
			defaultValue: selectValue.current.config[0].value  // 第几个的value值
		}
	})
	let node = useRef(null);

	// 新增click 事件
	function newAdd() {
		let obj = {};
		for (let key in window.currentEditObj) {
			obj[key] = '';
		}
		window.currentEditObj = obj;
		$.modal.openFull('添加级别', '/html/meetinglevel/edit/index.html?edit=false&id=502' + `&dept_id=${window.locationParams.dept_id}&ip=${window.locationParams.ip}`)
	}
	function confirmDelete() {
		deleteIcon()
	}
	function deleteIcon(id) { //icon 删除
		deleteFn([id])
	}
	function allImport() {

	}
	// 右边
	function searchFn(value) {
		let params = {}
		if (selectValue.current.selectCurrent === true) { //全部
			// params.
			params.all_query = value;
		} else {
			// name
			// level
			// user_name
			params[selectValue.current.selectCurrent] = value
		}
		setSearch(params)
		getTableData(params)
	}
	function selectChangeFn(value) {
		selectValue.current.selectCurrent = value;
	}
	function handleScroll(e) {
		node.current.scrollTop > 0 ? setIsShowBtn(true) : setIsShowBtn(false)
	}
	function deleteFn(id) {
		setIsLoading(true)
		batchDelete({
			ids: id || rowKey
		}, function (res) {
			if (res.success) {
				message.success('删除成功', .5)
				getTableData()
			} else {

			}
		})
	}
	function onSelectChange(rowKey, current) {
		setRowKey(rowKey)
	};
	const rowSelection = {
		onChange: onSelectChange,
	};

	//获取 数据 function search ?
	function getTableData(searchConfig = {}, next, pageSize) {
		setIsLoading(true)
		getMeetingLevelByPage({
			page_num: next || paginationConfig.current,
			page_size: pageSize || paginationConfig.pageSize,
			...searchConfig,
			...search
		}, (res) => {
			if (res.success) {
				setPaginationConfig({
					...paginationConfig,
					current: res.data.current_page,
					total: res.data.total,
					pageSize: res.data.page_size,
				})
				setDataSource(res.data.result)
			} else {
				message.error('接口报错', .5)
			}
			setIsLoading(false)
		})
	}
	useEffect(function () {
		window.getTableData = getTableData;
		getTableData()
	}, [])

	useEffect(function () {
	}, [rowKey])

	return <div onScroll={handleScroll} className="meeting-level-box" style={{
	}} ref={node}>
		<div className="table-box">
			<TableComponent
				isLoading={isLoading}
				paginationConfig={paginationConfig}
				rightConfig={rightConfig.current}
				btnArr={btnArr}
				title={"会议级别"}
				isBordered={true}
				dataSource={dataSource}
				rowSelection={rowSelection}
				columns={columns.current}
				onChange={function (selectedRowKeys, selectedRows, orderByClause) {
					let { columnKey, order } = orderByClause;
					getTableData({
						order_by_clause: `${columnKey} ${!order ? '' : order == "ascend" ? 'asc' : 'desc'}`,
						...search

					})
					// let { columnKey, order } = orderByClause;
					// // console.log(this.props, 'ss'),
					// this.props.reqListFn(this.state.currentPage, order ? `${columnKey} ${order.slice(0, -3)}` : '')
					// this.setState({
					// 	checkArr: selectedRows,
					// });
				}}
			/>
		</div>
	</div >

}

ReactDOM.render(<App />, window.rootNode);
