const cov = require('istanbul-middleware');
const express = require('express');
const app = express();

/*
istanbulMiddleware.createHandler(opts)
returns connect middleware that exposes additional endpoints for coverage. Endpoints exposed are documented in the summary.

opts is optional and currently only supports one flag.

resetOnGet - boolean to allow resets of coverage to baseline numbers using GET in addition to POST --> it does not work; it only work as POST

GET /coverage/object --> JSON object coverage: there is coverage info for each single script
GET /coverage --> HTML of coverage. There is info for each folder but also the total in terms of statements, functions, branches, ect...
POST /coverage/reset --> it works fine, without body (or body {})
GET /coverage/download --> it doesn't work
*/

app.use('/coverage', cov.createHandler());

var args = process.argv.slice(2);
var port = 6969;

if (args[0]) {
	var parsed = parseInt(args[0]);
	if (isNaN(parsed)) {
		throw new Error("First argument is the port the express server is listening to. Must be a number. Found: " + args[0]);
	} else {
		port = parsed;
		console.log("Express server listening on port " + port);
	}
} else {
	console.log("Port argument not provided! Use default port for express server: " + port);
}

app.listen(port);


// that's all folks, lol
