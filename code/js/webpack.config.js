module.exports = {
    mode: "development",
    resolve: {
        extensions: [".js", ".ts", ".tsx", ".css"]
    },
    devServer: {
        historyApiFallback: true,
        port: 80
    },
    module: {
        rules: [
            {
                test: /\.tsx?$/,
                use: 'ts-loader',
                exclude: /node_modules/
            },
            {
                test: /\.css$/i,
                use: ['style-loader', 'css-loader'],
                exclude: /node_modules/
            },
        ]
    }
};
