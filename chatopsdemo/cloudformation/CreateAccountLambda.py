import json
import boto3

def lambda_handler(event, context):

    print(event)

    client = boto3.client("sns")

    response = client.publish(
        TopicArn="arn:aws:sns:us-east-1:354732697202:banking-sqs-topic",

        Message=json.dumps(event)
    )


    # TODO implement
    return {
        'statusCode': 200,
        'body': json.dumps(response)
    }