@webtest
Business Need: Registration Functionality


  Background:
    Given I am on the registration page

  Rule: User Registration Requirements

    Scenario Outline: Successful registration
      When I fill in "<FirstName>", "<LastName>", "<Email>", "<ConfirmEmail>", "<Password>", "<PasswordConfirm>", and "<DateOfBirth>"
      And I accept the terms and conditions "<AcceptTerms>"
      And I confirm age responsibility "<AgeAccept>"
      And I agree to the Code of Ethics and Conduct "<AgreeToCodeOfEthics>"
      And I click "CONFIRM AND JOIN"
      Then I should see the registration confirmation "<ExpectedResult>"


      Examples:
        | FirstName | LastName | Email | ConfirmEmail | Password  | PasswordConfirm | DateOfBirth | AcceptTerms | AgeAccept | AgreeToCodeOfEthics | ExpectedResult                                            |
        | a         | z        | a     | a            | abc123sa  | abc123sa        | 01/03/1971  | true        | true      | true                | THANK YOU FOR CREATING AN ACCOUNT WITH BASKETBALL ENGLAND |
        | z         | z        | b     | b            | password1 | password1       | 12/31/1990  | true        | true      | true                | THANK YOU FOR CREATING AN ACCOUNT WITH BASKETBALL ENGLAND |

    Scenario Outline: Registration fails due to missing or invalid details
      When I fill in "<FirstName>", "<LastName>", "<Email>", "<ConfirmEmail>", "<Password>", "<PasswordConfirm>", and "<DateOfBirth>"
      And I accept the terms and conditions "<AcceptTerms>"
      And I confirm age responsibility "<AgeAccept>"
      And I agree to the Code of Ethics and Conduct "<AgreeToCodeOfEthics>"
      And I click "CONFIRM AND JOIN"
      Then the "<ExpectedResult>" field should be highlighted as required

      Examples:
        | FirstName | LastName | Email       | ConfirmEmail | Password       | PasswordConfirm | DateOfBirth | AcceptTerms | AgeAccept | AgreeToCodeOfEthics | ExpectedResult                       |
        |           | b        | a           | a            | abc123s        | abc123s         | 01/03/1970  | true        | true      | true                | First Name is required               |
        | AAA       |          | a           | a            | abc123s        | abc123s         | 01/03/1983  | true        | true      | true                | Last Name is required                |
        | a         | b        |             | a            | password1      | password1       | 12/31/1990  | true        | true      | true                | Email Address is required            |
        | dd        | e        | a           | a            |                | password1       | 12/31/1990  | true        | true      | true                | Password is required                 |
        | ff        | bg       | a           | a            | Agentpassword1 |                 | 12/31/1990  | true        | true      | true                | Confirm Password is required         |
        | bb        | cc       | a@gmail.se  |              | password1      | password1       | 12/31/1987  | true        | true      | true                | Confirm Email Address does not match |
        | John      | Doe      | va@gmail.se | va@gmail.se  | abc123         | xyz789          | 01/01/2000  | true        | true      | true                | PASSWORD DID NOT MATCH               |






    Scenario Outline: Registration fails when terms and conditions are not accepted
      When I fill in "<FirstName>", "<LastName>", "<Email>", "<ConfirmEmail>", "<Password>", "<PasswordConfirm>", and "<DateOfBirth>"
      And I accept the terms and conditions "<AcceptTerms>"
      And I confirm age responsibility "<AgeAccept>"
      And I agree to the Code of Ethics and Conduct "<AgreeToCodeOfEthics>"
      And I click "CONFIRM AND JOIN"
      Then I should see "<ExpectedResult>"

      Examples:
        | FirstName | LastName | Email | ConfirmEmail | Password  | PasswordConfirm | DateOfBirth | AcceptTerms | AgeAccept | AgreeToCodeOfEthics | ExpectedResult                                                                 |
        | John      | Doe      | a     | a            | abc123    | abc123          | 01/01/2000  | false       | true      | true                | You must confirm that you have read and accepted our Terms and Conditions      |
        | Jane      | Smith    | a     | a            | password1 | password1       | 02/20/1995  | true        | false     | true                | You must confirm that you are over 18 or a person with parental responsibility |
        | Bob       | Johnson  | a     | a            | test123   | test123         | 03/15/1987  | true        | true      | false               | You must confirm that you have read and accepted our Terms and Conditions      |

