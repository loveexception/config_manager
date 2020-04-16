(function (w) {
	// 检查依赖
	if (!antd) {
		throw new Error('自定义组件缺少antd 依赖')
		return

	}
	if (!React) {
		throw new Error('自定义组件缺少React 依赖')
		return
	}

	if (!w._import) {
		w._import = {}
	}

	let { useState, uesRef, useEffect } = React;
	let { Form,
		Input,
		Tooltip,
		Icon,
		Cascader,
		Select,
		Row,
		Col,
		Checkbox,
		Button,
		LocaleProvider,
		ConfigProvider,
		DatePicker,
		AutoComplete } = antd;
	const { MonthPicker, RangePicker } = DatePicker;


	const WarrperFormComponent = (props) => {
		let { liObj = [], data = {} } = props;
		const [form] = Form.useForm();
		const layout = {
			labelCol: {
				span: 3,
			},
			wrapperCol: {
				span: 18,
			},
		};
		const tailLayout = {
			wrapperCol: {
				offset: 8,
				span: 16,
			},
		};
		const onGenderChange = value => {
			switch (value) {
				case 'male':
					form.setFieldsValue({
						note: 'Hi, man!',
					});
					return;

				case 'female':
					form.setFieldsValue({
						note: 'Hi, lady!',
					});
					return;

				case 'other':
					form.setFieldsValue({
						note: 'Hi there!',
					});
			}
		};

		const onFinish = values => {
			console.log(values);
		};

		const onReset = () => {
			form.resetFields();
		};

		const onFill = () => {
			form.setFieldsValue({
				note: 'Hello world!',
				gender: 'male',
			});
		};
		useEffect(() => {
			console.log(props, 'props')
		})
		return (
			<Form {...data} {...layout} form={form} name="control-hooks" onFinish={onFinish}>
				{liObj.map(({ type = "input", leftName = "", required = false, placeholder = "", key, }, index) => {
					let _props = {
						name: leftName,
						label: leftName,
						required,
						key: index,
					};
					switch (type) {
						case 'input':
							return <Form.Item
								{..._props}
								rules={[
									{
										required,
										message: `${leftName}是必填项!`,
									},
								]}
							>
								<Input placeholder={placeholder} />
							</Form.Item>
						case "select":
							return <Form.Item
								{..._props}
								rules={[
									{
										required: required,
										message: `${leftName}是必填项!`,
									},
								]}
							>
								<Select
									placeholder={placeholder}
								// onChange={onGenderChange}
								>
									<Option value="male">male</Option>
									<Option value="female">female</Option>
									<Option value="other">other</Option>
								</Select>
							</Form.Item>
						case "time":
							return <Form.Item
								{..._props}
								rules={[
									{
										required: required,
										message: `${leftName}是必填项！`
									},
								]}
							>
								<ConfigProvider locale={antd.locales.zh_CN}>
									<DatePicker placeholder={placeholder} showTime format="YYYY-MM-DD HH:mm:ss" />

								</ConfigProvider>
							</Form.Item>
						default:
							return (<Form.Item
								{...props}
								rules={[
									{
										required,
										message: `${leftName}是必填项!`,
									},
								]}
							>
								<Input placeholder={placeholder} />
							</Form.Item>)
					}

				})}


				<Form.Item
					noStyle
					shouldUpdate={(prevValues, currentValues) => prevValues.gender !== currentValues.gender}
				>
					{({ getFieldValue }) =>
						getFieldValue('gender') === 'other' ? (
							<Form.Item
								name="customizeGender"
								label="Customize Gender"
								rules={[
									{
										required: true,
									},
								]}
							>
								<Input />
							</Form.Item>
						) : null
					}
				</Form.Item>
				<Form.Item {...tailLayout} className="form-btn-box">
					<Button type="primary" htmlType="submit">
						提交
			  </Button>
					<Button htmlType="button" onClick={onReset}>
						重置
			  </Button>
				</Form.Item>
			</Form >
		);
	};
	function EditComponent(props) {
		// React
		let { title = "传入help为true获取config信息", help = false, liObj = [], data = {} } = props;
		useEffect(function () {
			if (help) {
				console.log("title :string 标题信息")
				console.log("liObj :Array 每一个表格的内容(object) attr >>> required :boolean(false), leftName: string(''),placeholder: string(''),whitespace:boolean (false)  data : object 直接传入组件 ")
			}
		}, [])
		useEffect(function () {
		}, [])
		return (<div className="my-edit-component-box">
			<div className="test-less-import">
				您好像没引入less文件那
			</div>
			<div className="my-edit-component-content">
				<div className="my-title"> {title} </div>
				<WarrperFormComponent data={data} liObj={liObj} />
			</div>
		</div>)
	}
	w._import.EditComponent = EditComponent;

})(window)