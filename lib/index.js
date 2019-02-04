const express = require('express')
const bodyParser = require('body-parser')
const cors = require('cors')
const morgan = require('morgan')
const { clone } = require('./services/clone.js')
const { build } = require('./services/compile')
const { setPending, setSuccess, setFailure } = require('./services/status.js')

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
  ci(req)
  res.sendStatus(201)
})

async function ci (req) {
  console.log(req.body.repository.git_url)
  console.log(req.body.pull_request.head.sha)
  try{
    ans = await clone(req.body.repository.clone_url, req.body.pull_request.head.ref, req.body.pull_request.head.sha, req.body.repository.full_name)
    if(!ans){ 
    }
    let response = build('tmp/', req.body.pull_request.head.sha)
    console.log(response)
    if(!response.build_success){
      setFailure(req.body.repository.full_name, req.body.pull_request.head.sha)
      return
    }
    else{
      setSuccess(req.body.repository.full_name, req.body.pull_request.head.sha)
      return
    }    
  }
  catch (error){
    console.log(error)
  }

  
 
  
  //console.log(buildResponse)
}

app.listen(PORT, () => {
  console.log(`Server listening on port ${PORT}`)
})
