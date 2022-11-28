import {useState} from 'react';

/**
 * Configuration of a form.
 *
 * @propertu initialValues the initial values of the form fields
 * @property validate validates the form fields
 * @property onSubmit the function to call when the form is submitted
 */
interface FormConfig {
    initialValues: any,
    validate: (values: any) => any,
    onSubmit: (values: any) => void
}

/**
 * Holds the form data.
 *
 * @property handleSubmit the function to call when the form is submitted
 * @property handleChange the function to call when a form field is changed
 * @property values the values of the form fields
 * @property errors the errors of the form fields
 */
type Form = {
    handleSubmit: (event: any) => void,
    handleChange: (event: any) => void,
    values: any,
    errors: any
}

/**
 * Returns an object with the form state and functions to manipulate the form.
 *
 * @param formConfig the configuration of the form
 * @returns the form object
 */
export function useForm(formConfig: FormConfig): Form {
    const {initialValues, validate, onSubmit} = formConfig;
    const [values, setValues] = useState(initialValues);
    const [errors, setErrors] = useState({});

    function handleChange(event: any) {
        const {name, value} = event.target;
        setValues({...values, [name]: value});
    }

    function handleSubmit(event: any) {
        event.preventDefault();
        const errors = validate(values);
        setErrors(errors);

        if (Object.values(errors).every(x => x == null))
            onSubmit(values);
    }

    return {handleSubmit, handleChange, values, errors};
}
