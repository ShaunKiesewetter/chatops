import json
import urllib3
import logging

logger = logging.getLogger()
logger.setLevel(logging.INFO)

def lambda_handler(event, context):


    #message_str = event['Message']
    #logger.info("Message string is {}".format(message_str))


    http = urllib3.PoolManager()


    message_body = json.loads(event['Records'][0]['body'])
    data = {"text": "{}".format(message_body['Message'])}


    r = http.request("POST",
                     "https://hooks.slack.com/services/yourWebhookhere",
                     body = json.dumps(data),
                     headers = {"Content-Type": "application/json"})

    # TODO implement
    return {
        'statusCode': 200,
        'body': json.dumps('Hello from Lambda!')
    }
