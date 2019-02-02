const shell = require('shelljs')

/**
 * Compiles the code of a given id which corresponds to a git commit.
 * Evaluates build status.
 */
function build (id) {
  var code
  var stdout
  var stderr
  // Executes from base directory. The script builds the repo. See details in script.
  var log = shell.exec('./build.sh ' + id, {silent:true}, function(code, stdout, stderr) {
    console.log('Build ' + id + ' exit code: ', code)
  })
  // TODO: Save build log and success status.
  return code === 0;
}

module.exports = {
  build
}
