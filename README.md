# Java Infrastructure Endpoints

This library deliveries a simple way to give your spring-boot app two restful endpoints for obtain data about application's info and health routes.

# Routes

## Application Info

### Response Example

#### Body
```json
{
  "applicationName": "my-application",
  "createdBy": "My Build Tool vX.Y.Z",
  "version": "Major.minor.release",
  "buildNumber": "20180301-25",
  "buildJdk": "1.8",
  "springBootVersion": "1.5.9.RELEASE"
}
```

#### Status
Application info always return 200 Ok.

### Objective
When called, this route should return information about the application's package construction.

### Response body explained
The response object have this following fields:


- *applicationName*: your application name (replacing blank spaces for dashes, accented characters for unaccented characters and omitting special charactes).
- *createdBy*: name and version of your build tool. If no one are used, you can specify the CI server name and version. 
- *version*: your application version, may be '{major}.{minor}.{release}' pattern or a git hash from repository.
- *buildNumber*: the date and the CI build number where itś possible to find the artifacts of this deployment. Preferable that it be in the following pattern: 'yyyyMMdd-{CI-BUILD_NUMBER}'. 

Pro Tip: You can configure your gradle (or maven) to configure your MANIFEST.MF to contains those keys above.

## Application Health

### Response Example

#### Body
```json
{
  "status": "UP",
  "message": "All dependencies are up",
  "dependencies": [
    {
      "name": "My Database",
      "isCritical": true,
      "status": "UP"
    },
    {
      "name": "My 1st Http Dependency",
      "isCritical": true,
      "status": "UP"
    },
    {
      "name": "My 2nd Http Dependency",
      "isCritical": false,
      "status": "UP"
    }
  ]
}
```

#### Status
Application health returns 200 for UP or PARTIAL statuses and 503 for the FAIL status.

### Objective
When called, this route should ping all the dependencies you've declared in your application: databases, filesystem directory, http dependencies, a queue, a topic, etc. Basically, these pings should garantee that application connections to dependencies are established or, at least, establishables.

### Response body explained

The response object have this following fields:

- *status*: The overall health of your application. It's an enumeration with _UP_, _PARTIAL_ or _FAIL_ as possible values
- *message*: An explanation about the status field
- *dependencies*: Dependencies declared in your application. Each dependency is a complex object that has in its structure the following fields:
    - *name*: The declared dependency name
    - *is_critical*: dependency's criticity. It should be determined by its impact when out of service: if your application gets only partially impacted, it should be _false_, it should be _true_ otherwise
    - *status*: the health of your application connectivity with this dependency. It's an enumeration with _UP_ or _FAIL_ as possible values.