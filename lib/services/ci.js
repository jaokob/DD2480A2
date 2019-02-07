const fs = require('fs')
const { clone } = require('./clone.js')
const { build } = require('./compile')
const { setPending, setSuccess, setFailure } = require('./status.js')
const { unitTest } = require('./jUnit.js')

/**
 * Function ci
 * Takes an entire pull request payload from Github's webhook and
 * Firstly: Clones the repo associated with the PR
 * Secondly: Tries to compile any Java class under the /src folder
 * Thirdly: Runs associated JUnit5 test classes located under the /test folder
 * Finally: sets the status correctly on the latest commit within the PR.
 * @param {JSON} req JSON object located within the body of a POST request
 */
async function ci (req) {

  setPending(req.body.repository.full_name, req.body.pull_request.head.sha)
  console.log(req.body.repository.git_url)
  console.log(req.body.pull_request.head.sha)
  try{
    ans = await clone(req.body.repository.clone_url, req.body.pull_request.head.ref, req.body.pull_request.head.sha, req.body.repository.full_name)
    if(!ans){ 
    }
    let response = await build('tmp/', req.body.pull_request.head.sha)
    console.log(response)
    let log = {SHA: req.body.pull_request.head.sha,
      started_at: req.body.pull_request.updated_at,
      success: '',
      compile_log: response.build_log,
      compile_error_log: response.build_err,  
      test_log: '',
      test_error_log: ''}
    if(response.build_success){
      let test_response = await unitTest('tmp/', req.body.pull_request.head.sha)
      console.log(test_response)
      log.success = response.build_success && test_response.test_success 
      log.test_log = test_response.test_log
      log.test_error_log = test_response.test_err
    }
    else{
    log.success = response.build_success
    }
    writeJSON(log)

    if(!log.success){
      setFailure(req.body.repository.full_name, req.body.pull_request.head.sha)
      return
    } 
    else{
      setSuccess(req.body.repository.full_name, req.body.pull_request.head.sha)
      return
    }    
  } catch (error) {
    console.log(error)
  }
}

/**
 * Writes JSON object to file
 * @param {JSON} log JSON object
 */
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
