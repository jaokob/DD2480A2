const express = require('express')
const bodyParser = require('body-parser')
const cors = require('cors')
const morgan = require('morgan')
const {setFailure, setPending, setSuccess} = require('./services/status.js')

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
  // console.log(req.body)
  test(req).then(result => {
    // ...
  }).catch(error => {
    console.log(error)
  })
  res.sendStatus(201)
})
async function test(req) {
  setPending(req.body.repository.full_name,req.body.pull_request.head.sha)
  setTimeout(() => setSuccess(req.body.repository.full_name,req.body.pull_request.head.sha), 5000)
}

app.listen(PORT, () => {
  console.log(`Server listening on port ${PORT}`)
})
