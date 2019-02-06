
const shell = require('shelljs')


function unitTest (dir, id) {
  // Executes from base directory. The script builds the repo. See details in script.
  const { code, stdout, stderr } = shell.exec('./testing.sh ' + dir + id, { silent: true })
  console.log('jUnit ' + id + ' exit code: ', code)
  
  //console.log("stdout: " + stdout)
  //console.log("stderr: " + stderr)

  //let success = !(stderr)
  let success = stdout.includes('0 tests failed')
  let result = { id: id, build_success: success, build_log: stdout, build_err: stderr }
  return result
}

module.exports = {
  unitTest
}

/*
const shell = require('shelljs')


function build (dir, id) {
  // Executes from base directory. The script builds the repo. See details in script.
  const { code, stdout, stderr } = shell.exec('./build.sh ' + dir + id, { silent: true })
  console.log('Build ' + id + ' exit code: ', code)
  let success = !(stderr)
  let result = { id: id, build_success: success, build_log: stdout, build_err: stderr }
  return result
}

module.exports = {
  build
}
*/