const express = require('express')
const bodyParser = require('body-parser')
const cors = require('cors')
const morgan = require('morgan')
// const nodegit = require('nodegit')
const axios = require('axios')
const request = require('request')
const octonode = require('octonode')

const PORT = 3000

const app = express()

app.use(morgan('dev'))

app.use(bodyParser.urlencoded({ extended: false }))
app.use(bodyParser.json())

app.use(cors())
app.get('/health', (req, res) => {
  res.json({ success: true })
})

app.post('/event', (req, res) => {
  console.log(req.body.repository.git_url)
  console.log(req.body.pull_request.head.sha)
  let client = octonode.client('c3be8fb2d126abd5ab410d5e5d5799da15ec5cc3');
  let ghrepo = client.repo(req.body.repository.full_name);
 
  ghrepo.status(req.body.pull_request.head.sha, {
    "state": "success",
    "target_url": "http://ci.mycompany.com/job/hub/3",
    "description": "Build success."
  }, function(err, data, headers) {
    console.log("error: " + err);
    console.log("data: " + data);
    console.log("headers:" + headers);
  }); 
    
  res.sendStatus(201)
})

app.listen(PORT, () => {
  console.log(`Server listening on port ${PORT}`)
})
