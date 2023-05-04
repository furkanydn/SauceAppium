@login-feature
  Feature: Login Scenarios
    #@test

  Scenario: Login with invalid mail or password
    When Enter mail as "<mail>"
    And Enter password as "<password>"
    And Click the login button
    Then Login should fail with an error "<error>"

