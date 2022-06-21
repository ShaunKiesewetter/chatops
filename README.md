# chatops
Technologies
1. Axon event sourceing and Sagas
2. AWS EventBridge
3. AWS SNS
4. AWS Chatbot

Objective:
1. To be able to be notified of Axon events of interest to a Slack channel via AWS SDK. (No custom slack client side library should be needed)
2. To be able to invoke Axon Commands via the slack channel.

Suitability:
Event driven architectures

Value:
To be able to diagnose and remediate long-running  processors. In the case of a banking service there may be a condition
where the banking partner provides a service that becomes unstable. We should be notified of this as the workflow discovers it. 
After contacting service providers and verifying service availability our workflow can be resumed by running a command from slack.
There may also be edge cases, where a decision should be made in terms of progressing with path A or path B. 


References:
Configure an eventbridge rule to forward events to slack
https://docs.aws.amazon.com/chatbot/latest/adminguide/create-eventbridge-rule.html

