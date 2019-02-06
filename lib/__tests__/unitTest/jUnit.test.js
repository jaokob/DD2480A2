/**
 * Tests the functionality of compile.js.
 */

const { unitTest } = require('../../services/jUnit.js')

describe('test code compilation', () => { // Describe the test
  it('testing pesting', () => {
    const result = unitTest('localTesting/', 'passing')
    expect(result.build_success).toEqual(true)
  })
})

