/**
 * This file clones a repository specified by the variable "URL", branch and commit "commitID".
 * The repository is stored in an appropriate location
 * INPUT: 1. URL (HTTPS) adress to the repository, 2. Which branch, 3. Commit id
 */

var shell = require('shelljs')
var NodeGit = require('nodegit')
var localPath = require('path').join(__dirname, 'tmp_Clone')


function clone (URL, branch, commitID){
  var cloneOptions = { checkoutBranch: branch }
  //  Clone the repository
  var res = NodeGit.Clone.clone(URL, localPath, cloneOptions).then(function (repository) {
    //  Operate on the repository
    console.log('Cloning completed!')

    //  Copy files to appropriate folder (could have just cloned to appropriate folder instantly)
    shell.mkdir('-p', 'tmp/' + commitID)
    shell.cp('-Rf', 'lib/services/tmp_Clone/*', 'tmp/' + commitID)

    //  Remove temporary cloned folder
    shell.rm('-Rf', 'lib/services/tmp_Clone/{*,.*}')
    shell.rm('-R', 'lib/services/tmp_Clone')

  //  Display errors
  }).catch(function (e) {
    console.log(e)
  })

  var errorAndAttemptOpen = function () {
    return NodeGit.Repository.open(local)
  }

  res.catch(errorAndAttemptOpen)
    .then(function (repository) {
      console.log('Is the repository bare? %s', Boolean(repository.isBare()))
  })
}
clone('https://github.com/jaokob/testarrr.git','dev','f2f0bc8274a80f642bc60d0a4ed39e8474729014')