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