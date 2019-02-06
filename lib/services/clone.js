/**
 * This file clones a repository specified by the variable "URL", branch and commit "commitID".
 * The repository is stored in an appropriate location
 * INPUT: 1. URL (HTTPS) adress to the repository, 2. Which branch, 3. Commit id
 */

var shell = require('shelljs')
var NodeGit = require('nodegit')
var localPath = require('path').join(__dirname, 'tmp_Clone')
const status = require('./status.js')

async function clone (URL, branch, commitID, repo) {
  var cloneOptions = { checkoutBranch: branch }
  //  Clone the repository
  return new Promise(async (resolve, reject) => {
    await NodeGit.Clone.clone(URL, localPath, cloneOptions).then(() => {
      // Set commit status
      status.setPending(repo, commitID)
      //  Operate on the repository
      console.log('Cloning completed!')

      //  Copy files to appropriate folder (could have just cloned to appropriate folder instantly)
      shell.mkdir('-p', 'tmp/' + commitID)
      shell.cp('-Rf', 'lib/services/tmp_Clone/*', 'tmp/' + commitID)

      //  Remove temporary cloned folder
      shell.rm('-Rf', 'lib/services/tmp_Clone/{*,.*}')
      shell.rm('-R', 'lib/services/tmp_Clone')

      //  Create log folder
      shell.mkdir('-p', 'logs/' + commitID)

      //  Display errors
    }).catch(function (e) {
      status.setFailure(repo, commitID)
      console.log(e)
      resolve(false)
    })
    resolve(true)
  })
}

module.exports = {
  clone
}
