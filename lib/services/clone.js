//  Input: data from github (repo name, commit hash... something more?)

//  Clone that commit/repo to the file system according to the agreed structure (tmp/[commit hash]/[relevant files])

var NodeGit = require("nodegit");
var URL = "https://github.com/nodegit/test"; //"https://github.com/PhilipWester/webhookTest.git";
var repoName = "testRepo";
var GITHUB_TOKEN = "ec2d2ee47b8242254cd382f3e9b77acdfc6c99e5";
var cloneOptions = {};
var localPath = require("path").join(__dirname, "tmp");


cloneOptions.fetchOpts = {
  callbacks: {
    certificateCheck: function() { return 1; },
    credentials: function(){
      return NodeGit.Cred.userpassPlaintextNew(GITHUB_TOKEN, "x-oauth-basic");
    } 
  }
};

//let cloneRepository = NodeGit.Clone(URL, "./", cloneOptions.then(function(repository)));

var res = NodeGit.Clone.clone(URL, localPath, cloneOptions).then(function(repository) {
  console.log("Cloning completed!");
}).catch(function(e){
  console.log('Cloning failed!');
});

var errorAndAttemptOpen = function() {
  return NodeGit.Repository.open(local);
};

res.catch(errorAndAttemptOpen)
  .then(function(repository) {
    // Access any repository methods here.
    console.log("Is the repository bare? %s", Boolean(repository.isBare()));
  });

/*


cloneRepository.catch(errorAndAttemptOpen)
  .then(function(repository) {
    // Access any repository methods here.
    console.log("Is the repository bare? %s", Boolean(repository.isBare()));
  });

*/
console.log(res);