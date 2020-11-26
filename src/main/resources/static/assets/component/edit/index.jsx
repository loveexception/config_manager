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
	function momentShowTime(momentObj) {
		return moment(momentObj).valueOf()
	}
	let start = "";
	let end = "";
	const WarrperFormComponent = (props) => {
		let { liObj = [], data = {}, commitFn, inputMessage = {}, formName = 'form' } = props;
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
		const onFinish = formData => {
			for (let d in formData) {
				if (moment.isMoment(formData[d])) {
					formData[d] = moment(formData[d]).valueOf();
				}

			}
			commitFn && commitFn(formData)
		};

		const onReset = () => {
			form.resetFields();
		};
		function handleChange() {
		}
		useEffect(() => {
		}, [])
		return (
			<Form {...data} {...layout} form={form} name={formName} onFinish={onFinish} onFinishFailed={function () {
			}} initialValues={parent.currentEditObj || {}}>
				{liObj.map(({ isStart = true, selectArr, type = "input", leftName = "", rule, required = false, placeholder = "", key, }, index) => {
					// required = false;
					let value = {};
					if (inputMessage[key]) {
						value.defaultValue = inputMessage[key]
					}
					let _props = {
						name: key,
						label: leftName,
						required,
						key: index,
						validateFirst: true
					};
					switch (type) {
						case 'input':
							return <Form.Item
								{..._props}
								name={key}
								rules={[
									{
										required,
										message: `${leftName}是必填项!`,
									},
								]}
							>
								<Input
									placeholder={placeholder} />
							</Form.Item>
						case "select":
							return <Form.Item
								name={key}
								{..._props}
								rules={[
									{
										required: required,
										message: `${leftName}是必填项!`,
									},
								]}
							>
								<Select
									name="edit"
									placeholder={placeholder}
								// onChange={onGenderChange}
								>
									{selectArr.map((e, index) => {
										return <Option key={index} value={e.value}>{e.key}</Option>
									})}
								</Select>
							</Form.Item>

						case "time":
							if (isStart) {
								start = key;
							} else {
								end = key;
							}
							return <ConfigProvider key={index} locale={antd.locales.zh_CN}>
								<Form.Item
									// initialValue={moment(parent.currentEditObj[key])} 	
									{..._props}
									rules={[
										{
											required: true,
											message: '请选择时间',
										}, ({ getFieldValue }) => ({

											validator(rule, value) {
												if (isStart) {
													return Promise.resolve()

												}
												if (momentShowTime(getFieldValue(start)) <= momentShowTime(getFieldValue(end))) {
													return Promise.resolve()
												}
												return Promise.reject('结束时间应大于开始时间');
											},
										}),
										({ getFieldValue }) => ({
											validator(rule, value) {
												if (isStart) {
													return Promise.resolve()
												}
												if (!rule) {
													return Promise.resolve()
												}
												if (momentShowTime(getFieldValue(start)) + 24 * 60 * 60 * 1000 >= momentShowTime(getFieldValue(end))) {
													return Promise.resolve()
												}
												return Promise.reject('时间差不能超过24小时');
											},
										})
									]}
								>
									<DatePicker
										// defaultValue={inputMessage[key]|| ''}
										placeholder={placeholder} showTime format="YYYY-MM-DD HH:mm:ss" />
								</Form.Item>
							</ConfigProvider>
						default:
							return (<Form.Item
								{...props}
								initialValue={parent.currentEditObj[key]}

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
		let { title = "传入help为true获取config信息", commitFn, help = false, liObj = [], data = {} } = props;
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
				<WarrperFormComponent {...props} />
			</div>
		</div>)
	}
	w._import.EditComponent = EditComponent;

})(window)