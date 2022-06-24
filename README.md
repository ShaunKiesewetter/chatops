# Overview
https://miro.com/app/board/uXjVOr4_nnA=/?share_link_id=77525196605
Password: hackweek

# Video:
https://drive.google.com/file/d/1GlLLrJc-P-IWaB8p4Nw64NF5nlyT0WA_/view?usp=sharing


# chatops
Technologies
1. Axon event sourcing and Sagas
2. AWS SQS
3. AWS SNS
4. AWS Chatbot
5. Slack API

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


#Note
You will need an AWS account to run this. I have used a personal account and have since deactivated access to it.
The ClouldFormationStack is a best attempt but some details maybe missing. 

