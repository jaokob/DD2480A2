const shell = require('shelljs')

/**
 * Compiles the code of a given id which corresponds to a git commit.
 * Evaluates build status.
 */
function build (dir, id) {
  // Executes from base directory. The script builds the repo. See details in script.
  const { code, stdout, stderr } = shell.exec('./build.sh ' + dir + id, {silent:true})
  console.log('Build ' + id + ' exit code: ', code)
  let success = (stderr) ? false : true
  let result = {id:id, build_success: success, build_log:stdout, build_err:stderr}
  return result;
}

module.exports = {
  build
}
