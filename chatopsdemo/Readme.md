AWS Chatbot commands:

Create a new account:
@aws lambda invoke --payload {"accountId":"4", "accountName":"Test Account 4", "accountDescription":"My account Description 4"} --function-name Create-Account --chatbot-replace-curly-quotes enable --region us-east-1