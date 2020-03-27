let { Message, Icon } = antd;
let { Scrollbars } = ReactCustomScrollbars;
// React
const scaleArr = [];
for (let i = 0.1; i < 2.1; i += 0.1) {
	scaleArr.push(i.toFixed(1))
}
const href = 'http://172.16.16.9/api/webManage/';
// console.log(, 'dd')
let urlData = {};
window.location.search.slice(1).split('&').map((e) => {
	return e.split('=')
}).forEach((e, i, arr) => {
	urlData[e[0]] = e[1]
	// urlData[e] = arr[i + 1]
});
console.log(urlData, 'url')
let { id, name } = urlData;
// urlData = 
// let id = urlData.find((e)=>{ 
// 	e.findIndex((element)=>{
// 		element 
// 	})
// })
//  api 
function diagramViewImg(params, callback) {
	$.ajax({
		cache: true,
		type: 'POST',
		url: href + 'topology/list',
		contentType: "application/json",
		data: JSON.stringify(params),
		dataType: 'json',
		async: false,
		success: callback
	})
}
function Topo(props) {
	let contentImg = React.useRef(null);
	let zoomInBtn = React.useRef(null);
	let zoomOutBtn = React.useRef(null);
	let imgInitValue = React.useRef(null);
	let selecetRef = React.useRef(null);
	let refImg = React.useRef(null);
	let [imgWidth, setImgWidth] = React.useState("auto");
	let [imgSrc, setImgSrc] = React.useState('');
	let [selecetValue, setSelectValue] = React.useState('1.0');
	let [imgReset, setImgReset] = React.useState(0)
	let content = React.useRef({})
	let optionArr = ['10%', '20%', '30%', '40%', '50%', '60%', '70%', '80%', '90%', '100%', '110%', '120%', '130%', '140%', '150%', '160%', '170%', '180%', '190%', '200%'];
	// let scaleArr = [];
	let DiagramAction = window.DiagramAction;
	// let { id = '', name = '' } = props.location.query;
	// zoomInBtn.current.onClick
	let handleChange = e => {
		if (selecetRef.current.value === 'auto') {
			autoValue()

		} else {
			handleImgWidth(selecetRef.current.value);
		}
	};
	function handleImgWidth(scale, boolean) {

		let selfImg = refImg.current;
		let resultIndex = scaleArr.findIndex((element) => {
			return selfImg.initObj.scale == element
		})
		let resultValueH = '';
		let resultValueW = '';

		if (boolean === undefined) {
			resultValueW = selfImg.initObj.width * scale;
			// console.log(Number(scaleArr[resultIndex]).toFixed(1), 'xx')
			// setSelectValue(Number(scaleArr[resultIndex]).toFixed(1))
			setSelectValue(scale)
			selfImg.initObj.scale = scale;
			selfImg.style.width = resultValueW + 'px';
			return
		} else {
			if (boolean) {
				if (scaleArr[resultIndex + 1] === undefined) {
					resultValueW = selfImg.initObj.width * scaleArr[resultIndex];
					// resultValueH = selfImg.initObj.height * scaleArr[resultIndex];

				} else {
					++resultIndex
					resultValueW = selfImg.initObj.width * scaleArr[resultIndex];
					// resultValueH = selfImg.initObj.height * scaleArr[resultIndex];
				}
			} else {
				if (scaleArr[resultIndex - 1] === undefined) {
					// resultValueH = selfImg.initObj.height * scaleArr[resultIndex];
					resultValueW = selfImg.initObj.width * scaleArr[resultIndex];
				} else {
					resultIndex--;
					// resultValueH = selfImg.initObj.height * scaleArr[resultIndex];
					resultValueW = selfImg.initObj.width * scaleArr[resultIndex];

				}
				// resultValue = scaleArr[--resultIndex] === undefined ? selfImg.initObj.width * scaleArr[resultIndex] : selfImg.initObj.width * scaleArr[--resultIndex];

			}
		}
		setSelectValue(Number(scaleArr[resultIndex]).toFixed(1))
		selfImg.initObj.scale = scaleArr[resultIndex]
		// console.log(resultIndex, '--------------xx')
		selfImg.style.width = resultValueW + 'px';
		selfImg.style.height = resultValueH + 'px';
	}
	function handleScale(boolean) {
		handleImgWidth(null, boolean)
	}
	function autoValue() {
		let selfImg = refImg.current;
		// selfImg.style.height = contentImg.current.style.height;
		selfImg.style.width = contentImg.current.style.width;
		setSelectValue('auto')
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
							let iO = {
								width: refImg.current.width,
								height: refImg.current.height,
								scale: 1,
								containerHeight: '5rem',
							};
							refImg.current.initObj = iO;
							setImgWidth(iO.width)
							imgInitValue.current = iO;
							setImgReset((500 - imgInitValue.current.height) > 0 ? (500 - imgInitValue.current.height) / 2 + 'px' : '0');
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
					<img
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
						onMouseOver={e => { }}
					/>
				</Scrollbars>
			</div>

			{/* </div> */}
			<div className="view-btn-container"
			// style={{
			// 	position: 'absolute',
			// 	left: '0',
			// 	top: 0
			// }}
			>
				<div className="view-btn" onClick={() => {
					handleScale(false)
				}}>
					<Icon type="zoom-out" className="handle-hover" />
					{/* <span className="iconfont ticobackicon-narrow"></span> */}
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
						<use xlinkHref="#ticobackicon-narrow"></use>
					</svg> */}
				</div>

			</div>
		</div >
	);

}

ReactDOM.render(<Topo />, window.rootNode);