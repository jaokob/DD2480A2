# DD2480A2

## Java Continuous Integration server

This is a bare bones continuous cntegration (CI) server written in nodeJS.
Currently it only supports Java and testing with JUnit 5.

## Usage

To use this CI server, clone the repo and start the server (for information on how to build and project and the relevant dependencies needed, see below). 

This CI server assumes the following file structure:
* The code needs to be under folder `src`
* The test class needs to be under the folder `tests`. 

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
To install the npm dependencies needed, type `yarn install` in terminal in project folder. 

To run, start ngrok locally in a seperate terminal window. Use `ngrok http 3000` to start it.
Then in project folder type `yarn run watch` to start the server, `yarn run build` to build project 

### Tests
We will use jest to implement unit testing.
Type `yarn run test` to execute tests.

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

| Verb | Route  | Description         |
|------|-------------|---------------------|
| get  | `/health`    | Health check route. |
| post | `/events` | Github webhook endpoint.|
| get  | `/history` | CI Build history is displayed here. |
| get  | `/log` | Shows the CI log of commit SHA that gets supplied via parameter `sha`.  |

### Response codes

| Status  Code |                                 Description                       |
|--------------|-------------------------------------------------------------------|
| 201          |                       Resource created                            |
| 404          |            Not Found - The requested resource could not be found. |
| 500          | Internal Server error                                             |           


### Statement of contribution

Jakob: Statuses, build history, integration between clone and compile, API documentation & README

Jenny: Compiling code and testing of this functionality

Philip: Cloning from git, logging build information.