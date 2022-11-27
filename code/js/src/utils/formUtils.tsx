import { useState } from 'react';

interface FormConfig {
	initialValues: any;
	validate: (values: any) => any;
	onSubmit: (values: any) => void;
}

export function useForm(formConfig: FormConfig): any {
	const { initialValues, validate, onSubmit } = formConfig;
	const [values, setValues] = useState(initialValues);
	const [errors, setErrors] = useState({});

	function handleChange(event) {
		const { name, value } = event.target;
		setValues({ ...values, [name]: value });
	}

	function handleSubmit(event) {
		event.preventDefault();
		const errors = validate(values);
		setErrors(errors);

		if (Object.values(errors).every(x => x == null))
			onSubmit(values);
	}

	return { handleSubmit, handleChange, values, errors };
}