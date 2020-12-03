let { InputNumber, Message, Icon, Spin, Tooltip, Input } = antd;
let { Scrollbars } = ReactCustomScrollbars;
// React
const scaleArr = [];
for (let i = 0.1; i < 2.1; i += 0.1) {
	scaleArr.push(i.toFixed(1))
}
// console.log(, 'dd')
let urlData = {};
window.location.search.slice(1).split('&').map((e) => {
	return e.split('=')
}).forEach((e, i, arr) => {
	urlData[e[0]] = e[1]
	// urlData[e] = arr[i + 1]
});
let { id, name } = urlData;
// urlData = 
// let id = urlData.find((e)=>{ 
// 	e.findIndex((element)=>{
// 		element 
// 	})
// })
//  api 

function transformCss(node, name, value) {
	// 添加一个容器
	//这个容器必须能区分开是给哪个元素存储的
	//而且这个容器如果存在，不能发生覆盖
	//最终我们的决定是传哪个dom元素对象，就在哪个元素对象身上创建存储属性值的对象
	// node 元素节点
	//  name 要添加的tranform 属性 
	if (!node.transformObj) {
		node.transformObj = {
		};
	}

	if (arguments.length > 2) {
		node.transformObj[name] = value;
		var result = '';
		for (var key in node.transformObj) {
			switch (key) {
				case 'translateX':
				case 'translateY':
				case 'translateZ':
				case 'translate':
					result += key + '(' + node.transformObj[key] + ') ';
					break;
				case 'rotate':
				case 'rotateX':
				case 'rotateY':
				case 'rotateZ':
				case 'skew':
				case 'skewX':
				case 'skewY':
					result += key + '(' + node.transformObj[key] + 'deg) ';
					break;

				case 'scale':
				case 'scaleX':
				case 'scaleY':
					result += key + '(' + node.transformObj[key] + ') ';
					break;
			}

		}

		node.style.transform = result;
	} else {
		var result = '';
		if (node.transformObj[name] == undefined) {
			if (name == 'scale' || name == 'scaleX' || name == 'scaleY') {
				result = '1';
			} else {
				result = 0;
			}
		} else {
			result = node.transformObj[name];
		}

		return result;
	}
}


// input 
function formatNumber(value) {
	value += '';
	const list = value.split('.');
	const prefix = list[0].charAt(0) === '-' ? '-' : '';
	let num = prefix ? list[0].slice(1) : list[0];
	let result = '';
	while (num.length > 3) {
		result = `,${num.slice(-3)}${result}`;
		num = num.slice(0, num.length - 3);
	}
	if (num) {
		result = num + result;
	}
	return `${prefix}${result}${list[1] ? `.${list[1]}` : ''}`;
}



function Topo(props) {
	let [routeValue, setRouteValue] = React.useState(0);
	let [imgWidth, setImgWidth] = React.useState("auto");
	let [imgSrc, setImgSrc] = React.useState('');
	let [selecetValue, setSelectValue] = React.useState('1.0');
	let [imgReset, setImgReset] = React.useState(0)
	let [imgLoading, setImgLoading] = React.useState(true);

	let contentImg = React.useRef(null);
	let zoomInBtn = React.useRef(null);
	let zoomOutBtn = React.useRef(null);
	let imgInitValue = React.useRef(null);
	let selecetRef = React.useRef(null);
	let timeId = React.useRef(null);
	let content = React.useRef({})
	let refImg = React.useRef(null);

	let optionArr = ['10%', '20%', '30%', '40%', '50%', '60%', '70%', '80%', '90%', '100%', '110%', '120%', '130%', '140%', '150%', '160%', '170%', '180%', '190%', '200%'];

	// let scaleArr = [];
	let DiagramAction = window.DiagramAction;
	// let { id = '', name = '' } = props.location.query;
	// zoomInBtn.current.onClick
	function handleImgPx(width = false, height = false) {
		let selfImg = refImg.current;
		selfImg.style.width = width == false ? "auto" : width;
		selfImg.style.height = height == false ? "auto" : height

	}
	let handleChange = e => {
		if (selecetRef.current.value === 'auto') {
			autoValue()

		} else {
			handleImgHeight(selecetRef.current.value);
		}
		resetImgMargin()
	};
	//  重置img  margin
	function resetImgMargin() {
		setImgReset((500 - refImg.current.height) > 0 ? (500 - refImg.current.height) / 2 + 'px' : '0');
	}
	function handleImgHeight(scale, boolean) {
		let selfImg = refImg.current;
		let resultIndex = scaleArr.findIndex((element) => {
			return selfImg.initObj.scale == element
		})
		let resultValueH = '';
		let resultValueW = '';

		if (boolean === undefined) {
			if (scale === 'auto') {
				selfImg.style.height = contentImg.current.style.height;  //容器高度
				selfImg.style.width = "auto";
				resultValueH = selfImg.initObj.containerHeightPx / selfImg.initObj.height;
				// resultValueH
				let scale = scaleArr.find((e) => {
					return resultValueH.toFixed(1) === e
				})
				// scale = -1
				if (scale !== undefined) {
					selfImg.initObj.scale = scale
				} else {
					// 大于 2倍 下标等于length  
					//  小于等于 -1；
					// scale
					selfImg.initObj.scale = resultValueH.toFixed(1) > 2 ? scaleArr.length : -1;
					// console.log(resultValueH.toFixed(1))
					// selfImg.initObj.scale = scaleArr.length;
					// selfImg.initObj.scale = -1
					// console.log(selfImg.initObj.scale, resultValueH.toFixed(1), 'xx')
				}
				setSelectValue('auto')
				// 处理自适应 传入 auto  记录 scale   
				// 高度为容器高度
				// setSelectValue(Number(scaleArr[resultIndex]).toFixed(1))

				// selfImg.initObj.scale = scaleArr[resultIndex]
				handleImgPx(false, selfImg.initObj.containerHeight);
				resetImgMargin()
				return
			} else {
				resultValueW = selfImg.initObj.width * scale;
				// console.log(Number(scaleArr[resultIndex]).toFixed(1), 'xx')
				// setSelectValue(Number(scaleArr[resultIndex]).toFixed(1))
				setSelectValue(scale)
				selfImg.initObj.scale = scale;
				handleImgPx(resultValueW + 'px')
				return
			}

		} else {
			// -1
			if (boolean) {
				// 2.5
				if (selfImg.initObj.scale === scaleArr.length) {
					// 最大不处理

					return
				}
				if (scaleArr[resultIndex + 1] === undefined) {
					// resultValueH = selfImg.initObj.height * scaleArr[resultIndex];

				} else {
					++resultIndex
					// resultValueH = selfImg.initObj.height * scaleArr[resultIndex];
				}
				resultValueW = (selfImg.initObj.width * scaleArr[resultIndex]).toFixed(1);

			} else {
				if (selfImg.initObj.scale === -1) {
					return
					// 最小不处理
				}
				if (selfImg.initObj.scale === scaleArr.length) {
					resultIndex = scaleArr.length
				}
				if (scaleArr[resultIndex - 1] === undefined) {
					// resultValueH = selfImg.initObj.height * scaleArr[resultIndex];
				} else {
					resultIndex--;
					// resultValueH = selfImg.initObj.height * scaleArr[resultIndex];

				}
				resultValueW = (selfImg.initObj.width * scaleArr[resultIndex]).toFixed(1);
				// resultValue = scaleArr[--resultIndex] === undefined ? selfImg.initObj.width * scaleArr[resultIndex] : selfImg.initObj.width * scaleArr[--resultIndex];

			}
		}
		setSelectValue(Number(scaleArr[resultIndex]).toFixed(1))
		selfImg.initObj.scale = scaleArr[resultIndex]
		handleImgPx(resultValueW ? resultValueW + 'px' : false, resultValueH ? resultValueH + 'px' : false)
		resetImgMargin()
		// selfImg.style.width = resultValueW + 'px';
		// selfImg.style.height = resultValueH + 'px';
	}
	function handleScale(boolean) {
		handleImgHeight(null, boolean);

	}
	function autoValue() {
		handleImgHeight("auto")
	}
	function handleRotate(rotateVlue, deg = 90) {
		// 顺时针 true
		if (!refImg.current) {
			return
		}
		if (rotateVlue === true) {
			let _value = transformCss(refImg.current, 'rotate') + deg
			transformCss(refImg.current, 'rotate', _value < 180 ? _value : 180)
			// refImg.current.style.
		} else if (rotateVlue === false) {
			let _value = transformCss(refImg.current, 'rotate') - deg
			transformCss(refImg.current, 'rotate', _value < -180 ? -180 : _value)
		} else if (rotateVlue === 'auto') {
			transformCss(refImg.current, 'rotate', 0)
		} else if (rotateVlue === 'settingValue') {
			let _value = deg > 180 ? 180 : deg;
			_value = _value < -180 ? -180 : _value;
			transformCss(refImg.current, 'rotate', _value)
		}
		setRouteValue(transformCss(refImg.current, 'rotate'))
	}
	function numberChange(numValue) {
		// let { value } = e.target;
		let rotateBoolean = numValue > 0 ? true : numValue === 0 ? 'auto' : false
		// handleRotate(rotateBoolean, Math.abs(numValue))
		handleRotate("settingValue", numValue)
	}
	function handleBulr(e) {
		let { value } = e.target;
		// console.log(, 'value')
		let result = value.slice(0, -1);
		let numValue = Number(result);
		// let rotateBoolean = numValue > 0 ? true : numValue === 0 ? 'auto' : false
	}
	React.useEffect(
		function () {
			DiagramAction.diagramViewImg({ id }, res => {
				if (res.success) {
					let d = res.data.file_url;
					setImgSrc(d);
					// let w = `${(refImg.current.width / 100).toFixed(2)}rem`;
					if (refImg.current) {
						refImg.current.onload = function () {
							setImgLoading(false)
							let iO = {
								width: refImg.current.width,
								height: refImg.current.height,
								scale: 1,
								containerHeight: '4.9rem', //防止 出现滚动条  
								containerHeightPx: '500',

							};
							refImg.current.initObj = iO;
							setImgWidth(iO.width)
							imgInitValue.current = iO;
							resetImgMargin()
						};
					} else {
						Message.error('图片加载失败', 0.5);
					}
				}
			});
		},
		[id, props]
	);
	// return
	return (
		<div className="diagram-view-box" style={{
		}}>
			{/* <div style={{ float: 'left' }}> */}
			<Spin spinning={imgLoading} indicator={<Icon type="loading" style={{ fontSize: 48 }} spin />} >
				<div className="diagram-view-container" ref={contentImg}
					style={{
						height: "5rem",
						width: "100%",
					}}
				>
					<Scrollbars
						style={
							{
								// width:1
								height: '5rem',
							}
						}
						renderThumbVertical={() => {
							const thumbStyle = {
								width: '8px',
								backgroundColor: '#8f8f8f',
								// opacity: '0.2',
								borderRadius: '6px',
								right: '4px',
							}
							return <div style={{
								...thumbStyle
							}}>

							</div>
						}}
						autoHide={true}
					>
						<ReactDraggable
							axis="both"
							handle=".handle"
							defaultPosition={{ x: 0, y: 0 }}
							position={null}
							grid={[2, 2]}
							scale={1}>
							<div>
								<img
									className="handle"
									draggable="false"
									ref={refImg}
									style={{
										marginTop: imgReset,
										width: imgWidth,
										// height: "100%",
										// width: "100%"
										// position: 'absolute',
										// left: 0,
										// right: 0,
										// top: 0,
										// bottom: 0,
										// margin: 'auto'
										// transform: 'translateX(-50%)',
										// transform: translateX(-50 %);
										// marginLeft: "50%"
										// zIndex: -1,
									}}
									src={imgSrc}
									alt=""
									// onClick={() => {
									// 	console.log(1)
									// }}
									onMouseOut={() => {
										// timeId.current && clearTimeout(timeId.current)
										// console.log(false)
									}}
									onMouseMove={e => {
										// this.
										// timeId.current && clearTimeout(timeId.current)
										// timeId.current = setTimeout(() => {
										// 	console.log(true)
										// }, 2000)
									}}
								// onMouseOver={e => { console.log(11) }}
								/>
							</div>
						</ReactDraggable>
					</Scrollbars>
				</div>
			</Spin>

			{/* </div> */}
			<div className="view-btn-container"
			>
				{/* 旋转组 */}
				<div className="view-btn-adapt " onClick={() => {
				}}>
					<Tooltip placement="bottom" title={'设置旋转角度'}>
						<InputNumber
							style={{
								width: "1rem",
								border: 'none'
							}}
							formatter={value => {
								value = Number(value);
								let _value = value < -180 ? -180 : value;
								_value = _value > 180 ? 180 : _value;
								return `${_value}°`
							}}
							onChange={numberChange}
							onBlur={handleBulr}
							placeholder="请输入调整角度"
							min={-180} max={180}
							defaultValue={0}
							value={routeValue}
						// onChange
						/>
					</Tooltip>
				</div>
				<div className="view-btn" onClick={() => {
					handleRotate(false)
				}}>
					<Icon type="undo" className="handle-hover" />
				</div>
				<div className="view-btn-adapt handle-hover" onClick={() => {
					handleRotate("auto")
				}}>
					重置
				</div>
				<div className="view-btn" onClick={() => {
					handleRotate(true)
				}}>
					<Icon type="redo" className="handle-hover" />
				</div>
				{/* 放大组 */}
				<div className="view-btn" onClick={() => {
					handleScale(false)
				}}>
					<Icon type="zoom-out" className="handle-hover" />
				</div>

				<div className="view-select handle-hover">
					<select ref={selecetRef} onChange={handleChange} name="view" value={selecetValue} className="view-select son">
						{
							<option value={'auto'} key={"auto"}>
								自适应
							</option>
						}
						{optionArr.map((item, index) => {
							// let  result =
							let value = (item.split('%')[0] / 100).toFixed(1);
							return (
								<option value={value} key={item}>
									{item}
								</option>
							);
						})}

					</select>
					{/* <Icon name="view" type="right" style={
						{
							// position: 'relative',
							// zIndex: '',
							fontSize: '0.08rem',
							position: "absolute",
							right: "0.12rem",
							top: "0.12rem",
							// fontWeight: 'bold'
							// transform: 'scale(1.5)'
						}
					} /> */}
				</div>
				<div className="view-btn" onClick={() => {
					handleScale(true)

				}}>
					<Icon className="handle-hover" type="zoom-in" />
					{/*  */}
				</div>
				<div
					className="view-btn-adapt handle-hover"
					onClick={function () {
						autoValue()
					}}
				>
					适应窗口大小
					{/* <svg aria-hidden="true" className="upload" ref={zoomOutBtn} style={{}}>
					</svg> */}
				</div>

			</div>
		</div >
	);

}

ReactDOM.render(<Topo />, window.rootNode);