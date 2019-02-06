const fs = require('fs')
const { clone } = require('./clone.js')
const { build } = require('./compile')
const { setPending, setSuccess, setFailure } = require('./status.js')

async function ci (req) {
  setPending(req.body.repository.full_name, req.body.pull_request.head.sha)
  console.log(req.body.repository.git_url)
  console.log(req.body.pull_request.head.sha)
  try {
    ans = await clone(req.body.repository.clone_url, req.body.pull_request.head.ref, req.body.pull_request.head.sha, req.body.repository.full_name)
    if (!ans) {
    }
    let response = await build('tmp/', req.body.pull_request.head.sha)
    console.log(response)
    let tmp_log
    if (!response.err_log === '') {
      tmp_log = response.build_log
    } else {
      tmp_log = response.build_err
    }
    let log = { SHA: req.body.pull_request.head.sha,
      started_at: req.body.pull_request.updated_at,
      success: response.build_success, // TODO: check junit testing also
      compile_log: tmp_log,
      test_log: 'TODO' } // TODO
    writeJSON(log)
    if (!response.build_success) {
      setFailure(req.body.repository.full_name, req.body.pull_request.head.sha)
      return
    } else {
      setSuccess(req.body.repository.full_name, req.body.pull_request.head.sha)
      return
    }
  } catch (error) {
    console.log(error)
  }
  // console.log(buildResponse)
}

function writeJSON (log) {
  console.log('Writing JSON file')
  fs.writeFile(`logs/${log.SHA}/log.json`, JSON.stringify(log), function (err) {
    if (err) {
      console.log(err)
    }
  })
}

module.exports = {
  ci
}
