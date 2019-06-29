This is the custom selenium library version 3.3.1

The standard selenium library had to be modified since some selenium commands do not work in when test cases are executed inside evosuite.
For example the `sendKeys` method in selenium throws an error (at least in the standard selenium version 3.3.1) and the test generation fails.
