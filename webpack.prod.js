const path = require('path')
const { merge } = require('webpack-merge')
const common = require('./webpack.common.js')
const HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = merge(common, {
    mode: 'production',
    plugins: [
        new HtmlWebpackPlugin({
            title: 'Sarafan',
        }),
    ],
    output: {
        filename: 'main.js',
        path: path.resolve(__dirname, 'src', 'main', 'resources','static', 'js'),
        clean: true,
    }
})