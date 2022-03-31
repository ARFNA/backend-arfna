# Input Payloads for Apis

## Testing Server Response(`/dummy`)
```json
{
  "version": "V1",
  "inputMessage": "Hi, this is my message"
}
```
## Mutating Subscribers (`/msubscriber`)
**Adding a new subscriber**
```json
{
    "version": "V1",
    "mutation": "REGISTER",
    "subscriber": {
        "name": "Roshnee Sharma",
        "emailAddress": "myrealemail@gmail.com"
    }
}
```
**Adding a password to existing subscriber**
```json
{
    "version": "V1",
    "mutation": "ADD_PASSWORD",
    "subscriber": {
        "emailAddress": "myrealemail@gmail.com",
        "password": "MyPassword123$"		
    }
}
```
**Logging in a user who has a password**
```json
{
    "version": "V1",
    "mutation": "LOGIN",
    "subscriber": {
        "emailAddress": "myrealemail@gmail.com",
        "password": "MyPassword123$"		
    }
}
```
**Registering a new subscriber with a password**
```json
{
    "version": "V1",
    "mutation": "ADD_SUBSCRIBER_WITH_PASSWORD",
    "subscriber": {
    "name": "Not Roshnee Sharma",
        "emailAddress": "notmyrealemail@gmail.com",
        "password": "NotMyPassword123$"		
    }
}
```

## Mutating the Blog Post Table (`/mpost`)
This API cannot be used unless a valid cookie has been transferred using the LOGIN API from the mutate subscriber API.

**Getting all posts that subscriber has authored**
```json
{
    "version": "V1",
    "mutation": "GET_FOR_SUBSCRIBER"
}
```
**Getting existing post given an ID**
```json
{
    "version": "V1",
    "mutation": "GET_EXISTING_POST", 
    "post": {
        "id": 1
    }
}
```
**Saving a new post**
```json
{
    "version": "V1",
    "mutation": "SAVE",
    "post": {
        "title": "My brand new post",
        "markdown": "It's not quite done, but I wanna"
    }
}
```
_If this was an existing post, include the ID of the post using the "id" key_

**Submitting a post**
```json
{
    "version": "V1",
    "mutation": "SUBMIT",
    "post": {
        "id": 5,
        "title": "My brand new post",
        "markdown": "It's not quite done, but I wanna finish it soon. Now I finally think it's ready"
    }
}
```
_If the post did not exist before, just don't submit the id key_

**Accepting a submitted post**

_In order to accept a post, you must have the "maint" role_
```json
{
    "version": "V1",
    "mutation": "ACCEPT",
    "post": {
        "id": 6
    }
}
```

**Publishing an accepted post**

_In order to publish a post, you must have the "admin" role_

```json
{
    "version": "V1",
    "mutation": "PUBLISH",
    "post": {
        "id": 6
    }
}
```

## Getting Blog Posts (`/gpost`)
This API has utilities for getting blog posts that do not require a cookie.

**Getting all published posts**
```json
{
    "version": "V1",
    "requestType": "GET_ALL_PUBLISHED"
}
```

## Validation and Error Codes
Every response is sent back with the following format
```json
{
  "status": {
    "code": 200,
    "message": "OK"
  },
  "response": {
    "originalMessage": "Hi, this is my message",
    "messageInPigLatin": "i,Hay isthay isay my essagemay"
  }
}
```
In the case of a validation failure, the response will contain a json list of messages under the key "messages". Each message will be annotated with its own code. These codes can be looked at in `EValidationMessage.java`.

Here is an example of a validation message
```json
{
  "status": {
    "code": 200,
    "message": "OK"
  },
  "response": {
    "messages": [
      {
        "code": 2,
        "message": "The subscriber has an email registered, but no password"
      }
    ]
  }
}
```