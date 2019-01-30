# DD2480A2

## TODO

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