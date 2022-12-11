
export async function delay(seconds: number) {
    return await new Promise(resolve => setTimeout(resolve, seconds));
}
