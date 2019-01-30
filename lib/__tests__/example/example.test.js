/**
 * Here is a simple test written with jest
 */

const { test } = require('../../services/example.js') //We need to fetch the function we want to test from the relevant class.
// the function test is 'destructed' out from example.js, all of this is of course to be removed later on.


describe('test totp code generation', () => { //Describe the test
    it('Test that function test works as intended, i.e. correctly return a+b', () => { //Describe what you are testing
      const ans = test(5,5);
      expect(ans).toEqual(10); //works like assertEquals in JUnit.
    })
})
