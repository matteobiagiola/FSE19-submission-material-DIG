
### Purpose

This server will display test coverage data. The data is stored in the /coverage directory

<p>

Start the server, HTTP POST some data to the server, and then `GET localhost:6969/coverage`

### Installing

```javascript
git clone https://github.com/ORESoftware/express-istanbul.git
npm install
```


## Running the server

`node index.js` or `node .` for short


### Sending coverage 

HTTP POST your coverage data to this server, using code of this nature:


```javascript

exports.loadCoverage = function (driver, yourHost, yourPort) {

  return async function(cb) {

    await driver.switchTo().defaultContent();
    let obj = await driver.executeScript('return window.__coverage__;');

    let str = JSON.stringify(obj);
    let options = {
      port: 6969,
      host: 'localhost',
      path: '/coverage/client',
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      }
    };
    
    let req = http.request(options, res => {

      let data = '';
      // you *must* listen to data event
      // so that the end event will fire...
      res.on('data', d => {
        data += d;
      });

      res.once('end', function () {
       // Finished sending coverage data
       cb();  // fire the final callback
      });
    });
    
    req.write(str);
    req.end();

  }

};

```