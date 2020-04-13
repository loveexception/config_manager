var domId = '';
var scripts = document.getElementsByTagName('script');
for (var i = 0; i < scripts.length; i++) {
	var script = scripts[i];
	if (script && script.getAttribute('type') == 'text/babel') {
		domId = script.getAttribute('domId');
	}
}
//Antd
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
			{/* {btnState ? <iframe className="updata" src="/wx/tOtherMessages" /> : <iframe className="updata" src="/wx/tOtherRemind" />} */}
			{btnState ? <iframe className="updata" src="/wx/tOtherMessages" /> : <iframe className="updata" src="/wx/tOtherRemind" />}
		</div>
	);
}
ReactDOM.render(<App />, document.getElementById('tables-box'));
