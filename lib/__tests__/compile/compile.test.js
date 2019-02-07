/**
 * Tests the functionality of compile.js.
 */

const { build } = require('../../services/compile.js')

describe('test code compilation', () => { // Describe the test
  it('Test that build successfully compiles a correct one class program, i.e. correctly return true', () => {
    const result = build('test_data/', 'simple_pass_build')
    expect(result.build_success).toEqual(true)
  })
  it('Test that build unsuccessfully compiles a incorrect one class program, i.e. correctly return false', () => {
    const result = build('test_data/', 'simple_fail_build')
    expect(result.build_success).toEqual(false)
  })
  it('Test that build fails when given an incorrect path, i.e. correctly return false', () => {
    const result = build('test_data/', 'missing')
    expect(result.build_success).toEqual(false)
  })
  it('Test that build successfully compiles a correct multi-class, non-executable program, i.e. correctly return true', () => {
    const result = build('test_data/', 'multi_file_pass_build')
    expect(result.build_success).toEqual(true)
  })
})
