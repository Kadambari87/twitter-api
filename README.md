# twitter-api

This project contains source code and supporting files for a serverless application that you can deploy with the SAM CLI. It includes the following files and folders.

- TwitterAppFunction/src/main - Code for the application's Lambda function.
- events - Invocation events that you can use to invoke the function.
- TwitterAppFunction/src/test - Unit tests for the application code. 
- template.yaml - A template that defines the application's AWS resources.

The application uses several AWS resources, including Lambda functions and an API Gateway API. These resources are defined in the `template.yaml` file in this project.

## Deploy the sample application

The Serverless Application Model Command Line Interface (SAM CLI) is an extension of the AWS CLI that adds functionality for building and testing Lambda applications. It uses Docker to run your functions in an Amazon Linux environment that matches Lambda. It can also emulate your application's build environment and API.

To use the SAM CLI, you need the following tools.

* SAM CLI - [Install the SAM CLI](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/serverless-sam-cli-install.html)
* Java8 - [Install the Java SE Development Kit 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* Docker - [Install Docker community edition](https://hub.docker.com/search/?type=edition&offering=community)

To build and deploy your application for the first time, run the following in your shell:

```bash
sam build
sam deploy --guided
```

The first command will build the source of your application. The second command will package and deploy your application to AWS, with a series of prompts:

* **Stack Name**: The name of the stack to deploy to CloudFormation. This should be unique to your account and region, and a good starting point would be something matching your project name.
* **AWS Region**: The AWS region you want to deploy your app to.
* **Confirm changes before deploy**: If set to yes, any change sets will be shown to you before execution for manual review. If set to no, the AWS SAM CLI will automatically deploy application changes.
* **Allow SAM CLI IAM role creation**: Many AWS SAM templates, including this example, create AWS IAM roles required for the AWS Lambda function(s) included to access AWS services. By default, these are scoped down to minimum required permissions. To deploy an AWS CloudFormation stack which creates or modifies IAM roles, the `CAPABILITY_IAM` value for `capabilities` must be provided. If permission isn't provided through this prompt, to deploy this example you must explicitly pass `--capabilities CAPABILITY_IAM` to the `sam deploy` command.
* **Save arguments to samconfig.toml**: If set to yes, your choices will be saved to a configuration file inside the project, so that in the future you can just re-run `sam deploy` without parameters to deploy changes to your application.

You can find your API Gateway Endpoint URL in the output values displayed after deployment.

## Use the SAM CLI to build and test locally

Build your application with the `sam build` command.

```bash
twitter-api$ sam build
```

The SAM CLI installs dependencies defined in `TwitterAppFunction/build.gradle`, creates a deployment package, and saves it in the `.aws-sam/build` folder.

```

The SAM CLI can also emulate your application's API. Use the `sam local start-api` to run the API locally on port 3000.

```bash
twitter-api$ sam local start-api
twitter-api$ curl http://localhost:3000/
```

## Unit tests

Tests are defined in the `TwitterAppFunction/src/test` folder in this project.

```bash
twitter-api$ cd TwitterAppFunction
TwitterAppFunction$ gradle test
```

## Cleanup

You can run the following to delete the resources from AWS:

```bash
aws cloudformation delete-stack --stack-name twitter-api
```

## Resources

There are two tables created with this project - 
- twitter_users : which stores 5 users at the moment
- twitter_feeds : which stores the tweets posted by the users

## API Links

Use the below links to access the backend api's using POSTMAN - 

Base URL - https://rbzx7rki00.execute-api.eu-west-1.amazonaws.com/Prod/

| Link        | Request Type           | Definition  |
| ------------- |:-------------:| -----:|
| /tweets/     | GET | This will list all tweets of all users in the application |
| /tweets/{userId}/    | GET      |   This will list all tweets of a particular user in the application |
| /tweets/create/{userId}/ | POST     |    This will create a tweet for a user |
| /twitter/login/{userId}/ | GET     |    This will get user details of a valid user |
| /twitter/login/create/{userId}/{firstName}/{lastName}/{email}/{password}/ | POST     |    This will create a tweet for a user |

## IMROVEMENTS /TODOs

Below are few improvements and todo's that can be made in order to make this application more secure and user-friendly  -
- Create a domain name for Api Gateway using Route 53
- Enable server side CORS configuration on the api in order to be used by front end client  
- Store passwords in dynamodb with encryption
- Add authentication to created api's

See the [AWS SAM developer guide](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/what-is-sam.html) for an introduction to SAM specification, the SAM CLI, and serverless application concepts.
