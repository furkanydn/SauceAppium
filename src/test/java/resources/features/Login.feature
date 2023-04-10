@login-feature
  Feature: Login Scenarios
    #@test

  Scenario Outline: Login with invalid mail or password
    When Enter mail as "<mail>"
    And Enter password as "<password>"
    And Click the login button
    Then Login should fail with an error "<error>"

    Examples:
      | mail     | password | error                                                      |
      | invalidU | failPass | Provided credentials do not match any user in this service |

    Scenario Outline: Account locked out with alternative mail address
      When Enter mail as "<mail>"
      And Enter password as "<password>"
      And Click the login button
      Then The Account locked out notification will appear at the bottom.
      Examples:
        | mail              | password |
        | alice@example.com | 10203040 |

    Scenario Outline: Login with valid mail and password.
    When Enter mail as "<mail>"
    And Enter password as "<password>"
    And Click the login button
    Then  If the given mail and password are correct successful login is possible

    Examples:
      | mail            | password |
      | bob@example.com | 10203040 |

