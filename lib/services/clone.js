//  Input: data from github (repo name, commit hash... something more?)

//  Clone that commit/repo to the file system according to the agreed structure (tmp/[commit hash]/[relevant files])

/**
 * This file clones a repository specified by the variable "URL"
 * INPUT: 1. URL (HTTPS) adress to the repository, 2. A personal access token (for access to the repository)
 */

var NodeGit = require("nodegit");
var URL = "https://github.com/PhilipWester/webhookTest.git";
var repoName = "testRepo";
var GITHUB_TOKEN = "ec2d2ee47b8242254cd382f3e9b77acdfc6c99e5";
var cloneOptions = {};
var localPath = require("path").join(__dirname, "tmp");
var commitID;


cloneOptions.fetchOpts = {
  callbacks: {
    certificateCheck: function() { return 1; },
    credentials: function(){
      return NodeGit.Cred.userpassPlaintextNew(GITHUB_TOKEN, "x-oauth-basic");
    } 
  }
};
//  TODO fetch commit id and make repo structure. (Take commit id as argument and test commit id agains repo name?)
var res = NodeGit.Clone.clone(URL, localPath, cloneOptions).then(function(repository) {
  console.log("Cloning completed!");
  repository.getHeadCommit().then(function(commit){
    console.log(commit.id()); 
  });
}).catch(function(e){
  console.log(e);
});

var errorAndAttemptOpen = function() {
  return NodeGit.Repository.open(local);
};

res.catch(errorAndAttemptOpen)
  .then(function(repository) {
    console.log("Is the repository bare? %s", Boolean(repository.isBare()));
  });

console.log(res);

//  Find commit id

