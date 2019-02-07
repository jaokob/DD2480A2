
const shell = require('shelljs')

/**
 * Tests the code of a given id which corresponds to a git commit.
 * Evaluates test status.
 */
function unitTest (dir, id) {
  // Executes from base directory. The script builds the repo. See details in script.
  const { code, stdout, stderr } = shell.exec('./testing.sh ' + dir + id, { silent: true })
  console.log('jUnit ' + id + ' exit code: ', code)
  
  let success = stdout.includes('0 tests failed')
  let result = { id: id, test_success: success, test_log: stdout, test_err: stderr }
  return result
}

module.exports = {
  unitTest
}
