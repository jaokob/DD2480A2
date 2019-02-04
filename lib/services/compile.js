const shell = require('shelljs')

/**
 * Compiles the code of a given id which corresponds to a git commit.
 * Evaluates build status.
 */
function build (dir, id) {
  // Executes from base directory. The script builds the repo. See details in script.
  const { code, stdout, stderr } = shell.exec('./build.sh ' + dir + id, {silent:true})
  // TODO: Save build log and success status.
  console.log('Build ' + id + ' exit code: ', code)
  return code === 0;
}

module.exports = {
  build
}
