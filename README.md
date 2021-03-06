# DD2480A2

**NOTE:** We use "dev" branch, not "assessment".

## Java Continuous Integration server

This is a bare bones continuous integration (CI) server written in nodeJS.
Currently it only supports Java and testing with JUnit 5.

## Usage

To use this CI server, clone the repo and start the server (for information on how to build project and the relevant dependencies needed, see below). 

This CI server assumes the following file structure:
* The code needs to be under folder `src`
* The test class needs to be under the folder `test`. 

Furthermore, [a github personal token](https://github.com/settings/tokens) is needed. The token needs to be exported to environment variable `GITHUB_TOKEN`. The token only needs access to statuses, nothing more. 

After starting the server and ngrok, add the `http://ngrok_address/events` as a webhook to the repo where you wish to use the CI.
Note that the CI is built to only handle pull requests, it will NOT work if you trigger the webhook on other triggers.
## Build logs

The logs are available at `http://ngrok_address/history`

## BUILD

### Dependencies 

* Node v11.8.0
* Yarn (`brew install yarn` on mac)
* npm (`brew install npm` on mac)
* nodemon (`npm install nodemon -g`)
* ngrok (`npm install ngrok -g`)

### How to start
To install the npm dependencies needed, type `yarn install` in the terminal in the project folder. 

To run, start ngrok locally in a seperate terminal window. Use `ngrok http 3000` to start it.
Then in project folder type `yarn watch` to start the server, `yarn build` to build project 

### Tests
We will use jest to implement unit testing.
Type `yarn test` to execute tests.

### Linting
To lint your code (fix syntax), type `yarn lint`. 

### Code style
#### Syntax
ES6-syntax as supported in Node. No `import` / `export`.

#### Standard
[![js-standard-style](https://cdn.rawgit.com/feross/standard/master/badge.svg)](http://standardjs.com)

We use [`standard`](http://standardjs.com), with some modifications:
* Allow single parameter arrow functions to not include parenthesis. See [arrow-parens](http://eslint.org/docs/rules/arrow-parens)
* Prefer `const` over `let` where possible. See [prefer-const](http://eslint.org/docs/rules/prefer-const)
* No `var`. See [no-var](http://eslint.org/docs/rules/no-var)

# Endpoint specification
### Available endpoints

| Verb | Route  | Description         | Parameter |
|------|-------------|---------------------|--------|
| get  | `/health`    | Health check route. | N/a |
| post | `/events` | Github webhook endpoint.| N/a |
| get  | `/history` | CI Build history is displayed here. | N/a |
| get  | `/log` | Shows the CI log of commit SHA.  | `sha` |

### Response codes

| Status  Code |                                 Description                       |
|--------------|-------------------------------------------------------------------|
| 200          |                      Ok                                           |
| 201          |                       Resource created                            |
| 404          |            Not Found - The requested resource could not be found. |
| 500          | Internal Server error                                             |           


### Statement of contribution

Jakob: Statuses, build history, integration between clone and compile, API documentation & README, CI server endpoints

Jenny: Handling the SUT compilation phase. An attempt is made at compiling the SUT and the result (success/fail) of this attempt is returned together with build logs. Also responsible for testing the CI implementation of this compilation phase.

Philip: Cloning from git, logging build information, API documentation & README

Shiva:  Handling the SUT testing phase. Tests defined in the SUT are run and evaluated, the result of the SUT tests are returned. Also responsible for testing the CI implementation of this testing phase.

Fredrik: Handling the SUT testing phase. Tests defined in the SUT are run and evaluated, the result of the SUT tests are returned. Also responsible for testing the CI implementation of this testing phase.

### CI test repo

https://github.com/jaokob/testarrr
