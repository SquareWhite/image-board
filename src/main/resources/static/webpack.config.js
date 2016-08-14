var path = require('path');

module.exports = {
    entry: './app.js',
    devtool: 'sourcemaps',
    cache: true,
    debug: true,
    output: {
        path: __dirname,
        filename: './built/bundle.js'
    },
    module: {
        loaders: [
            { 
                test: /\.jsx?$/,         // Matches both .js and .jsx files
                exclude: /node_modules/, 
                loader: "babel-loader?presets[]=es2015&presets[]=react"
            }
        ]
    }
};