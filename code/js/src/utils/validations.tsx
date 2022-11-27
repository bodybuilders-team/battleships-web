
export function validateEmail(email) {
	if (!email)
		return "Email is required"

	if (!email.includes("@"))
		return "Email is invalid"

	return null;
}


export function validateUsername(username) {
	if (!username)
		return "Username is required"

	if (username.length < 3)
		return "Username must be at least 3 characters"

	return null;
}

export function validatePassword(password) {
	if (!password)
		return "Password is required"

	if (password.length < 8)
		return "Password must be at least 8 characters"

	return null;
}