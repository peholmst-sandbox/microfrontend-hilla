# No Portal

In this approach, there is no dedicated portal application. Every application is self-contained, but brings in a shared
application shell library that makes them look the same and makes it possible to navigate between applications in
different browser tabs.

SSO is handled by Keycloak, no logging out has been implemented yet. For the time being, you will end up with multiple
sessions when using this application:

- The Keycloak session
- The gateway (proxy) session
- An application session for every frontend application you access

It should be possible to make the frontend applications stateless since they are all using React but this has not
yet been attempted.

## Running the demo

Please note that the edges are *very rough*!

1. Edit your HOSTS file and make sure the hostname `host.docker.internal` points to 127.0.0.1.
2. Run the `build.sh` script to build all the Docker images.
3. Start the entire thing by running `docker compose up`.
4. Browse to http://host.docker.internal:8080/ to login. You may sometimes end up with Spring complaining about bad
   credentials, ignore it for now.
5. You can log into Keycloak using the username `user` and password `password123!`.
6. Browse to http://host.docker.internal:8080/frontend-a/ or http://host.docker.internal:8080/frontend-b/ (won't work
   without the trailing slashes)
