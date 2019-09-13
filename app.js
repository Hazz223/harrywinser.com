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

app.get("/api/articles", async (req, resp) => {

    const response = await superagent.get("https://api.harrywinser.com/article?size=1000")

    resp.status(200)
    resp.json(response.body.content)
});

app.get("/api/articles/:cleanTitle", async (req, resp) => {

    const url = `https://api.harrywinser.com/article/${req.params.cleanTitle}`

    let respBody = {};

    try{
        const response = await superagent.get(url)
        resp.status(200)    
        respBody = response.body;
    }catch(err) {
        console.log(`${Date.now()} - Error occurred for api request - response status: ${err.response.body.statusCode}, response body: ${err.response.body.errorMessage}`)
        resp.status(err.response.body.statusCode)
        respBody = {"error": `An error occurred when trying to get: ${req.params.cleanTitle}`}
    }

    resp.json(respBody)
});

app.listen(port, () => console.log(`listening on port ${port}!`))