const emailRegex = new RegExp("^[A-Za-z0-9+_.-]+@(.+)\$");

const minUsernameLength = 3;
const maxUsernameLength = 40;

const minPasswordLength = 8;
const maxPasswordLength = 127;

/**
 * Validates if the given string is a valid email address.
 *
 * @param email the email address to validate
 * @returns an error message if the email is invalid, otherwise null
 */
export function validateEmail(email: string): string {
    if (!email || email.trim().length === 0)
        return "Email is required";

    if (!emailRegex.test(email))
        return "Invalid email";

    return null;
}

/**
 * Validates if the given string is a valid username.
 *
 * @param username the username to validate
 * @returns an error message if the username is invalid, otherwise null
 */
export function validateUsername(username: string): string {
    if (!username || username.trim().length === 0)
        return "Username is required";

    if (username.length < minUsernameLength || username.length > maxUsernameLength)
        return `Username must be between ${minUsernameLength} and ${maxUsernameLength} characters`;

    return null;
}

/**
 * Validates if the given string is a valid password.
 *
 * @param password the password to validate
 * @returns an error message if the password is invalid, otherwise null
 */
export function validatePassword(password) {
    if (!password || password.trim().length === 0)
        return "Password is required";

    if (password.length < minPasswordLength || password.length > maxPasswordLength)
        return `Password must be between ${minPasswordLength} and ${maxPasswordLength} characters`;

    return null;
}
