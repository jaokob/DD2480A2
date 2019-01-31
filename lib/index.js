const express = require('express')
const bodyParser = require('body-parser')
const cors = require('cors')
const morgan = require('morgan')
// const nodegit = require('nodegit')
const path = require('path');
const ejs = require('ejs')
const fs = require('fs')


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
  res.sendStatus(201)
})

app.get('/history', (req, res) => {
  const testFolder = './tmp/';
    fs.readdir(testFolder, (err, directories) => {
      res.render(path.resolve('lib/html/history.ejs'), {directories: directories});
    })
  
})

app.get('/log', (req, res) => {
  console.log(req.query.sha)
  res.json({
    'SHA': req.query.sha,
    'Started at': '2019-01-29T11:41:47Z',
    'success': true,
    'compile logs': 'blabla',
    'test logs': 'blabla'
  })
})

app.listen(PORT, () => {
  console.log(`Server listening on port ${PORT}`)
})
