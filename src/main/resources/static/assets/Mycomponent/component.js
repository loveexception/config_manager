function MyButton(props) {
	return (
		<button className="My-button-style" {...props}>
			{props.text}
		</button>
	);
}
