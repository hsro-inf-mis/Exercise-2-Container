# Exercise 2: Container

As already explained, the best way to create a new Docker container is to implement a Dockerfile.
The given project is a simple Payara Micro application to store and query todo items.
Your mission should you decide to accept it is:

1. Pull the PostgreSQL Docker image (`postgres:9-alpine` it might also work with `postgres:10-alpine` but I haven't tested it yet). Have a look at the debug scripts to setup the database correctly (credentials, database name and so on). If you don't want to setup the container in the terminal have a look at Kitematic.
2. Have a look at the source code if you aren't familiar with Payara (Micro) already.
3. Try to start the application by running one of the given debug scripts:
    * debug.sh
    * debug.ps1
    
    _Keep in mind that you might have to adopt your configured database settings!_
4. Complete the given empty Dockerfile to be able to create a new local Docker image (and obviously build the image - just being able to do it isn't very helpful at this time)
5. Create a `docker-compose.yml` to wire up the given application with a PostgreSQL container (contact me if you can't make any progress with the Dockerfile for the application)