(function (w) {
	// 检查依赖
	if (!antd) {
		throw new Error('自定义组件缺少antd 依赖')
	}
	if (!React) {
		throw new Error('自定义组件缺少React 依赖')
	}

	if (!w._import) {
		w._import = {}
	}
	function EditComponent(props) {
		return (<div className="my-edit-component-box">
			<div className="test-less-import">
				您好像没引入less文件那
			</div>
			<div className="my-edit-component-content">
				内容
			</div>
		</div>)
	}
	w._import.EditComponent = EditComponent;

})(window)