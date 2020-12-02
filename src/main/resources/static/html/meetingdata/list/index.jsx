let { useRef, useState, useEffect, useLayoutEffect, PureComponent } = React;
let allHeight = 150; // 图高度设置 不包括饼图
let DiagramAction = window.DiagramAction;
let { Popconfirm, Upload, Tag, UpCircleFilled, Form, message, Spin, Icon, Input, InputNumber, Button, Table, Tooltip, Modal, DatePicker, BackTop, ConfigProvider } = antd;
let { confirm } = Modal;
let Message = message;
let { RangePicker } = DatePicker;
let { FormOutlined, LoadingOutlined } = icons;

let { downLoad, getDownLoadUrl, getMeetingByPage, batchDeleteMeeting, getDurationStats, getMeetingLevelByDept, getMeetingLevelStats } = window.backgroundInterface;
let { _import = {} } = window;
let { TableComponent, MyIcon } = _import;
moment.locale('zh-cn');
getMeetingLevelByDept({}, function (res) {
	if (res.success) {
		window.meetingLevelObj = res.data;
	} else {
		window.meetingLevelObj = [];
		message.error('接口报错', 0.5);
	}
});

function toDayNow() {
	let time = new Date(Date.now());
	let now = time.getFullYear() + '-' + (time.getMonth() - 0 + 1) + '-' + time.getDate();
	let result = moment(now).valueOf();

	result = Date.now() - 1000 * 60 * 60 * 24 * 30;
	return result;
}
// 请求函数
function chartFn({ begin_time = toDayNow(), end_time = Date.now() }, upDateFn = {}) {
	let fn = function () {};
	function getYearAndMonthAndDay(startTime, endTime) {
		// debugger
		var date_all = [],
			i = 0;
		// var startTime = start.getDate();
		// var endTime = end.getDate();
		while (endTime.getTime() - startTime.getTime() >= 0) {
			var year = startTime.getFullYear();
			var month = (startTime.getMonth() + 1).toString().length == 1 ? '0' + (startTime.getMonth() + 1).toString() : (startTime.getMonth() + 1).toString();
			var day = startTime.getDate().toString().length == 1 ? '0' + startTime.getDate() : startTime.getDate();
			date_all[i] = year + '-' + month + '-' + day;
			startTime.setDate(startTime.getDate() + 1);
			i += 1;
		}
		return date_all;
	}
	let { isLoadingLeft = fn, isLoadingRight = fn, visualBottomOfLoading = fn, visualBottomData = fn, visualTopData = fn } = upDateFn;
	isLoadingRight(true);
	visualBottomOfLoading(true);
	if (moment.isMoment(begin_time)) {
		begin_time = begin_time.valueOf();
		end_time = end_time.valueOf();
	}
	let paramTime = { begin_time, end_time };
	getDurationStats(paramTime, function (res) {
		if (res.success) {
			let start = 0;
			let end = 0;
			let arr = [];
			for (let k in res.data) {
				// moment(k)
				// debugger
				let m = moment(k).valueOf();
				start = start ? (m < start ? m : start) : m;
				end = end > m ? end : m;
			}

			if (start && end) {
				arr = getYearAndMonthAndDay(new Date(start), new Date(end));
			}
			arr.forEach((e) => {
				res.data[e] = res.data[e] ? res.data[e] : { sum: 0 }; //补0
			});
			visualBottomData(res.data);
			setTimeout(() => {
				visualBottomOfLoading(false);
			}, 300);
		} else {
			message.error('接口报错', 0.5);
		}
	});
	getMeetingLevelStats(paramTime, function (res) {
		if (res.success) {
			setTimeout(() => {
				isLoadingRight(false);
			}, 300);
			visualTopData(res.data);
		} else {
			message.error('接口报错', 0.5);
		}
	});
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
		begin_time: toDayNow(),
		end_time: Date.now(),
	};

	disabledStartDate = (startValue) => {
		const { endValue } = this.state;
		if (!startValue || !endValue) {
			return false;
		}
		return startValue.valueOf() > endValue.valueOf();
	};

	disabledEndDate = (endValue) => {
		const { startValue } = this.state;
		if (!endValue || !startValue) {
			return false;
		}
		return endValue.valueOf() <= startValue.valueOf();
	};
	componentDidMount() {
		console.log(this.state, 'xxxs');
		setTimeout(() => {
			chartFn({}, this.props.useChartObj);
			this.props.getResetFn && this.props.getResetFn(this.resetTimeData);
		}, 500);
	}
	onChange = (field, value, xx) => {
		let { useChartObj = {} } = this.props;
		this.setState(
			{
				[field]: value,
			},
			() => {
				if (this.state.endValue && this.state.startValue) {
					// loading
					// 发请求
					chartFn({ begin_time: this.state.startValue, end_time: this.state.endValue }, useChartObj);
				}
			}
		);
	};
	onChange2 = (field, value, xx) => {
		let { useChartObj = {} } = this.props;
		let [begin_time, end_time] = field;
		this.setState({
			begin_time,
			end_time,
		});
		if (begin_time && end_time) {
			chartFn({ begin_time, end_time }, useChartObj);
		}
	};
	resetTimeData = () => {
		let { useChartObj = {} } = this.props;
		chartFn({ begin_time: this.state.begin_time, end_time: this.state.end_time }, useChartObj);
	};
	onStartChange = (value) => {
		this.onChange('startValue', value);
	};

	onEndChange = (value) => {
		this.onChange('endValue', value);
	};

	handleStartOpenChange = (open) => {
		if (!open) {
			this.setState({ endOpen: true });
		}
	};

	handleEndOpenChange = (open) => {
		this.setState({ endOpen: open });
	};

	render() {
		const { startValue, endValue, endOpen } = this.state;
		return (
			<div
				style={{
					display: 'flex',
					textAlign: 'center',
					lineHeight: '0.3rem',
				}}
			>
				<span className="data-title">会议数据统计表</span>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<ConfigProvider locale={antd.locales && antd.locales.zh_CN}>
					<RangePicker defaultValue={[moment(toDayNow()), moment(Date.now())]} onChange={this.onChange2} showTime format="YYYY/MM/DD HH:mm:ss" />
				</ConfigProvider>
			</div>
		);
	}
}

//圆柱图 && 饼图
function VisualTop(props) {
	let [count, setCount] = useState(0);
	let [isLoadingLeft, setIsLoadingLeft] = useState(false);
	let [isLoadingRight, setIsLoadingRight] = useState(false);
	let [visualTopData, setVisualTopData] = useState([]);
	let [chartObj, setChartObj] = props.useChartObj;
	let pie = useRef(null);
	let circle = useRef(null);
	// 圆柱
	useEffect(() => {
		circle.current.firstChild && circle.current.removeChild(circle.current.firstChild);
		const data = []; //{ year: '正常例会', value: 5 },
		visualTopData.forEach((e) => {
			data.push({
				year: e.level_name,
				value: e.duration,
			});
		});
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
		chart
			.interval()
			.position('year*value')
			.color('l(90) 0:#4c71fe 1:rgba(76, 113, 254, 0.64) ')
			.size(15)
			.shape('date*actual', (date, val) => {
				if (val === 0) {
					return;
				}
				return 'border-radius';
			});
		chart.render();
	}, [visualTopData]);
	//饼图
	useEffect(() => {
		pie.current.firstChild && pie.current.removeChild(pie.current.firstChild);
		let data = [];
		let totalCount = 0;
		visualTopData.forEach((e) => {
			totalCount += e.count;
		});
		setCount(totalCount);
		visualTopData.forEach((e) => {
			if (e.count === 0) {
				return;
			}
			data.push({
				item: e.level_name,
				count: e.count,
				percent: Number((e.count / totalCount).toFixed(2)),
			});
		});
		if (data.length === 0) {
			return;
		}
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
				val = (val * 100).toFixed(0) + '%';
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
			.color('item', ['rgb(76, 113, 254)', 'rgb(112, 141, 254)', 'rgb(183, 198, 255)', 'rgb(201, 212, 255)'])
			// .color('item')
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
	}, [visualTopData]);
	//更新数据方法
	useEffect(() => {
		if (chartObj.visualTopData) {
			return;
		}
		let config = { ...chartObj, visualTopData: setVisualTopData, isLoadingLeft: setIsLoadingLeft, isLoadingRight: setIsLoadingRight };
		setChartObj(config);
	}, [chartObj]);
	return (
		<Spin style={{}} spinning={isLoadingRight} tip={'加载中...'} indicator={<LoadingOutlined style={{ fontSize: 24 }} spin />}>
			{' '}
			<div className="visual-top-box">
				<div className="circle-box padding-view">
					<div className="view-title">会议级别时长</div>
					<span className="view-unit">单位: 小时</span>

					<div id="circle" ref={circle}></div>
				</div>

				<div className="pie-box padding-view">
					<div className="view-title">会议级别统计</div>
					<span className="view-unit">单位: 次</span>
					<div className="all-count">
						<div className="all-title">
							总次数
							<br />
							<span className="all-number">{count}</span>
						</div>
					</div>

					{/* {visualTopData.length === 0 || true ? <div className="">
					{emptyImg}
				</div> : */}
					<div id="pie" ref={pie}></div>
					{/* } */}
				</div>
			</div>
		</Spin>
	);
}

// 折线图
function VisualBottom(props) {
	let [isLoading, setIsLoading] = useState(false);
	let [visualBottomData, setVisualBottomData] = useState({});
	let [chartObj, setChartObj] = props.useChartObj;
	let line = useRef(null);
	useEffect(() => {
		line.current.firstChild && line.current.removeChild(line.current.firstChild);
		let data = [];

		for (let key in visualBottomData) {
			data.push({ year: key, value: Number(visualBottomData[key].sum.toFixed(2)) });
		}
		data = data.sort((e1, e2) => {
			return moment(e1.year).valueOf() - moment(e2.year).valueOf();
		});
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
			crosshairs: {
				type: 'xy', // 展示十字辅助线
			},
		});

		// chart.line().position('year*value').label('value');
		chart.point().position('year*value');
		chart.area().position('year*value').style({
			fill: 'l(90) 0:rgb(119, 147, 254) 1:rgb(85, 120, 254)',
		});
		chart.tooltip({
			// showCrosshairs: true, // 展示 Tooltip 辅助线
			shared: true,
			showTitle: false,
			itemTpl: '<li class="test">{value}h</li>',
		});

		chart.render();
	}, [visualBottomData]); //duixiang
	useEffect(() => {
		if (chartObj.visualBottomData) {
			return;
		}
		setChartObj({ ...chartObj, visualBottomData: setVisualBottomData, visualBottomOfLoading: setIsLoading });
	}, [chartObj]);
	return (
		<Spin style={{}} spinning={isLoading} tip={'加载中...'} indicator={<LoadingOutlined style={{ fontSize: 24 }} spin />}>
			<div className="line-box ">
				<div className="line-title view-title">会议保障时长</div>
				<span className="line-unit view-unit">单位: 小时</span>
				<div>
					{/* {visualBottomData.key ?  */}
					<div id="line-content" ref={line}></div>
				</div>
			</div>
		</Spin>
	);
}

// retrun 图函数
function ImgFn(props) {
	let { showImg } = props;
	return showImg === undefined ? (
		''
	) : showImg ? (
		<LoadingOutlined style={{ fontSize: 24 }} spin />
	) : (
		<svg className="ant-empty-img-simple" width="64" height="41" viewBox="0 0 64 41" xmlns="http://www.w3.org/2000/svg">
			<g transform="translate(0 1)" fill="none" fill-rule="evenodd">
				<ellipse class="ant-empty-img-simple-ellipse" cx="32" cy="33" rx="32" ry="7"></ellipse>
				<g class="ant-empty-img-simple-g" fill-rule="nonzero">
					<path d="M55 12.76L44.854 1.258C44.367.474 43.656 0 42.907 0H21.093c-.749 0-1.46.474-1.947 1.257L9 12.761V22h46v-9.24z"></path>
					<path d="M41.613 15.931c0-1.605.994-2.93 2.227-2.931H55v18.137C55 33.26 53.68 35 52.05 35h-40.1C10.32 35 9 33.259 9 31.137V13h11.16c1.233 0 2.227 1.323 2.227 2.928v.022c0 1.605 1.005 2.901 2.237 2.901h14.752c1.232 0 2.237-1.308 2.237-2.913v-.007z" class="ant-empty-img-simple-path"></path>
				</g>
			</g>
		</svg>
	);
}
function App() {
	let [isShowBtn, setIsShowBtn] = useState(false);
	let [isLoading, setIsLoading] = useState(true);
	let [dataSource, setDataSource] = useState([]);
	let [search, setSearch] = useState({});
	let [chartObj, setChartObj] = useState({});
	let [_paginationConfig, _setPaginationConfig] = useState({
		current: 1,
		pageSize: 5,
	});
	let [paginationConfig, setPaginationConfig] = useState({
		current: 1,
		pageSize: 5,
		handleClosure: { current: 1, pageSize: 5 },
		// total: 550,
		onChange(next, pageSize) {
			_setPaginationConfig({
				current: next,
				pageSize,
			});
			getTableData({}, next, pageSize);
			// setPaginationConfig({
			// 	...paginationConfig,
			// 	current: next
			// })
		},
	});
	let [rowKey, setRowKey] = useState([]);
	let resetFn = useRef(() => {
		return () => {};
	});
	// let [deleteFn, setDeleteFn] = useState()
	let selectValue = useRef({
		config: [
			// {key: '全部', value: 0 },
			// {key: '会议名称', value: 0 },
			// {key: '开始时间', value: 0 },
			// {key: '结束时间', value: 0 },
			// {key: '会议时长', value: 0 },
			// {key: '会议级别', value: 0 },
			// {key: '保证人员', value: 0 },
			{ key: '全部', value: true },
			{ key: '会议名称', value: 'name' },
			{ key: '会议级别', value: 'level' },
			{ key: '保证人员', value: 'user_name' },
		],
		selectCurrent: true,
	}); //选择器配置 容器
	let rightConfig = useRef({
		searchConfig: {
			onSearch: searchFn,
			placeholder: '请输入搜索内容',
		},
		select: {
			selectArr: selectValue.current.config,
			onSelectChange: selectChangeFn,
			defaultValue: selectValue.current.config[0].value, // 第几个的value值
		},
	});
	let node = useRef(null);

	// 新增click 事件
	function newAdd() {
		let obj = {};
		for (let key in window.currentEditObj) {
			obj[key] = undefined;
		}
		window.currentEditObj = obj;
		$.modal.openFull('新增会议', '/html/meeting/edit/index.html?edit=false&id=502');
	}
	function confirmDelete() {
		deleteIcon();
	}
	function deleteIcon(id) {
		//icon 删除

		deleteFn([id]);
	}
	function dowloadFn() {
		//模板下载
		getDownLoadUrl({}, function (res) {
			if (res.success) {
				window.top.open(res.data);
				// console.log(, 'xx')
				// window.open(res.data)
				// let dealDownload = (res, fileName) => {
				// 	let $blob = new Blob([res]);
				// 	if (window.navigator.msSaveOrOpenBlob) {
				// 		navigator.msSaveBlob($blob, fileName);
				// 	} else {
				// 		// 创建 a 标签并为其添加属性
				// 		let $a = document.createElement("a");
				// 		// 创建下载链接
				// 		$a.href = window.URL.createObjectURL($blob);
				// 		$a.download = fileName;
				// 		document.body.appendChild($a);
				// 		// 触发点击事件执行下载
				// 		$a.click();
				// 		document.body.removeChild($a);
				// 	}
				// }
				// dealDownload(res.data, '模板.xlsx')
				// let a = document.createElement('a');
				// a.download = "模板";
				// a.href = res.data;
				// document.body.appendChild(a);
				// a.click();
				// document.body.removeChild(a);
				// < a download="PHP实现并发请求.html" href={res.data} > PHP实现并发请求</a >
			} else {
				// console.log()
				message.error('接口报错');
			}
			// console.log(res, 'res')
		});
	}

	// 右边
	function searchFn(value) {
		let params = {};
		let result = value && value.trim();
		if (result) {
			if (selectValue.current.selectCurrent === true) {
				//全部
				// params.
				params.all_query = result;
			} else {
				params[selectValue.current.selectCurrent] = result;
			}
		} else {
		}
		setSearch(params);
		getTableData(params);
	}
	function selectChangeFn(value) {
		selectValue.current.selectCurrent = value;
	}
	function handleScroll(e) {
		node.current.scrollTop > 0 ? setIsShowBtn(true) : setIsShowBtn(false);
	}
	let deleteFn = function deleteFn(id) {
		setIsLoading(true);
		batchDeleteMeeting(
			{
				ids: id || rowKey,
			},
			function (res) {
				if (res.success) {
					message.success('删除成功', 0.5);
					getTableData();
				} else {
				}
			}
		);
	};
	function onSelectChange(rowKey, current) {
		setRowKey(rowKey);
	}

	function getResetFn(fn) {
		resetFn.current = fn;
	}
	const rowSelection = {
		onChange: onSelectChange,
	};

	//获取 数据 function search ?
	let getTableData = function getTableData(searchConfig = {}, next, pageSize) {
		// paginationConfig
		// useEffect(
		// 	(state) => {
		// 		console.log(state, 'state');
		// 	},
		// 	[paginationConfig]
		// );
		setIsLoading(true);
		getMeetingByPage(
			{
				page_num: next || paginationConfig.handleClosure.current,
				page_size: pageSize || paginationConfig.handleClosure.pageSize,
				...searchConfig,
			},
			(res) => {
				if (res.success) {
					setPaginationConfig((state) => {
						state.handleClosure.current = res.data.current_page;
						state.handleClosure.pageSize = res.data.page_size;
						return {
							...state,
							total: res.data.total,
							current: res.data.current_page,
							pageSize: res.data.page_size,
						};
					});

					setDataSource(res.data.result);
					resetFn.current();
				} else {
					message.error('接口报错', 0.5);
				}
				setIsLoading(false);
			}
		);
	};
	useEffect(function () {
		window.getTableData = getTableData;
		getTableData();
	}, []);

	useEffect(function () {}, [rowKey]);
	return (
		<div onScroll={handleScroll} className="meeting-list-box" style={{}} ref={node}>
			<div
				className="to-top"
				style={{
					position: 'fixed',
					right: '0',
					bottom: '0.7rem',
				}}
			>
				<Button
					style={{
						display: isShowBtn ? '' : 'none',
					}}
					onClick={function () {
						node.current.scroll({ top: 0, left: 0, behavior: 'smooth' });
					}}
				>
					<img
						style={{
							width: '0.18rem',
						}}
						src="/assets/img/uparrow.png"
						alt=""
					/>
				</Button>
			</div>
			<div className="visual-box">
				<div className="visual-select-box">
					<DateRange getResetFn={getResetFn} useChartObj={chartObj} />
				</div>
				<div className="visual-top">
					<VisualTop useChartObj={[chartObj, setChartObj]} />
				</div>
				<div className="visual-bottom padding-view">
					<VisualBottom useChartObj={[chartObj, setChartObj]} />
				</div>
			</div>
		</div>
	);
}

ReactDOM.render(<App />, window.rootNode);
