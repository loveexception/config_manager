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
