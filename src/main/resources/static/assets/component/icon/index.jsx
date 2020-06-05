(function (w) {
	// 检查依赖
	if (!antd) {
		throw new Error('自定义组件缺少antd 依赖');
		return;
	}
	if (!React) {
		throw new Error('自定义组件缺少React 依赖');
		return;
	}
	if (!icons) {
		throw new Error('自定义icon 支支持 antd 4.0 以上 需要引入antd icon');
		return;
	}
	if (!w._import) {
		w._import = {};
	}
	let { useState, useRef, useEffect } = React;
	let {} = antd;
	function MyIcon(props) {
		let { help = false, iconKey = 'LikeOutlined ', placement = 'top', text = '强', click, _props = {}, overlayClassName } = props;
		let ShowIcon = icons[iconKey];
		useEffect(function () {
			if (help) {
				console.log('iconKey :string icontype');
				console.log('placement : string 显示位置  》》 text:string 显示文字 click:function 点击的callback');
			}
		}, []);
		return (
			<div className="my-icon-component-box">
				<div className="test-less-import">您好像没引入less文件那</div>
				<div className="my-icon-component-content">
					<Tooltip
						{..._props}
						overlayClassName={overlayClassName}
						onClick={
							click
								? click
								: function () {
										console.log('不是一个函数');
								  }
						}
						placement={placement}
						title={text}
					>
						<ShowIcon {..._props} />
					</Tooltip>
				</div>
			</div>
		);
	}
	w._import.MyIcon = MyIcon;
})(window);
