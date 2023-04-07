@login-feature
  Feature: Login Scenarios
    #@test

  Scenario Outline: Login with invalid user name
    When Enter username as "<username>"
    And Enter password as "<password>"
    And Click the login button
    Then Login should fail with an error "<error>"

    Examples:
      | username | password | error                                                      |
      | invalidU | failPass | Provided credentials do not match any user in this service |