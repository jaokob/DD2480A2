/**
 * Tests the functionality of jUnit.js.
 */

const { unitTest } = require('../../services/jUnit.js')

describe('Testing the code that runs the test code', () => { // Describe the test
  it('Test that return all tests as passed; should pass', () => {
    const result = unitTest('test_data/', 'simple_pass_test')
    expect(result.test_success).toEqual(true)
  })
  it('Test that returns one test as failed; should not pass', () => {
    const result = unitTest('test_data/', 'simple_fail_test')
    expect(result.test_success).toEqual(false)
  })
})

