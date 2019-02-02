const shell = require('shelljs')

/**
 * Compiles the code of a given id which corresponds to a git commit.
 * Evaluates build status.
 */
function build (id) {
  // Executes from base directory. The script builds the repo. See details in script.
  var log = shell.exec('./build.sh ' + id, {silent:true}).stdout;
  // TODO: Evaluate if the build succeeds. Save build log and success status.Z
  return false
}

module.exports = {
  build
}
