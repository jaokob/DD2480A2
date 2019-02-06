const express = require('express')
const bodyParser = require('body-parser')
const cors = require('cors')
const morgan = require('morgan')
const { ci } = require('./services/ci.js')



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

app.listen(PORT, () => {
  console.log(`Server listening on port ${PORT}`)
})

