let octonode = require('octonode')
let client = octonode.client() // Use environment variable instead, DO NOT push this to git

/**
 * This function sets a commit status to pending
 * @param {String} repo repo where the commit exists within (req.body.repository.full_name)
 * @param {String} sha commit hash where the status is to be updated  (req.body.pull_request.head.sha)
 */
function setPending (repo, sha) {
  let status = {
    'state': 'pending',
    'description': 'Started build'
  }
  setStatus(repo, sha, status)
}

/**
 * This function sets a commit status to success
 * @param {String} repo repo where the commit exists within (req.body.repository.full_name)
 * @param {String} sha commit hash where the status is to be updated  (req.body.pull_request.head.sha)
 */
function setSuccess (repo, sha) {
  let status = {
    'state': 'success',
    'description': 'Successfully built and tested code'
  }
  setStatus(repo, sha, status)
}

/**
 * This function sets a commit status to failure
 * @param {String} repo repo where the commit exists within (req.body.repository.full_name)
 * @param {String} sha commit hash where the status is to be updated  (req.body.pull_request.head.sha)
 */
function setFailure (repo, sha) {
  let status = {
    'state': 'failure',
    'description': 'Build failed.'
  }
  setStatus(repo, sha, status)
}

/**
 * Helper function for the set function, sends request to git to change statuses of commits
 */
function setStatus (repo, sha, body) {
  let ghrepo = client.repo(repo)
  ghrepo.status(sha, body,
    (err, data, headers) => {
      if (err) {
        console.log(err)
      }
      /* console.log("data: " + data);
        console.log("headers:" + headers); */
      // Remove when debugging commit statuses.
    })
}

module.exports = {
  setPending,
  setSuccess,
  setFailure
}
