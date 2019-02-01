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

/**
 * Health check route
 */

app.get('/health', (req, res) => {
  res.json({ success: true })
})

/**
 * Webhook endpoint, this is where git will post when events happens
 */
app.post('/event', (req, res) => {
  console.log(req.body.repository.git_url)
  console.log(req.body.pull_request.head.sha)
  res.sendStatus(201)
})

/**
 * History endpoint, reads from ./tmp folder and displays its contents as clickable links
 */
app.get('/history', (req, res) => {
  const historyFolder = './tmp/';
    fs.readdir(historyFolder, (err, directories) => {
      res.render(path.resolve('lib/html/history.ejs'), {directories: directories});
    }) 
})

/**
 * Log endpoint, this endpoint will render the relevant build information for past builds.
 */
app.get('/log', (req, res) => {
  const logPath = `./tmp/${req.query.sha}/logs.json`;
  let log = JSON.parse(fs.readFileSync(logPath, 'utf8'));
  res.render(path.resolve('lib/html/log.ejs'), {log: log});
})

/**
 * This initiates the server, starts listening for request on supplies PORT.
 */
app.listen(PORT, () => {
  console.log(`Server listening on port ${PORT}`)
})
