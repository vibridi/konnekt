# konnekt

Small collection of quick-and-dirty transport clients to avoid rewriting the same boilerplate code every time. 

Available connectors:
- TCP/IP
- MLLP
- HTTP

The project was born around the need to have MLLP client and server facilities for quick testing/prototyping.
MLLP is the (probably) most used transport protocol in healthcare settings. It especially conveys HL7 messages.

## More about MLLP

MLLP is a protocol built on top of TCP/IP that uses two lower-ASCII control characters to signal the beginning and end of the message. An MLLP envelope looks like:

```
<VT>payload<FS><CR>
```

Whereas `<VT>` is ASCII 0x0B, `<FS>` is ASCII 0x1C and `<CR>` is the carriage return, or '\r'.

## More about usage

The clients make use of builders to suggest the proper parameter setting and object constructing order. The methods are chainable, so that you can do stuff like:

```
String response =
HTTP.build("http://127.0.0.1")
  .setConnection("keep-alive")
  .setContentType("application/json")
  .post(someJSONPayload);
```

```
String response =
MLLP.build("http://127.0.0.1", 8090)
  .send(someHL7Payload);
```

## Room for improvement

Plenty. But this is not a core library so it is maintained with moderate zeal.


