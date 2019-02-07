/**
 * Tests the functionality of compile.js.
 */

const { unitTest } = require('../../services/jUnit.js')

describe('test code compilation', () => { // Describe the test
  it('Test that return all tests as passed; should pass', () => {
    const result = unitTest('test_data/', 'simple_pass_test')
    expect(result.test_success).toEqual(true)
  })
  it('Test that returns one test as failed; should not pass', () => {
    const result = unitTest('test_data/', 'simple_fail_test')
    expect(result.test_success).toEqual(false)
  })
})

