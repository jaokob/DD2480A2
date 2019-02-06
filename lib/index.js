const express = require('express')
const bodyParser = require('body-parser')
const cors = require('cors')
const morgan = require('morgan')
const path = require('path')
const fs = require('fs')
const { ci } = require('./services/ci.js')
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
  ci(req)
  res.sendStatus(201)
})

/**
 * History endpoint, reads from ./tmp folder and displays its contents as clickable links
 */
app.get('/history', (req, res) => {
  const historyFolder = './logs/'
  fs.readdir(historyFolder, (err, directories) => {
    if (err) {
      console.log(err)
    } else {
      res.render(path.resolve('lib/html/history.ejs'), { directories: directories })
    }
  })
})

/**
 * Log endpoint, this endpoint will render the relevant build information for past builds.
 */
app.get('/log', (req, res) => {
  const logPath = `./logs/${req.query.sha}/log.json`
  let log = JSON.parse(fs.readFileSync(logPath, 'utf8'))
  res.render(path.resolve('lib/html/log.ejs'), { log: log })
})

/**
 * This initiates the server, starts listening for request on supplies PORT.
 */
app.listen(PORT, () => {
  console.log(`Server listening on port ${PORT}`)
})
