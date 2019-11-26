let { Icon, Spin, Modal, Button, Table, message, Popover } = antd;

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
class TitleBox extends React.PureComponent {
	constructor(props) {
		super(props);
		this.state = {};
	}
	render() {
		let { titleImg = true, titleText = '', count = 0 } = this.props;
		return (
			<div className="assets-title-box">
				<div className="assets-container">
					<img className="assets-title-img" src={titleImg ? '/assets/img/assetAll.png' : '/assets/img/money.png'} alt="photo"></img>
					<img src="" alt="" />
					<div className="assets-text-box">
						<div className="assets-text-top">{titleText}</div>
						<div className="assets-text-bottom">{titleImg ? count : '￥' + count}</div>
					</div>
				</div>
			</div>
		);
	}
}
class ChartCircle extends React.PureComponent {
	constructor(props) {
		super(props);
		this.state = {
			data: []
		};
	}
	componentDidMount() {
		let { chartData = [] } = this.props.chartData;
		let data = [];
		let allCount = 0;
		chartData.forEach(item => {
			allCount += item.count;
		});
		!_.isEmpty(chartData) &&
			chartData.forEach(e => {
				if (e.asset === '已用') {
					data.unshift({
						type: `已用:${((e.count / allCount) * 100).toFixed(0)}%`,
						value: e.count
					});
				} else {
					data.push({
						type: `闲置:${((e.count / allCount) * 100).toFixed(0)}%`,
						value: e.count
					});
				}
			});
		this.setState({
			data
		});
	}
	componentDidUpdate() {
		//复用
		let { chartData = [] } = this.props;
		let data = [];
		let allCount = 0;
		chartData.forEach(item => {
			allCount += item.count;
		});
		!_.isEmpty(chartData) &&
			chartData.forEach(e => {
				if (e.asset === '已用') {
					data.unshift({
						type: `已用:${((e.count / allCount) * 100).toFixed(0)}%`,
						value: e.count
					});
				} else {
					data.push({
						type: `闲置:${((e.count / allCount) * 100).toFixed(0)}%`,
						value: e.count
					});
				}
			});
		if (data.length != this.state.data.length) {
			this.setState(
				{
					data
				},
				() => {
					this.init();
				}
			);
		}
	}

	init = () => {
		var startAngle = (-Math.PI / 2) * -1;
		let { data } = this.state;
		var ds = new DataSet();
		var dv = ds.createView().source(data);
		dv.transform({
			type: 'percent',
			field: 'value',
			dimension: 'type',
			as: 'percent'
		});
		var chart = new G2.Chart({
			container: 'mountNode',
			forceFit: true,
			height: '350',
			width: '450',
			padding: 'auto'
		});
		chart.source(dv);
		chart.legend(false);
		chart.coord('theta', {
			radius: 0.75,
			innerRadius: 0.5,
			startAngle: startAngle,
			endAngle: startAngle + Math.PI * 2
		});
		chart
			.intervalStack()
			.position('value')
			.color('type', ['#30359D', '#ACAED7'])
			.opacity(1)
			.label('percent', {
				offset: -20,
				textStyle: {
					fill: 'white',
					fontSize: 12,
					shadowBlur: 2,
					shadowColor: 'rgba(0, 0, 0, .45)'
				},
				formatter: function formatter(val) {
					return (val - 0).toFixed(2) * 100 + '%';
				}
			});
		chart.guide().html({
			position: ['50%', '50%'],
			html: '<div></div>'
			//  html: '<div class="g2-guide-html"><p class="title">总计</p><p class="value">19670</p></div>'
		});
		chart.render();
		//draw label
		var OFFSET = 20;
		var APPEND_OFFSET = 50;
		var LINEHEIGHT = 60;
		var coord = chart.get('coord'); // 获取坐标系对象
		var center = coord.center; // 极坐标圆心坐标
		var r = coord.radius; // 极坐标半径
		var canvas = chart.get('canvas');
		var canvasWidth = chart.get('width');
		var canvasHeight = chart.get('height');
		var labelGroup = canvas.addGroup();
		var labels = [];
		addPieLabel(chart);
		canvas.draw();
		chart.on('afterpaint', function() {
			addPieLabel(chart);
		});
		//main
		function addPieLabel() {
			var halves = [[], []];
			var data = dv.rows;
			var angle = startAngle;

			for (var i = 0; i < data.length; i++) {
				var percent = data[i].percent;
				var targetAngle = angle + Math.PI * 2 * percent;
				var middleAngle = angle + (targetAngle - angle) / 2;
				angle = targetAngle;
				var edgePoint = getEndPoint(center, middleAngle, r);
				var routerPoint = getEndPoint(center, middleAngle, r + OFFSET);
				//label
				var label = {
					_anchor: edgePoint,
					_router: routerPoint,
					_data: data[i],
					x: routerPoint.x,
					y: routerPoint.y,
					r: r + OFFSET,
					fill: '#bfbfbf'
				};
				// 判断文本的方向
				if (edgePoint.x < center.x) {
					label._side = 'left';
					halves[0].push(label);
				} else {
					label._side = 'right';
					halves[1].push(label);
				}
			} // end of for
			var maxCountForOneSide = parseInt(canvasHeight / LINEHEIGHT, 10);
			halves.forEach(function(half, index) {
				// step 2: reduce labels
				if (half.length > maxCountForOneSide) {
					half.sort(function(a, b) {
						return b._percent - a._percent;
					});
					half.splice(maxCountForOneSide, half.length - maxCountForOneSide);
				}

				// step 3: distribute position (x and y)
				half.sort(function(a, b) {
					return a.y - b.y;
				});
				antiCollision(half, index);
			});
		}

		function getEndPoint(center, angle, r) {
			return {
				x: center.x + r * Math.cos(angle),
				y: center.y + r * Math.sin(angle)
			};
		}

		function drawLabel(label) {
			var _anchor = label._anchor,
				_router = label._router,
				fill = label.fill,
				y = label.y;

			var labelAttrs = {
				y: y,
				fontSize: 17, // 字体大小
				fill: '#808080',
				text: label._data.type, //+ '\n' + label._data.value,
				textBaseline: 'bottom'
			};
			var lastPoint = {
				y: y
			};

			if (label._side === 'left') {
				// 具体文本的位置
				lastPoint.x = APPEND_OFFSET;
				labelAttrs.x = APPEND_OFFSET; // 左侧文本左对齐并贴着画布最左侧边缘
				labelAttrs.textAlign = 'left';
			} else {
				lastPoint.x = canvasWidth - APPEND_OFFSET;
				labelAttrs.x = canvasWidth - APPEND_OFFSET; // 右侧文本右对齐并贴着画布最右侧边缘
				labelAttrs.textAlign = 'right';
			}

			// 绘制文本
			var text = labelGroup.addShape('Text', {
				attrs: labelAttrs
			});
			labels.push(text);
			// 绘制连接线
			var points = void 0;
			if (_router.y !== y) {
				// 文本位置做过调整
				points = [
					[_anchor.x, _anchor.y],
					[_router.x, y],
					[lastPoint.x, lastPoint.y]
				];
			} else {
				points = [
					[_anchor.x, _anchor.y],
					[_router.x, _router.y],
					[lastPoint.x, lastPoint.y]
				];
			}

			labelGroup.addShape('polyline', {
				attrs: {
					points: points,
					lineWidth: 1,
					stroke: fill
				}
			});
		}

		function antiCollision(half, isRight) {
			var startY = center.y - r - OFFSET - LINEHEIGHT;
			var overlapping = true;
			var totalH = canvasHeight;
			var i = void 0;

			var maxY = 0;
			var minY = Number.MIN_VALUE;
			var boxes = half.map(function(label) {
				var labelY = label.y;
				if (labelY > maxY) {
					maxY = labelY;
				}
				if (labelY < minY) {
					minY = labelY;
				}
				return {
					size: LINEHEIGHT,
					targets: [labelY - startY]
				};
			});
			if (maxY - startY > totalH) {
				totalH = maxY - startY;
			}

			while (overlapping) {
				boxes.forEach(function(box) {
					var target = (Math.min.apply(minY, box.targets) + Math.max.apply(minY, box.targets)) / 2;
					box.pos = Math.min(Math.max(minY, target - box.size / 2), totalH - box.size);
				});

				// detect overlapping and join boxes
				overlapping = false;
				i = boxes.length;
				while (i--) {
					if (i > 0) {
						var previousBox = boxes[i - 1];
						var box = boxes[i];
						if (previousBox.pos + previousBox.size > box.pos) {
							// overlapping
							previousBox.size += box.size;
							previousBox.targets = previousBox.targets.concat(box.targets);

							// overflow, shift up
							if (previousBox.pos + previousBox.size > totalH) {
								previousBox.pos = totalH - previousBox.size;
							}
							boxes.splice(i, 1); // removing box
							overlapping = true;
						}
					}
				}
			}

			// step 4: normalize y and adjust x
			i = 0;
			boxes.forEach(function(b) {
				var posInCompositeBox = startY; // middle of the label
				b.targets.forEach(function() {
					half[i].y = b.pos + posInCompositeBox + LINEHEIGHT / 2;
					posInCompositeBox += LINEHEIGHT;
					i++;
				});
			});

			// (x - cx)^2 + (y - cy)^2 = totalR^2
			half.forEach(function(label) {
				var rPow2 = label.r * label.r;
				var dyPow2 = Math.pow(Math.abs(label.y - center.y), 2);
				if (rPow2 < dyPow2) {
					label.x = center.x;
				} else {
					var dx = Math.sqrt(rPow2 - dyPow2);
					if (!isRight) {
						// left
						label.x = center.x - dx;
					} else {
						// right
						label.x = center.x + dx;
					}
				}
				drawLabel(label);
			});
		}
	};

	render() {
		let { chartData = [] } = this.props;
		return (
			<div className="ChartCircle-box">
				<div id="mountNode"></div>
				<div className="mountNode-chart">
					<ul className="mountNode-left-list">
						<li className="mountNode-left-title">在用</li>
						<li className="mountNode-left-content">
							<div className="mountNode-left-item">
								<div className="mountNode-left-strip"></div>
								<div className="mountNode-left-text">
									<div>资产个数</div>
									<div>{chartData[0] && chartData[0].count ? chartData[0].count : '0'}个</div>
								</div>
							</div>
						</li>
					</ul>
					<ul className="mountNode-right-list">
						<li className="mountNode-right-title">闲置</li>
						<li className="mountNode-right-content">
							{/* <div className="mountNode-right-item">
								<div className="mountNode-right-strip"></div>
								<div className="mountNode-right-text">
									<div>资产金额</div>
									<div>{chartData[1] && chartData[1].sum ? '￥' + chartData[1].sum : '￥0'}</div>
								</div>
							</div> */}
							<div className="mountNode-right-item">
								<div className="mountNode-right-strip"></div>
								<div className="mountNode-right-text">
									<div>资产个数</div>
									<div>{chartData[1] && chartData[1].count ? chartData[1].count : '0'}个</div>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</div>
		);
	}
}
function Pillar(props) {
	let [data, setData] = React.useState([]);
	let [chartFlag, setChartFlag] = React.useState(false);
	function init() {
		var chart = new G2.Chart({
			container: 'content',
			//				forceFit: true,
			width: 450,
			height: 390,
			padding: [10, 20, 50, 51]
		});
		setChartFlag(true);
		chart.source(data);
		chart.scale('value', {
			alias: '分类统计(台)'
		});
		chart.axis('title', {
			label: {
				textStyle: {
					fill: '#aaaaaa'
				}
			},
			tickLine: {
				alignWithLabel: false,
				length: 0
			}
		});

		chart.axis('value', {
			label: {
				textStyle: {
					fill: '#aaaaaa'
				}
			}
		});

		chart.tooltip({
			share: true
		});
		chart
			.interval()
			.position('title*value')
			.opacity('title', function(val) {
				//				if(val === '视频') {
				//					return 0.4;
				//				}
				return 1;
			})
			.style('title', {
				lineWidth: function lineWidth(val) {
					return 0;
				},
				stroke: '#636363',
				lineDash: [3, 9]
			})
			.color('#30359D');
		chart.render();
	}
	React.useEffect(() => {
		if (data.length !== props.pillar.length) {
			let result = [];
			props.pillar.forEach((e, i) => {
				if (e.type) {
					result.push({
						title: e.type,
						value: e.count - 0
					});
				}
				return;
			});

			setData(result);
		}
		if (data.length > 0 && !chartFlag) {
			init();
		}
	}, [props, data]);
	return (
		<div className="Pillar-box">
			<div className="Pillar-unit">单位：{'台'}</div>
			<div id="content"></div>
		</div>
	);
}
class ContentBox extends React.PureComponent {
	render() {
		let { width = '100px', height = '100px', text = '', Component = '', componentData = {}, useCounter = 0 } = this.props;
		return (
			<div className="contentBox-box" style={{ width, height }}>
				{text ? <div className="contentBox-title">{text}</div> : ''}
				<Component {...componentData}></Component>
			</div>
		);
	}
}
const antIcon = <Icon type="loading" style={{ fontSize: 24, color: 'deeppink' }} spin />;
function AssetFooter(props) {
	let [text, setText] = React.useState({ leftText: '运维提醒', rightText: '更换提醒', useCount: '设备应用总数' });
	let [isEdit, setIsEdit] = React.useState(false);
	let [isEditUpdata, setIsEditUpdata] = React.useState(false);
	// let [isShow, setShow] = React.useState(false);
	let [color, setColor] = React.useState('#2F359D');
	let [colorUpdata, setcolorUpdata] = React.useState('#2F359D');
	let [changeCount, setChangeCount] = React.useState(0);
	let changFlag = true;

	React.useEffect(() => {
		// setColor(
		// 	// props.couter === 0 ? 'greenyellow' :
		// 	props.couter < 10 ? '#19A15F' : 'red'

		// );
		changFlag ? reqChange() : '';
		// setcolorUpdata(props.couterUpdata < 10 ? '#19A15F' : 'red');
		return () => {};
	}, [props, changeCount]);

	function reqChange() {
		$.get('/wx/tOtherMessages/change', obj => {
			if (obj.code == 0) {
				changFlag = false;
				setChangeCount(obj.data);
			}
		});
	}
	function leftHandleClick() {
		setIsEdit(!isEdit);
	}
	function rightHandleClick() {
		setIsEditUpdata(!isEditUpdata);
	}

	// function reqFuncUpdata() {
	// 	alert(1);c
	// }
	return (
		<div className="asset-footer-box">
			<div className="option-box-left">
				<img className="option-left-img" style={{ height: 50 }} src="/assets/img/assetAll.png" alt="" />
				<div className="option-left-text">{text.useCount}</div>
				<div className="option-waring-box" style={{ color, left: '46%' }}>
					{(props && props.useCounter) || 0}
				</div>
			</div>
			<Frame reqFunc={props.reqFunc} isEdit={isEdit} leftHandleClick={leftHandleClick}></Frame>
			<div className="option-box-left" onClick={leftHandleClick}>
				<img className="option-left-img" height="70px" src="/assets/img/footer-setting.png" alt="" />
				<div className="option-left-text">{text.leftText}</div>
				<div className="option-waring-box" style={{ color }}>
					{props.couter}
				</div>
			</div>
			{/*  text={} */}
			<Frame right={{ isRight: true }} isEditUpdata={isEditUpdata} rightHandleClick={rightHandleClick}></Frame>
			<div className="option-box-right" onClick={rightHandleClick}>
				<img className="option-left-img" src="/assets/img/footer-arrow.png" alt="" />
				<div className="option-left-text">{text.rightText}</div>
				<div className="option-waring-box" style={{ color: colorUpdata, left: '53%' }}>
					{changeCount}
				</div>
			</div>
		</div>
	);
}
AssetFooter = React.memo(AssetFooter, (oldProps, props) => {
	//优化 return
});
// rowSelection objects indicates the need for row selection

//messagFrame props.isEdit(boolen) show or hidden
function Frame(props) {
	let [modal1Visible, setModal1Visible] = React.useState(false);
	let [reqFlag, setReqFlag] = React.useState(true);
	let [reqArrList, setReqArrList] = React.useState([]);
	let [data, setData] = React.useState([]);
	let [messageArr, setMessageArr] = React.useState([]);
	let messageFlag = true;
	let mesFlag;
	React.useEffect(() => {
		// if(props.isEdit === !props.isEdit){
		// 	return
		// }
		props.right && props.right.isRight && messageFlag ? reqMessage() : '';
		if (props.right && props.right.isRight) {
		}
		setModal1Visible(props.isEdit || props.isEditUpdata);
		if (reqFlag && reqRestFnc) {
			reqRestFnc && reqRestFnc();
			setReqFlag(false);
		}
		return () => {};
	}, [props]);

	function reqMessage() {
		$.get('/wx/tOtherMessages/messages', result => {
			if (result.code == 0) {
				let resultArr = [];
				let obj = result.data;
				if (Array.isArray(obj)) {
					messageFlag = false;
					obj.forEach(item => {
						let { sno, cnName = '', id, message } = item;
						resultArr.push({
							sno,
							cnName,
							message,
							id,
							key: id
						});
					});
					setMessageArr(resultArr);
				}
			} else {
				message.error('接口报错');
			}
		});
	}
	const rowSelection = {
		onChange: (selectedRowKeys, selectedRows) => {},
		onSelect: (record = {}, selected, selectedRows) => {
			if (selected) {
				setReqArrList(list => {
					return [...list, record.id];
				});
			} else {
				setReqArrList(list => {
					let index = reqArrList.findIndex(id => {
						return record.id === id;
					});

					list.splice(index, index + 1);
					return [...list];
				});
			}
		},
		onSelectAll: (selected, selectedRows = [], changeRows) => {
			if (selected) {
				//点击 全部 的回调 if true allckecked
				let result = selectedRows.map(e => {
					return e.id;
				});
				setReqArrList(result);
			} else {
				setReqArrList([]);
			}
		}
	};
	function reqUpdata(arr = []) {
		let url = `/wx/tIotDevices/change?`;
		arr.forEach((id, index) => {
			if (index + 1 == arr.length) {
				url += `id=${id}`;
			} else {
				url += `id=${id}&`;
			}
		});
		$.get(url, function(obj) {
			if (obj.code == 0) {
				reqRestFnc();
				antd.message.success('操作成功', 0.5);
			} else {
				antd.message.error('操作失败', 0.5);
			}
		});
	}
	function reqUpdataMessage(arr = []) {
		let url = `/wx/tOtherMessages/remove?`;
		let ids = '';
		arr.forEach((id, index) => {
			if (index + 1 == arr.length) {
				ids += `${id}`;
			} else {
				ids += `${id},`;
			}
		});
		$.post(url, { ids }, function(obj) {
			if (obj.code == 0) {
				reqMessage();
				antd.message.success('操作成功', 0.5);
			} else {
				antd.message.error('操作失败', 0.5);
			}
		});
	}

	const columns = [
		{
			title: '资产机器码',
			width: 200,
			dataIndex: 'sno',
			key: 'sno'
		},
		{
			title: '资产状态',
			dataIndex: 'assetStatus',
			key: 'assetStatus',
			width: 150
		},
		{
			title: '资产编码',
			dataIndex: 'enName',
			key: '资产编码',
			width: 100
		},
		{
			title: '资产名称',
			dataIndex: 'cnName',
			key: '资产名称',
			width: 150
		},
		{
			title: '部门',
			dataIndex: 'dept',
			key: '部门',
			width: 150
		},
		{
			title: '检查时间',
			dataIndex: 'gatewayExtsno',
			key: '检查时间',
			width: 200
		},
		{
			title: '类型',
			dataIndex: 'kind',
			key: '类型'
		},
		{ title: '厂家', dataIndex: 'kindmap', key: '厂家', width: 150 },
		{
			title: '操作',
			key: '操作',
			width: 160,
			align: 'center',
			render: (a, record) => (
				<LinkButton
					text="确认"
					onClick={function() {
						handleReq(record);
					}}
				/>
			)
		}
	];
	const columnsUpdata = [
		{
			title: 'sno',
			dataIndex: 'sno',
			key: 'sno'
		},

		{
			title: '资产名称',
			dataIndex: 'cnName',
			key: '资产名称'
		},
		{
			title: '提醒',
			dataIndex: 'message',
			key: '提醒'
		},
		{
			title: '操作',
			key: '操作',
			width: 160,
			align: 'center',
			render: (a, record) => (
				<LinkButton
					text="确认"
					onClick={function() {
						handleReqMessage(record);
					}}
				/>
			)
		}
	];
	function handleReq(data) {
		if (Array.isArray(data)) {
			let arr = [];
			data.forEach(e => {
				arr.push(e);
			});
			data.length > 0 ? reqUpdata(arr) : '';
		} else {
			reqUpdata([data.id]);
		}
	}
	function handleReqMessage(data) {
		if (Array.isArray(data)) {
			let arr = [];
			data.forEach(e => {
				arr.push(e);
			});
			data.length > 0 ? reqUpdataMessage(arr) : '';
		} else {
			reqUpdataMessage([data.id]);
		}
	}
	function reqRestFnc() {
		$.get(
			'/wx/tIotDevices/out_time',
			{
				next_time: dateUtil(new Date(Date.now() + 1000 * 60 * 60 * 24 * 7))
			},
			function(obj) {
				if (obj.code == 0) {
					let arr = obj.rows;
					let reqArrRest = [];
					!_.isEmpty(arr) &&
						arr.forEach(({ sno, assetStatus, id, enName, cnName, dept = {}, price, orderTime, gatewayExtsno, kindmap, kind = {} }, index) => {
							if (assetStatus === '2') {
								assetStatus = '正常';
							} else {
								assetStatus = '异常';
							}
							reqArrRest.push({
								sno,
								assetStatus,
								enName,
								cnName,
								dept: dept.deptName,
								price,
								orderTime,
								gatewayExtsno,
								kindmap,
								kind: kind.cnName,
								id,
								key: id
							});
						});
					setData(reqArrRest);
				} else {
					console.log('接口报错');
					setData([]);
				}
			}
		);
	}

	function dateUtil(time) {
		let result = time.getFullYear() + '-' + (time.getMonth() - 0 + 1) + '-' + time.getDate();
		return result;
	}
	let linkButtonConfig = {
		text: '确认'
	};
	//selet callback
	function resetReq() {
		// 关闭弹窗
		props.leftHandleClick && props.leftHandleClick();
		props.rightHandleClick && props.rightHandleClick();
		props.reqFunc && props.reqFunc();
	}
	return (
		<div className="modal-box">
			<Modal title={props.right && props.right.isRight ? '更换提醒' : '检修提醒'} width={'80%'} afterClose={resetReq} footer={null} style={{ top: 0 }} visible={modal1Visible} cancelText="取消" okText="确认" onOk={() => setModal1Visible(false)} onCancel={() => setModal1Visible(false)}>
				<LinkButton
					onClick={function() {
						if (props.right && props.right.isRight) {
							handleReqMessage(reqArrList);
						} else {
							handleReq(reqArrList);
						}
					}}
					{...linkButtonConfig}
					style={{ background: '#08CE01', color: '#F3F3F3', margin: '0 0 15px 0' }}
				/>
				{/*  */}
				<Table bordered columns={props.right && props.right.isRight ? columnsUpdata : columns} rowSelection={rowSelection} dataSource={props.right && props.right.isRight ? messageArr : data} />
			</Modal>
		</div>
	);
}

// button component 组件直接设置style 行内式callback执行 函数
function LinkButton(props) {
	// let {} = props;
	let btn = React.createRef();
	React.useEffect(() => {}, []);
	return (
		<button ref={btn} className="link-button" {...props}>
			{props.text}
		</button>
	);
}
class AssetContent extends React.PureComponent {
	constructor(props) {
		super(props);
		this.state = {
			change: 0,
			couter: 0,
			isLoading: false,
			chartData: [],
			pillar: [],
			reqFlag: false,
			isEdit: false,
			couterUpdata: 0
		};
	}
	reqFunc = () => {
		function dateUtil(time) {
			let result = time.getFullYear() + '-' + (time.getMonth() - 0 + 1) + '-' + time.getDate();
			return result;
		}
		let url = '/wx/tIotDevices/';
		let params = {
			next_time: dateUtil(new Date(Date.now() + 1000 * 60 * 60 * 24 * 7))
		};
		$.get(url + 'count', params, obj => {
			if (obj.code == 0) {
				this.setState({
					couter: obj.data.count, //更改 数量 左1
					isLoading: false
				});
			} else {
				this.setState({
					isLoading: false
				});
				antd.message.error('接口报错', 0.5);
			}
		});
	};
	componentDidMount() {
		this.init();
		this.reqFunc();
		$.get('/wx/tIotDevices/money', obj => {
			if (obj.code == 0) {
				if (Array.isArray(obj.data) && obj.data.length > 0) {
					let resultArr = obj.data.map(e => {
						e.count = e.count - 0;
						e.sum = e.sum - 0;
					});
					this.setState({
						chartData: obj.data
					});
				}
			} else {
				console.log('接口报错');
			}
		});
		$.get('/wx/tIotDevices/group', obj => {
			if (obj.code == 0) {
				this.setState({
					pillar: obj.data
				});
			} else {
				console.log('接口报错');
			}
		});
	}
	init = () => {};
	componentWillUnmount() {
		this.setState = () => {
			return;
		};
	}

	couterUtil = (arr, objName) => {
		if (!Array.isArray(arr)) {
			return 0;
		}
		let numCount = 0;
		arr.forEach((e, i) => {
			numCount += e[objName];
		});
		if (!numCount) {
			return 0;
		}
		return numCount;
	};

	render() {
		let { isLoading, chartData = [], isEdit } = this.state;
		let { couterUtil, reqFunc } = this;
		let useCount = couterUtil(chartData, 'count'); // 应用总数
		// let assteCount = couterUtil(chartData, 'sum'); // 资产总数
		let obj = { ...this.state, reqFunc, useCounter: useCount };
		// titlebox    img boolean  titleText 头部文字  count 数量 false 有单位￥
		return (
			<Spin size="large" spinning={isLoading} indicator={antIcon}>
				<div className="asset-box">
					<div className="asset-title-container">
						<ContentBox Component={AssetFooter} componentData={obj} width="100%" height="194px" text={false}></ContentBox>

						{/* <TitleBox titleImg={false} titleText="设备资产总数" count={assteCount} /> */}
					</div>
					<div className="asset-middle">
						<ContentBox componentData={obj} Component={ChartCircle} width="450px" height="350px" text="资产概况"></ContentBox>
						<ContentBox componentData={obj} Component={Pillar} width="450px" height="345px" text="资产分类统计"></ContentBox>
					</div>
				</div>
			</Spin>
		);
	}
}
ReactDOM.render(<AssetContent />, document.getElementById(domId));
