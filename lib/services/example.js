/** 
 * Simple function we are testing.
 */
function test(a,b,){
    return a+b;
}

/** 
 * To be able to access function from other files, we need to export it.
 */
module.exports = {
    test
}