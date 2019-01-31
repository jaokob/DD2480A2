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
  
  /*let body 
  let request = {
    headers: {'Authorization': {token: 'c3be8fb2d126abd5ab410d5e5d5799da15ec5cc3'}},
    body: body
  }
  let headers = {}
  axios.post(`https://api.github.com/repos/${req.body.repository.full_name}/statuses/${req.body.pull_request.head.sha}`, request, headers)
    .then(response => { 
      getCreds(response.data)
    })
    .catch(error => {
      console.log(error)
  //   })*/
  // request.post({
  //   url: `https://api.github.com/repos/${req.body.repository.full_name}/statuses/${req.body.pull_request.head.sha}`,
  //   body:  {
  //     "state": "success",
  //     "target_url": "XXX",
  //     "description": "Build verify",
  //     "context": "continuous-integration"
  //   },
  //   headers: {
  //     'Authorization': {token: 'c3be8fb2d126abd5ab410d5e5d5799da15ec5cc3'},
  //     'user-agent': 'jaokob'
  //   },
  //   json: true,
  //    }, function(error, response, body){
  //       console.log(response);
  // })
  let client = octonode.client('c3be8fb2d126abd5ab410d5e5d5799da15ec5cc3');
  let ghuser         = client.user('jaokob');
  let ghrepo         = client.repo('jaokob/testarrr');
 
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
