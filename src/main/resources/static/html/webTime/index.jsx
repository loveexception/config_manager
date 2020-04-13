









let { Button, Radio, Icon } = antd;
let { useState, useEffect, useRef } = React;
function Title(props) {
	let [btnState, setBtnState] = useState(true);

	return (
		<div>
			<Radio.Group

				value={'large'}
			>
				<Radio.Button
					onClick={function () {
						props.needState(true);
						btnState ? '' : setBtnState(true);
					}}
					className={btnState ? 'active' : ''}
				>
					更换提醒
				</Radio.Button>
				<Radio.Button
					onClick={function () {
						props.needState(false);
						btnState ? setBtnState(false) : '';
					}}
					className={btnState ? '' : 'active'}
				>
					运维提醒
				</Radio.Button>
			</Radio.Group>
		</div>
	);
}

function App(props) {
	let [btnState, BtnSetState] = useState(true);
	useEffect(() => {
		return function () { };
	}, []);
	function needState(state) {
		BtnSetState(state);
	}
	return (
		<div className="updata-box">
			<Title needState={needState} />
		</div>
	);
}


React.render(<App />, window.rootNode);