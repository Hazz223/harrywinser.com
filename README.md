# harrywinser.com
[![Build Status](https://travis-ci.org/Hazz223/harrywinser.com.svg?branch=master)](https://travis-ci.org/Hazz223/harrywinser.com)

My personal website; rebuilt and fully opensource!

Started to get super annoyed when using VisualStudio.com to host the git repo, so I'm restarting from here.
Also, now i don't have a reliance on the database directly, it's much easier to share the code.

This site will be hosting my blog posts and reviews. Not technical stuff. That can be found at
tech.harrywinser.com, though i will link through from here.

Happy reading!

## Technology Stack
- SpringBoot as Server framework
- Mustache as the templating engine
- Knockout.js as the frontend framework. Soon to be more once i get around to learning stuff.
- Data comes from api.harrywinser.com. No access to the Database, because it doesn't need to!

## Plan
- Recreate harrywinser.com, and use the api available from api.harrywinser.com
- Build a better looking frontend. Use more advanced tech (this is a future goal!)

## Build / running
- Ensure you have Java 8 installed
- Go to the cloned directory, and type in `./gradlew bootRepackage`. This will create a deployable Jar in folder `build/libs`.
- use `java -jar <insert jar name here>`. This will boot the app, at the port specified in `src/main/resources/application.properties`

## Docker and Raspberry Pi
You can now boot the application within a Docker container. Just grab the built jar, the application.properties, and put them in the Docker-pi folder. Then use the standard docker commands to build it!
At the moment, this is designed for Raspberry Pi's, and use the Java images found [here](https://github.com/Hazz223/pi-docker-files).

