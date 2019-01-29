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