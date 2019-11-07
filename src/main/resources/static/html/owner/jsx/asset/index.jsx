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
					<img className="assets-title-img" src={titleImg ? './jsx/list/assetAll.png' : './jsx/list/money.png'} alt="photo"></img>
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
			data: [
				{
					type: '再用:50%',
					value: 50
				},
				{
					type: '闲置:50%',
					value: 50
				}
			]
		};
	}
	componentDidMount() {
		this.init();
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
			height: '338',
			width: '100%',
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
					return parseInt(val * 100) + '%';
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
				points = [[_anchor.x, _anchor.y], [_router.x, y], [lastPoint.x, lastPoint.y]];
			} else {
				points = [[_anchor.x, _anchor.y], [_router.x, _router.y], [lastPoint.x, lastPoint.y]];
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
		let { countMenoy, count, countMenoyIdle, countIdle } = this.props;
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
									<div>资产金额</div>
									<div>{countMenoy ? '￥' + countMenoy : '￥511,325'}</div>
								</div>
							</div>
							<div className="mountNode-left-item">
								<div className="mountNode-left-strip"></div>
								<div className="mountNode-left-text">
									<div>资产个数</div>
									<div>{count ? countMenoy + '个' : '25个'}</div>
								</div>
							</div>
						</li>
					</ul>
					<ul className="mountNode-right-list">
						<li className="mountNode-right-title">闲置</li>
						<li className="mountNode-right-content">
							<div className="mountNode-right-item">
								<div className="mountNode-right-strip"></div>
								<div className="mountNode-right-text">
									<div>资产金额</div>
									<div>{countMenoyIdle ? '￥' + countMenoyIdle : '￥511,325'}</div>
								</div>
							</div>
							<div className="mountNode-right-item">
								<div className="mountNode-right-strip"></div>
								<div className="mountNode-right-text">
									<div>资产个数</div>
									<div>{countIdle ? countMenoyIdle + '个' : '25个'}个</div>
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
	let [data, setData] = React.useState([
		{
			title: '视频',
			value: 17
		},
		{
			title: '音频',
			value: 40
		},
		{
			title: '网络',
			value: 50
		},
		{
			title: '其他',
			value: 70
		}
	]);
	function init() {
		var chart = new G2.Chart({
			container: 'content',
			//				forceFit: true,
			width: 700,
			height: 368,
			padding: [10, 20, 50, 51]
		});
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
	React.useEffect((...data) => {
		init();
	});
	return (
		<div className="Pillar-box">
			<div className="Pillar-unit">单位：{'台'}</div>
			<div id="content"></div>
		</div>
	);
}
class ContentBox extends React.PureComponent {
	render() {
		let { width = '100px', height = '100px', text = '', Component = '' } = this.props;
		return (
			<div className="contentBox-box" style={{ width, height }}>
				<div className="contentBox-title">{text}</div>
				<Component></Component>
			</div>
		);
	}
}
function AssetFooter(props) {
	let [text, setText] = React.useState({ leftText: '检修提醒', rightText: '更换提醒' });

	return (
		<div className="asset-footer-box">
			<div className="option-box-left">
				<img className="option-left-img" src="/html/drive/jsx/list/footer-setting.png" alt="" />
				<div className="option-left-text">{text.leftText}</div>
			</div>
			<div className="option-box-right">
				<img className="option-left-img" src="/html/drive/jsx/list/footer-arrow.png" alt="" />
				<div className="option-left-text">{text.rightText}</div>
			</div>
		</div>
	);
}

class AssetContent extends React.PureComponent {
	constructor(props) {
		super(props);
	}
	render() {
		// titlebox    img boolean  titleText 头部文字  count 数量 false 有单位￥
		return (
			<div className="asset-box">
				<div className="asset-title-container">
					<TitleBox titleImg={true} titleText="设备资产总数" count="25" />
					<TitleBox titleImg={false} titleText="设备资产总数" count="25" />
				</div>
				<div className="asset-middle">
					<ContentBox Component={ChartCircle} width="686px" height="500px" text="资产概况"></ContentBox>
					<ContentBox Component={Pillar} width="686px" height="500px" text="资产分类统计"></ContentBox>
				</div>
				<div className="asset-footer">
					<ContentBox Component={AssetFooter} width="100%" height="194px" text="信息提醒"></ContentBox>
				</div>
			</div>
		);
	}
}
ReactDOM.render(<AssetContent />, document.getElementById(domId));
