const express = require('express')
const superagent = require('superagent');
const path = require('path');

const app = express()
const port = 3000

app.use(express.json()) // for parsing application/json
app.use(express.static('dist'))

app.get("/", (req, res) => {
    
    res.sendFile(path.join(__dirname + '/dist/index.html'));
})

app.listen(port, () => console.log(`listening on port ${port}!`))